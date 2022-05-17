package models.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import models.entity.VotoPiloto;

@Component
public class VotoPilotoListRowMapper implements RowMapper<VotoPiloto>{
	@Override
	public VotoPiloto mapRow(ResultSet rs, int rowNum) throws SQLException {
		VotoPiloto voto = new VotoPiloto();
		voto.setNombrePiloto(rs.getString("piloto"));
		return voto;
	}
}
