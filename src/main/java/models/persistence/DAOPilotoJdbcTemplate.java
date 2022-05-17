package models.persistence;

import java.util.List;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import models.entity.Miembro;
import models.entity.Noticia;
import models.entity.Piloto;

@Repository("daoPiloto")
public class DAOPilotoJdbcTemplate implements DAOPiloto {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PilotoRowMapperDAO pilotoMapper;

	@Override
	public List<Piloto> listarPilotos() {
		String query = "SELECT sql_no_cache * FROM Pilotos";
		final List<Piloto> listaPilotos = jdbcTemplate.query(query, pilotoMapper);
		return listaPilotos;
	}

	@Override
	public Piloto insertar(Piloto piloto) {
		jdbcTemplate.execute("SET autocommit = 0");
		String query = "INSERT INTO Pilotos (nombre, apellidos, siglas, dorsal, foto, pais, twitter, equipo_id)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(query, piloto.getNombre(), piloto.getApellidos(), piloto.getSiglas(), piloto.getDorsal(),
				piloto.getFoto(), piloto.getPais(), piloto.getTwitter(), piloto.getEquipoId());

		jdbcTemplate.execute("COMMIT");
		return piloto;
	}

	@Override
	public List<Piloto> buscarPilotosName(String nombre) {
		String query = "SELECT sql_no_cache * FROM Pilotos WHERE apellidos = '" + nombre + "'";
		return jdbcTemplate.query(query, pilotoMapper);
	}

	@Override
	public void borrar(Integer pilotoId) {
		String query = "Delete FROM Pilotos where id=?";

		jdbcTemplate.update(query, pilotoId);
		jdbcTemplate.execute("COMMIT");
	}

	@Override
	public String update(Piloto piloto) {
		String fichero = "";
		jdbcTemplate.execute("SET autocommit = 0");
		// if(piloto.getFoto()!="" && piloto.getFoto()!=null) {
		String query = "SELECT sql_no_cache * FROM Pilotos where id=" + piloto.getId();
		// final List<Piloto> listaPilotos = jdbcTemplate.query(query, pilotoMapper);

		// fichero = listaPilotos.get(0).getFoto();
		// query = "UPDATE Pilotos SET nombre = ?, apellidos = ?, siglas = ?, dorsal =
		// ?, foto = ?, pais = ?, twitter = ?, equipo_id = ?"
		// + " WHERE id = ?";
		query = "UPDATE Pilotos SET nombre = ?, apellidos = ?, siglas = ?, dorsal = ?, pais = ?, twitter = ?"
				+ " WHERE id = ?";
		jdbcTemplate.update(query, piloto.getNombre(), piloto.getApellidos(), piloto.getSiglas(), piloto.getDorsal(),
				// piloto.getFoto(),
				piloto.getPais(), piloto.getTwitter(), piloto.getId());
		// piloto.getEquipoId());
		// }
		/*
		 * else { String query =
		 * "UPDATE Pilotos SET nombre = ?, apellidos = ?, siglas = ?, dorsal = ?, pais = ?, twitter = ?, equipo_id = ?"
		 * + " WHERE id = ?"; jdbcTemplate.update(query, piloto.getNombre(),
		 * piloto.getApellidos(), piloto.getSiglas(), piloto.getDorsal(),
		 * piloto.getPais(), piloto.getTwitter(), piloto.getId(), piloto.getEquipoId());
		 * }
		 */

		jdbcTemplate.execute("COMMIT");
		return fichero;
	}

	public void borrarPilotosEquipo(Integer itemId) {
		String query = "Delete FROM Pilotos where equipo_id=?";
		jdbcTemplate.update(query, itemId);
		jdbcTemplate.execute("COMMIT");
	}

	

}
