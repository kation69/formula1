package models.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import models.entity.VotoUsuario;

@Component
public class VotarListRowMapperDAO implements RowMapper<VotoUsuario>{
	@Override
	public VotoUsuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		VotoUsuario voto = new VotoUsuario();
		voto.setContador(rs.getInt("total"));
		voto.setNombrePiloto(rs.getString("piloto"));
		return voto;
	}
}
