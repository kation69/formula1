package models.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import models.entity.Piloto;

@Component
public class PilotoRowMapperDAO implements RowMapper<Piloto> {

	@Override
	public Piloto mapRow(ResultSet rs, int rowNum) throws SQLException {
		Piloto piloto = new Piloto();
		piloto.setId(rs.getInt("id"));
		piloto.setNombre(rs.getString("nombre"));
		piloto.setApellidos(rs.getString("apellidos"));
		piloto.setSiglas(rs.getString("siglas"));
		piloto.setDorsal(rs.getInt("dorsal"));
		piloto.setFoto(rs.getString("foto"));
		piloto.setPais(rs.getString("pais"));
		piloto.setTwitter(rs.getString("twitter"));
		piloto.setEquipoId(rs.getInt("equipo_id"));
		return piloto;
	}
}


