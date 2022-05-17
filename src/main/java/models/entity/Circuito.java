package models.entity;

import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class Circuito {
	private String id;
	private String circuitId;
	private String url;
	private String circuitName;
	private String locality;
	private String country;
	private Integer longitude;
	private String latitude;
	private String imgTrazado;
	private Integer numVueltas;
	private Integer curvasLentas;
	private Integer curvasMedias;
	private Integer curvasRapidas;
	private Date fecha;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getCircuitId() {
		return circuitId;
	}
	public void setCircuitId(String circuitId) {
		this.circuitId = circuitId;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getCircuitName() {
		return circuitName;
	}
	public void setCircuitName(String circuitName) {
		this.circuitName = circuitName;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Integer getLongitude() {
		return longitude;
	}
	public void setLongitude(Integer longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getImgTrazado() {
		return imgTrazado;
	}
	public void setImgTrazado(String imgTrazado) {
		this.imgTrazado = imgTrazado;
	}
	public Integer getNumVueltas() {
		return numVueltas;
	}
	public void setNumVueltas(Integer numVueltas) {
		this.numVueltas = numVueltas;
	}
	public Integer getCurvasLentas() {
		return curvasLentas;
	}
	public void setCurvasLentas(Integer curvasLentas) {
		this.curvasLentas = curvasLentas;
	}
	public Integer getCurvasMedias() {
		return curvasMedias;
	}
	public void setCurvasMedias(Integer curvasMedias) {
		this.curvasMedias = curvasMedias;
	}
	public Integer getCurvasRapidas() {
		return curvasRapidas;
	}
	public void setCurvasRapidas(Integer curvasRapidas) {
		this.curvasRapidas = curvasRapidas;
	}
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	@Override
	public String toString() {
		return "Circuito [circuitId=" + circuitId + ", url=" + url + ", circuitName=" + circuitName + ", locality="
				+ locality + ", country=" + country + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", imgTrazado=" + imgTrazado + ", numVueltas=" + numVueltas + ", curvasLentas=" + curvasLentas
				+ ", curvasMedias=" + curvasMedias + ", curvasRapidas=" + curvasRapidas + "]";
	}

}