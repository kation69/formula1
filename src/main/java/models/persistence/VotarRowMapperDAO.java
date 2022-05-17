package models.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import models.entity.VotoUsuario;

@Component
public class VotarRowMapperDAO implements RowMapper<VotoUsuario>{
	@Override
	public VotoUsuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		VotoUsuario voto = new VotoUsuario();
		voto.setId(rs.getInt("id"));
		voto.setNombrePiloto(rs.getString("piloto"));
		voto.setEmail(rs.getString("email"));
		return voto;
	}
}
