package com.developer.backend.apirest.service;

import java.util.List;

import com.developer.backend.apirest.dto.ClienteDto;
import com.developer.backend.apirest.dto.FacturaDto;
import com.developer.backend.apirest.dto.ProductoDto;
import com.developer.backend.apirest.dto.RegionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.developer.backend.apirest.model.entity.Cliente;
import com.developer.backend.apirest.model.entity.Factura;
import com.developer.backend.apirest.model.entity.Producto;
import com.developer.backend.apirest.model.entity.Region;

public interface IClienteService  {

	
	public List<ClienteDto> findAll();
	
	public Page<ClienteDto> findAll(Pageable pageable);
	
	public ClienteDto findById(Long id);
	
	public ClienteDto save(ClienteDto cliente);
	
	public void delete(Long id);

	public List<RegionDto> findAllRegiones();
	
	public FacturaDto findFacturaById(Long id);
	
	public FacturaDto saveFactura(FacturaDto factura);
	
	public void deleteFacturaById(Long id);
	
	public List<ProductoDto> findProductoByNombre(Long term);
	
	
	
	
	
}
