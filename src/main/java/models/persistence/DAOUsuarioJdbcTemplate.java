package models.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import models.entity.Usuario;

@Repository("daoUsuario")
public class DAOUsuarioJdbcTemplate implements DAOUsuario {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private DAOUsuarioRowMapper usuarioMapper;
	
	@Override
	public void insertar(Usuario usuario) {
		jdbcTemplate.execute("SET autocommit = 0");
		String query = "INSERT INTO Usuarios (usuario, nombre, password, email, rol)"
					+ " VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(query,
				usuario.getUsuario(),
				usuario.getNombre(),
				usuario.getPassword(),
				usuario.getEmail(),
				usuario.getRol());
		jdbcTemplate.execute("COMMIT");
	}
	@Override
	public void insertaradmin(Usuario usuario) {
		jdbcTemplate.execute("SET autocommit = 0");
		String query = "INSERT INTO Usuarios (usuario, nombre, password, email, rol,verificada)"
					+ " VALUES (?, ?, ?, ?, ?,1)";
		jdbcTemplate.update(query,
				usuario.getUsuario(),
				usuario.getNombre(),
				usuario.getPassword(),
				usuario.getEmail(),
				usuario.getRol());
		jdbcTemplate.execute("COMMIT");
	}
	@Override
	public void modificar(Usuario usuario) {
		jdbcTemplate.execute("SET autocommit = 0");
		String query = "UPDATE Usuarios SET nombre = ?, password = ? , verificada = ? , rol = ?"
				+ " WHERE id = ?";
		jdbcTemplate.update(query,
				usuario.getNombre(),
				usuario.getPassword(),
				usuario.getVerificada(),
				usuario.getRol(),
				usuario.getId());
		jdbcTemplate.execute("COMMIT");
	}
	@Override
	public void update(Usuario usuario) {
		jdbcTemplate.execute("SET autocommit = 0");
		String query="";
		if(usuario.getPassword()=="") {
			query = "UPDATE Usuarios SET nombre = ?, email = ?"
					+ " WHERE id = ?";
			jdbcTemplate.update(query,
					usuario.getNombre(),
					usuario.getEmail(),
					usuario.getId());
		}
		else {
			query = "UPDATE Usuarios SET nombre = ?, password = ? , email = ?"
					+ " WHERE id = ?";
			jdbcTemplate.update(query,
					usuario.getNombre(),
					usuario.getPassword(),
					usuario.getEmail(),
					usuario.getId());
		}
		 
		
		jdbcTemplate.execute("COMMIT");
	}

	@Override
	public void borrar(Usuario usuario) {
		jdbcTemplate.execute("SET autocommit = 0");
		String query = "DELETE FROM Usuarios WHERE id = ?";
		jdbcTemplate.update(query, usuario.getId());
		jdbcTemplate.execute("COMMIT");
	}

	@Override
	public Usuario buscar(Integer id) {
		String query = "SELECT sql_no_cache * FROM Usuarios WHERE id = ?";
		Usuario usuario = jdbcTemplate.queryForObject(query, usuarioMapper, id);
		return usuario;
	}
	
	@Override
	public List<Usuario> buscarUsuario(String user, String email) {
		String query = "SELECT sql_no_cache * FROM Usuarios WHERE usuario = '" + user + "' OR email = '" + email +"'";
		List<Usuario> listaUsuarios  = jdbcTemplate.query(query, usuarioMapper);
		return listaUsuarios;
	}
	
	@Override
	public Usuario buscar(String userName, String password) {
		String query = "SELECT sql_no_cache * FROM Usuarios WHERE usuario = ? AND password = ?";
		Usuario usuario = jdbcTemplate.queryForObject(query, usuarioMapper, userName, password);
		return usuario;
	}
	
	@Override
	public List<Usuario> getUsuariosByListIds(List<Integer> strIds) {
		StringJoiner strJoiner = new StringJoiner(",");
		for (Integer id: strIds) {
			strJoiner.add(String.valueOf(id));
		}
		String queryStr = "SELECT sql_no_cache * FROM Usuarios WHERE Id IN ("+strJoiner+")";
	    return jdbcTemplate.query(queryStr, usuarioMapper);
	}
	
	@Override
	public HashMap<Integer, Usuario> getMapUsuarios(List<Integer> strIds) {
		HashMap<Integer, Usuario> mapUser = new HashMap<Integer, Usuario>();
		for (Usuario user : getUsuariosByListIds(strIds)) {
			mapUser.put(user.getId(), user);
		}
		return mapUser;
	}

	@Override
	public List<Usuario> getListaUsuarios() {
		String query = "SELECT sql_no_cache * FROM Usuarios";
		List<Usuario> listaUsuarios  = jdbcTemplate.query(query, usuarioMapper);
		return listaUsuarios;
	}
	
	@Override
	public List<Usuario> getUsuariosByRol(String rol) {
		String query = "SELECT sql_no_cache * FROM Usuarios WHERE verificada = true AND Rol = '"+rol+"'";
		return jdbcTemplate.query(query, usuarioMapper);
	}

}
