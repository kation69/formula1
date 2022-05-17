package controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import helpers.CustomLabels_ES;
import helpers.HelperGlobal;
import helpers.SaveAndResponseWrapper;
import models.entity.Coches;
import models.entity.Usuario;
import models.logic.CochesGestor;
import models.logic.MiembrosGestor;

public class Coches_Controlador {
	
	
	public String borrarCoche(HttpSession session, int coche_id, CochesGestor gCoches, MiembrosGestor gMiembros) {
		final String views[] = {"tusCoches", "error"};
		final List<Object> listSecurity = HelperGlobal.securityAccess(session, views);
		String ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, "error generico"));
		if((Boolean) listSecurity.get(0)) {
			try {
				final Usuario user = HelperGlobal.castingUserObj(session);
				// Lookup up miembro by usuario_id
				final Boolean accessAdmin = HelperGlobal.accessPortalAdmin(user);
				session.setAttribute("sectionActive", "Portal_TuEquipo");
				gCoches.eliminar(coche_id);
				HelperGlobal.createException(session, CustomLabels_ES.success_borrar_coche, "success");
				ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(200, null));
			} catch (Exception e) {
				HelperGlobal.createException(session, "error", "error");
				ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, "error catch"));
			}
		}
		return ret;
	}
	
	public ModelAndView insertarCoche(CommonsMultipartFile file, Integer equipoId,
									HttpServletRequest request,HttpSession session, CochesGestor gCoches) {
		String page = "redirect:/portalResponsable/"+equipoId.toString()+"/gestorCoches";
		String nombreCoche= request.getParameter("nombre");
		String codigo= request.getParameter("codigo");
		
		Float ERSLento = Float.valueOf(request.getParameter("ERS_Curvas_Lentas"));
		Float ERSRapido = Float.valueOf(request.getParameter("ERS_Curvas_Rapidas"));
		Float ERSMedio = Float.valueOf(request.getParameter("ERS_Curvas_Medias"));
		Float Consumo = Float.valueOf(request.getParameter("consumo"));
		Float min = (float) 0.01;
		Float max = (float) 0.06;
		Float minconsumo = (float) 20.0;
		Float maxconsumo = (float) 100.0;
		
		if (nombreCoche.strip() == "") {
			HelperGlobal.createException(session, CustomLabels_ES.error_create_coche_nombre, "error");
        	return new ModelAndView(page);
		}
		if (codigo.strip()=="") {
			HelperGlobal.createException(session, CustomLabels_ES.error_create_coche_codigo, "error");
        	return new ModelAndView(page);
		}
		if (Consumo.compareTo(minconsumo) < 0 || Consumo.compareTo(maxconsumo) > 0) {
			HelperGlobal.createException(session, CustomLabels_ES.error_create_coche_consumo, "error");
        	return new ModelAndView(page);
		}
		if (ERSLento.compareTo(min) < 0 || ERSLento.compareTo(max) > 0) {
			HelperGlobal.createException(session, CustomLabels_ES.error_create_coche_ERS_Lento, "error");
        	return new ModelAndView(page);
		}
		if (ERSMedio.compareTo(min) < 0 || ERSMedio.compareTo(max) > 0) {
			HelperGlobal.createException(session, CustomLabels_ES.error_create_coche_ERS_Medio, "error");
        	return new ModelAndView(page);
		}
		if (ERSRapido.compareTo(min) < 0 || ERSRapido.compareTo(max) > 0) {
			HelperGlobal.createException(session, CustomLabels_ES.error_create_coche_ERS_Rapido, "error");
        	return new ModelAndView(page);
		}
		
		if (ERSLento.compareTo(ERSMedio) < 0 ) {
			HelperGlobal.createException(session, CustomLabels_ES.error_create_coche_ERS_mayor_lento, "error");
        	return new ModelAndView(page);
		}
		if (ERSMedio.compareTo(ERSRapido) < 0 ) {
			HelperGlobal.createException(session, CustomLabels_ES.error_create_coche_ERS_mayor_medio, "error");
        	return new ModelAndView(page);
		}
		//Foto?
		String filename=file.getOriginalFilename().replace(" ", "");
		if (filename!=""){
			String ruta=session.getServletContext().getRealPath("/resources/imgCoches");  
	          
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
				HelperGlobal.createException(session, CustomLabels_ES.error_create_coche_imagen, "error");
	        	return new ModelAndView(page);		        	
	        }
		}
		try {
			
	        final Coches coche = new Coches();
	        coche.setNombre(nombreCoche);
	        coche.setCodigo(codigo);
	        coche.setEquipoId(equipoId);
	        coche.setConsumo(Consumo);
	        coche.setErsCurvasLentas(ERSLento);
	        coche.setErsCurvasMedias(ERSMedio);
	        coche.setErsCurvasRapidas(ERSRapido);
	        coche.setImagen(filename);
			gCoches.insertar(coche);
			HelperGlobal.createException(session, CustomLabels_ES.success_create_coche, "success");
		} catch(Exception e) {
			HelperGlobal.createException(session, CustomLabels_ES.error_create_coche, "error");
		}
		return new ModelAndView(page);
	}
	
	public ModelAndView editarCoche(CommonsMultipartFile file,
									Integer equipoId,
									int coche_id,
									HttpServletRequest request,
									HttpSession session,
									CochesGestor gCoches) {
		String page = "redirect:/portalResponsable/"+equipoId.toString()+"/gestorCoches";
		String nombreCoche= request.getParameter("nombre");
		String codigo= request.getParameter("codigo");
		Float ERSLento = Float.valueOf(request.getParameter("ERS_Curvas_Lentas"));
		Float ERSRapido = Float.valueOf(request.getParameter("ERS_Curvas_Rapidas"));
		Float ERSMedio = Float.valueOf(request.getParameter("ERS_Curvas_Medias"));
		Float Consumo = Float.valueOf(request.getParameter("consumo"));
		Float min = (float) 0.01;
		Float max = (float) 0.06;
		Float minconsumo = (float) 20.0;
		Float maxconsumo = (float) 100.0;
		
		if (nombreCoche.strip() == "") {
			HelperGlobal.createException(session, CustomLabels_ES.error_editar_coche_nombre, "error");
        	return new ModelAndView(page);
		}
		if (codigo.strip()=="") {
			HelperGlobal.createException(session, CustomLabels_ES.error_editar_coche_codigo, "error");
        	return new ModelAndView(page);
		}
		if (Consumo.compareTo(minconsumo) < 0 || Consumo.compareTo(maxconsumo) > 0) {
			HelperGlobal.createException(session, CustomLabels_ES.error_editar_coche_consumo, "error");
        	return new ModelAndView(page);
		}
		if (ERSLento.compareTo(min) < 0 || ERSLento.compareTo(max) > 0) {
			HelperGlobal.createException(session, CustomLabels_ES.error_editar_coche_ERS_Lento, "error");
        	return new ModelAndView(page);
		}
		if (ERSMedio.compareTo(min) < 0 || ERSMedio.compareTo(max) > 0) {
			HelperGlobal.createException(session, CustomLabels_ES.error_editar_coche_ERS_Medio, "error");
        	return new ModelAndView(page);
		}
		if (ERSRapido.compareTo(min) < 0 || ERSRapido.compareTo(max) > 0) {
			HelperGlobal.createException(session, CustomLabels_ES.error_editar_coche_ERS_Rapido, "error");
        	return new ModelAndView(page);
		}
		
		if (ERSLento.compareTo(ERSMedio) < 0 ) {
			HelperGlobal.createException(session, CustomLabels_ES.error_editar_coche_ERS_mayor_lento, "error");
        	return new ModelAndView(page);
		}
		if (ERSMedio.compareTo(ERSRapido) < 0 ) {
			HelperGlobal.createException(session, CustomLabels_ES.error_editar_coche_ERS_mayor_medio, "error");
        	return new ModelAndView(page);
		}
		//Foto?
		String filename=file.getOriginalFilename().replace(" ", "");
		if (filename!=""){
			String ruta=session.getServletContext().getRealPath("/resources/imgCoches");  
	          
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
		try {
			
	        final Coches coche = new Coches();
	        coche.setId(coche_id);
	        coche.setNombre(nombreCoche);
	        coche.setCodigo(codigo);
	        coche.setEquipoId(equipoId);
	        coche.setConsumo(Consumo);
	        coche.setErsCurvasLentas(ERSLento);
	        coche.setErsCurvasMedias(ERSMedio);
	        coche.setErsCurvasRapidas(ERSRapido);
	        coche.setImagen(filename);
			gCoches.actualizar(coche);
			HelperGlobal.createException(session, CustomLabels_ES.success_editar_coche, "success");
		} catch(Exception e) {
			HelperGlobal.createException(session, CustomLabels_ES.error_editar_coche, "error");
		}
		return new ModelAndView(page);
	}
	

}
