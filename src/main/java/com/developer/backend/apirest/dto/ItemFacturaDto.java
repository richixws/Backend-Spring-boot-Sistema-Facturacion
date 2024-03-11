package com.developer.backend.apirest.dto;

import com.developer.backend.apirest.model.entity.Producto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
public class ItemFacturaDto implements Serializable {

	private Long id;
	private Integer cantidad;
	private Producto producto;


	private static final long serialVersionUID = 1L;

}
