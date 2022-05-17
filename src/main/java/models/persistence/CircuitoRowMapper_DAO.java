package models.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import models.entity.Circuito;

@Component
public class CircuitoRowMapper_DAO implements RowMapper<Circuito> {

	@Override
	public Circuito mapRow(ResultSet rs, int rowNum) throws SQLException {
		Circuito circuito = new Circuito();
		circuito.setId(rs.getString("id"));
		circuito.setCircuitId(rs.getString("circuitId"));
		circuito.setCircuitName(rs.getString("nombre"));
		circuito.setCountry(rs.getString("pais"));
		circuito.setLocality(rs.getString("ciudad"));
		circuito.setImgTrazado(rs.getString("trazado"));
		//circuito.setUrl(rs.getString("url"));  //falta este campo en el modelo
		circuito.setNumVueltas(rs.getInt("numVueltas"));
		circuito.setLongitude(rs.getInt("longitud"));
		circuito.setCurvasLentas(rs.getInt("curvasLentas"));
		circuito.setCurvasMedias(rs.getInt("curvasMedias"));
		circuito.setCurvasRapidas(rs.getInt("curvasRapidas"));
		circuito.setFecha(rs.getDate("fecha"));
		return circuito;
	}
}
