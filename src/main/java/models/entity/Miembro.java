package models.entity;

import org.springframework.stereotype.Component;

@Component
public class Miembro {
	
	private Integer id;
	private Integer usuarioId;
	private Integer equipoId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUsuarioId() {
		return usuarioId;
	}
	public void setUsuarioId(Integer usuarioId) {
		this.usuarioId = usuarioId;
	}
	public Integer getEquipoId() {
		return equipoId;
	}
	public void setEquipoId(Integer equipoId) {
		this.equipoId = equipoId;
	}
	@Override
	public String toString() {
		return "Miembro [id=" + id + ", usuarioId=" + usuarioId + ", equipoId=" + equipoId + "]";
	}
	
}
