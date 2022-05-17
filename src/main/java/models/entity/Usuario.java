package models.entity;

import javax.validation.constraints.NotBlank;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class Usuario {
	
	private int id;
	@NotBlank
	private String nombre;
	@NotBlank
	private String password;
	@NotBlank
	private String email;
	@NotBlank
	private String rol;
	@NotBlank
	private String usuario;
	@NotBlank
	private Boolean verificada;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRol() {
		return rol;
	}
	public void setRol(String rol) {
		this.rol = rol;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Boolean getVerificada() {
		return verificada;
	}
	public void setVerificada(Boolean verificada) {
		this.verificada = verificada;
	}
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", password=" + password + ", email=" + email + ", rol="
				+ rol + ", usuario=" + usuario + ", verificada="+ verificada +"]";
	}
	
}
