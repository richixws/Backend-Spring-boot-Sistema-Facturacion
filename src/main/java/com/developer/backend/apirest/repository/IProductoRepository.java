package com.developer.backend.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.developer.backend.apirest.model.entity.Producto;

public interface IProductoRepository extends JpaRepository<Producto, Long>{
	
	@Query("select p from Producto p where p.nombre like %?1%")
	public List<Producto> findByNombre(Long term);
	
	public List<Producto> findByNombreContainingIgnoreCase(Long term);
	
	public List<Producto> findByNombreStartingWithIgnoreCase(Long term);

}
