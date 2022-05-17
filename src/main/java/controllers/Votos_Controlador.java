package controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
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
import models.entity.Piloto;
import models.entity.Voto;
import models.entity.VotoPiloto;
import models.entity.VotoUsuario;
import models.logic.PilotosGestor;
import models.logic.VotarListaPilotos;
import models.logic.VotarPilotos;
import models.logic.VotosGestor;
import java.util.regex.*;

@Controller
@Scope("request")
@RequestMapping("votaciones")
public class Votos_Controlador {

	@Autowired
	private VotosGestor gVotos;
	@Autowired
	private VotarPilotos gVotar;

	@Autowired
	private VotarListaPilotos gListaPilotos;

	@RequestMapping(path = "guardarVotacion", method = RequestMethod.POST)
	public ModelAndView guardarVoto(HttpServletRequest request, HttpSession session) {
		String titulo = request.getParameter("titulo");
		String descripcion = request.getParameter("descripcion");
		Time hora = null;
		Date fecha = null;
		// String pilotos = request.getParameter("pilotos"); // apellidos de pilotos
		String[] pilotos = request.getParameterValues("pilotos");
		if (titulo.strip() == "") {
			session.setAttribute("error", CustomLabels_ES.error_create_voto_titulo);
			return new ModelAndView("redirect:/gestorVotaciones");
		}
		if (descripcion.strip() == "") {
			session.setAttribute("error", CustomLabels_ES.error_create_voto_descripcion);
			return new ModelAndView("redirect:/gestorVotaciones");
		}
		if (!request.getParameter("fecha").isEmpty() && !request.getParameter("hora").isEmpty()) {
			hora = Time.valueOf(request.getParameter("hora") + ":00");
			fecha = Date.valueOf(request.getParameter("fecha"));
		}

		try {

			final Voto voto = new Voto();
			final VotoPiloto votop = new VotoPiloto();
			voto.setTitulo(titulo);
			voto.setDescripcion(descripcion);
			voto.setHora(hora);
			voto.setLimite(fecha);
			List<Voto> lista_votos = gVotos.getLstVotos();
			String link = HelperGlobal.limpiarCaracteres(titulo).replace(" ", "").toLowerCase();
			String linktemp = link;
			Integer link_posible = 0;
			

			for (int i = 0; i < pilotos.length; i++) {
				votop.setNombrePiloto(pilotos[i]);
				System.out.println(pilotos[i]);
				gListaPilotos.insertar(votop);
			}

			for (Voto vot : lista_votos) {

				if (vot.getPermantlink().equalsIgnoreCase(linktemp.strip())) {
					link_posible++;
					linktemp = link + "-" + link_posible.toString();
				}
			}
			voto.setPermantlink(linktemp);

			gVotos.guardar(voto);
		} catch (Exception e) {
			session.setAttribute("error", CustomLabels_ES.error_create_voto);
		}
		return new ModelAndView("redirect:/gestorVotaciones");
	}

	@RequestMapping(path = "editVotacion", method = RequestMethod.POST)
	public ModelAndView editarVotacion(HttpServletRequest request, HttpSession session) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		String titulo = request.getParameter("titulo");
		String descripcion = request.getParameter("descripcion");
		Time hora = null;
		Date fecha = null;
		
		String [] pilotos = request.getParameterValues("pilotos");
		
		if (titulo.strip() == "") {
			session.setAttribute("error", CustomLabels_ES.error_create_voto_titulo);
			return new ModelAndView("redirect:/gestorVotaciones");
		}
		if (descripcion.strip() == "") {
			session.setAttribute("error", CustomLabels_ES.error_create_voto_descripcion);
			return new ModelAndView("redirect:/gestorVotaciones");
		}
		if (!request.getParameter("fecha").isEmpty() && !request.getParameter("hora").isEmpty()) {
			hora = Time.valueOf(request.getParameter("hora") + ":00");
			fecha = Date.valueOf(request.getParameter("fecha"));
		}

		try {

			final Voto voto = new Voto();
			final VotoPiloto votop = new VotoPiloto();
			voto.setId(id);
			voto.setTitulo(titulo);
			voto.setDescripcion(descripcion);
			voto.setHora(hora);
			voto.setLimite(fecha);
			gVotos.editar(voto);
			
			for(int i=0; i<pilotos.length; i++) {
				votop.setNombrePiloto(pilotos[i]);
				gListaPilotos.insertar(votop);
			}
			
		} catch (Exception e) {
			session.setAttribute("error", CustomLabels_ES.error_create_voto);
		}
		return new ModelAndView("redirect:/gestorVotaciones");
	}

	@RequestMapping(path = "finalizar/{id}", method = RequestMethod.GET)
	public ModelAndView finalizarVotacion(@PathVariable("id") int itemId, HttpServletRequest request,
			HttpSession session) {

		try {
			Voto voto = gVotos.getVoto_id(itemId);
			Date dia = new Date(System.currentTimeMillis());

			Time hora = new Time(System.currentTimeMillis() - 60000);
			voto.setHora(hora);
			voto.setLimite(dia);
			gVotos.editar(voto);
		} catch (Exception e) {
			session.setAttribute("error", CustomLabels_ES.error_create_voto);
		}
		return new ModelAndView("redirect:/gestorVotaciones");
	}

	@RequestMapping(path = "activar/{id}", method = RequestMethod.GET)
	public ModelAndView activarVotacion(@PathVariable("id") int itemId, HttpServletRequest request,
			HttpSession session) {

		try {
			Voto voto = gVotos.getVoto_id(itemId);
			voto.setHora(null);
			voto.setLimite(null);
			gVotos.editar(voto);
		} catch (Exception e) {
			session.setAttribute("error", CustomLabels_ES.error_create_voto);
		}
		return new ModelAndView("redirect:/gestorVotaciones");
	}

	@RequestMapping(path = "ver", method = RequestMethod.GET)
	public ModelAndView verVotacion(@RequestParam(value = "pag", required = true) String pagina,
			HttpServletRequest request, HttpSession session) {
		String page = "redirect:/";
		try {
			if (!pagina.isBlank()) {
				Voto voto = gVotos.getVoto(pagina);
				if (voto.getHora() != null && voto.getLimite() != null) {
					Date dia = new Date(System.currentTimeMillis());

					Time hora = new Time(System.currentTimeMillis());
					if (voto.getLimite().compareTo(dia) < 1) {
						if (voto.getHora().compareTo(hora) > 0) {
							session.setAttribute("votacion_acabada", "false");
						} else {
							session.setAttribute(String.format("votacion_%d", voto.getId()), true);
							session.setAttribute("votacion_acabada", "true");
						}
					} else {
						session.setAttribute("votacion_acabada", "false");
					}
				} else {
					session.setAttribute("votacion_acabada", "false");
				}
				if (voto != null) {
					session.setAttribute("id_votacion", voto.getId());
					session.setAttribute("titulo", voto.getTitulo());
					session.setAttribute("descripcion", voto.getDescripcion());
					page = "ver_votacion";
				}
				List<VotoUsuario> Votos_Realizados = gVotar.listar(voto.getId());

				List<Integer> cantidades_grupo = new ArrayList<Integer>();
				List<String> listado_pilotos = new ArrayList<String>();
				for (VotoUsuario votados : Votos_Realizados) {
					cantidades_grupo.add(votados.getContador());
					listado_pilotos.add("\"" + votados.getNombrePiloto() + "\"");
				}
				session.setAttribute("cantidades_grupo", cantidades_grupo);
				session.setAttribute("listado_pilotos", listado_pilotos);
			}

		} catch (Exception ex) {
		}
		return new ModelAndView(page);
	}

	@RequestMapping(path = "ver/{id}", method = RequestMethod.GET)
	public ModelAndView verVotacion_id(@PathVariable("id") int itemId, HttpServletRequest request,
			HttpSession session) {
		String page = "redirect:/";
		try {
			if (itemId > 0) {
				Voto voto = gVotos.getVoto_id(itemId);
				if (voto.getHora() != null && voto.getLimite() != null) {
					Date dia = new Date(System.currentTimeMillis());

					Time hora = new Time(System.currentTimeMillis());
					if (voto.getLimite().compareTo(dia) < 1) {
						if (voto.getHora().compareTo(hora) > 0) {
							session.setAttribute("votacion_acabada", "false");
						} else {
							session.setAttribute(String.format("votacion_%d", voto.getId()), true);
							session.setAttribute("votacion_acabada", "true");
						}
					} else {
						session.setAttribute("votacion_acabada", "false");
					}
				} else {
					session.setAttribute("votacion_acabada", "false");
				}
				if (voto != null) {
					session.setAttribute("id_votacion", voto.getId());
					session.setAttribute("titulo", voto.getTitulo());
					session.setAttribute("descripcion", voto.getDescripcion());
					page = "ver_votacion";
				}
				List<VotoUsuario> Votos_Realizados = gVotar.listar(voto.getId());

				List<Integer> cantidades_grupo = new ArrayList<Integer>();
				List<String> listado_pilotos = new ArrayList<String>();
				for (VotoUsuario votados : Votos_Realizados) {
					cantidades_grupo.add(votados.getContador());
					listado_pilotos.add("\"" + votados.getNombrePiloto() + "\"");
				}

				session.setAttribute("cantidades_grupo", cantidades_grupo);
				session.setAttribute("listado_pilotos", listado_pilotos);

			}

		} catch (Exception ex) {
		}
		return new ModelAndView(page);
	}

	@ResponseBody
	@RequestMapping(path = "borrar/{id}", method = RequestMethod.POST)
	public String borrar(@PathVariable("id") int itemId, HttpSession session) {
		String ret = "";
		try {
			gVotos.borrar(itemId);
			ret = SaveAndResponseWrapper
					.convertToJson(new SaveAndResponseWrapper(200, CustomLabels_ES.success_delete_votacion));
		} catch (Exception e) {
			ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, null));
		}
		return ret;
	}

	@RequestMapping(path = "votar/{id}", method = RequestMethod.POST)
	public ModelAndView votar(@PathVariable("id") Integer itemId, HttpServletRequest request, HttpSession session) {
		String page = "redirect:/votaciones/ver/" + itemId.toString();
		try {
			String regx = "^(.+)@(.+)$";
			Pattern pattern = Pattern.compile(regx);
			VotoUsuario voto = new VotoUsuario();
			voto.setId(itemId);
			voto.setEmail(request.getParameter("correo"));
			Matcher matcher = pattern.matcher(voto.getEmail());
			if (matcher.matches() == false) {
				HelperGlobal.createException(session, CustomLabels_ES.error_votar_email, "error");
			} else {
				String piloto = request.getParameter("pilotos");
				if (piloto == null) {
					HelperGlobal.createException(session, CustomLabels_ES.error_votar_piloto, "error");
				} else if (piloto.isEmpty() || piloto.isBlank()) {
					HelperGlobal.createException(session, CustomLabels_ES.error_votar_piloto, "error");
				} else {
					if (piloto.equals("otro_piloto")) {
						piloto = request.getParameter("otro_piloto");
					}
					if (piloto == null) {
						HelperGlobal.createException(session, CustomLabels_ES.error_votar_piloto, "error");
					} else if (piloto.isEmpty() || piloto.isBlank()) {
						HelperGlobal.createException(session, CustomLabels_ES.error_votar_piloto, "error");
					} else {
						voto.setNombrePiloto(piloto);
						Boolean votado_anterior = gVotar.insert(voto);
						session.setAttribute(String.format("votacion_%d", itemId), true);
						if (votado_anterior) {
							HelperGlobal.createException(session, CustomLabels_ES.error_votar_repetido, "error");
						} else {
							HelperGlobal.createException(session, CustomLabels_ES.success_votar, "success");
						}
					}

				}
			}

		} catch (Exception ex) {
			HelperGlobal.createException(session, CustomLabels_ES.error_votar, "error");
		}
		return new ModelAndView(page);
	}

}
