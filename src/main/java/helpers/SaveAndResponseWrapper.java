package helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SaveAndResponseWrapper {
	
	private Integer codeService;
	private String bodyResponse;
	
	public Integer getCodeService() {
		return codeService;
	}
	public void setCodeService(Integer codeService) {
		this.codeService = codeService;
	}
	public String getBodyResponse() {
		return bodyResponse;
	}
	public void setBodyResponse(String bodyResponse) {
		this.bodyResponse = bodyResponse;
	}
	
	public SaveAndResponseWrapper(Integer codeService, String bodyResponse) {
		super();
		this.codeService = codeService;
		this.bodyResponse = bodyResponse;
	}
	
	public static String convertToJson(SaveAndResponseWrapper saveAndResponse) {
		String arrJson = "";
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			arrJson = objectMapper.writeValueAsString(saveAndResponse);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrJson;
	}
	
	public static String convertJSON(Object obj) {
		String arrJson = "";
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			arrJson = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return arrJson;
	}

}
