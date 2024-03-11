package com.developer.backend.apirest.dto;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Data
public class RegionDto implements Serializable {

	private Long id;
	private String nombre;

	private static final long serialVersionUID = 1L;

}
