package controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
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
import models.logic.PilotosGestor;

public class Pilotos_Controlador {

	public ModelAndView insertarPiloto(CommonsMultipartFile file, Integer equipoId,
			HttpServletRequest request,
			HttpSession session,
			PilotosGestor gPilotos){  
		    String page = "redirect:/portalResponsable/"+equipoId.toString()+"/gestorPilotos";
			String nombre= request.getParameter("nombre");
			String apellidos= request.getParameter("apellidos");
			String siglas = request.getParameter("siglas");
			Integer dorsal = Integer.parseInt(request.getParameter("dorsal"));
			//String foto = request.getParameter("foto");
			String pais = request.getParameter("pais");
			String twitter = request.getParameter("twitter");
			//Integer equipoId = Integer.parseInt(request.getParameter("equipo_id"));
			if (nombre.strip() == "") {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_piloto_nombre, "error");
	        	return new ModelAndView(page);
			}
			if (apellidos.strip()=="") {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_piloto_apellidos, "error");
				return new ModelAndView(page);
			}
			if (siglas.strip()=="") {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_piloto_siglas, "error");
				return new ModelAndView(page);
			}
			if (dorsal.equals(null)) {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_piloto_dorsal, "error");
				return new ModelAndView(page);
			}
			if (pais.strip()=="") {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_piloto_pais, "error");
				return new ModelAndView(page);
			}
			String filename=file.getOriginalFilename().replace(" ", "");
			if (filename!=""){
				String ruta=session.getServletContext().getRealPath("/resources/imgPilotos");  
		          
		        String directory=Paths.get(ruta,filename).toString();
		        
				File fichero = new File(directory);
			    if(fichero.exists()) {		    	
					String tipo= FilenameUtils.getExtension(filename);
			    	String imagen = filename.substring(0,filename.length()-tipo.length()-1);
			    	Integer alternativa=1;
			    	
			    	while(true) {
			    		filename = imagen+"-"+alternativa.toString()+"."+tipo;
			    		directory = Paths.get(ruta,filename).toString();
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
					HelperGlobal.createException(session, CustomLabels_ES.error_create_piloto_foto, "error");
					return new ModelAndView(page);
		        }
			}
			try {
				
		        final Piloto piloto = new Piloto();
				piloto.setNombre(nombre);
				piloto.setApellidos(apellidos);
				piloto.setSiglas(siglas);
				piloto.setDorsal(dorsal);
				piloto.setFoto(filename);
				piloto.setPais(pais);
				piloto.setTwitter(twitter);
				piloto.setEquipoId(equipoId);
				
				gPilotos.insertar(piloto);
				HelperGlobal.createException(session, CustomLabels_ES.success_create_piloto, "success");
			} catch(Exception e) {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_piloto, "error");
			}
		
			return new ModelAndView(page);
    }
	
	public ModelAndView editarPiloto(CommonsMultipartFile file, 
			Integer equipoId, 
			int piloto_id, 
			HttpServletRequest request,
			HttpSession session,
			PilotosGestor gPilotos){  
		    String page = "redirect:/portalResponsable/"+equipoId.toString()+"/gestorPilotos";
		    //Integer id = Integer.parseInt(request.getParameter("id"));
			String nombre = request.getParameter("nombre");
			String apellidos = request.getParameter("apellidos");
			String siglas = request.getParameter("siglas");
			Integer dorsal = Integer.parseInt(request.getParameter("dorsal"));
			//String foto = request.getParameter("foto");
			String pais = request.getParameter("pais");
			String twitter = request.getParameter("twitter");
			
			if (nombre.strip() == "") {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_piloto_nombre, "error");
				return new ModelAndView(page);
			}
			if (apellidos.strip()=="") {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_piloto_apellidos, "error");
				return new ModelAndView(page);
			}
			if (siglas.strip()=="") {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_piloto_siglas, "error");
				return new ModelAndView(page);
			}
			if (dorsal.equals(null)) {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_piloto_dorsal, "error");
				return new ModelAndView(page);
			}
			if (pais.strip()=="") {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_piloto_pais, "error");
				return new ModelAndView(page);
			}
			String filename=file.getOriginalFilename().replace(" ", "");
			if (filename!=""){
				String ruta=session.getServletContext().getRealPath("/resources/imgPilotos");  
		          
		        String directory=Paths.get(ruta,filename).toString();
		        
				File fichero = new File(directory);
			    if(fichero.exists()) {		    	
					String tipo= FilenameUtils.getExtension(filename);
			    	String imagen = filename.substring(0,filename.length()-tipo.length()-1);
			    	Integer alternativa=1;
			    	
			    	while(true) {
			    		filename = imagen+"-"+alternativa.toString()+"."+tipo;
			    		directory = Paths.get(ruta,filename).toString();
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
					HelperGlobal.createException(session, CustomLabels_ES.error_modificar_foto_piloto, "error");
					return new ModelAndView(page);
		        }
			}
			try {
				
		        final Piloto piloto = new Piloto();
		        piloto.setId(piloto_id);
		        piloto.setNombre(nombre);
				piloto.setApellidos(apellidos);
				piloto.setSiglas(siglas);
				piloto.setDorsal(dorsal);
				piloto.setPais(pais);
				piloto.setTwitter(twitter);
				piloto.setEquipoId(equipoId);
				/*if (filename!="") {
					piloto.setFoto(filename);
				}
				
				String fichero_antiguo = gPilotos.update(piloto);
				if (fichero_antiguo!="") {
					try {
						String ruta=session.getServletContext().getRealPath("/resources/imgPilotos");  
						String directory=Paths.get(ruta,fichero_antiguo).toString();
						File fichero = new File(directory);
					    if(fichero.exists()) {
					    	fichero.delete();
					    }
					}
					catch(Exception e) {
						
					}
				}*/
				gPilotos.update(piloto);
				HelperGlobal.createException(session, CustomLabels_ES.success_modificar_piloto, "success");
			}catch(Exception e){
				HelperGlobal.createException(session, CustomLabels_ES.error_modificar_piloto, "error");
			}
			
			return new ModelAndView(page);
    }

	public ModelAndView verPiloto(String pagina ,HttpSession session,
			PilotosGestor gPilotos, Integer equipoId) {
		//String page = "redirect:/";
		String page = "redirect:/PortalResponsable/" + equipoId + "/tusPilotos/ver_piloto";
		try {
			if (!pagina.isBlank()) {
				Piloto piloto = (Piloto) gPilotos.buscarPilotosName(page);
				if (piloto != null) {
					session.setAttribute("nombre",piloto.getNombre());
					session.setAttribute("apellidos",piloto.getApellidos());
					session.setAttribute("siglas",piloto.getSiglas());
					session.setAttribute("dorsal", piloto.getDorsal());
					session.setAttribute("foto", piloto.getFoto());
					session.setAttribute("pais", piloto.getPais());
					session.setAttribute("twitter", piloto.getTwitter());
					page = "ver_piloto";
				}				
			}
		} catch (Exception ex) { }
		return new ModelAndView(page);		
	}
	
	public String borrar(@PathVariable("id") int itemId, HttpSession session, PilotosGestor gPilotos) {
		String ret = "";
		try {
			gPilotos.borrar(itemId);
			ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(200, null));
		} catch (Exception e) {
			ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, null));
		}
		return ret;
	}
	
	public ModelAndView listarPilotos(String pagina ,HttpSession session,
			PilotosGestor gPilotos, Integer equipoId) {
		//String page = "redirect:/PortalResponsable/" + equipoId + "/tusPilotos/ver_piloto";
		String page = "redirect:/gestorVotaciones";
		try {
			if (!pagina.isBlank()) {
				List<Piloto> lista = gPilotos.getLstPilotos();
				Piloto piloto = (Piloto) gPilotos.buscarPilotosName(page);
				if (piloto != null) {
					session.setAttribute("listaPilotos", lista);
					session.setAttribute("nombre",piloto.getNombre());
					session.setAttribute("apellidos",piloto.getApellidos());
					session.setAttribute("siglas",piloto.getSiglas());
					session.setAttribute("dorsal", piloto.getDorsal());
					session.setAttribute("foto", piloto.getFoto());
					session.setAttribute("pais", piloto.getPais());
					session.setAttribute("twitter", piloto.getTwitter());
					//page = "ver_piloto";
				}				
			}
		} catch (Exception ex) { }
		return new ModelAndView(page);		
	}
	
}
