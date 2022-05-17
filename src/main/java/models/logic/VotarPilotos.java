package models.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.entity.VotoUsuario;
import models.persistence.DAOVotar;

@Service
public class VotarPilotos {
	@Autowired
	private DAOVotar daoVotar;

	public Boolean insert(VotoUsuario votoId) {
		return daoVotar.insertar(votoId);
	}
	public List<VotoUsuario> listar(Integer id) {
		return daoVotar.listar(id);
	}
}
