package com.developer.backend.apirest.service;

import com.developer.backend.apirest.model.entity.Usuario;

public interface IUsuarioService  {

	public Usuario findByUsername(String username);
	
}
