package models.entity;

import java.util.Date;

import org.springframework.stereotype.Component;


public class Consumo {
	public Consumo(String nombre, Double consumo) {
		super();
		this.nombre = nombre;
		this.consumo = consumo;
	}
	private String nombre;
	private Double consumo;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getConsumo() {
		return consumo;
	}
	public void setConsumo(Double consumo) {
		this.consumo = consumo;
	}
	
}