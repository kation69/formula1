package models.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import models.entity.Voto;

@Repository("daoVoto")
public class DAOVotoJdbcTemplate implements DAOVoto{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private VotoRowMapperDAO votoMapper;
	
	@Override
	public Voto guardar(Voto voto) {
		jdbcTemplate.execute("SET autocommit = 0");
			String query = "";
			if(voto.getLimite()!=null && voto.getHora()!=null) {
				query = "INSERT INTO Votacion (titulo, descripcion, permaLink, limite)"
						+ " VALUES (?, ?, ?, '"+voto.getLimite().toString()+" "+voto.getHora().toString()+"')";
				
			}
			else {
				query = "INSERT INTO Votacion (titulo, descripcion, permaLink)"
						+ " VALUES (?, ?, ?)";
				
			}
			jdbcTemplate.update(query,
					voto.getTitulo(),
					voto.getDescripcion(),
					voto.getPermantlink());
			
		
		
		jdbcTemplate.execute("COMMIT");
		return voto;
	}
	@Override
	public Voto editar(Voto voto) {
		jdbcTemplate.execute("SET autocommit = 0");
			String query = "";
			if(voto.getLimite()!=null && voto.getHora()!=null) {
				query = "UPDATE Votacion set titulo=?, descripcion=?, limite='"+
						voto.getLimite().toString()+" "+voto.getHora().toString()+"' where id = ?";
			}
			else {
				query = "UPDATE Votacion set titulo=?, descripcion=?, limite=null where id = ?";
			}
			jdbcTemplate.update(query,
					voto.getTitulo(),
					voto.getDescripcion(),
					voto.getId());
		
		
		jdbcTemplate.execute("COMMIT");
		return voto;
	}

	@Override
	public List<Voto> buscarVotos() {
		String query = "SELECT sql_no_cache * FROM Votacion";
		final List<Voto> listaVotos  = jdbcTemplate.query(query, votoMapper);
		return listaVotos;
	}
	
	@Override
	public Voto buscarVoto(String link) {
		String query = "SELECT sql_no_cache * FROM Votacion where permalink='"+link+"'";
		final List<Voto> listaVotos  = jdbcTemplate.query(query, votoMapper);
		
		return listaVotos.get(0);
	}
	@Override
	public Voto buscarVoto_id(Integer id) {
		String query = "SELECT sql_no_cache * FROM Votacion where id="+id.toString();
		final List<Voto> listaVotos  = jdbcTemplate.query(query, votoMapper);
		
		return listaVotos.get(0);
	}
	
	@Override
	public void borrar(Integer votoId) {
		String query = "Delete FROM Votacion where id=?";
		
		jdbcTemplate.update(query, votoId);
		jdbcTemplate.execute("COMMIT");
		
	}
}
