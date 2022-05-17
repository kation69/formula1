package models.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import models.entity.VotoPiloto;

@Repository("daoVotoPiloto")
public class DAOVotoPilotoJdbcTemplate implements DAOVotoPiloto{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private VotoPilotoListRowMapper votarPilotoListRowMapper;
	
	@Override
	public VotoPiloto insertar(VotoPiloto voto) {
		// TODO Auto-generated method stub
		String query = "";
		System.out.println("Se va ha hacer la query");
		//List<VotoPiloto> listaVotos = jdbcTemplate.query(query, votarPilotoListRowMapper);
		//if(listaVotos.size()==0) {
			System.out.println(query);
			jdbcTemplate.execute("SET autocommit = 0");
			query = "INSERT INTO votacionpiloto (votacion_id,apellidos)"
					+ " VALUES (?, ?)";
			jdbcTemplate.update(query,
					voto.getId(),
					voto.getNombrePiloto());
					
		
		
			jdbcTemplate.execute("COMMIT");
		//}
			return voto;
		
		
	}

	@Override
	public List<VotoPiloto> listar(Integer id) {
		String query = "SELECT sql_no_cache count(*) FROM votacionPiloto where votacion_id = "+id.toString() +
				" group by piloto";
		final List<VotoPiloto> listaVotos  = jdbcTemplate.query(query, votarPilotoListRowMapper);
		return listaVotos;
	}

}
