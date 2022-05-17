package models.logic;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.entity.Circuito;
import models.persistence.DAOCircuito;

@Service
public class CircuitosGestor {
	
	@Autowired
	private DAOCircuito daoCircuitos;

	public void insertar(Circuito circuito) {
		daoCircuitos.insertar(circuito);
	}
	
	public List<Circuito> getAllCircuits() {
		return daoCircuitos.getAllCircuits();
	}
	public Circuito buscarcircuito (String circuito){
		return daoCircuitos.getCirtuit(circuito);
	}
	
	public Boolean eliminarById(Integer id) {
		return daoCircuitos.eliminarById(id);
	}

	public String editarById(Circuito circuito) {
		return daoCircuitos.editarById(circuito);
	}

	public void asignarFecha(Integer id,String fecha) {
		daoCircuitos.asignarFecha(id,fecha);
	}
	
}
