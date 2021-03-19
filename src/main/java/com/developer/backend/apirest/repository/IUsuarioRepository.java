package com.developer.backend.apirest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.developer.backend.apirest.model.entity.Usuario;

public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	
	
	public Usuario findByUsername(String username);
	
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario findByUsername2(String userName);
	
	
	

}
