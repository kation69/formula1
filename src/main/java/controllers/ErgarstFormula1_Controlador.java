package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import models.entity.Circuito;


public class ErgarstFormula1_Controlador {
	
	public static List<Circuito> lstCircuito = new ArrayList<Circuito>();
	
	private static String SERVER = "http://ergast.com/api/f1/";
	
	public static WebClient webclient = WebClient.create();

	@Autowired
	static
    ResourceLoader resourceLoader;
	
	
	public static List<Circuito> callGetListCircuit() {
        List<Circuito> lstCircuit = new ArrayList<Circuito>();
        try {
    		ObjectMapper mapper = new ObjectMapper();

    		String responseSpec = webclient.get()
    			    .uri(SERVER + "circuits.json?callback=myParser")
    			    .header("Content-Type", "application/json")
    			    .retrieve()
    			    .bodyToMono(String.class)
    			    .block();
    		
            responseSpec = responseSpec.replace("myParser(", "");
    		responseSpec = responseSpec.substring(0, responseSpec.length()-1);
            
            // convert JSON string to Map
    		Map<String,Object> result = new ObjectMapper().readValue(responseSpec, HashMap.class);
            Map<String,Object> result_MRData = (Map<String, Object>) result.get("MRData");
            Map<String,Object> result_Circuit = (Map<String, Object>) result_MRData.get("CircuitTable");
            List<Map<String, Object>> lstCircuits = (List<Map<String, Object>>) result_Circuit.get("Circuits");
            for (Map<String, Object> obj : (List<Map<String, Object>>) result_Circuit.get("Circuits")) {
            	final Map<String,Object> res_loc = (Map<String, Object>) obj.get("Location");
            	Circuito circuit = new Circuito();
            	circuit.setCircuitId((String) obj.get("circuitId"));
            	circuit.setUrl((String) obj.get("url"));
            	circuit.setCircuitName((String) obj.get("circuitName"));
            	circuit.setLocality((String) res_loc.get("locality"));
            	circuit.setCountry((String) res_loc.get("country"));
            	lstCircuit.add(circuit);
            }
            lstCircuito = lstCircuit;
            

        } catch (IOException e) {
            e.printStackTrace();
        }
        return lstCircuit;
	}
	
	public static Circuito findCircuitById(String circuitId) {
		Circuito circuitoRet = new Circuito();
		for (Circuito item : lstCircuito) {
			if (item.getCircuitId().equals(circuitId)) {
				circuitoRet = item;
				break;
			}
		}
		return circuitoRet;
	}

	public static String convertToJson(List<Circuito> lstObject) {
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
