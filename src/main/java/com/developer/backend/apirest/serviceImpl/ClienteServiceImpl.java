package com.developer.backend.apirest.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import com.developer.backend.apirest.config.ModelMappingConfig;
import com.developer.backend.apirest.dto.ClienteDto;
import com.developer.backend.apirest.dto.FacturaDto;
import com.developer.backend.apirest.dto.ProductoDto;
import com.developer.backend.apirest.dto.RegionDto;
import lombok.extern.log4j.Log4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.developer.backend.apirest.model.entity.Cliente;
import com.developer.backend.apirest.model.entity.Factura;
import com.developer.backend.apirest.model.entity.Producto;
import com.developer.backend.apirest.model.entity.Region;
import com.developer.backend.apirest.repository.ClienteRepository;
import com.developer.backend.apirest.repository.IFacturaRepository;
import com.developer.backend.apirest.repository.IProductoRepository;
import com.developer.backend.apirest.service.IClienteService;

@Service
@Log4j
public class ClienteServiceImpl implements IClienteService {

	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private IFacturaRepository facturaRepository;
	
	@Autowired 
	private IProductoRepository productoRepository;

	@Autowired
	private ModelMapper modelMapping;
	
	@Override
	@Transactional(readOnly = true)
	public List<ClienteDto> findAll() {

		List<Cliente> clientes=clienteRepository.findAll();
		return  clientes.stream()
				.map(cliente -> modelMapping.map(cliente,ClienteDto.class))
				.collect(Collectors.toList());
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<ClienteDto> findAll(Pageable pageable) {

		Page<Cliente> pageClie=clienteRepository.findAll(pageable);
		Page<ClienteDto> clienteDtoPage=pageClie
				.map(cliente -> modelMapping.map(cliente,ClienteDto.class));

	   return clienteDtoPage;
	}

	@Override
	@Transactional(readOnly = true)

	public ClienteDto findById(Long id) {

		Cliente cliente=clienteRepository.findById(id).orElseThrow(null);
		ClienteDto clienteDto=modelMapping.map(cliente,ClienteDto.class);
		return clienteDto;
	}

	@Override
	@Transactional
	public ClienteDto save(ClienteDto clientedto) {

		Cliente client=modelMapping.map(clientedto,Cliente.class);
		Cliente clientesave=clienteRepository.save(client);
		ClienteDto clienteDto=modelMapping.map(clientesave,ClienteDto.class);
		return clienteDto;

	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		clienteRepository.deleteById(id);
		
	}

	// metodo de listado de todas las regiones 
	@Override
	@Transactional(readOnly = true)
	public List<RegionDto> findAllRegiones() {

		List<Region> listRegiones=clienteRepository.findAllRegiones();
		return listRegiones.stream().map(region -> modelMapping.map(region,RegionDto.class))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public FacturaDto findFacturaById(Long id) {
		// TODO Auto-generated method stub
		Factura factura= facturaRepository.findById(id).orElse(null);
		FacturaDto facturaDto=modelMapping.map(factura,FacturaDto.class);
		return facturaDto;
	}

	@Override
	@Transactional
	public FacturaDto saveFactura(FacturaDto factura) {
		// TODO Auto-generated method stub
		Factura facturanew=modelMapping.map(factura,Factura.class);
		Factura facturasave=facturaRepository.save(facturanew);
	    FacturaDto facturaDto=modelMapping.map(facturanew,FacturaDto.class);
		return facturaDto;
	}

	@Override
	@Transactional
	public void deleteFacturaById(Long id) {

		facturaRepository.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<ProductoDto> findProductoByNombre(Long term) {
		// TODO Auto-generated method stub

		List<Producto> listProducto=productoRepository.findByNombre(term);
		 return listProducto.stream().map(producto -> modelMapping.map(producto,ProductoDto.class))
				.collect(Collectors.toList());

	}

	

}
