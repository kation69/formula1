package helpers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Comparator;
import java.util.Date;

import helpers.DatosCalendario;
import models.entity.Circuito;
import java.util.Calendar;

public final class CircuitSort {
	
    public static List<Circuito> sortByFecha(List<Circuito> circuitList) {
    	List<Circuito> circuitList2 = new ArrayList<Circuito>();
    	circuitList2.addAll(circuitList);
    	circuitList2.removeIf(n->n.getFecha()==null);
        Collections.sort(circuitList2, new Comparator<Circuito>() {
	        	public int compare(Circuito s1, Circuito s2) {
	        		if(s1.getFecha() == null && s2.getFecha()==null) {
	        			return 0;
	        		}
	        		else if(s1.getFecha() != null && s2.getFecha()==null) {
	        			return -1;
	        		}
	        		else if(s1.getFecha() == null && s2.getFecha()!=null) {
	        			return 1;
	        		}
	        		else {
	        			return s1.getFecha().compareTo(s2.getFecha());
	        		}
			        
		    	}
	        }
        );
        return circuitList2;
    }
    public static List<Circuito> WithOutFecha(List<Circuito> circuitList) {
    	List<Circuito> circuitList2 = new ArrayList<Circuito>();
    	circuitList2.addAll(circuitList);
    	circuitList2.removeIf(n->n.getFecha()!=null);
        return circuitList2;
    }
    @SuppressWarnings("deprecation")
	public static DatosCalendario datos_calendario(Integer MES, Integer ANIO) {
    	DatosCalendario datoss= new DatosCalendario();
    	String months[] = {"Enero", "Febrero", "Maro", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", 
    	         "Octubre", "Noviembre", "Diciembre"};
    	
    	Calendar calendar = Calendar.getInstance();
    	calendar.clear();
    	calendar.set(Calendar.YEAR, ANIO);
    	calendar.set(Calendar.MONTH, MES-1);
    	calendar.set(Calendar.DATE, 1);
    	Date fecha = calendar.getTime();
    	fecha.getDay();
    	datoss.setDIA_SEMANA_INICIO((fecha.getDay()+6)%7);
    	calendar.add(Calendar.MONTH, 1);
    	calendar.add(Calendar.DATE, -1);

    	fecha = calendar.getTime();
    	datoss.setDIA_SEMANA_FIN((fecha.getDay()+6)%7);
    	datoss.setDIA_FIN(fecha.getDate());
    	datoss.setNONBRE_MES(months[MES-1]);
    	
    	
        return datoss;
    }
    
}