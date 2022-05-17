package models.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import models.entity.Coches;

@Component
public class CocheRowMapperDAO implements RowMapper<Coches> {

	@Override
	public Coches mapRow(ResultSet rs, int rowNum) throws SQLException {
		Coches coche = new Coches();
		coche.setId(rs.getInt("id"));
		coche.setEquipoId(rs.getInt("equipo_id"));
		coche.setCodigo(rs.getString("codigo"));
		coche.setNombre(rs.getString("nombre"));
		coche.setConsumo(rs.getFloat("consumo"));
		coche.setErsCurvasLentas(rs.getFloat("ersCurvaLenta"));
		coche.setErsCurvasMedias(rs.getFloat("ersCurvaMedia"));
		coche.setErsCurvasRapidas(rs.getFloat("ersCurvaRapida"));
		coche.setImagen(rs.getString("imagen"));
		return coche;
	}
}


