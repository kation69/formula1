package models.entity;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

import com.sun.tools.javac.util.List;

@Component
public class Voto {
	private int id;
	@NotBlank
	private String titulo;
	private String descripcion;
	private Date limite;
	private String permantlink;
	private Time hora;
	
	private Array pilotos;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getLimite() {
		return limite;
	}
	public void setLimite(Date limite) {
		this.limite = limite;
	}
	public String getPermantlink() {
		return permantlink;
	}
	public void setPermantlink(String permantlink) {
		this.permantlink = permantlink;
	}
	public Time getHora() {
		return hora;
	}
	public void setHora(Time hora) {
		this.hora = hora;
	}
	
	
	
	public Array getPilotos() {
		return pilotos;
	}
	public void setPilotos(Array pilotos) {
		this.pilotos = pilotos;
	}
	@Override
	public String toString() {
		return "Voto [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", limite=" + limite
				+ ", permantlink=" + permantlink + ", hora=" + hora + ", pilotos=" + pilotos + "]";
	}
	
	
	

	
	
	
}
