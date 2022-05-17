package models.persistence;

import java.util.List;

import models.entity.Piloto;

public interface DAOPiloto {
	
	List<Piloto> listarPilotos();
	List<Piloto> buscarPilotosName(String nombre);
	Piloto insertar(Piloto piloto);
	String update(Piloto piloto);
	void borrar(Integer pilotoId);
	void borrarPilotosEquipo(Integer equipoId);
	
	//List<Piloto> listarPilotos();
	
}
