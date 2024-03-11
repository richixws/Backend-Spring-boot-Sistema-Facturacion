package com.developer.backend.apirest.dto;

import com.developer.backend.apirest.model.entity.Cliente;
import com.developer.backend.apirest.model.entity.ItemFactura;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacturaDto implements Serializable{

	private Long id;
	private String descripcion;
	private String observacion;

	@Temporal(TemporalType.DATE)
	private Date createAt;

    private Cliente cliente;
	private List<ItemFactura> items;

	public FacturaDto() {

	   this.items=new ArrayList<>();
	}
	
	
	public void prePersist() {
		this.createAt=new Date();
	}

	 public Double getTotal() {
    	 
    	 Double total=0.00;
    	 for (ItemFactura item : items) {
			total +=item.getImporte();
		}
    	 return total;
    	 
     }

	private static final long serialVersionUID = 1L;

}
