package models.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.entity.Piloto;
import models.persistence.DAOPiloto;

@Service
public class PilotosGestor {
	
	@Autowired
	private DAOPiloto daoPilotos;
	
	public List<Piloto> getLstPilotos() {
		return daoPilotos.listarPilotos();
	}
	
	public List<Piloto> buscarPilotosName(String nombre) {
		return daoPilotos.buscarPilotosName(nombre);
	}
	
	public Piloto insertar(Piloto piloto) {
		return daoPilotos.insertar(piloto);
	}
	
	public String update(Piloto piloto) {
		return daoPilotos.update(piloto);
	}
	
	public void borrar(Integer pilotoId) {
		daoPilotos.borrar(pilotoId);
	}
}
