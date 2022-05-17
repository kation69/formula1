package controllers;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import models.entity.Noticia;
import models.logic.CircuitosGestor;

@Controller
@Scope("request")
@RequestMapping("circuito")
public class Circuitos_Controlador {
	DateFormat dateSelectFormat = new SimpleDateFormat("dd-MM-yyyy");
	DateFormat dateInsertFormat = new SimpleDateFormat("dd-MM-yyyy");
	@Autowired
	CircuitosGestor gCircuitos = new CircuitosGestor();
	
	@ResponseBody
	@RequestMapping(path="ergarst", method=RequestMethod.GET) 
	public String ergarstRepo(HttpSession session) {
		String retVal = "";
		try {
			retVal = ErgarstFormula1_Controlador.convertToJson(ErgarstFormula1_Controlador.callGetListCircuit());
		} catch (Exception e) {
			retVal = "500";
		}
		return retVal;
	}

	@RequestMapping(path="insertarCircuito", method=RequestMethod.POST) 
	public ModelAndView insertarCircuito(@RequestParam CommonsMultipartFile file,HttpServletRequest request,HttpSession session) {
		try{
			Integer curvasLentas = Integer.parseInt(request.getParameter("curvasLentas"));
			Integer curvasMedias = Integer.parseInt(request.getParameter("curvasMedias"));
			Integer curvasRapidas = Integer.parseInt(request.getParameter("curvasRapidas"));
			Integer longitud = Integer.parseInt(request.getParameter("longitud"));
			Integer numVueltas = Integer.parseInt(request.getParameter("numVueltas"));
			String radioSelect= request.getParameter("radioSelect");
			String filename=file.getOriginalFilename().replace(" ", "");
			Circuito circSelect = new Circuito();
			if(radioSelect=="" || radioSelect==null) {
				String nombreCircuito= request.getParameter("nombreCircuito");
				String ciudadCircuito= request.getParameter("ciudadCircuito");
				String paisCircuito= request.getParameter("paisCircuito");
				circSelect.setCircuitName(nombreCircuito);
				circSelect.setLocality(ciudadCircuito);
				circSelect.setCountry(paisCircuito);
				circSelect.setImgTrazado(filename);
			} else {
				circSelect = ErgarstFormula1_Controlador.findCircuitById(radioSelect);
				circSelect.setImgTrazado(radioSelect+".png");
			}
			
		    String fileName = HelperGlobal.saveImageFile(file, session, filename);
		    if (fileName == null) {	
				HelperGlobal.createException(session, CustomLabels_ES.error_create_circuito_imagen, "error");
				return new ModelAndView("redirect:/gestorCircuitos");					        	
			}
			circSelect.setLongitude(longitud);
			circSelect.setCurvasLentas(curvasLentas);
			circSelect.setCurvasMedias(curvasMedias);
			circSelect.setCurvasRapidas(curvasRapidas);
			circSelect.setNumVueltas(numVueltas);
			gCircuitos.insertar(circSelect);
			HelperGlobal.createException(session, CustomLabels_ES.success_create_circuito, "success");
		}
		catch(Exception e) {
			HelperGlobal.createException(session, CustomLabels_ES.error_create_circuito, "error");			
		}
		return new ModelAndView("redirect:/gestorCircuitos");
	}
	
	@RequestMapping(path="editarCircuito", method=RequestMethod.POST) 
	public ModelAndView editarCircuito(@RequestParam CommonsMultipartFile file,HttpServletRequest request,HttpSession session) {
		try{
			String ids = request.getParameter("id");
			
			Integer curvasLentas = Integer.parseInt(request.getParameter("curvasLentas"));
			Integer curvasMedias = Integer.parseInt(request.getParameter("curvasMedias"));
			Integer curvasRapidas = Integer.parseInt(request.getParameter("curvasRapidas"));
			Integer longitud = Integer.parseInt(request.getParameter("longitud"));
			Integer numVueltas = Integer.parseInt(request.getParameter("numVueltas"));
			String filename=file.getOriginalFilename().replace(" ", "");
			String nombreCircuito= request.getParameter("nombreCircuito");
			String ciudadCircuito= request.getParameter("ciudadCircuito");
			String paisCircuito= request.getParameter("paisCircuito");

			String fileName = HelperGlobal.saveImageFile(file, session, "/resources/imgCircuit");
			if (fileName == null) {	
				HelperGlobal.createException(session, CustomLabels_ES.error_edit_circuito_imagen, "error");
				return new ModelAndView("redirect:/gestorCircuitos");		        	
			}
			
			Circuito circSelect = new Circuito();
			circSelect.setId(ids);
			circSelect.setCircuitName(nombreCircuito);
			circSelect.setLocality(ciudadCircuito);
			circSelect.setCountry(paisCircuito);
			circSelect.setLongitude(longitud);
			circSelect.setCurvasLentas(curvasLentas);
			circSelect.setCurvasMedias(curvasMedias);
			circSelect.setCurvasRapidas(curvasRapidas);
			circSelect.setNumVueltas(numVueltas);
			String fichero_antiguo = gCircuitos.editarById(circSelect);
			if (fichero_antiguo!="") {
				try {
					String ruta=session.getServletContext().getRealPath("/resources/imgCircuit");  
					String directory=Paths.get(ruta,fichero_antiguo).toString();
					File fichero = new File(directory);
				    if(fichero.exists()) {
				    	fichero.delete();
				    }
				}
				catch(Exception e) {
					
				}
			}
			HelperGlobal.createException(session, CustomLabels_ES.success_edit_circuito, "success");
		}
		catch(Exception e) {
			HelperGlobal.createException(session, CustomLabels_ES.error_edit_circuito, "error");			
		}
		return new ModelAndView("redirect:/gestorCircuitos");
	}
	
	@ResponseBody
	@RequestMapping(path="borrar/{id}", method=RequestMethod.POST) 
	public String borrar(@PathVariable("id") int itemId, HttpSession session) {
		String ret = "";
		try {
			if(gCircuitos.eliminarById(itemId)) {
				ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(200, CustomLabels_ES.success_delete_circuit));
			}
			else {
				ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, CustomLabels_ES.error_delete_circuit_date));
			}
			
		} catch (Exception e) {
			ret = SaveAndResponseWrapper.convertToJson(new SaveAndResponseWrapper(500, CustomLabels_ES.error_delete_circuit));
		}
		return ret;
	}
	
	@RequestMapping(path="ver/{nombre}", method=RequestMethod.GET) 
	public ModelAndView verCircuito(@PathVariable("nombre") String nombre, HttpSession session) {
		String page = "redirect:/";
		try {
			if (!nombre.isBlank()) {
				Circuito circuit = gCircuitos.buscarcircuito(nombre.replace(" ", ""));
				if (circuit != null && circuit.getCircuitName() != null) {
					session.setAttribute("titulo",circuit.getCircuitId());
					session.setAttribute("country",circuit.getCountry());
					session.setAttribute("locality",circuit.getLocality());
					session.setAttribute("imagen",circuit.getImgTrazado());
					session.setAttribute("numVueltas",circuit.getNumVueltas());
					session.setAttribute("longitude",circuit.getLongitude());
					session.setAttribute("curvasRapidas",circuit.getCurvasRapidas());
					session.setAttribute("curvasLentas",circuit.getCurvasLentas());
					session.setAttribute("curvasMedias",circuit.getCurvasMedias());
					if(circuit.getFecha()==null) {
						session.setAttribute("fecha","");
					}
					else {
						
						session.setAttribute("fecha",dateSelectFormat.format(circuit.getFecha()));
					}
					
					page = "ver_circuito";
				}				
			}
		} catch (Exception ex) { 
			String hola = "";
		}
		return new ModelAndView(page);
	}
	
	@RequestMapping(path="calendarizar/{id}", method=RequestMethod.POST) 
	public ModelAndView calendarizar(@PathVariable("id") int itemId,@Valid @RequestParam("fecha") String fecha_leida,HttpServletRequest request,HttpSession session) {
		try {
			if(fecha_leida=="") {
				gCircuitos.asignarFecha(itemId,null);
				HelperGlobal.createException(session, CustomLabels_ES.success_no_date_circuito, "success");
			}
			else{
				gCircuitos.asignarFecha(itemId,fecha_leida);
				HelperGlobal.createException(session, CustomLabels_ES.success_date_circuito, "success");
			}
		}
		catch(Exception e){
			if(fecha_leida=="") {
				HelperGlobal.createException(session, CustomLabels_ES.error_set_no_date_circuito, "error");
			}
			else {
				HelperGlobal.createException(session, CustomLabels_ES.error_set_date_circuito, "error");
			}
			
		}
		ModelAndView mav = new ModelAndView("redirect:/gestorCircuitos");
		mav.addObject("circuitosPersist", gCircuitos.getAllCircuits());
		return mav;
	}
	
	
	
	
}
