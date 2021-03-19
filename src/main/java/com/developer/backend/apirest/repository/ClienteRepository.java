package com.developer.backend.apirest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.developer.backend.apirest.model.entity.Cliente;
import com.developer.backend.apirest.model.entity.Region;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	

	@Query("from Region")
	public List<Region> findAllRegiones();
	
}
