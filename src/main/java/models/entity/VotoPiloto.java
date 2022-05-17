package models.entity;

import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

@Component
public class VotoPiloto {
	private int id;
	@NotBlank
	private String nombrePiloto;

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

	@Override
	public String toString() {
		return "VotoPiloto [id=" + id + ", nombrePiloto=" + nombrePiloto + "]";
	}

}
