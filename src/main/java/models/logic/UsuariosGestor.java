package models.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.entity.Equipo;
import models.entity.Miembro;
import models.entity.Usuario;
import models.persistence.DAOEquipo;
import models.persistence.DAOMiembro;
import models.persistence.DAOUsuario;

@Service
public class UsuariosGestor {
	
	@Autowired
	private DAOUsuario daoUsuario;
	@Autowired
	private DAOMiembro daoMiembro;
	@Autowired
	private EquiposGestor gEquipos;
	
	public class UserMember {

		public Integer id;
		public String usuario;
		public String nombre;
		public String email;
		public Integer userId;
		public Boolean permRemove;
		
		UserMember(Integer id, String usuario, String nombre, String email, Integer userId, Boolean permRemove) {
			this.id = id;
			this.usuario = usuario;
			this.nombre = nombre;
			this.email = email;
			this.userId = userId;
			this.permRemove = permRemove;
		}
		

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getUsuario() {
			return usuario;
		}

		public void setUsuario(String usuario) {
			this.usuario = usuario;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}
		
		public Integer getUserId() {
			return userId;
		}
		
		public void setUserId(Integer userId) {
			this.userId = userId;
		}

		public Boolean getPermRemove() {
			return permRemove;
		}

		public void setPermRemove(Boolean permRemove) {
			this.permRemove = permRemove;
		}

		@Override
		public String toString() {
			return "UserMember [id=" + id + ", usuario=" + usuario + ", nombre=" + nombre + ", email=" + email + ", userId=" + userId + ", permRemove =" + permRemove +"]";
		}
	}

	public void insertar(Usuario usuario) {
		daoUsuario.insertar(usuario);
	}
	public void insertaradmin(Usuario usuario) {
		daoUsuario.insertaradmin(usuario);
	}
	
	public Usuario buscar(Integer id) {
		return daoUsuario.buscar(id);
	}
	
	public List<Usuario> buscarUsuario(String nameUser, String email) {
		return daoUsuario.buscarUsuario(nameUser, email);
	}
	
	public Usuario buscar(String userName, String password) {
		Usuario user = new Usuario();
		List<Usuario> listaUsuarios = daoUsuario.getListaUsuarios();
		for(Usuario usuario : listaUsuarios) {
			if(usuario.getUsuario() != null && usuario.getUsuario().equals(userName)) {
				if(usuario.getPassword() != null && usuario.getPassword().equals(password)) {
					user = usuario;
				}
			}
		}
		return user;
	}
	
	public List<Usuario> getListaUsuarios() {
		return daoUsuario.getListaUsuarios();
	}
	
	public void borrar(Usuario usuario) {
		daoUsuario.borrar(usuario);
	}
	
	public void modificar(Usuario usuario) {
		daoUsuario.modificar(usuario);
	}
	public void update(Usuario usuario) {
		daoUsuario.update(usuario);
	}
	
	public List<UserMember> getUsuariosByListIds(List<Integer> strIds, Integer equipoId, Integer usuarioCreador, Usuario userSession) {
		HashMap <Integer, Usuario> mapUsuarios = new HashMap <Integer, Usuario>();
		List<UserMember> lstUserMember = new ArrayList<UserMember>();
		List<Usuario> lstUsuarios = daoUsuario.getUsuariosByListIds(strIds);
		for (Usuario user : lstUsuarios) {
			mapUsuarios.put(user.getId(), user);
		}
		for (Miembro miembro : daoMiembro.getMiembrosByEquipo(equipoId)) {
			Usuario userTemp = mapUsuarios.get(miembro.getUsuarioId());
			final Boolean permBorrado = userTemp.getId() != usuarioCreador &&  userSession.getRol().equals("responsable") ? true : false;
			UserMember usuarioNew = new UserMember(miembro.getId(), userTemp.getUsuario(), userTemp.getNombre(), userTemp.getEmail(), userTemp.getId(), permBorrado);	
			lstUserMember.add(usuarioNew);
		}
		return lstUserMember;
	}
	
	public List<UserMember> getAllResponsables() {		
		final List<Integer> lstIdsUserWithTeam = new ArrayList<Integer>();
		final List<UserMember> lstUsersFree = new ArrayList<UserMember>();
		for (Miembro item : daoMiembro.getAllMiembros()) {
			lstIdsUserWithTeam.add(item.getUsuarioId());
		}
		for (Usuario user : daoUsuario.getUsuariosByRol("responsable")) {
			if (!lstIdsUserWithTeam.contains(user.getId())) {
				lstUsersFree.add(new UserMember(user.getId(), user.getUsuario(), user.getNombre(), user.getEmail(), user.getId(), false));
			}
		}
		return lstUsersFree;
	}
	
	public List<UserMember> getUsersMemberTeam(int itemId) {		
		final List<Integer> lstIdsUserWithTeam = new ArrayList<Integer>();
		final List<UserMember> lstUsersFree = new ArrayList<UserMember>();
		final Equipo team = gEquipos.getEquipoById(itemId);
		for (Miembro item : daoMiembro.getMiembrosByEquipo(itemId)) {
			if (team.getUsuarioCreador() != item.getUsuarioId()) {				
				lstIdsUserWithTeam.add(item.getUsuarioId());
			}
		}
		for (Usuario user : daoUsuario.getUsuariosByListIds(lstIdsUserWithTeam)) {
			lstUsersFree.add(new UserMember(user.getId(), user.getUsuario(), user.getNombre(), user.getEmail(), user.getId(), false));
		}
		return lstUsersFree;
	}
	
}
