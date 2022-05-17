package models.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import models.entity.VotoPiloto;

@Component
public class VotarPilotoRowMapperDAO implements RowMapper<VotoPiloto>{
	@Override
	public VotoPiloto mapRow(ResultSet rs, int rowNum) throws SQLException {
		VotoPiloto voto = new VotoPiloto();
		voto.setId(rs.getInt("id"));
		voto.setNombrePiloto(rs.getString("piloto"));
		return voto;
	}
}
