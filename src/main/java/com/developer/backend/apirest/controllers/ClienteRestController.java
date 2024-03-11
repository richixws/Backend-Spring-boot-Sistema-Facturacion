package com.developer.backend.apirest.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.developer.backend.apirest.dto.ClienteDto;
import com.developer.backend.apirest.dto.RegionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.developer.backend.apirest.model.entity.Cliente;
import com.developer.backend.apirest.model.entity.Region;
import com.developer.backend.apirest.serviceImpl.ClienteServiceImpl;
import com.developer.backend.apirest.serviceImpl.UploadFileServiceImpl;


@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	
	@Autowired
	private ClienteServiceImpl clienteserviceimpl;
	
	@Autowired
	private UploadFileServiceImpl uploadFileServiceImpl;
	
	
	@GetMapping("/clientes")
	public List<ClienteDto> index(){
		
		return clienteserviceimpl.findAll();
		
	}
	@GetMapping("/clientes/page/{page}")
	public Page<ClienteDto> index(@PathVariable Integer page){
		
		Pageable pageable=PageRequest.of(page, 4);
		
		return clienteserviceimpl.findAll(pageable);
		
	}
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> getClientById(@PathVariable Long id){
		
	     ClienteDto cliente=null;
	     
	     Map<String, Object> response=new HashMap<>();
	     
	     try {
	    	 
			cliente=clienteserviceimpl.findById(id);
			
		} catch (DataAccessException e) {

		     response.put("mensaje", "error al realizar la consulta en la base de datos");
		     response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
		     return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		     
		}
	     
	     if (cliente == null) {
			
	    	 response.put("mensaje", "el cliente Id:".concat(id.toString().concat("no existe en la base de datos")));
	    	 return new ResponseEntity< Map<String, Object>>(response,HttpStatus.NOT_FOUND);
	    	 
		}
		
		return new ResponseEntity<ClienteDto>(cliente,HttpStatus.OK);
		
	}
	@Secured({"ROLE_ADMIN"})
	@PostMapping("/clientes")
	public ResponseEntity<?> saveClient(@Valid @RequestBody ClienteDto cliente ,BindingResult result) {
		
		ClienteDto clienteNew=null;
		
		Map<String, Object> response=new HashMap<>();
		
		if (result.hasErrors()) {

			List<String> errors=result.getFieldErrors()
					                  .stream().map(err-> "el campo '"+err.getField()+"'"+err.getDefaultMessage())
					                  .collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		
		try {
			
			clienteNew=clienteserviceimpl.save(cliente);
		} catch (DataAccessException e) {
		
		response.put("mensaje", "Error al insert datos en la base de datos");
		response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
			
		}
		
		response.put("mensaje", "el cliente a sido creado con exito");
		response.put("cliente", clienteNew);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}
	@Secured({"ROLE_ADMIN"})
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> updateClient(@Valid @RequestBody ClienteDto cliente, BindingResult result, @PathVariable Long id) {
		
		ClienteDto clienteActual=clienteserviceimpl.findById(id);
		
		ClienteDto clienteUpload=null;
		
		Map<String, Object> response=new HashMap<>();
		

		if (result.hasErrors()) {
			
			List<String> errors=result.getFieldErrors()
					                  .stream().map(err-> "el campo '"+err.getField()+"'"+err.getDefaultMessage())
					                  .collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		
		
		
		if (clienteActual==null) {
			
			response.put("mensaje","Error: no se pudo editar,el cliente ID".concat(id.toString().concat("no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(HttpStatus.NOT_FOUND);
		}
		try {
			
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setEmail(cliente.getEmail());
		    clienteActual.setCreateAt(cliente.getCreateAt());
			
			clienteUpload=clienteserviceimpl.save(clienteActual);
		    
		} catch (DataAccessException e) {
			
			response.put("mensaje", "Error al actualizar datos en la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "el cliente a sido actualizado con exito");
		response.put("cliente", clienteUpload);
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
		
	}
	@Secured({"ROLE_ADMIN"})
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		
		
		Map<String, Object> response=new HashMap<>();
		try {
			
			ClienteDto cliente=clienteserviceimpl.findById(id);
			
			String nombreFotoAnterior=cliente.getFoto();
			
			uploadFileServiceImpl.eliminar(nombreFotoAnterior);
			
			clienteserviceimpl.delete(id);
			
		} catch (DataAccessException e) {
			
			response.put("mensaje", "Error al eliminar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "el cliente a sido elimando con exito");
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		
	}
	
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@PostMapping("/clientes/upload")
	public ResponseEntity<?> upload(@RequestParam("archivo") MultipartFile archivo, @RequestParam("id") Long id){
		
		Map<String, Object> response= new HashMap<>();
		
		ClienteDto cliente=clienteserviceimpl.findById(id);
		
		if(!archivo.isEmpty()) {
			
			String nombreArchivo=null;
			
			try {
				
			nombreArchivo= uploadFileServiceImpl.copiar(archivo);
				
			} catch (IOException e) {
				
				response.put("mensaje", "error al subir la imagen del cliente");
				response.put("error", 	e.getMessage().concat(":").concat(e.getCause().getMessage()));
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
				
			}
			
			
			String nombreFotoAnterior=cliente.getFoto();
			
			uploadFileServiceImpl.eliminar(nombreFotoAnterior);
			
			
			cliente.setFoto(nombreArchivo);
			clienteserviceimpl.save(cliente);
			
			
			response.put("cliente", cliente);
			response.put("mensaje", "has subido correctamente la imagen "+nombreArchivo);
		}
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
		
	}


	@GetMapping("/uploads/img/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String nombreFoto){
		
	    Resource recurso=null;
	    
	    try {
			recurso = uploadFileServiceImpl.cargar(nombreFoto);
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		
		HttpHeaders cabecera=new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+recurso.getFilename()+"\"");
		
		
		return new ResponseEntity<Resource> (recurso,cabecera,HttpStatus.OK);
		
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("clientes/regiones")
	public List<RegionDto> listarRegiones(){
		return clienteserviceimpl.findAllRegiones();
	}
	
}
