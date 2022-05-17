package models.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import models.entity.Equipo;

@Component
public class EquipoRowMapperDAO implements RowMapper<Equipo> {

	@Override
	public Equipo mapRow(ResultSet rs, int rowNum) throws SQLException {
		Equipo equipo = new Equipo();
		equipo.setId(rs.getInt("id"));
		equipo.setNombre(rs.getString("nombre"));
		equipo.setLogo(rs.getString("logo"));
		equipo.setTwitter(rs.getString("twitter"));
		equipo.setFechaCreacion(rs.getDate("fechaCreacion"));
		equipo.setUsuarioCreador(rs.getInt("usuarioCreador"));
		return equipo;
	}
}


