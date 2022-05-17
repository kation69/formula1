package controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import helpers.CustomLabels_ES;
import helpers.HelperGlobal;
import helpers.SaveAndResponseWrapper;
import models.entity.Circuito;
import models.entity.Coches;
import models.entity.Consumo;
import models.entity.ERS;
import models.entity.Equipo;
import models.entity.Miembro;
import models.entity.Usuario;
import models.entity.Piloto;
import models.logic.CircuitosGestor;
import models.logic.CochesGestor;
import models.logic.EquiposGestor;
import models.logic.MiembrosGestor;
import models.logic.PilotosGestor;
import models.logic.UsuariosGestor;

@Controller
@Scope("request")
@RequestMapping("portalResponsable")
public class PortalResponsable_Controlador {

	@Autowired
	private MiembrosGestor gMiembros;
	@Autowired
	private EquiposGestor gEquipos;
	@Autowired
	private CochesGestor gCoches;
	@Autowired
	private PilotosGestor gPilotos;
	@Autowired
	private UsuariosGestor gUsuarios;
	@Autowired
	private CircuitosGestor gCircuitos;

	/**
	 * @param: itemId (team id)
	 * @param: method (GET)
	 */
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ModelAndView portalResponsable(@PathVariable("id") int itemId, HttpSession session) {
		final String views[] = { "portalResponsable/wizard", "portalResponsable" };
		final Usuario user = HelperGlobal.castingUserObj(session);
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		final Boolean securityAccess = (Boolean) listSecurity.get(0);
		ModelAndView mav = (ModelAndView) listSecurity.get(1);
		if (securityAccess) {
			// Lookup up miembro by usuario_id
			final Boolean accessAdmin = HelperGlobal.accessPortalAdmin(user);
			if (gMiembros.hasMemberTeam(user.getId(), itemId) != null || accessAdmin) {
				mav = (ModelAndView) listSecurity.get(2);
				mav = setCommonVariables(mav, itemId, accessAdmin);
				session.setAttribute("rolUser", user.getRol());
			}
		}
		return mav;
	}

	@RequestMapping(path = "/wizard", method = RequestMethod.GET)
	public ModelAndView ModelAndView(HttpSession session) {
		final String views[] = { "wizardEquipo", "redirect:/" };
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		final Usuario user = HelperGlobal.castingUserObj(session);
		ModelAndView mav = (ModelAndView) listSecurity.get(2);
		final Boolean withoutMember = gMiembros.getMiembroByUserId(user.getId()).isEmpty();
		if (withoutMember && user.getRol().equals("responsable")) {
			mav = (ModelAndView) listSecurity.get(1);
		}
		return mav;
	}

	/**
	 * MIEMBROS ENDPOINT
	 * 
	 * @description: gestor de miembros para el portal responsable
	 * @param: itemId (team id)
	 */
	@RequestMapping(path = "/{id}/gestorMiembros", method = RequestMethod.GET)
	public ModelAndView gestorMiembros(@PathVariable("id") int itemId, HttpSession session) {
		final String views[] = { "portalGestorMiembros", "redirect:/" };
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		ModelAndView mav = (ModelAndView) listSecurity.get(1);
		if ((Boolean) listSecurity.get(0)) {
			final Usuario user = HelperGlobal.castingUserObj(session);
			// Lookup up miembro by usuario_id
			final Boolean accessAdmin = HelperGlobal.accessPortalAdmin(user);
			final List<Integer> lstUsuariosId = gMiembros.getMiembrosByEquipo(itemId);
			final Equipo equipo = gEquipos.getEquipoById(itemId);
			final List<UsuariosGestor.UserMember> lstUsuarios = gUsuarios.getUsuariosByListIds(lstUsuariosId, itemId, equipo.getUsuarioCreador(), user);

			final Miembro hasMember = gMiembros.hasMemberTeam(user.getId(), itemId);
			if (hasMember != null || accessAdmin) {
				session.setAttribute("userid", user.getId());
				session.setAttribute("sectionActive", "Portal_GestorMiembros");
				mav = setCommonVariables(mav, itemId, accessAdmin);
				mav.addObject("lstMiembros", lstUsuarios);
			}
		} else {
			mav = (ModelAndView) listSecurity.get(2);
		}
		return mav;
	}

	@ResponseBody
	@RequestMapping(path = "/{id}/creaMiembro/{userId}", method = RequestMethod.GET)
	public String creaMiembroEquipo(@PathVariable("id") int itemId, @PathVariable("userId") int userId,
			HttpSession session) {
		final Miembros_Controlador miembrosController = new Miembros_Controlador();
		return miembrosController.creaMiembroEquipo(itemId, userId, session, gMiembros);
	}

	@ResponseBody
	@RequestMapping(path = "/gestorMiembros/borrar/{id}", method = RequestMethod.POST)
	public String borrar(@PathVariable("id") int itemId, HttpSession session) {
		final Miembros_Controlador miembrosController = new Miembros_Controlador();
		return miembrosController.borrar(itemId, session, gMiembros);
	}

	/**
	 * EQUIPOS ENDPOINT
	 * 
	 * @description: gestor de miembros para el portal responsable
	 * @param: itemId (team id)
	 */
	@RequestMapping(path = "/{id}/tuEquipo", method = RequestMethod.GET)
	public ModelAndView verTuEquipo(@PathVariable("id") int itemId, HttpSession session) {
		Equipo_Controlador equipoController = new Equipo_Controlador();
		return equipoController.verTuEquipo(itemId, session, gUsuarios, gMiembros, gEquipos);		
	}

	@RequestMapping(path = "/creaEquipo", method = RequestMethod.POST)
	public ModelAndView creaEquipo(@RequestParam CommonsMultipartFile file, HttpServletRequest request,
			HttpSession session) {
		Equipo_Controlador equipoController = new Equipo_Controlador();
		return equipoController.creaEquipo(file, request, session, gEquipos, gMiembros);
	}
	@ResponseBody
	@RequestMapping(path="/gestorEquipos/borrar/{id}", method=RequestMethod.POST) 
	public String borrarEquipos(@PathVariable("id") int itemId, HttpSession session) {
		Equipo_Controlador equipoController = new Equipo_Controlador();
		return equipoController.borrarEquipo(itemId, session, gEquipos, gMiembros);
	}
	@RequestMapping(path="/{id}/gestorEquipos/editarEquipo", method=RequestMethod.POST)
	public ModelAndView editarCoche(@PathVariable("id") int itemId, @RequestParam CommonsMultipartFile file,HttpServletRequest request,HttpSession session) {
		Equipo_Controlador equipoController = new Equipo_Controlador();
		return equipoController.editarEquipo(itemId, file, request, session, gEquipos, gMiembros);
	}
	@RequestMapping(path="/{id}/gestorEquipos/reasignCreador/{type}", method=RequestMethod.POST)
	public ModelAndView reasignCreador(@PathVariable("id") int itemId,@PathVariable("type") int type ,HttpServletRequest request,HttpSession session) {
		Equipo_Controlador equipoController = new Equipo_Controlador();
		return equipoController.reasignCreador(itemId, type, request, session, gEquipos, gMiembros);
	}
	
	/*
	 * COCHES ENDPOINT
	 * 
	 * @Description: coches endpoint
	 * 
	 */
	@RequestMapping(path = "/{id}/gestorCoches", method = RequestMethod.GET)
	public ModelAndView verGestorCoches(@PathVariable("id") int itemId, HttpSession session) {
		final String views[] = { "tusCoches", "redirect:/" };
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		ModelAndView mav = (ModelAndView) listSecurity.get(1);
		if ((Boolean) listSecurity.get(0)) {
			final Usuario user = HelperGlobal.castingUserObj(session);
			// Lookup up miembro by usuario_id
			final Boolean accessAdmin = HelperGlobal.accessPortalAdmin(user);
			if (gMiembros.hasMemberTeam(user.getId(), itemId) != null || accessAdmin) {
				session.setAttribute("sectionActive", "Portal_TuEquipo");
				final List<Coches> cochesDetail = gCoches.getLstCoches(itemId);
				mav = setCommonVariables(mav, itemId, accessAdmin);
				mav.addObject("cochesDetail", cochesDetail);
			}
		} else {
			mav = (ModelAndView) listSecurity.get(2);
		}
		return mav;
	}

	@ResponseBody
	@RequestMapping(path = "/gestorCoches/borrar/{coche_id}", method = RequestMethod.POST)
	public String borrarCoche(@PathVariable("coche_id") int coche_id, HttpSession session) {
		final Coches_Controlador cochesController = new Coches_Controlador();
		return cochesController.borrarCoche(session, coche_id, gCoches, gMiembros);
	}

	@RequestMapping(path = "/{id}/gestorCoches/insertarCoche", method = RequestMethod.POST)
	public ModelAndView insertarCoche(@RequestParam CommonsMultipartFile file, @PathVariable("id") Integer equipoId,
			HttpServletRequest request, HttpSession session) {
		Coches_Controlador cochesController = new Coches_Controlador();
		return cochesController.insertarCoche(file, equipoId, request, session, gCoches);
	}

	@RequestMapping(path = "/{id}/gestorCoches/editarCoche/{coche_id}", method = RequestMethod.POST)
	public ModelAndView editarCoche(@RequestParam CommonsMultipartFile file, @PathVariable("id") Integer equipoId,
			@PathVariable("coche_id") int coche_id, HttpServletRequest request, HttpSession session) {
		final Coches_Controlador cochesController = new Coches_Controlador();
		return cochesController.editarCoche(file, equipoId, coche_id, request, session, gCoches);
	}

	/*
	 * PILOTOS ENDPOINT
	 * 
	 * @Description: pilotos endpoint
	 * 
	 */

	@RequestMapping(path = "/{id}/gestorPilotos", method = RequestMethod.GET)
	public ModelAndView verGestorPilotos(@PathVariable("id") int itemId, HttpSession session) {
		final String views[] = { "tusPilotos", "redirect:/" };
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		ModelAndView mav = (ModelAndView) listSecurity.get(1);
		if ((Boolean) listSecurity.get(0)) {
			final Usuario user = HelperGlobal.castingUserObj(session);
			// Lookup up miembro by usuario_id
			final Boolean accessAdmin = HelperGlobal.accessPortalAdmin(user);
			if (gMiembros.hasMemberTeam(user.getId(), itemId) != null || accessAdmin) {
				session.setAttribute("sectionActive", "Portal_TuEquipo");
				final List<Piloto> listaPilotos = gPilotos.getLstPilotos();
				mav = setCommonVariables(mav, itemId, accessAdmin);
				mav.addObject("listaPilotos", listaPilotos);
			}
		} else {
			mav = (ModelAndView) listSecurity.get(2);
		}
		return mav;
	}

	@ResponseBody
	@RequestMapping(path = "/gestorPilotos/borrar/{id}", method = RequestMethod.POST)
	public String borrarPiloto(@PathVariable("id") int itemId, HttpSession session) {
		System.out.println("borrarPiloto");
		final Pilotos_Controlador pilotosController = new Pilotos_Controlador();
		return pilotosController.borrar(itemId, session, gPilotos);
	}

	@RequestMapping(path = "/{id}/gestorPilotos/insertarPiloto", method = RequestMethod.POST)
	public ModelAndView insertarPiloto(@RequestParam CommonsMultipartFile file, @PathVariable("id") Integer equipoId,
			HttpServletRequest request, HttpSession session) {
		final Pilotos_Controlador pilotosController = new Pilotos_Controlador();
		return pilotosController.insertarPiloto(file, equipoId, request, session, gPilotos);
	}

	@RequestMapping(path = "/{id}/gestorPilotos/editarPiloto/{piloto_id}", method = RequestMethod.POST)
	public ModelAndView editarPiloto(@RequestParam CommonsMultipartFile file, @PathVariable("id") Integer equipoId,
			@PathVariable("piloto_id") int piloto_id, HttpServletRequest request, HttpSession session) {
		final Pilotos_Controlador pilotosController = new Pilotos_Controlador();
		return pilotosController.editarPiloto(file, equipoId, piloto_id, request, session, gPilotos);
	}
	/*
	 * HERRAMIENTAS ENDPOINT
	 * 
	 * @Description: herramientas endpoints
	 * 
	 */
	@RequestMapping(path = "/{id}/gestorHerramientas", method = RequestMethod.GET)
	public ModelAndView verHerramientas(@PathVariable("id") int itemId, HttpSession session) {
		final String views[] = { "portalHerramientas", "redirect:/" };
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		ModelAndView mav = (ModelAndView) listSecurity.get(1);
		if ((Boolean) listSecurity.get(0)) {
			final Usuario user = HelperGlobal.castingUserObj(session);
			// Lookup up miembro by usuario_id
			final Boolean accessAdmin = HelperGlobal.accessPortalAdmin(user);
			if (gMiembros.hasMemberTeam(user.getId(), itemId) != null || accessAdmin) {
				session.setAttribute("sectionActive", "Portal_Herramientas");
				final List<Coches> lstCoches = gCoches.getLstCoches(itemId);
				final List<Circuito> lstCircuitos = gCircuitos.getAllCircuits();
				if (lstCoches.isEmpty()) {
					//mav.addObject("msgError", CustomLabels_ES.error_herramientas_coches);
				} else if (lstCircuitos.isEmpty()) {					
					//mav.addObject("msgError", CustomLabels_ES.error_herramientas_circuitos);
				} else {
					
					mav.addObject("lstCoches", lstCoches);
					mav.addObject("lstCircuitos", lstCircuitos);					
				}
				mav = setCommonVariables(mav, itemId, accessAdmin);
			}
		} else {
			mav = (ModelAndView) listSecurity.get(2);
		}
		return mav;
	}
	@RequestMapping(path = "/{id}/gestorHerramientas/toolFuel", method = RequestMethod.POST)
	public ModelAndView executeToolFuel(@PathVariable("id") int itemId, HttpServletRequest request,HttpSession session) {
		final String views[] = { "portalHerramCombust", "redirect:/" };
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		String coche = request.getParameter("selectCoche_Combustible");
		String circuito = request.getParameter("selectCircuito_Combustible");
		ModelAndView mav = (ModelAndView) listSecurity.get(1);
		if ((Boolean) listSecurity.get(0)) {
			final Usuario user = HelperGlobal.castingUserObj(session);
			// Lookup up miembro by usuario_id
			final Boolean accessAdmin = HelperGlobal.accessPortalAdmin(user);
			if (gMiembros.hasMemberTeam(user.getId(), itemId) != null || accessAdmin) {
				session.setAttribute("sectionActive", "Portal_Herramientas");
				List<Coches> lstCoches = gCoches.buscarCochesName(coche);
				Circuito circuitoObj = gCircuitos.buscarcircuito(circuito.replace(" ", ""));
				List<Consumo> consumos= new ArrayList<>();
				consumos.add(new Consumo("Ahorrador",Double.valueOf(lstCoches.get(0).getConsumo()*0.95)));
				consumos.add(new Consumo("Normal",Double.valueOf(lstCoches.get(0).getConsumo()*1.25)));
				consumos.add(new Consumo("Deportivo",Double.valueOf(lstCoches.get(0).getConsumo()*1.6)));
				mav.addObject("consumos",consumos);
				List<Consumo> consumosTotales= new ArrayList<>();
				consumosTotales.add(new Consumo("Ahorrador",Double.valueOf(lstCoches.get(0).getConsumo()*circuitoObj.getNumVueltas()*0.95)));
				consumosTotales.add(new Consumo("Media",Double.valueOf(lstCoches.get(0).getConsumo()*circuitoObj.getNumVueltas())));
				consumosTotales.add(new Consumo("Normal",Double.valueOf(lstCoches.get(0).getConsumo()*circuitoObj.getNumVueltas()*1.25)));
				consumosTotales.add(new Consumo("Deportivo",Double.valueOf(lstCoches.get(0).getConsumo()*circuitoObj.getNumVueltas()*1.6)));
				mav.addObject("consumos_totales",consumosTotales);
				mav.addObject("consumo_medio",new Consumo("Media",Double.valueOf(lstCoches.get(0).getConsumo())));
				mav.addObject("coche", lstCoches.get(0));
				mav.addObject("circuito", circuitoObj);
				mav = setCommonVariables(mav, itemId, accessAdmin);
			}
		} else {
			mav = (ModelAndView) listSecurity.get(2);
		}
		return mav;
	}
	@RequestMapping(path = "/{id}/gestorHerramientas/toolKers", method = RequestMethod.POST)
	public ModelAndView executeToolKers(@PathVariable("id") int itemId, HttpServletRequest request,HttpSession session) {
		final String views[] = { "portalHerramKers", "redirect:/" };
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		String coche = request.getParameter("selectCoche_kers");
		String circuito = request.getParameter("selectCircuito_kers");
		String tipo = request.getParameter("selectModoCond_kers");
		ModelAndView mav = (ModelAndView) listSecurity.get(1);
		if ((Boolean) listSecurity.get(0)) {
			final Usuario user = HelperGlobal.castingUserObj(session);
			// Lookup up miembro by usuario_id
			final Boolean accessAdmin = HelperGlobal.accessPortalAdmin(user);
			if (gMiembros.hasMemberTeam(user.getId(), itemId) != null || accessAdmin) {
				session.setAttribute("sectionActive", "Portal_Herramientas");
				List<Coches> lstCoches = gCoches.buscarCochesName(coche);
				Circuito circuitoObj = gCircuitos.buscarcircuito(circuito.replace(" ", ""));
				List<ERS> ers= new ArrayList<>();
				if(tipo.toUpperCase().equals("AHORRADOR +5%") || tipo.toUpperCase().equals("TODOS")) {
					ers.add(new ERS("Ahorrador","rgba(107, 223, 95, 0.8)","rgba(32, 151, 19, 0.8)"
							,lstCoches.get(0).getErsCurvasLentas(),circuitoObj.getCurvasLentas()
							,lstCoches.get(0).getErsCurvasMedias(),circuitoObj.getCurvasMedias()
							,lstCoches.get(0).getErsCurvasRapidas(),circuitoObj.getCurvasRapidas()));
				}
				if(tipo.toUpperCase().equals("NORMAL -25%") || tipo.toUpperCase().equals("TODOS")) {
					ers.add(new ERS("Normal","rgba(207, 139, 108, 0.8)","rgba(255, 115, 10, 0.8)"
							,lstCoches.get(0).getErsCurvasLentas(),circuitoObj.getCurvasLentas()
							,lstCoches.get(0).getErsCurvasMedias(),circuitoObj.getCurvasMedias()
							,lstCoches.get(0).getErsCurvasRapidas(),circuitoObj.getCurvasRapidas()));
				}
				if(tipo.toUpperCase().equals("DEPORTIVO -60%") || tipo.toUpperCase().equals("TODOS")) {
					ers.add(new ERS("Deportivo","rgba(169, 197, 253, 0.8)","rgba(39, 110, 250, 0.8)"
							,lstCoches.get(0).getErsCurvasLentas(),circuitoObj.getCurvasLentas()
							,lstCoches.get(0).getErsCurvasMedias(),circuitoObj.getCurvasMedias()
							,lstCoches.get(0).getErsCurvasRapidas(),circuitoObj.getCurvasRapidas()));
				}
				
				mav.addObject("lstERS",ers);
				mav.addObject("coche", lstCoches.get(0));
				mav.addObject("circuito", circuitoObj);
				mav = setCommonVariables(mav, itemId, accessAdmin);
			}
		} else {
			mav = (ModelAndView) listSecurity.get(2);
		}
		return mav;
	}


	// HELPER METHODS
	public ModelAndView setCommonVariables(ModelAndView mav, int itemId, Boolean accessAdmin) {
		mav.addObject("equipoId", itemId);
		mav.addObject("editPermissions", !accessAdmin);
		return mav;
	}

}
