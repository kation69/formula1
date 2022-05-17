package models.entity;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

@Component
public class VotoUsuario {
	private int id;
	@NotBlank
	private String nombrePiloto;
	private String email;
	private Integer contador;


	
	public Integer getContador() {
		return contador;
	}
	public void setContador(Integer contador) {
		this.contador = contador;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombrePiloto() {
		return nombrePiloto;
	}
	public void setNombrePiloto(String nombrePiloto) {
		this.nombrePiloto = nombrePiloto;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String toString() {
		return "VotoUsuario [id=" + id + ", nombrePiloto=" + nombrePiloto + ", email=" + email + "]";
	}

}
