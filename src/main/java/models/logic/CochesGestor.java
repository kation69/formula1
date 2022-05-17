package models.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.entity.Coches;
import models.persistence.DAOCoches;

@Service
public class CochesGestor {
	
	@Autowired
	private DAOCoches daoCoches;
	
	public List<Coches> getLstCoches(Integer equipoId) {
		return daoCoches.buscarCoches(equipoId);
	}
	
	public Coches getCochesById(Integer id) {
		return daoCoches.getCochesById(id);
	}
	
	public List<Coches> buscarCochesName(String nombre) {
		return daoCoches.buscarCochesName(nombre);
	}
	
	public Coches insertar(Coches coche) {
		return daoCoches.insertar(coche);
	}

	public Coches actualizar(Coches coche) {
		return daoCoches.update(coche);
	}

	public void eliminar(Integer coche_id) {
		daoCoches.borrar(coche_id);
	}
}
