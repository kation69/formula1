package models.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.RowMapper;

import models.entity.Noticia;

@Component
public class NoticiaRowMapperDAO implements RowMapper<Noticia> {

	@Override
	public Noticia mapRow(ResultSet rs, int rowNum) throws SQLException {
		Noticia noticia = new Noticia();
		noticia.setId(rs.getInt("id"));
		noticia.setTitulo(rs.getString("titulo"));
		noticia.setCuerpo(rs.getString("texto"));
		noticia.setImagen(rs.getString("imagen"));
		noticia.setPermantlink(rs.getString("permaLink"));
		return noticia;
	}
}
