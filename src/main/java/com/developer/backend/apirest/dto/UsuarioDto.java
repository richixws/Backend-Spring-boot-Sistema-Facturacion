package com.developer.backend.apirest.dto;

import com.developer.backend.apirest.model.entity.Role;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
public class UsuarioDto implements Serializable {

	
	
	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String password;
	private Boolean enabled;
	private String nombre;
	private String apellido;
	private String email;
	private List<Role> roles;

}
