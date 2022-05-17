package controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import helpers.CustomLabels_ES;
import helpers.HelperGlobal;
import helpers.SaveAndResponseWrapper;
import models.entity.Equipo;
import models.entity.Miembro;
import models.entity.Usuario;
import models.logic.EquiposGestor;
import models.logic.MiembrosGestor;

public class Miembros_Controlador {
	
	public String creaMiembroEquipo(int itemId, int userId, HttpSession session, MiembrosGestor gMiembros) {
		String ret = "";
		try {
			final String views[] = {"redirect:/"};
			final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
			final Boolean securityAccess = (Boolean) listSecurity.get(0);
			if (securityAccess) {
				final Miembro miembro = new Miembro();
				miembro.setEquipoId(itemId);
				miembro.setUsuarioId(userId);
				gMiembros.insertar(miembro);
				ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(200, CustomLabels_ES.success_create_miembro));
			} else {
				ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, CustomLabels_ES.error_ruta));
			}
		} catch (Exception e) {
			ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, CustomLabels_ES.error_create_miembro));
		}
		return ret;
	}
	
	public String borrar(@PathVariable("id") int itemId, HttpSession session, MiembrosGestor gMiembros) {
		String ret = "";
		try {
			final String views[] = {"redirect:/"};
			final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
			final Boolean securityAccess = (Boolean) listSecurity.get(0);
			if (securityAccess) {
				gMiembros.borrar(itemId);
				ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(200, CustomLabels_ES.success_borrar_miembro));
			} else {
				ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, CustomLabels_ES.error_ruta));
			}
		} catch (Exception e) {
			ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, CustomLabels_ES.error_borrar_miembro));
		}
		return ret;
	}
	

}
