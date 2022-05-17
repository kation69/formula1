package controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import helpers.CustomLabels_ES;
import helpers.HelperGlobal;
import helpers.SaveAndResponseWrapper;
import models.entity.Equipo;
import models.entity.Miembro;
import models.entity.Noticia;
import models.entity.Usuario;
import models.logic.EquiposGestor;
import models.logic.MiembrosGestor;
import models.logic.UsuariosGestor;

public class Equipo_Controlador {
	
	public ModelAndView creaEquipo(CommonsMultipartFile file, HttpServletRequest request, HttpSession session,
			EquiposGestor gEquipos, MiembrosGestor gMiembros) {
		String page = "redirect:/";
		final Usuario user = HelperGlobal.castingUserObj(session);
		String nombre = request.getParameter("nombre");
		String twitter = request.getParameter("twitter");
		List<Equipo> lstEquipo = gEquipos.buscarEquiposName(nombre);
		if (lstEquipo.isEmpty()) {
			String filename = HelperGlobal.saveImageFile(file, session, "/resources/imgEquipos");
			if (filename == null) {	
				HelperGlobal.createException(session, CustomLabels_ES.error_create_equipos_imagen, "error");
				return new ModelAndView("redirect:/");		        	
			}
			Equipo equipo = new Equipo();
			equipo.setNombre(nombre);
			equipo.setTwitter(twitter);
			equipo.setLogo(filename);
			equipo.setFechaCreacion(new Date());
			equipo.setUsuarioCreador(user.getId());
			equipo = gEquipos.insertar(equipo);
			equipo = gEquipos.buscarEquiposName(nombre).get(0);
			
			Miembro miembro = new Miembro();
			miembro.setEquipoId(Integer.valueOf(equipo.getId()));
			miembro.setUsuarioId(user.getId());
			miembro = gMiembros.insertar(miembro);
		} else {
			HelperGlobal.createException(session, CustomLabels_ES.error_create_equipos_nameRepeat, "error");
			page = "redirect:/error";
		}
		return new ModelAndView(page);
	}
	
	public ModelAndView verTuEquipo(int itemId, HttpSession session, UsuariosGestor gUsuarios,MiembrosGestor gMiembros, EquiposGestor gEquipos) {
		final String views[] = {"tuEquipo", "redirect:/"};
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		ModelAndView mav = (ModelAndView) listSecurity.get(1);
		if((Boolean) listSecurity.get(0)) {
			final Usuario user = HelperGlobal.castingUserObj(session);
			// Lookup up miembro by usuario_id
			final Boolean accessAdmin = HelperGlobal.accessPortalAdmin(user);
			final Miembro memberTeam = gMiembros.hasMemberTeam(user.getId(), itemId);
			if (memberTeam != null || accessAdmin) {
				session.setAttribute("sectionActive", "Portal_TuEquipo");
				final Equipo equipoDetail = gEquipos.getEquipoById(itemId);
				final Boolean privileges = equipoDetail.getUsuarioCreador() == user.getId() ? true : false;
				final Usuario userCreador = gUsuarios.buscar(equipoDetail.getUsuarioCreador());
				mav.addObject("equipoId", itemId);
				mav.addObject("equipoDetail", equipoDetail);
				mav.addObject("usuarioCreador", userCreador.getNombre());
				mav.addObject("privilegesCreater", privileges);
				mav.addObject("accessAdmin", accessAdmin);
				if (memberTeam != null) {
					mav.addObject("userMemberId", memberTeam.getId());
				}
				mav.addObject("confirmBorrarEquipo_lbl", CustomLabels_ES.confirmBorrarEquipo_lbl);
				mav.addObject("confirmAbandonar_label", CustomLabels_ES.msg_abandonar_equipo);
			} else {
				mav = (ModelAndView) listSecurity.get(2);				
			}
		} else {
			mav = (ModelAndView) listSecurity.get(2);
		}
		return mav;
	}
	
	public String borrarEquipo(int itemId, HttpSession session, EquiposGestor gEquipos, MiembrosGestor gMiembros) {
		final String views[] = {"redirect:/"};
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		String ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, "error generico"));
		if((Boolean) listSecurity.get(0)) {
			try {
				final Usuario user = HelperGlobal.castingUserObj(session);
				final Equipo equipo = gEquipos.getEquipoById(itemId);
				if (user.getId() == equipo.getUsuarioCreador()) {					
					gEquipos.borrarEquipo(equipo);
					ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(200, "success"));
				} else {
					HelperGlobal.createException(session, "error", "error");
					ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, "error catch"));					
				}
			} catch (Exception e) {
				HelperGlobal.createException(session, "error", "error");
				ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, "error catch"));
			}
		}
		return ret;
	}
	
	public ModelAndView editarEquipo(int itemId,CommonsMultipartFile file, HttpServletRequest request, HttpSession session, EquiposGestor gEquipos, MiembrosGestor gMiembros) {
		String page = "redirect:/portalResponsable/"+itemId+"/tuEquipo";
		Equipo equipoDetail = gEquipos.getEquipoById(itemId);
		final Usuario user = HelperGlobal.castingUserObj(session);
		if (equipoDetail.getUsuarioCreador() == user.getId()) {			
			String nombreEquipo = request.getParameter("nombreEquipo");
			String twitterEquipo= request.getParameter("twitterEquipo");
			if (nombreEquipo.strip() == "") {
				HelperGlobal.createException(session, CustomLabels_ES.error_nombreEquipo_obligatorio, "error");
				return new ModelAndView("redirect:/");
			}
			if (twitterEquipo.strip()=="") {
				HelperGlobal.createException(session, CustomLabels_ES.error_twitterEquipo_obligatorio, "error");
				return new ModelAndView("redirect:/");
			}
			//Foto?
			String filename=file.getOriginalFilename().replace(" ", "");
			if (filename!=""){
				String ruta=session.getServletContext().getRealPath("/resources/imgEquipos");  
		          
		        String directory=Paths.get(ruta,filename).toString();
		        
				File fichero = new File(directory);
			    if(fichero.exists()) {		    	
					String tipo= FilenameUtils.getExtension(filename);
			    	String nombre = filename.substring(0,filename.length()-tipo.length()-1);
			    	Integer alternativa=1;
			    	
			    	while(true) {
			    		filename=nombre+"-"+alternativa.toString()+"."+tipo;
			    		directory=Paths.get(ruta,filename).toString();
			    		fichero = new File(directory);
			    		if(fichero.exists()) {
			    			alternativa++;
			    		}
			    		else {
			    			break;
			    		}
					}
			    }
		        try{  
			        byte barr[]=file.getBytes();  
			          
			        BufferedOutputStream bout=new BufferedOutputStream(  
			                 new FileOutputStream(Paths.get(ruta,filename).toString()));  
			        bout.write(barr);  
			        bout.flush();  
			        bout.close();  
		          
		        }catch(Exception e) {
					HelperGlobal.createException(session, CustomLabels_ES.error_editar_coche_imagen, "error");
		        	return new ModelAndView(page);		        	
		        }
			}
			// Verified name exist
			if (gEquipos.buscarEquiposName(nombreEquipo).isEmpty()) {				
				final Equipo equipoObj = new Equipo();
				equipoObj.setId(itemId);
				equipoObj.setNombre(nombreEquipo);
				equipoObj.setTwitter(twitterEquipo);
				equipoObj.setUsuarioCreador(equipoDetail.getUsuarioCreador());
				equipoObj.setFechaCreacion(equipoDetail.getFechaCreacion());
				
				if (filename != "") {
					equipoObj.setLogo(filename);
				}
				gEquipos.editarEquipo(equipoObj);
				HelperGlobal.createException(session, CustomLabels_ES.success_modificar_equipo, "success");
			} else {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_equipos_nameRepeat, "error");
				page = "redirect:/error";
			}
		} else {
			HelperGlobal.createException(session, CustomLabels_ES.success_modificar_equipo, "success");
			page = "redirect:/";
		}
		return new ModelAndView(page);
	}
	
	public ModelAndView reasignCreador(int itemId, int type, HttpServletRequest request, HttpSession session, EquiposGestor gEquipos, MiembrosGestor gMiembros) {
		String views[] = {"tuEquipo", "redirect:/"};
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		ModelAndView mav = (ModelAndView) listSecurity.get(1);
		if (type == 1) {
			mav = new ModelAndView("redirect:/gestorEquipos");
		}
		if((Boolean) listSecurity.get(0)) {
			final Usuario user = HelperGlobal.castingUserObj(session);
			// Lookup up miembro by usuario_id
			final Boolean accessAdmin = HelperGlobal.accessPortalAdmin(user);
			final Equipo equipo = gEquipos.getEquipoById(itemId);
			if (equipo.getUsuarioCreador() == user.getId() || accessAdmin) {
				String userId1 = request.getParameter("userId");
				equipo.setUsuarioCreador(Integer.valueOf(userId1));
				gEquipos.editarEquipo(equipo);
				mav.addObject("equipoId", itemId);
			} else {
				mav = (ModelAndView) listSecurity.get(2);				
			}
		} else {
			mav = (ModelAndView) listSecurity.get(2);
		}
		return mav;
	}



}
