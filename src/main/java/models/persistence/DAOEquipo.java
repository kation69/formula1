package models.persistence;

import java.util.List;

import models.entity.Equipo;

public interface DAOEquipo {
	
	List<Equipo> buscarEquipos();
	Equipo getEquipoById(Integer id);
	List<Equipo> buscarEquiposName(String nombre);
	Equipo insertar(Equipo equipo);
	void update(Equipo equipo);
	void borrar(Integer equipoId);
	
}
