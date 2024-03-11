package com.developer.backend.apirest.dto;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class ProductoDto implements Serializable{


	private Long id;
	private String nombre;
	private Double precio;
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	public void prePersist() {
	this.createAt=new Date();
	}

	private static final long serialVersionUID = 1L;
	
}
