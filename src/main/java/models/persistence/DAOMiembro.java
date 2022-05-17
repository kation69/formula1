package models.persistence;

import java.util.List;

import models.entity.Miembro;

public interface DAOMiembro {
	
	List<Miembro> getMiembrosByEquipo(Integer equipoId);
	List<Miembro> buscarMiembroByUser(Integer userId);
	Miembro insertar(Miembro miembro);
	void update(Miembro miembro);
	void borrar(Integer miembroId);
	List<Miembro> getAllMiembros();
	void borrarMiembros(List<Miembro> lstMiembro);

}
