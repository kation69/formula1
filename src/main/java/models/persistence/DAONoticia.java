package models.persistence;

import java.util.List;

import models.entity.Noticia;

public interface DAONoticia {
	
	List<Noticia> buscarNoticias();
	Noticia buscarNoticia(String link);
	Noticia insertar(Noticia noticia);
	String update(Noticia noticia);
	void borrar(Integer noticia);
	
}