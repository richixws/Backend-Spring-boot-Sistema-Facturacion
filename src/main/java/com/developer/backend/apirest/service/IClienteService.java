package com.developer.backend.apirest.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.developer.backend.apirest.model.entity.Cliente;
import com.developer.backend.apirest.model.entity.Factura;
import com.developer.backend.apirest.model.entity.Producto;
import com.developer.backend.apirest.model.entity.Region;

public interface IClienteService  {

	
	public List<Cliente> findAll();
	
	public Page<Cliente> findAll(Pageable pageable);
	
	public Cliente findById(Long id);
	
	public Cliente save(Cliente cliente);
	
	public void delete(Long id);

	public List<Region> findAllRegiones();
	
	public Factura findFacturaById(Long id);
	
	public Factura  saveFactura(Factura factura);
	
	public void deleteFacturaById(Long id);
	
	public List<Producto> findProductoByNombre(Long term);
	
	
	
	
	
}
