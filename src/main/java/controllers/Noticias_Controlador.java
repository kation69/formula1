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
import models.entity.Noticia;
import models.logic.NoticiasGestor;


@Controller
@Scope("request")
@RequestMapping("noticias")
public class Noticias_Controlador {

	@Autowired
	private NoticiasGestor gNoticias;
	@RequestMapping(path="insertarNoticia", method=RequestMethod.POST)
	public ModelAndView insertarNoticia(@RequestParam CommonsMultipartFile file,HttpServletRequest request,HttpSession session){  
			String titulo= request.getParameter("titulo");
			String cuerpo= request.getParameter("cuerpo");
			if (titulo.strip() == "") {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_noticia_titulo, "error");
	        	return new ModelAndView("redirect:/gestorNoticias");
			}
			if (cuerpo.strip()=="") {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_noticia_cuerpo, "error");
	        	return new ModelAndView("redirect:/gestorNoticias");
			}
			String filename=file.getOriginalFilename().replace(" ", "");
			if (filename!=""){
				String ruta=session.getServletContext().getRealPath("/resources/imgNoticias");  
		          
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
					HelperGlobal.createException(session, CustomLabels_ES.error_create_noticia_imagen, "error");
		        	return new ModelAndView("redirect:/gestorNoticias");		        	
		        }
			}
			try {
				
		        final Noticia noticia = new Noticia();
				noticia.setTitulo(titulo);
				noticia.setCuerpo(cuerpo);
				noticia.setImagen(filename);

				List<Noticia> lista_noticias= gNoticias.getLstNoticias();
				String link = HelperGlobal.limpiarCaracteres(titulo).replace(" ","").toLowerCase();
				String linktemp=link;
				Integer link_posible=0;
				for(Noticia noti:lista_noticias) {
					
					if (noti.getPermantlink().equalsIgnoreCase(linktemp.strip())) {
						link_posible++;
						linktemp=link+"-"+link_posible.toString();
					}
				}
				noticia.setPermantlink(linktemp);
				
				gNoticias.insertar(noticia);
				HelperGlobal.createException(session, CustomLabels_ES.success_create_noticia, "success");
			} catch(Exception e) {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_noticia, "error");
			}
			return new ModelAndView("redirect:/gestorNoticias");
    }
	
	@RequestMapping(path="editNoticia", method=RequestMethod.POST)
	public ModelAndView editarnoticia(@RequestParam CommonsMultipartFile file,HttpServletRequest request,HttpSession session){  
			Integer id = Integer.parseInt(request.getParameter("id"));
			String titulo= request.getParameter("titulo");
			String cuerpo= request.getParameter("cuerpo");
			if (titulo.strip() == "") {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_noticia_titulo, "error");
	        	return new ModelAndView("redirect:/gestorNoticias");
			}
			if (cuerpo.strip()=="") {
				HelperGlobal.createException(session, CustomLabels_ES.error_create_noticia_cuerpo, "error");
	        	return new ModelAndView("redirect:/gestorNoticias");
			}
			String filename=file.getOriginalFilename().replace(" ", "");
			if (filename!=""){
				String ruta=session.getServletContext().getRealPath("/resources/imgNoticias");  
		          
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
					HelperGlobal.createException(session, CustomLabels_ES.error_modificar_noticia_imagen, "error");
		        	return new ModelAndView("redirect:/gestorNoticias");		        	
		        }
			}
			try {
				
		        final Noticia noticia = new Noticia();
				noticia.setId(id);
				noticia.setTitulo(titulo);
				noticia.setCuerpo(cuerpo);
				if (filename!="") {
					noticia.setImagen(filename);
				}
				
				String fichero_antiguo = gNoticias.update(noticia);
				if (fichero_antiguo!="") {
					try {
						String ruta=session.getServletContext().getRealPath("/resources/imgNoticias");  
						String directory=Paths.get(ruta,fichero_antiguo).toString();
						File fichero = new File(directory);
					    if(fichero.exists()) {
					    	fichero.delete();
					    }
					}
					catch(Exception e) {
						
					}
				}
				HelperGlobal.createException(session, CustomLabels_ES.success_modificar_noticia, "success");
			}catch(Exception e){
				HelperGlobal.createException(session, CustomLabels_ES.error_modificar_noticia, "error");
			}
			return new ModelAndView("redirect:/gestorNoticias");
    }

	@RequestMapping(path="ver", method=RequestMethod.GET) 
	public ModelAndView verNoticia(@RequestParam(value = "pag", required = true) String pagina ,HttpSession session) {
		String page = "redirect:/";
		try {
			if (!pagina.isBlank()) {
				Noticia noticia = gNoticias.getNoticia(pagina);
				if (noticia != null || noticia.getPermantlink() != null) {
					session.setAttribute("titulo",noticia.getTitulo());
					session.setAttribute("imagen",noticia.getImagen());
					session.setAttribute("cuerpo",noticia.getCuerpo());
					page = "ver_noticias";
				}				
			}
		} catch (Exception ex) { }
		return new ModelAndView(page);		
	}
	
	@ResponseBody
	@RequestMapping(path="borrar/{id}", method=RequestMethod.POST) 
	public String borrar(@PathVariable("id") int itemId, HttpSession session) {
		String ret = "";
		try {
			gNoticias.borrar(itemId);
			ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(200, CustomLabels_ES.success_delete_noticia));
		} catch (Exception e) {
			ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, CustomLabels_ES.error_delete_noticia));
		}
		return ret;
	}
	
}
