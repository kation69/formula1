<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="models.entity.Usuario" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />

<html>
	<jsp:include flush="true" page="./header.jsp"/>
	<jsp:include flush="true" page="./helpers/ModalTemplate.jsp"/>
	<script src="${baseURL}/resources/js/script_admin.js" ></script>
	
<body>
	<jsp:include flush="true" page="./jumbotron.jsp"/>
	<div class="container mt-3">
		<jsp:include flush="true" page="./menu.jsp"/>
		<section class="sectionStyle">
			<div class="row">
				<div class="col-6">
					<h3>Gestor de usuarios</h3>
				</div>
			</div>
			<div class="row">
				<div class="col-12" style="margin: 15px;">
					<table class="table table-bordered">
					  <thead>
					    <tr>
					      <th scope="col">#</th>
					      <th scope="col">Usuario</th>
					      <th scope="col">Nombre</th>
					      <th scope="col">Email</th>
					      <th scope="col">Rol</th>
					      <th scope="col">Verificacion</th>
					      <th scope="col">Opciones</th>
					    </tr>
					  </thead>
					  <tbody>	  
						<c:forEach items="${ lstUsuarios }" var="usuarios">
						    <tr>
						      <th scope="row"><c:out value="${usuarios.id}"></c:out></th>
						      <td><c:out value="${usuarios.usuario}"></c:out></td>
						      <td><c:out value="${usuarios.nombre}"></c:out></td>
						      <td><c:out value="${usuarios.email}"></c:out></td>
						      <td><div id="userRol-${usuarios.id}" >${usuarios.rol}</div></td>
						      <td>
    					      	<c:if test="${userid != usuarios.id}">				      
						      		<input type="button"
						      				class="btn ${ usuarios.verificada ? 'btn-success' : 'btn-danger'}"
						      				id="verifyBtn-${usuarios.id}"					      				
						      				onclick="showVerifyUser(${usuarios.id},this);"
						      				value="${ usuarios.verificada ? 'Verificada' : 'Sin Verificar'}" />
						      	</c:if>
					      		<c:if test="${userid == usuarios.id}">	
						      		<input type="button" class="btn btn-success" value="Verificada" disabled />
				      			</c:if>
						      </td>
						      <td>
						      	<i class="fas fa-edit xl" 
							      	onClick="showEditUser('${usuarios.id}','${usuarios.nombre}','${usuarios.email}');"></i>
						      	<c:if test="${userid != usuarios.id}">
							      	<i class="fas fa-trash-alt xl ${usuarios.id }-btnRemove-table" 
								      	onClick="OpenConfirmModalTitle(${usuarios.id}, 'usuarios', null, null);"></i>
						      	</c:if>
						      </td>
						    </tr>
						</c:forEach>
						<% session.removeAttribute("userid");%>
					  </tbody>
					</table>
				</div>
			</div>
		</section>
	</div>
	
	<!-- Modal Verificar -->
	<div class="modal fade" id="gestorUsuariosModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Verificar usuario</h5>
	        <button type="button" class="close" data-dismiss="modal" id="closeModal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      	<div id="validateUserSection">	      	
		      	<form id="validateUserForm" action="" method="POST">
				  <div class="modal-body">
				  	<div class="form-group">
				    <input type="text" id="userIdHidden" value="" hidden="true"/>
				  	<label for="exampleFormControlSelect1">Elige un rol</label>
				  	<select class="form-control" name="rol" id="rolSelect" onChange="changeSelectRol();">
					  <option value="admin">Admin</option>
					  <option value="responsable">Responsable de equipo</option>
				    </select>
			  		</div>
				</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal" id="buttonClose">Cerrar</button>
						<input type="submit" id="verifyBtn" class="btn btn-primary" value="Verificar" />
					</div>
				</form>
	      	</div>
	    </div>
	  </div>
	</div>	
	<jsp:include flush="true" page="helpers/ModalEditarUsuario.jsp"/>
				    	
	<script type="text/javascript">
		function showVerifyUser(id) {
			$('#gestorUsuariosModal').modal('show');
			$("#rolSelect").val($("#userRol-"+id).text());
			$('#userIdHidden').attr('value', id);
			var id = $('#userIdHidden').val();		
			var roleSelect = $('#rolSelect').find(":selected").val();
			var endUri = '/FormulaProject/usuarios/activarusuario?user='+id+'&rol='+roleSelect;
			$('#validateUserForm').attr('action', endUri);
		}
		
		function changeSelectRol(){
			var roleSelect = $('#rolSelect').find(":selected").val();
			var id = $('#userIdHidden').val();		
			var endUri = '/FormulaProject/usuarios/activarusuario?user='+id+'&rol='+roleSelect;
			$('#validateUserForm').attr('action', endUri);
		}	
		</script>
	
</body>
</html>