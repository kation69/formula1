package models.persistence;

import java.util.List;
import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import models.entity.Miembro;

@Repository("daoMiembro")
public class DAOMiembroJdbcTemplate implements DAOMiembro {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private MiembroRowMapper_DAO miembroMapper;
	
	@Override
	public List<Miembro> getMiembrosByEquipo(Integer equipoId) {
		String query = "SELECT sql_no_cache * FROM Miembros WHERE equipo_id = '" + equipoId+"'";
		return jdbcTemplate.query(query, miembroMapper);
	}
	@Override
	public List<Miembro> buscarMiembroByUser(Integer userId) {
		String query = "SELECT sql_no_cache * FROM Miembros WHERE usuario_id = '" + userId+"'";
		List<Miembro> miembros = jdbcTemplate.query(query, miembroMapper);
		return miembros;
	}

	@Override
	public Miembro insertar(Miembro miembro) {
		jdbcTemplate.execute("SET autocommit = 0");
		String query = "INSERT INTO Miembros (usuario_id, equipo_id)"
				+ " VALUES (?, ?)";
		jdbcTemplate.update(query,
				miembro.getUsuarioId(),
				miembro.getEquipoId());
		
		jdbcTemplate.execute("COMMIT");
		return miembro;
	}

	@Override
	public void update(Miembro miembro) {
		jdbcTemplate.execute("SET autocommit = 0");
		String query = "UPDATE Miembros SET usuario_id = ?, equipo_id = ?"
					+ " WHERE id = ?";
		jdbcTemplate.update(query,
			miembro.getUsuarioId(),
			miembro.getEquipoId());
		jdbcTemplate.execute("COMMIT");
	}

	@Override
	public void borrar(Integer miembroId) {
		String query = "DELETE FROM Miembros where id = ?";
		jdbcTemplate.update(query, miembroId);
		jdbcTemplate.execute("COMMIT");
	}
	
	@Override
	public List<Miembro> getAllMiembros() {
		String query = "SELECT sql_no_cache * FROM Miembros";
		return jdbcTemplate.query(query, miembroMapper);
	}
	
	@Override
	public void borrarMiembros(List<Miembro> lstMember) {
		final StringJoiner strJoiner = new StringJoiner(",");
		for (Miembro item: lstMember) {
			strJoiner.add(String.valueOf(item.getId()));
		}
		String query = "DELETE FROM Miembros where id IN ("+strJoiner+")";
		jdbcTemplate.update(query);
		jdbcTemplate.execute("COMMIT");
	}



}
