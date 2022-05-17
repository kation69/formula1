package models.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import models.entity.Miembro;

@Component
public class MiembroRowMapper_DAO implements RowMapper<Miembro> {

	@Override
	public Miembro mapRow(ResultSet rs, int rowNum) throws SQLException {
		Miembro miembro = new Miembro();
		miembro.setId(rs.getInt("id"));
		miembro.setUsuarioId(rs.getInt("usuario_id"));
		miembro.setEquipoId(rs.getInt("equipo_id"));
		return miembro;
	}
}
