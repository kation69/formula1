package models.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import models.entity.Equipo;
import models.entity.Noticia;

@Repository("daoEquipo")
public class DAOEquipoJdbcTemplate implements DAOEquipo {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private EquipoRowMapperDAO equipoMapper;

	@Override
	public List<Equipo> buscarEquipos() {
		String query = "SELECT sql_no_cache * FROM Equipos";
		final List<Equipo> listaEquipos  = jdbcTemplate.query(query, equipoMapper);
		return listaEquipos;
	}

	@Override
	public Equipo insertar(Equipo equipo) {
		jdbcTemplate.execute("SET autocommit = 0");
		String query = "INSERT INTO Equipos (nombre, logo, twitter, fechaCreacion, usuarioCreador)"
				+ " VALUES (?, ?, ?, ?, ?)";
		jdbcTemplate.update(query,
				equipo.getNombre(),
				equipo.getLogo(),
				equipo.getTwitter(),
				equipo.getFechaCreacion(),
				equipo.getUsuarioCreador());
		
		jdbcTemplate.execute("COMMIT");
		return equipo;
	}
	
	@Override
	public List<Equipo> buscarEquiposName(String nombre) {
		String query = "SELECT sql_no_cache * FROM Equipos WHERE nombre = '" + nombre + "'";
		final List<Equipo> listaEquipos  = jdbcTemplate.query(query, equipoMapper);
		return listaEquipos;
	}
	
	@Override
	public Equipo getEquipoById(Integer id) {
		String query = "SELECT sql_no_cache * FROM Equipos WHERE Id = '" + id + "'";
		final List<Equipo> listaEquipos  = jdbcTemplate.query(query, equipoMapper);
		return listaEquipos.get(0);
	}

	@Override
	public void update(Equipo equipo) {
		jdbcTemplate.execute("SET autocommit = 0");
		String query = "UPDATE Equipos SET nombre=?, logo=?, twitter=?, fechaCreacion=?, usuarioCreador=? WHERE id =?";
		jdbcTemplate.update(query,
				equipo.getNombre(),
				equipo.getLogo(),
				equipo.getTwitter(),
				equipo.getFechaCreacion(),
				equipo.getUsuarioCreador(),
				equipo.getId());
		
		jdbcTemplate.execute("COMMIT");
	}

	@Override
	public void borrar(Integer equipoId) {
		jdbcTemplate.execute("SET autocommit = 0");
		String query = "DELETE FROM Equipos "
				+ " WHERE id =?";
		jdbcTemplate.update(query,
				equipoId);
		
		jdbcTemplate.execute("COMMIT");
	}

}
