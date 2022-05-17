package models.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service; 

import models.entity.VotoPiloto;
import models.persistence.DAOVotoPiloto;

@Service
public class VotarListaPilotos {
	@Autowired
	private DAOVotoPiloto daoVotar;

	public void insertar(VotoPiloto votoId) {
		daoVotar.insertar(votoId);
	}
	public List<VotoPiloto> listar(Integer id) {
		return daoVotar.listar(id);
	}
}
