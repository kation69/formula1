package models.persistence;

import java.util.List;

import models.entity.VotoPiloto;

public interface DAOVotoPiloto {
	public VotoPiloto insertar(VotoPiloto  voto);
	public List<VotoPiloto> listar(Integer id);
}
