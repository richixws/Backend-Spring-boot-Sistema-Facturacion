package com.developer.backend.apirest.dto;

import com.developer.backend.apirest.model.entity.Factura;
import com.developer.backend.apirest.model.entity.Region;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ClienteDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private Long id;
	private String nombre;
	@NotEmpty(message = "no puede estar vacio")
	private String apellido;
	@NotEmpty(message = "no puede estar vacio")
	@Email(message = "no es una direccion de correo bien formada")
	private String email;
	@NotNull(message="no puede estar vacio")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	private String foto;
	
	@NotNull(message = "la region no puede ser vacia")
	private Region region;
	private List<Factura> facturas;


}
