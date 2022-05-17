package helpers;

public class CustomLabels_ES {

	
	// Generic messages
	public static String error_ruta = "Ruta inexistente, vuelva a intentarlo.";
	public static String error_not_found = "Error not found.";
	// User & Login
	public static String error_login = "Error, el nombre de usuario o la contraseña no son correctos.";
	public static String verify_admin_user = "Su cuenta será verificada por un administrador";
	public static String verify_create_admin_user = "La cuenta fue administrador fue creada: admin-admin";
	public static String error_password = "Error, password con longitud mínima 5 carácteres.";
	public static String error_exist_user = "Error, nombre de usuario o email existente.";
	public static String error_no_exist_user = "Error, el usuario no existe.";
	public static String error_pendiente_verific = "Su cuenta está pendiente de verificación.";
	public static String error_edit_user = "Error al editar el usuario.";
	public static String success_edit_user = "Se modificaron los datos del usuario correctamente.";
	public static String error_borrar_responsableAsignado = "No se ha podido borrar el usuario, el responsable tiene un equipo asignado actualmente.";
	public static String success_borrado = "El usuario ha sido borrado.";
	// Noticia
	public static String success_create_noticia = "Noticia creada correctamente.";
	public static String success_modificar_noticia = "Noticia actualizo correctamente.";
	public static String success_delete_noticia = "Noticia eliminada correctamente.";
	public static String error_delete_noticia = "Error al eliminar la noticia.";
	public static String error_create_noticia = "La noticia no se pudo crear.";
	public static String error_modificar_noticia = "La noticia no se pudo modificar.";
	public static String error_create_noticia_titulo = "Es obligatorio insertar un titulo en la noticia, la notica no se pudo crear.";
	public static String error_create_noticia_cuerpo = "Es obligatorio insertar un cuerpo en la noticia, la notica no se pudo crear.";
	public static String error_create_noticia_imagen = "La noticia no se pudo crear porque la imagen no es correcta.";
	public static String error_modificar_noticia_imagen = "La noticia no se pudo modificar porque la imagen no es correcta.";
	// Circuito
	public static String error_create_circuito_imagen = "El circuito no se pudo crear porque la imagen no es correcta.";
	public static String error_edit_circuito_imagen = "El circuito no se pudo modificar porque la imagen no es correcta.";
	public static String success_edit_circuito = "El circuito se modificó correctamente.";
	public static String success_create_circuito = "El circuito se creó correctamente.";
	public static String error_create_circuito = "El circuito no se pudo crear correctamente.";
	public static String error_edit_circuito = "El circuito no se pudo modificar correctamente.";
	public static String success_date_circuito = "Se asigno la fecha correctamente.";
	public static String success_no_date_circuito = "Se desasigno la fecha correctamente.";
	public static String error_set_date_circuito = "No se pudo asingar la fecha.";
	public static String error_set_no_date_circuito = "No se pudo designar la fecha.";
	public static String success_delete_circuit = "Se borro el circuito.";
	public static String error_delete_circuit = "No se pudo borrar el circuito.";
	public static String error_delete_circuit_date = "No se pudo borrar el circuito porque tiene fecha asignada.";
	
	// Usuario
	public static String error_validar_usuario = "El usuario no se puede validar.";
	public static String error_validar_usuario_rol_equipo = "El usuario no se puede convertir de rol porque tiene un equipo asignado.";
	public static String success_validar_usuario = "Se ha validado el usuario correctamente.";
	public static String error_convertir_usuario = "No se puede convertir de rol al usuario.";
	public static String success_desverificar_usuario = "Se ha desactivado al usuario correctamente.";
	// Votacion
	public static String error_create_voto_titulo = "Es obligatorio insertar un titulo en el voto, el voto no se pudo crear.";
	public static String error_create_voto_descripcion = "Es obligatorio insertar una descripcion en el voto, el voto no se pudo crear.";
	public static String error_create_voto = "El voto no se pudo crear.";
	public static String success_delete_votacion = "La votación eliminada correctamente";
	//Votar
	public static String success_votar= "Se voto correctamente.";
	public static String error_votar_email= "El correo electronico no tiene el formato correcto.";
	public static String error_votar_repetido= "Ese correo electronico ya habia votado.";
	public static String error_votar_piloto= "Es obligatorio elegir un piloto.";
	public static String error_votar= "Error al rezaliar el voto.";
	// Pilotos
	public static String error_create_piloto_nombre = "Es obligatorio darle un nombre a un piloto. Error al crear el piloto.";
	public static String error_create_piloto_apellidos = "Es obligatorio darle un apellido a un piloto. Error al crear el piloto.";
	public static String error_create_piloto_siglas = "Es obligatorio darle unas siglas al piloto. Error al crear el piloto.";
	public static String error_create_piloto_dorsal = "Es obligatorio darle un dorsal a un piloto. Error al crear el piloto";
	public static String error_create_piloto_pais = "Es obligatorio darle una nacionalidad a un piloto. Error al crear el piloto";
	public static String error_create_piloto_foto = "Es obligatorio darle una foto a un piloto. Error al crear el piloto.";
	public static String success_create_piloto = "Piloto creado correctamente.";
	public static String error_create_piloto = "El piloto no se pudo crear.";
	public static String error_modificar_foto_piloto = "No se pudo modificar la foto del piloto";
	public static String success_modificar_piloto = "Piloto modificado correctamente.";
	public static String error_modificar_piloto = "Error al modificar el piloto.";
	// Equipos
	public static String error_create_equipos_imagen = "El equipo no se pudo crear porque la imagen no es correcta.";
	public static String error_create_equipos_nameRepeat = "No se puede asignar este nombre, ya existe un equipo. Intente otro nombre distinto.";
	public static String msg_abandonar_equipo = "Confirmando dejará de tener acceso al equipo, perderá todo el acceso a su perfil como responsable.";
	public static String confirmBorrarEquipo_lbl = "Si borra el equipo completo, los responsables de equipo perderán todos los datos. Al igual que los coches y pilotos asociados.";
	public static String error_nombreEquipo_obligatorio = "El nombre del equipo es obligatorio.";
	public static String error_twitterEquipo_obligatorio = "El twitter del equipo es obligatorio";
	public static String success_modificar_equipo = "Se ha modificado correctamente el equipo.";
	public static String error_modificar_equipo = "Error al modificar el equipo";
	public static String error_assign_equipo = "El usuario esta asignado a un equipo actualmente, debe abandonar primero el equipo.";
	public static String error_assign_creator_equipo = "El usuario es el creador del equipo actualmente, debe gestionar el traspaso.";
	// Coches
	public static String success_create_coche = "Se creo el coche correctamente.";
	public static String error_create_coche = "No se pudo crear el coche correctamente.";
	public static String error_create_coche_nombre = "Es obligatorio insertar un nombre al coche, el coche no se pudo crear.";
	public static String error_create_coche_codigo = "Es obligatorio insertar un nombre al coche, el coche no se pudo crear.";
	public static String error_create_coche_consumo = "El consumo del coche debe estar entre 24 y 100 (L/ 100km), el coche no se pudo crear.";
	public static String error_create_coche_ERS_Lento = "El ERS de curvas lentas del coche debe estar entre 0.01 y 0.06 (kWh), el coche no se pudo crear.";
	public static String error_create_coche_ERS_Medio = "El ERS de curvas medias del coche debe estar entre 0.01 y 0.06 (kWh), el coche no se pudo crear.";
	public static String error_create_coche_ERS_Rapido = "El ERS de curvas rapidas del coche debe estar entre 0.01 y 0.06 (kWh), el coche no se pudo crear.";
	public static String error_create_coche_ERS_mayor_lento = "El ERS de curvas lentas no puede ser menor que ERS de curvas medias, el coche no se pudo crear.";
	public static String error_create_coche_ERS_mayor_medio = "El ERS de curvas medias no puede ser menor que ERS de curvas rapidas, el coche no se pudo crear.";
	public static String error_create_coche_imagen = "El coche no se pudo crear porque la imagen no es correcta.";
	public static String success_editar_coche = "Se edito el coche correctamente.";
	public static String error_editar_coche = "No se pudo crear el coche correctamente.";
	public static String error_editar_coche_nombre = "Es obligatorio insertar un nombre al coche, el coche no se pudo editar.";
	public static String error_editar_coche_codigo = "Es obligatorio insertar un nombre al coche, el coche no se pudo editar.";
	public static String error_editar_coche_consumo = "El consumo del coche debe estar entre 24 y 100 (L/ 100km), el coche no se pudo editar.";
	public static String error_editar_coche_ERS_Lento = "El ERS de curvas lentas del coche debe estar entre 0.01 y 0.06 (kWh), el coche no se pudo editar.";
	public static String error_editar_coche_ERS_Medio = "El ERS de curvas medias del coche debe estar entre 0.01 y 0.06 (kWh), el coche no se pudo editar.";
	public static String error_editar_coche_ERS_Rapido = "El ERS de curvas rapidas del coche debe estar entre 0.01 y 0.06 (kWh), el coche no se pudo editar.";
	public static String error_editar_coche_ERS_mayor_lento = "El ERS de curvas lentas no puede ser menor que ERS de curvas medias, el coche no se pudo editar.";
	public static String error_editar_coche_ERS_mayor_medio = "El ERS de curvas medias no puede ser menor que ERS de curvas rapidas, el coche no se pudo editar.";
	public static String error_editar_coche_imagen = "El coche no se pudo editar porque la imagen no es correcta.";
	public static String success_borrar_coche = "El coche se elimino correctamente.";
	// Miembros
	public static String success_create_miembro = "Se ha añadido nuevo miembro al equipo.";
	public static String error_create_miembro = "Error al añadir nuevo miembro al equipo.";
	public static String success_borrar_miembro = "El miembro se elimino correctamente.";
	public static String error_borrar_miembro = "Error al eliminar el miembro.";
	// Herramientas
	public static String error_herramientas_coches = "Herramientas no disponibles, primero debes crear coches en tu equipo";	
	public static String error_herramientas_circuitos = "Herramientas no disponibles, no hay circuitos disponibles. Contacta con el administrador";	
	
}
