package models.persistence;

import java.util.List;

import models.entity.Voto;

public interface DAOVoto {
	List<Voto> buscarVotos();
	Voto buscarVoto(String link);
	Voto buscarVoto_id(Integer id);
	Voto guardar(Voto voto);
	Voto editar(Voto voto);
	
	void borrar(Integer votoId);
}
