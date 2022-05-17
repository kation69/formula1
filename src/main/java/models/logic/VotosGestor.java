package models.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.entity.Voto;
import models.persistence.DAOVoto;

@Service
public class VotosGestor {
	@Autowired
	private DAOVoto daoVoto;

	public void guardar(Voto voto) {
		daoVoto.guardar(voto);
	}
	public void editar(Voto voto) {
		daoVoto.editar(voto);
	}
	
	public List<Voto> getLstVotos() {
		return daoVoto.buscarVotos();
	}
	public Voto getVoto(String voto) {
		return daoVoto.buscarVoto(voto);
	}

	public Voto getVoto_id(Integer id) {
		return daoVoto.buscarVoto_id(id);
	}
	public void borrar(Integer votoId) {
		daoVoto.borrar(votoId);
	}
}
