package models.persistence;

import java.util.List;

import models.entity.VotoUsuario;

public interface DAOVotar {
	public Boolean insertar(VotoUsuario  voto);
	public List<VotoUsuario> listar(Integer id);
}
