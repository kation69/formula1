package models.persistence;

import java.util.Date;
import java.util.List;

import models.entity.Circuito;

public interface DAOCircuito {
	
	List<Circuito> getAllCircuits();
	Circuito insertar(Circuito circuito);
	Boolean eliminarById(Integer id);
	String editarById(Circuito circuito);
	void asignarFecha(Integer id,String fecha);
	Circuito getCirtuit(String circuito);
	
}