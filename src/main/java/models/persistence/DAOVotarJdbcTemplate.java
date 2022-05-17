package models.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import models.entity.Voto;
import models.entity.VotoUsuario;

@Repository("daoVotar")
public class DAOVotarJdbcTemplate implements DAOVotar{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private VotarRowMapperDAO votarMapper;
	@Autowired
	private VotarListRowMapperDAO ListvotarMapper;


	@Override
	public Boolean insertar(VotoUsuario voto) {
		// TODO Auto-generated method stub
		//String query = "";
		Boolean votado_anterior = false;
		String query = "SELECT sql_no_cache count(*) as total,piloto FROM VotacionUsuario where votacion_id = "+voto.getId() +
				" and email like '"+voto.getEmail().toLowerCase()+"' group by piloto";
		List<VotoUsuario> listaVotos  = jdbcTemplate.query(query, ListvotarMapper);
		if(listaVotos.size()==0) {
			jdbcTemplate.execute("SET autocommit = 0");
			query = "INSERT INTO votacionUsuario (votacion_id, email,piloto)"
					+ " VALUES (?, ?, ?)";
			jdbcTemplate.update(query,
					voto.getId(),
					voto.getEmail(),
					voto.getNombrePiloto());
					
		
		
			jdbcTemplate.execute("COMMIT");
		}
		else {
			votado_anterior=true;
		}
		
		return votado_anterior;
	}
	public List<VotoUsuario> listar(Integer id) {
		String query = "SELECT sql_no_cache count(*) as total,piloto FROM VotacionUsuario where votacion_id = "+id.toString() +
				" group by piloto";
		final List<VotoUsuario> listaVotos  = jdbcTemplate.query(query, ListvotarMapper);
		return listaVotos;
	}
}
