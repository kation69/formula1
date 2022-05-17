package models.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import models.entity.Coches;

@Repository("daoCoches")
public class DAOCochesJdbcTemplate implements DAOCoches {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private CocheRowMapperDAO cocheMapper;

	@Override
	public List<Coches> buscarCoches(Integer equipoId) {
		String query = "SELECT sql_no_cache * FROM Coche WHERE equipo_id="+equipoId.toString();
		final List<Coches> listaCoches  = jdbcTemplate.query(query, cocheMapper);
		return listaCoches;
	}

	@Override
	public Coches insertar(Coches coche) {
		jdbcTemplate.execute("SET autocommit = 0");
		String query = "INSERT INTO Coche (equipo_id, nombre, codigo,consumo,ersCurvaLenta,ersCurvaMedia,ersCurvaRapida,imagen)"
				+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(query,
				coche.getEquipoId(),
				coche.getNombre(),
				coche.getCodigo(),
				coche.getConsumo(),
				coche.getErsCurvasLentas(),
				coche.getErsCurvasMedias(),
				coche.getErsCurvasRapidas(),
				coche.getImagen());
		
		jdbcTemplate.execute("COMMIT");
		return coche;
	}
	
	@Override
	public List<Coches> buscarCochesName(String nombre) {
		String query = "SELECT sql_no_cache * FROM Coche WHERE nombre = '" + nombre + "'";
		final List<Coches> listaCoches  = jdbcTemplate.query(query, cocheMapper);
		return listaCoches;
	}
	
	@Override
	public Coches getCochesById(Integer id) {
		String query = "SELECT sql_no_cache * FROM Coche WHERE Id = '" + id + "'";
		final List<Coches> listaCoches  = jdbcTemplate.query(query, cocheMapper);
		return listaCoches.get(0);
	}

	@Override
	public Coches update(Coches coche) {
		jdbcTemplate.execute("SET autocommit = 0");
		String query = "UPDATE Coche SET nombre=?, codigo=?,consumo=?,ersCurvaLenta=?,ersCurvaMedia=?,ersCurvaRapida=?,imagen=?"
				+ " WHERE id =?";
		jdbcTemplate.update(query,
				coche.getNombre(),
				coche.getCodigo(),
				coche.getConsumo(),
				coche.getErsCurvasLentas(),
				coche.getErsCurvasMedias(),
				coche.getErsCurvasRapidas(),
				coche.getImagen(),
				coche.getId());
		
		jdbcTemplate.execute("COMMIT");
		return coche;
	}

	@Override
	public void borrar(Integer cocheId) {
		jdbcTemplate.execute("SET autocommit = 0");
		String query = "DELETE FROM Coche "
				+ " WHERE id =?";
		jdbcTemplate.update(query,
				cocheId);
		
		jdbcTemplate.execute("COMMIT");
		
	}
	
	@Override
	public void borrarCochesEquipo(Integer itemId) {
		String query = "Delete FROM Coche where equipo_id=?";
		jdbcTemplate.update(query, itemId);
		jdbcTemplate.execute("COMMIT");
	}


}
