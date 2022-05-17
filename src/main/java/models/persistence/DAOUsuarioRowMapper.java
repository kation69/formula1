package models.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import models.entity.Usuario;

@Component
public class DAOUsuarioRowMapper implements RowMapper<Usuario> {

	@Override
	public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setId(rs.getInt("id"));
		usuario.setUsuario(rs.getString("usuario"));
		usuario.setNombre(rs.getString("nombre"));
		usuario.setPassword(rs.getString("password"));
		usuario.setRol(rs.getString("rol"));
		usuario.setEmail(rs.getString("email"));
		usuario.setVerificada(rs.getBoolean("verificada"));
		return usuario;
	}

}
