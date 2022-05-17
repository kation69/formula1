package models.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

import helpers.HelperGlobal;

@Component
public class Equipo {
	
	private int id;
	private String nombre;
	private String logo;
	private String twitter;
	private Date fechaCreacion;
	private int usuarioCreador;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public int getUsuarioCreador() {
		return usuarioCreador;
	}
	public void setUsuarioCreador(int usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
	@Override
	public String toString() {
		return HelperGlobal.convertToJson(this);
	}
	
	
}
