package models.entity;

public class ERS {
	
	
	public ERS( String tipo,String color,String vuelta_color,Float curvasLentas,Integer num_curvas_Lentas, Float curvasMedias,Integer num_curvas_Medias, Float curvasRapidas,Integer num_curvas_Rapidas) {
		super();
		this.tipo=tipo;
		Double tipo_conduccion=1.0;
		switch (tipo.toUpperCase()) {
	        case "AHORRADOR":  tipo_conduccion = 1.05;
	                 break;
	        case "NORMAL":  tipo_conduccion = 0.75;
            		break;
	        case "DEPORTIVO":  tipo_conduccion = 0.60;
    				break;
	        default:
	        	tipo_conduccion = 1.0;
		}
		this.ERS=curvasLentas*num_curvas_Lentas*tipo_conduccion+curvasMedias*num_curvas_Medias*tipo_conduccion+curvasRapidas*num_curvas_Rapidas*tipo_conduccion;
		if(this.ERS>0.6) {
			this.ERS=0.6;
		}
		this.num_vueltas=1.2/this.ERS;
		this.color=color;
		this.setVuelta_color(vuelta_color);
		
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Double getERS() {
		return ERS;
	}

	public void setERS(Double eRS) {
		ERS = eRS;
	}

	public Double getNum_vueltas() {
		return num_vueltas;
	}

	public void setNum_vueltas(Double num_vueltas) {
		this.num_vueltas = num_vueltas;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getVuelta_color() {
		return vuelta_color;
	}

	public void setVuelta_color(String vuelta_color) {
		this.vuelta_color = vuelta_color;
	}

	private String tipo;
	private Double ERS;
	private Double num_vueltas;
	private String color;
	private String vuelta_color;
	
}