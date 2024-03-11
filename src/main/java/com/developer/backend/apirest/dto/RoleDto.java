package com.developer.backend.apirest.dto;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class RoleDto implements Serializable {

	private static final long serialVersionUID = 1L;
	

	private Long id;
	private String nombre;


}
