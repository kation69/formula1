package models.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import models.entity.Noticia;
import models.entity.Usuario;

@Repository("daoNoticia")
public class DAONoticiaJdbcTemplate implements DAONoticia {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private NoticiaRowMapperDAO noticiaMapper;
	
	@Override
	public Noticia insertar(Noticia noticia) {
		jdbcTemplate.execute("SET autocommit = 0");
		if(noticia.getImagen()!="") {
			String query = "INSERT INTO Noticias (titulo, texto, imagen, permaLink)"
					+ " VALUES (?, ?, ?, ?)";
			jdbcTemplate.update(query,
					noticia.getTitulo(),
					noticia.getCuerpo(),
					noticia.getImagen(),
					noticia.getPermantlink());
		}
		else {
			String query = "INSERT INTO Noticias (titulo, texto, permaLink)"
					+ " VALUES (?, ?, ?)";
			jdbcTemplate.update(query,
					noticia.getTitulo(),
					noticia.getCuerpo(),
					noticia.getPermantlink());
		}
		
		jdbcTemplate.execute("COMMIT");
		return noticia;
	}
	@Override
	public String update(Noticia noticia) {
		String fichero ="";
		jdbcTemplate.execute("SET autocommit = 0");
		if(noticia.getImagen()!="" && noticia.getImagen()!=null) {
			String query = "SELECT sql_no_cache * FROM Noticias where id="+noticia.getId();
			final List<Noticia> listaNoticias  = jdbcTemplate.query(query, noticiaMapper);
			
			fichero =listaNoticias.get(0).getImagen();
			query = "UPDATE Noticias SET titulo = ?, texto = ?, imagen = ?"
					+ " WHERE id = ?";
			jdbcTemplate.update(query,
					noticia.getTitulo(),
					noticia.getCuerpo(),
					noticia.getImagen(),
					noticia.getId());
		}
		else {
			String query = "UPDATE Noticias SET titulo = ?, texto = ?"
					+ " WHERE id = ?";
			jdbcTemplate.update(query,
					noticia.getTitulo(),
					noticia.getCuerpo(),
					noticia.getId());
		}
		
		jdbcTemplate.execute("COMMIT");
		return fichero;
	}

	@Override
	public List<Noticia> buscarNoticias() {
		String query = "SELECT sql_no_cache * FROM Noticias";
		final List<Noticia> listaNoticias  = jdbcTemplate.query(query, noticiaMapper);
		return listaNoticias;
	}
	@Override
	public Noticia buscarNoticia(String link) {
		String query = "SELECT sql_no_cache * FROM Noticias where permaLink='"+link+"'";
		final List<Noticia> listaNoticias  = jdbcTemplate.query(query, noticiaMapper);
		
		return listaNoticias.get(0);
	}
	@Override
	public void borrar(Integer noticia) {
		String query = "Delete FROM Noticias where id=?";
		
		jdbcTemplate.update(query, noticia);
		jdbcTemplate.execute("COMMIT");
		
	}

}
