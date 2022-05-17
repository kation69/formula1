package models.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.ModelAndView;

import controllers.ErgarstFormula1_Controlador;
import models.entity.Circuito;
import models.entity.Noticia;
import models.entity.Usuario;

@Repository("daoCircuito")
public class DAOCircuitoJdbcTemplate implements DAOCircuito {


	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private CircuitoRowMapper_DAO circuitoMapper;

	@Override
	public Circuito insertar(Circuito circSelect) {
		jdbcTemplate.execute("SET autocommit = 0");
		if(circSelect.getImgTrazado()!=null && circSelect.getImgTrazado()!="") {
			String query = "INSERT INTO Circuito (circuitId, nombre, ciudad, pais, trazado, numVueltas, longitud,"
					+ "curvasLentas, curvasMedias, curvasRapidas)"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(query,
					circSelect.getCircuitId(),
					circSelect.getCircuitName(),
					circSelect.getLocality(),
					circSelect.getCountry(),
					circSelect.getImgTrazado(),
					circSelect.getNumVueltas(),
					circSelect.getLongitude(),
					circSelect.getCurvasLentas(),
					circSelect.getCurvasMedias(),
					circSelect.getCurvasRapidas());
		}
		else{
			String query = "INSERT INTO Circuito (circuitId, nombre, ciudad, pais, numVueltas, longitud,"
					+ "curvasLentas, curvasMedias, curvasRapidas)"
						+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
			jdbcTemplate.update(query,
					circSelect.getCircuitId(),
					circSelect.getCircuitName(),
					circSelect.getLocality(),
					circSelect.getCountry(),
					circSelect.getNumVueltas(),
					circSelect.getLongitude(),
					circSelect.getCurvasLentas(),
					circSelect.getCurvasMedias(),
					circSelect.getCurvasRapidas());
		}
		jdbcTemplate.execute("COMMIT");
		return circSelect;
	}
	@Override
	public String editarById(Circuito circSelect) {
		String fichero ="";
		
		jdbcTemplate.execute("SET autocommit = 0");
		if(circSelect.getImgTrazado()!=null) {
			String query = "SELECT sql_no_cache * FROM Circuito where id="+circSelect.getId();
			final List<Circuito> listaCircuito  = jdbcTemplate.query(query, circuitoMapper);
			
			fichero =listaCircuito.get(0).getImgTrazado();
			
			query = "UPDATE Circuito SET circuitId = ?, nombre = ?, ciudad = ?, pais = ?, trazado = ?, numVueltas = ?, longitud = ?,"
					+ "curvasLentas = ?, curvasMedias = ?, curvasRapidas = ?"
						+ " WHERE id="+circSelect.getId();
			jdbcTemplate.update(query,
					circSelect.getCircuitId(),
					circSelect.getCircuitName(),
					circSelect.getLocality(),
					circSelect.getCountry(),
					circSelect.getImgTrazado(),
					circSelect.getNumVueltas(),
					circSelect.getLongitude(),
					circSelect.getCurvasLentas(),
					circSelect.getCurvasMedias(),
					circSelect.getCurvasRapidas());
		}
		else{
			String query = "UPDATE Circuito SET circuitId = ?, nombre = ?, ciudad = ?, pais = ?, numVueltas = ?, longitud = ?,"
					+ "curvasLentas = ?, curvasMedias = ?, curvasRapidas = ?"
					+ " WHERE id="+circSelect.getId();
			jdbcTemplate.update(query,
					circSelect.getCircuitId(),
					circSelect.getCircuitName(),
					circSelect.getLocality(),
					circSelect.getCountry(),
					circSelect.getNumVueltas(),
					circSelect.getLongitude(),
					circSelect.getCurvasLentas(),
					circSelect.getCurvasMedias(),
					circSelect.getCurvasRapidas());
		}
		jdbcTemplate.execute("COMMIT");
		
		return fichero;
	}

	@Override
	public List<Circuito> getAllCircuits() {
		String query = "SELECT * FROM Circuito";
		return jdbcTemplate.query(query, circuitoMapper);
	}

	@Override
	public Boolean eliminarById(Integer id) {
		String query = "delete from Circuito where id=? and fecha IS NULL";
		jdbcTemplate.update(query,id);
		jdbcTemplate.execute("COMMIT");
		query = "SELECT * FROM Circuito where id ="+id.toString();
		List<Circuito> cirts = jdbcTemplate.query(query, circuitoMapper);
		if (cirts.size()>0) {
			return false;
		}
		return true;
	}
	@Override
	public void asignarFecha(Integer id,String fecha) {
		String query = "UPDATE Circuito SET fecha = ? where id=?";
		jdbcTemplate.update(query,fecha,id);
		jdbcTemplate.execute("COMMIT");
	}

	@Override
	public Circuito getCirtuit(String circuito) {
		String query = "SELECT * FROM Circuito where Replace(nombre,' ','') like '"+circuito+"'";
		Circuito cirt = jdbcTemplate.query(query, circuitoMapper).get(0);
		return cirt;
	}
	
}
