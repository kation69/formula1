package models.entity;



import org.springframework.stereotype.Component;

@Component
public class Coches {
	private Integer id;
	private Integer equipoId;
	private String nombre;
	private String codigo;
	private Float consumo;
	private Float ersCurvasLentas;
	private Float ersCurvasMedias;
	private Float ersCurvasRapidas;
	private String imagen;
	
	

	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getEquipoId() {
		return equipoId;
	}
	public void setEquipoId(Integer equipoId) {
		this.equipoId = equipoId;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Float getConsumo() {
		return consumo;
	}
	public void setConsumo(Float consumo) {
		this.consumo = consumo;
	}
	public Float getErsCurvasLentas() {
		return ersCurvasLentas;
	}
	public void setErsCurvasLentas(Float ersCurvasLentas) {
		this.ersCurvasLentas = ersCurvasLentas;
	}
	public Float getErsCurvasMedias() {
		return ersCurvasMedias;
	}
	public void setErsCurvasMedias(Float ersCurvasMedias) {
		this.ersCurvasMedias = ersCurvasMedias;
	}
	public Float getErsCurvasRapidas() {
		return ersCurvasRapidas;
	}
	public void setErsCurvasRapidas(Float ersCurvasRapidas) {
		this.ersCurvasRapidas = ersCurvasRapidas;
	}
	

}