package models.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.entity.Equipo;
import models.entity.Usuario;
import models.persistence.DAOCoches;
import models.persistence.DAOEquipo;
import models.persistence.DAOMiembro;
import models.persistence.DAOPiloto;
import models.persistence.DAOUsuario;

@Service
public class EquiposGestor {
	
	@Autowired
	private DAOEquipo daoEquipos;
	@Autowired
	private DAOMiembro daoMiembro;
	@Autowired
	private DAOCoches daoCoches;
	@Autowired
	private DAOPiloto daoPilotos;
	@Autowired
	private DAOUsuario daoUsuario;
	
	public class EquipoUsuario {
		public Equipo equipo;
		public Usuario user;
		
		public EquipoUsuario(Equipo equipo, Usuario user) {
			this.equipo = equipo;
			this.user = user;
		}

		public Equipo getEquipo() {
			return equipo;
		}

		public void setEquipo(Equipo equipo) {
			this.equipo = equipo;
		}

		public Usuario getUser() {
			return user;
		}

		public void setUser(Usuario user) {
			this.user = user;
		}
		@Override
		public String toString() {
			return "EquipoUsuario [equipo=" + equipo + ", user=" + user + "]";
		}
	}
	
	public List<EquipoUsuario> getLstEquiposUsuario() {
		List<Integer> lstUserIds = new ArrayList<Integer>();
		List<Equipo> lstEquipos = daoEquipos.buscarEquipos();
		for (Equipo team : lstEquipos) {
			lstUserIds.add(team.getUsuarioCreador());
		}
		HashMap<Integer, Usuario> mapUser = daoUsuario.getMapUsuarios(lstUserIds);
		List<EquipoUsuario> wrapper = new ArrayList<EquipoUsuario>();
		for (Equipo team : lstEquipos) {
			Usuario user = mapUser.get(team.getUsuarioCreador());
			Usuario userNew = new Usuario();
			userNew.setNombre(user.getNombre());
			userNew.setUsuario(user.getUsuario());
			wrapper.add(new EquipoUsuario(team, userNew));
		}
		return wrapper;
	}
	
	public Equipo getEquipoById(Integer id) {
		return daoEquipos.getEquipoById(id);
	}
	
	public List<Equipo> buscarEquiposName(String nombre) {
		return daoEquipos.buscarEquiposName(nombre);
	}
	
	public Equipo insertar(Equipo equipo) {
		return daoEquipos.insertar(equipo);
	}
	
	public void borrar(Integer id) {
		daoEquipos.borrar(id);
	}
	
	public void borrarEquipo(Equipo equipo) {
		daoMiembro.borrarMiembros(daoMiembro.getMiembrosByEquipo(equipo.getId()));
		daoPilotos.borrarPilotosEquipo(equipo.getId());
		daoCoches.borrarCochesEquipo(equipo.getId());
		daoEquipos.borrar(equipo.getId());
	}
	public void editarEquipo(Equipo equipo) {
		daoEquipos.update(equipo);
	}
}
