package models.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import models.entity.Voto;

@Component
public class VotoRowMapperDAO implements RowMapper<Voto>{
	@Override
	public Voto mapRow(ResultSet rs, int rowNum) throws SQLException {
		Time UnaHora = new Time(3600000);
		Voto voto = new Voto();
		voto.setId(rs.getInt("id"));
		voto.setTitulo(rs.getString("titulo"));
		voto.setDescripcion(rs.getString("descripcion"));
		
		if (rs.getString("limite")!=null) {
			voto.setLimite(rs.getDate("limite"));
			Time horas=rs.getTime("limite");
			horas.setTime(horas.getTime()-UnaHora.getTime());
			voto.setHora(horas);
		}
		voto.setPermantlink(rs.getString("permaLink"));
		//voto.setPilotos(rs.getString("pilotos"));
		//voto.setPilotos(rs.getArray("pilotos"));
		return voto;
	}
}
