package models.logic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.entity.Noticia;
import models.persistence.DAONoticia;

@Service
public class NoticiasGestor {
	
	@Autowired
	private DAONoticia daoNoticias;

	public void insertar(Noticia noticia) {
		daoNoticias.insertar(noticia);
	}
	
	public List<Noticia> getLstNoticias() {
		return daoNoticias.buscarNoticias();
	}
	public Noticia getNoticia(String link) {
		return daoNoticias.buscarNoticia(link);
	}
	public void borrar(Integer noticia) {
		daoNoticias.borrar(noticia);
	}
	public String update(Noticia noticia) {
		return daoNoticias.update(noticia);
	}
	
}
