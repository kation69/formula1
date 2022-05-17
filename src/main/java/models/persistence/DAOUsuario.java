package models.persistence;

import java.util.HashMap;
import java.util.List;

import models.entity.Usuario;

public interface DAOUsuario {
	void insertar(Usuario usuario);
	void modificar(Usuario usuario);
	void update(Usuario usuario);
	void borrar(Usuario usuario);
	Usuario buscar(Integer id);
	List<Usuario> buscarUsuario(String usuario, String email);
	Usuario buscar(String userName, String password);
	List<Usuario> getListaUsuarios();
	void insertaradmin(Usuario usuario);
	List<Usuario> getUsuariosByListIds(List<Integer> strIds);
	List<Usuario> getUsuariosByRol(String rol);
	HashMap<Integer, Usuario> getMapUsuarios(List<Integer> strIds);
}
