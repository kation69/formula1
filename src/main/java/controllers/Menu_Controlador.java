package controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import helpers.HelperGlobal;
import models.entity.Usuario;
import models.logic.CircuitosGestor;
import models.logic.EquiposGestor;
import models.logic.NoticiasGestor;
import models.logic.UsuariosGestor;
import models.logic.VotarListaPilotos;
import models.logic.VotosGestor;
import models.logic.PilotosGestor;

@Controller
@Scope("request")
public class Menu_Controlador {

	@Autowired
	private VotosGestor gVotaciones;
	@Autowired
	private NoticiasGestor gNoticias;
	@Autowired
	private UsuariosGestor gUsuarios;
	@Autowired
	private CircuitosGestor gCircuitos;
	@Autowired
	private PilotosGestor gPilotos;
	@Autowired
	private EquiposGestor gEquipos;
	@Autowired
	private VotarListaPilotos gListaPilotos;
	
	// PUBLIC ENDPOINTS

	@RequestMapping(path="calendario", method=RequestMethod.GET) 
	public ModelAndView calendarizar(HttpServletRequest request,HttpSession session) {
		final ModelAndView mav = new ModelAndView("gestorCircuitos");
		mav.addObject("circuitosPersist", gCircuitos.getAllCircuits());
		return mav;
	}
	@RequestMapping(path="listaVotaciones", method=RequestMethod.GET) 
	public ModelAndView votaciones(HttpServletRequest request,HttpSession session) {
		ModelAndView mav = new ModelAndView("gestorVotaciones");			
		session.setAttribute("sectionActive", "GestionVotaciones");
		mav.addObject("listaVotaciones", gVotaciones.getLstVotos());
		mav.addObject("listaPilotos", gPilotos.getLstPilotos());
		
		return mav;
	}
	
	// ADMIN ENDPOINTS
	@RequestMapping(path="gestorNoticias", method=RequestMethod.GET) 
	public ModelAndView gestorNoticias(HttpSession session) {
		final String views[] = {"gestorNoticias", "redirect:/"};
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		ModelAndView mav = (ModelAndView) listSecurity.get(1);
		final Usuario user = HelperGlobal.castingUserObj(session);
		if((Boolean) listSecurity.get(0) && HelperGlobal.accessPortalAdmin(user)) {			
			session.setAttribute("sectionActive", "GestionNoticias");
			mav.addObject("listaNoticias", gNoticias.getLstNoticias());
		} else {
			mav = (ModelAndView) listSecurity.get(2);
		}
		return mav; 
	}
	
	@RequestMapping(path="gestorVotaciones", method=RequestMethod.GET) 
	public ModelAndView gestorVotaciones(HttpSession session) {
		final String views[] = {"gestorVotaciones"};
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		ModelAndView mav = (ModelAndView) listSecurity.get(1);
		final Usuario user = HelperGlobal.castingUserObj(session);
		if((Boolean) listSecurity.get(0) && HelperGlobal.accessPortalAdmin(user)) {			
			session.setAttribute("perfil", "admin");
			session.setAttribute("sectionActive", "GestionVotaciones");
			mav.addObject("listaVotaciones", gVotaciones.getLstVotos());
			mav.addObject("listaPilotos", gPilotos.getLstPilotos());
			//mav.addObject("listaPilotos", gListaPilotos.listar(null));
		} else {
			mav = (ModelAndView) listSecurity.get(2);
		}
		return mav; 
	}
	
	@RequestMapping(path="gestorUsuarios", method=RequestMethod.GET) 
	public ModelAndView gestorUsuarios(HttpSession session) {
		final String views[] = {"gestorUsuarios"};
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		ModelAndView mav = (ModelAndView) listSecurity.get(1);
		final Usuario user = HelperGlobal.castingUserObj(session);
		if((Boolean) listSecurity.get(0) && HelperGlobal.accessPortalAdmin(user)) {			
			mav.addObject("lstUsuarios", gUsuarios.getListaUsuarios());
			session.setAttribute("sectionActive", "GestionUsuarios");
			session.setAttribute("userid", ((Usuario)session.getAttribute("usuario")).getId());
		} else {
			mav = (ModelAndView) listSecurity.get(2);
		}
		return mav;
	}
	
	@RequestMapping(path="gestorEquipos", method=RequestMethod.GET) 
	public ModelAndView gestorEquipos(HttpSession session) {
		final String views[] = {"gestorEquipos"};
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		ModelAndView mav = (ModelAndView) listSecurity.get(1);
		final Usuario user = HelperGlobal.castingUserObj(session);
		if((Boolean) listSecurity.get(0) && HelperGlobal.accessPortalAdmin(user)) {			
			mav.addObject("lstEquipos", gEquipos.getLstEquiposUsuario());
			session.setAttribute("sectionActive", "GestionEquipos");
		} else {
			mav = (ModelAndView) listSecurity.get(2);
		}
		return mav;
	}
	
	@RequestMapping(path="gestorCircuitos", method=RequestMethod.GET) 
	public ModelAndView gestorCircuitos(HttpSession session) {
		final String views[] = {"gestorCircuitos"};
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		ModelAndView mav = (ModelAndView) listSecurity.get(1);
		final Usuario user = HelperGlobal.castingUserObj(session);
		if((Boolean) listSecurity.get(0) && HelperGlobal.accessPortalAdmin(user)) {			
			session.setAttribute("stepModalCircuito", "init");
			session.setAttribute("sectionActive", "GestionCircuitos");
			session.setAttribute("perfil", "admin");
			mav.addObject("circuitosPersist", gCircuitos.getAllCircuits());
		} else {
			mav = (ModelAndView) listSecurity.get(2);
		}	
		return mav;
	}
	
	
	
	
}
