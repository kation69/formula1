package models.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import models.entity.Miembro;
import models.persistence.DAOMiembro;

@Service
public class MiembrosGestor {
	
	@Autowired
	private DAOMiembro daoMiembros;
	
	public List<Integer> getMiembrosByEquipo(Integer equipoId) {
		List<Integer> strIds = new ArrayList<Integer>();
		List<Miembro> lstMiembros = daoMiembros.getMiembrosByEquipo(equipoId);
		for (Miembro miembro : lstMiembros) {
			strIds.add(miembro.getUsuarioId());
		}
		return strIds;
	}
	
	public List<Miembro> getListMiembrosByEquipo(Integer equipoId) {
		return daoMiembros.getMiembrosByEquipo(equipoId);
	}

	public List<Miembro> getMiembroByUserId(Integer userId) {
		return daoMiembros.buscarMiembroByUser(userId);
	}
	
	public Miembro insertar(Miembro miembro) {
		return daoMiembros.insertar(miembro);
	}

	public Miembro hasMemberTeam(Integer userId, int itemId) {
		Boolean retVal = false;
		final List<Miembro> lstMiembros = getMiembroByUserId(userId);
		if (!lstMiembros.isEmpty()) {
			if (lstMiembros.get(0).getEquipoId().equals(itemId)) {
				retVal = true;
			}
		}
		return retVal ? lstMiembros.get(0) : null;
	}
	
	public void borrar(Integer id) {
		daoMiembros.borrar(id);
	}

}
