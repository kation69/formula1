package models.persistence;

import java.util.List;

import models.entity.Coches;

public interface DAOCoches {
	
	List<Coches> buscarCoches(Integer equipoId);
	Coches getCochesById(Integer id);
	List<Coches> buscarCochesName(String nombre);
	Coches insertar(Coches coche);
	Coches update(Coches coche);
	void borrar(Integer cocheId);
	void borrarCochesEquipo(Integer equipoId);
	
}
