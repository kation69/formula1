package models.entity;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

@Component
public class Noticia {
	
	private int id;
	@NotBlank
	private String titulo;
	private String cuerpo;
	private String imagen;
	private String permantlink;
	
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
	public String getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(String cuerpo) {
		this.cuerpo = cuerpo;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		if (imagen==null) {
			this.imagen = "";
		}
		else {
			this.imagen = imagen;
		}
		
	}
	public String getPermantlink() {
		return permantlink;
	}
	public void setPermantlink(String permantlink) {
		this.permantlink = permantlink;
	}
	@Override
	public String toString() {
		return "Noticia [id=" + id + ", titulo=" + titulo + ", cuerpo=" + cuerpo + ", imagen=" + imagen + "]";
	}
	
}
