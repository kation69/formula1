package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import helpers.CustomLabels_ES;
import helpers.HelperGlobal;
import helpers.SaveAndResponseWrapper;
import models.entity.Equipo;
import models.entity.Miembro;
import models.entity.Usuario;
import models.logic.EquiposGestor;
import models.logic.MiembrosGestor;
import models.logic.UsuariosGestor;

@Controller
@Scope("request")
@RequestMapping("usuarios")
public class Usuarios_Controlador {
	
	@Autowired
	private UsuariosGestor gUsuarios;
	@Autowired
	private MiembrosGestor gMiembros;
	@Autowired
	private EquiposGestor gEquipos;
	
	private String redirect_page = "redirect:/gestorUsuarios";
	
	@RequestMapping(path="loginUser", method=RequestMethod.POST)
	public ModelAndView ingresarUsuario(@Valid @RequestParam("usuario") String userName,
										@Valid @RequestParam("password") String password,
										HttpSession session) {
		final Usuario user = gUsuarios.buscar(userName, password);
		if(user.getEmail() == null) {
			HelperGlobal.createException(session, CustomLabels_ES.error_login, "error");
		} else if (!user.getVerificada()) {
			HelperGlobal.createException(session, CustomLabels_ES.error_pendiente_verific, "error");
		} else {
			final Usuario usuario = user;
			session.setAttribute("usuario", usuario);
		}
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(path="altaUsuario", method=RequestMethod.GET) 
	public ModelAndView altaUsuario(HttpSession session) {
		try {
			if (HelperGlobal.createAdmin(gUsuarios)) {
				HelperGlobal.createException(session, CustomLabels_ES.verify_create_admin_user, "success");
				return new ModelAndView("redirect:/");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ModelAndView("creacionUsuario");
	}
	
	@RequestMapping(path="activarusuario", method=RequestMethod.POST) 
	public ModelAndView activarusuario(@RequestParam(value = "user", required = true) String user,
										@RequestParam(value = "rol", required = true) String rol, 
										HttpSession session) {
		Usuario user_buscado = gUsuarios.buscar(Integer.parseInt(user));
		String[] lstRol = rol.split(",");
		final ModelAndView mav = new ModelAndView(redirect_page);

		// User logged
		if (session.getAttribute("usuario") != null && user_buscado != null) {
			final List<Miembro> lstMiembro = gMiembros.getMiembroByUserId(user_buscado.getId());
			
			if (lstMiembro.isEmpty()) {	
				final Usuario usuarioid= (Usuario)session.getAttribute("usuario");
				if (usuarioid.getId() != user_buscado.getId()) {
					final String labelDyn = user_buscado.getVerificada() ? CustomLabels_ES.success_desverificar_usuario : CustomLabels_ES.success_validar_usuario;
					user_buscado.setVerificada(!user_buscado.getVerificada());
					user_buscado.setRol(lstRol[0]);
					gUsuarios.modificar(user_buscado);
					HelperGlobal.createException(session, labelDyn, "success");
				} else {
					HelperGlobal.createException(session, CustomLabels_ES.error_validar_usuario, "error");
				}
			} else {
				final Equipo equipo = gEquipos.getEquipoById(lstMiembro.get(0).getEquipoId());
				final String labelDyn = equipo.getUsuarioCreador() == user_buscado.getId() ? CustomLabels_ES.error_assign_creator_equipo : CustomLabels_ES.error_assign_equipo;
				HelperGlobal.createException(session, labelDyn, "error");
			}
		} else {
			HelperGlobal.createException(session, CustomLabels_ES.error_convertir_usuario, "error");
		}
		return mav;
	}
	
	@RequestMapping(path="insertarUsuario", method=RequestMethod.POST) 
	public ModelAndView insertarUsuario(HttpSession session,
										@Valid @RequestParam("nombre") String nombre,
										@Valid @RequestParam("password") String password,
										@Valid @RequestParam("email") String email,
										@Valid @RequestParam("usuario") String usuario,
										@Valid @RequestParam("rol") String rol) {
		ModelAndView mav = null;
		// Check if exist user
		
		if (gUsuarios.buscarUsuario(usuario, email).isEmpty()) {
			// Check if password has 5 length minimum
			if (HelperGlobal.validatePassword(password)) {				
				Usuario usuarioNew = new Usuario();
				usuarioNew.setNombre(nombre);
				usuarioNew.setPassword(password);
				usuarioNew.setUsuario(usuario);
				usuarioNew.setEmail(email);
				usuarioNew.setRol(rol);
				gUsuarios.insertar(usuarioNew);
				HelperGlobal.createException(session, CustomLabels_ES.verify_admin_user, "success");
				mav = new ModelAndView("redirect:/");
			} else {
				session.setAttribute("errorRegistro", CustomLabels_ES.error_password);
				mav = new ModelAndView("creacionUsuario");
			}
		} else {
			session.setAttribute("errorRegistro", CustomLabels_ES.error_exist_user);
			mav = new ModelAndView("creacionUsuario");
		}
		return mav;
	}
	
	@ResponseBody
	@RequestMapping(path="borrar/{id}", method=RequestMethod.POST) 
	public String borrar(@PathVariable("id") int itemId, HttpSession session) {
		String ret = "";
		try {
			Usuario user_buscado = gUsuarios.buscar(itemId);
			if (user_buscado != null) {
				final List<Miembro> lstMember = gMiembros.getMiembroByUserId(itemId);
				if (lstMember.isEmpty()) {				
					gUsuarios.borrar(user_buscado);			
					ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(200, CustomLabels_ES.success_borrado));
				} else {
					ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, CustomLabels_ES.error_borrar_responsableAsignado));					
				}
			}
		} catch (Exception e) {
			ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, null));
		}
		return ret;
	}
	
	@ResponseBody
	@RequestMapping(path="getUsersResp", method=RequestMethod.GET) 
	public String getUsuariosResponsables(HttpSession session) {
		final List<UsuariosGestor.UserMember> lstUsuariosResponsables = 
					(List<UsuariosGestor.UserMember>) gUsuarios.getAllResponsables();
		String ret = "";
		if (lstUsuariosResponsables.isEmpty()) {			
			ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, null));
		} else {
			ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(200, SaveAndResponseWrapper.convertJSON(lstUsuariosResponsables)));			
		}
		return ret;
	}
	
	@ResponseBody
	@RequestMapping(path="getUsersMemberTeam/{id}", method=RequestMethod.GET) 
	public String getUsuariosResponsables(@PathVariable("id") int itemId, HttpSession session) {
		final List<UsuariosGestor.UserMember> lstUsuariosResponsables = 
					(List<UsuariosGestor.UserMember>) gUsuarios.getUsersMemberTeam(itemId);
		String ret = "";
		if (lstUsuariosResponsables.isEmpty()) {			
			ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, null));
		} else {
			ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(200, SaveAndResponseWrapper.convertJSON(lstUsuariosResponsables)));			
		}
		return ret;
	}
	
	@RequestMapping(path="editarUser", method=RequestMethod.POST) 
	public ModelAndView editarUser(HttpSession session,
										@Valid @RequestParam("id") Integer id,
										@Valid @RequestParam("nombre") String nombre,
										@Valid @RequestParam("password") String password,
										@Valid @RequestParam("email") String email,
										@Valid @RequestParam("redirect") String redirect,HttpServletRequest request) {
		String referer = "redirect:"+redirect;
		ModelAndView mav = null;
		// Check if exist user
		try {
			if (gUsuarios.buscar(id) != null) {
				// Check if password has 5 length minimum
				if (HelperGlobal.validatePassword(password) || password.isEmpty()) {				
					Usuario usuario = new Usuario();
					usuario.setId(id);
					usuario.setNombre(nombre);
					usuario.setPassword(password);
					usuario.setEmail(email);
					gUsuarios.update(usuario);
					HelperGlobal.createException(session, CustomLabels_ES.success_edit_user, "success");
					mav = new ModelAndView(referer);
				} else {
					HelperGlobal.createException(session, CustomLabels_ES.error_password, "error");
					mav = new ModelAndView(referer);
				}
			} else {
				HelperGlobal.createException(session, CustomLabels_ES.error_no_exist_user, "error");
				mav = new ModelAndView(referer);
			}
		}
		catch(Exception e) {
			HelperGlobal.createException(session, CustomLabels_ES.error_edit_user, "error");
			mav = new ModelAndView(referer);
		}
		return mav;
	}
	
}
