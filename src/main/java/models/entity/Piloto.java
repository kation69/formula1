package models.entity;

import org.springframework.stereotype.Component;

@Component
public class Piloto {
	
	private int id;
	private String nombre;
	private String apellidos;
	private String siglas;
	private int dorsal;
	private String foto;
	private String pais;
	private String twitter;
	private int equipoId;
	
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
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	public String getSiglas() {
		return siglas;
	}
	public void setSiglas(String siglas) {
		this.siglas = siglas;
	}
	public int getDorsal() {
		return dorsal;
	}
	public void setDorsal(int dorsal) {
		this.dorsal = dorsal;
	}
	public String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		this.foto = foto;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getTwitter() {
		return twitter;
	}
	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}
	
	public int getEquipoId() {
		return equipoId;
	}
	public void setEquipoId(int equipoId) {
		this.equipoId = equipoId;
	}
	@Override
	public String toString() {
		return "Piloto [id=" + id + ", nombre=" + nombre + ", apellidos=" + apellidos + ", siglas=" + siglas
				+ ", dorsal=" + dorsal + ", foto=" + foto + ", pais=" + pais + ", twitter=" + twitter + ", equipoId="
				+ equipoId + "]";
	}
	

	
	
}
