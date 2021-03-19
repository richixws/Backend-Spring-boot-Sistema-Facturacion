package com.developer.backend.apirest.serviceImpl;

import java.util.List;

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
public class ClienteServiceImpl implements IClienteService {

	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private IFacturaRepository facturaRepository;
	
	@Autowired 
	private IProductoRepository productoRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
	
		return (List<Cliente>) clienteRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
	
		return clienteRepository.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)

	public Cliente findById(Long id) {
		
		return clienteRepository.findById(id).orElseThrow(null);
	}

	@Override
	@Transactional
	public Cliente save(Cliente cliente) {
		
		return clienteRepository.save(cliente);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		
		clienteRepository.deleteById(id);
		
	}

	// metodo de listado de todas las regiones 
	@Override
	@Transactional(readOnly = true)
	public List<Region> findAllRegiones() {
	
		return clienteRepository.findAllRegiones();
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		// TODO Auto-generated method stub
		return facturaRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Factura saveFactura(Factura factura) {
		// TODO Auto-generated method stub
		return facturaRepository.save(factura);
	}

	@Override
	@Transactional
	public void deleteFacturaById(Long id) {

		facturaRepository.deleteById(id);
		
	}

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findProductoByNombre(Long term) {
		// TODO Auto-generated method stub
		return productoRepository.findByNombre(term);
	}

	

}
