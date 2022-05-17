package controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import models.entity.Miembro;
import models.entity.Usuario;
import models.logic.MiembrosGestor;
import models.logic.NoticiasGestor;

@Controller
@RequestMapping("/")
public class Init_Controlador {
	
	@Autowired
	private NoticiasGestor gNoticias;
	@Autowired
	private MiembrosGestor gMiembros;

	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView index(HttpSession session) {
		String page = "index";
		final Object userActive = session.getAttribute("usuario");
		final Usuario user = (Usuario) userActive;
		if (user != null) {
			if (user.getRol().equals("admin")) {
				page = "index";
			} else if (user.getRol().equals("responsable")) {
				page = "portalResponsable";
				List<Miembro> lstMiembros = gMiembros.getMiembroByUserId(user.getId());
				if (lstMiembros.isEmpty()) {
					page = "redirect:/portalResponsable/wizard";
				} else {
					page = "redirect:/portalResponsable/" + lstMiembros.get(0).getEquipoId();					
				}
			}
		}
		final ModelAndView mav = new ModelAndView(page);
		session.setAttribute("sectionActive", "Home");
		mav.addObject("listaNoticias", gNoticias.getLstNoticias());
		return mav;
	}

	@RequestMapping(path="cerrarSesion", method=RequestMethod.GET) 
	public ModelAndView cerrarSesion(HttpSession session) {
		session.invalidate();
		final ModelAndView mav = new ModelAndView("redirect:/");
		return mav;
	}

}
