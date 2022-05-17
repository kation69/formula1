package helpers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import models.entity.Usuario;
import models.logic.UsuariosGestor;

public class HelperGlobal {
	
	public static String saveImageFile(CommonsMultipartFile file, HttpSession session, String rutaDirectory) {
		String filename = file.getOriginalFilename().replace(" ", "");
		if (filename != "") {
			final String ruta = session.getServletContext().getRealPath(rutaDirectory);  
	        String directory = Paths.get(ruta, filename).toString();
			File fichero = new File(directory);
		    if(fichero.exists()) {	
				String tipo = FilenameUtils.getExtension(filename);
		    	String nombreFichero = filename.substring(0,filename.length()-tipo.length()-1);
		    	Integer alternativa = 1;
		    	
		    	while(true) {
		    		filename = nombreFichero + "-" + alternativa.toString() + "." + tipo;
		    		directory = Paths.get(ruta,filename).toString();
		    		fichero = new File(directory);
		    		if(fichero.exists()) {
		    			alternativa++;
		    		} else {
		    			break;
		    		}
				}
		    }
	        try {
		        byte barr[]=file.getBytes();  
		          
		        BufferedOutputStream bout = new BufferedOutputStream(  
		                 new FileOutputStream(Paths.get(ruta,filename).toString()));  
		        bout.write(barr);  
		        bout.flush();  
		        bout.close();  
	        } catch(Exception e) {
	        	filename = null;
	        }
		}
		return filename;
	}
	
	public static List<Object> securityAccess(HttpSession session, String[] lstViews) {
		final List<Object> list = new ArrayList<Object>();
		final Boolean retVal = session.getAttribute("usuario") == null ? false : true;
		list.add(retVal);
		if ((Boolean) list.get(0)) {
			for (String view : lstViews) {
				list.add(new ModelAndView(view));
			}
		} else {
			list.add(new ModelAndView("redirect:/"));
		}
		return list;
	}
	
	public static boolean accessPortalAdmin(Usuario user) {
		Boolean retVal = false;
		if (user != null && user.getRol().equals("admin")) {
			retVal = true;
		}
		return retVal;
	}

	public static Boolean validatePassword(String password) {
		Boolean res = false;
		if (password.length() >= 5) {
			res = true;
		}
		return res;
	}
	
	public static Boolean createAdmin(UsuariosGestor gUsuarios) {
		String nombre="admin";
		String password="admin";
		String email="admin@admin.es";
		String usuario="admin";
		String rol="admin";
		Boolean realizado=false;
		
		// Check if exist user
		if (gUsuarios.buscarUsuario(usuario, email).isEmpty()) {
			Usuario usuarioNew = new Usuario();
			usuarioNew.setNombre(nombre);
			usuarioNew.setPassword(password);
			usuarioNew.setUsuario(usuario);
			usuarioNew.setEmail(email);
			usuarioNew.setRol(rol);
			realizado=true;
			try {
				gUsuarios.insertaradmin(usuarioNew);
			}
			catch(Exception e){
				realizado=false;
			}
		}
		else {
			realizado=false;
		}
		return realizado;
	}
	
	public static HttpSession createException(HttpSession session, String msgAlert, String alertType) {
		String typeAlert = "";
		if (alertType == "success") {			
			typeAlert = Constants.alert_success_class;
		} else if (alertType == "error") {			
			typeAlert = Constants.alert_danger_class;
		}
		session.setAttribute("typeAlert", typeAlert);
		session.setAttribute("msgAlert", msgAlert);
		return session;
	}
	
	public static String limpiarCaracteres(String texto) {
	    String original = "Ã€Ã�Ã‚ÃƒÃ„Ã…Ã†Ã‡ÃˆÃ‰ÃŠÃ‹ÃŒÃ�ÃŽÃ�Ã�Ã‘Ã’Ã“Ã”Ã•Ã–Ã˜Ã™ÃšÃ›ÃœÃ�Ã‘Ã±ÃŸÃ Ã¡Ã¢Ã£Ã¤Ã¥Ã¦Ã§Ã¨Ã©ÃªÃ«Ã¬Ã­Ã®Ã¯Ã°Ã±Ã²Ã³Ã´ÃµÃ¶Ã¸Ã¹ÃºÃ»Ã¼Ã½Ã¿_:.,%#><-\"|@Â¨Â´/\\'?=!Â¿Â¡Â·$&()Â¨^*[]{}-+ ";
	    // Cadena de caracteres ASCII que reemplazarÃ¡n los originales.
	    String ascii = "AAAAAAACEEEEIIIIDNOOOOOOUUUUYÃ‘Ã±Baaaaaaaceeeeiiiionoooooouuuuyy";
	    String output = texto.replace("  ", " ");
	    for (int i=0; i<original.length(); i++) {
	    // Reemplazamos los caracteres especiales.
	    	if (ascii.length()<=i){
	    		output = output.replace(original.charAt(i), "-".toCharArray()[0]);
	    	}
	    	else {
	    		output = output.replace(original.charAt(i), ascii.charAt(i));
	    	}
	    }
	    return output.toLowerCase();
    }


	public static Usuario castingUserObj(HttpSession session) {
		final Object userActive = session.getAttribute("usuario");
		final Usuario user = (Usuario) userActive;
		return user;
	}
	
	public static String convertToJson(Object lstObject) {
		String arrayToJson = "";
		   try {
			   ObjectMapper objectMapper = new ObjectMapper();
			   objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			   arrayToJson = objectMapper.writeValueAsString(lstObject);
		   } catch (JsonProcessingException e) {
			   // TODO Auto-generated catch block
			   e.printStackTrace();
		   }
		   return arrayToJson;
   }


}
