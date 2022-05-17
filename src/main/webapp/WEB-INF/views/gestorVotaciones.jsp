<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL"
	value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />

<html>
<jsp:include flush="true" page="./header.jsp" />
<jsp:include flush="true" page="./helpers/ModalTemplate.jsp" />
<script src="${baseURL}/resources/js/script_admin.js"></script>

<body>
	<jsp:include flush="true" page="./jumbotron.jsp" />
	<div class="container mt-3">
		<jsp:include flush="true" page="./menu.jsp" />
		<section class="sectionStyle">
			<%
			if ((session.getAttribute("usuario") != null) & (session.getAttribute("perfil") == "admin")) {
			%>
			<div class="row mb-4">
				<div class="col-6">
					<h3>Gestor de votaciones</h3>
				</div>
				<div class="col-2" align="right">
					<button type="button" class="btn btn-primary" data-toggle="modal"
						data-target="#exampleModal" id="addNewNoticia">Añadir
						nueva votacion</button>
				</div>
			</div>
			<%
			} else {
			%>
			<div class="row mb-4">
				<div class="col-6">
					<h3>Votaciones</h3>
				</div>
			</div>

			<%
			}
			%>
			<c:if test="${ listaVotaciones.size() == 0 }">
				<div class="alert alert-info" role="alert">No hay votaciones
					creadas.</div>
			</c:if>
			<div class="row" id="table_visual">
				<div class="col-12" style="margin: 15px;">
					<table class="table table-bordered">
						<thead>
							<tr>
								<th scope="col">#</th>
								<th scope="col">Titulo</th>
								<th scope="col">Descripcion</th>
								<th scope="col">Limite</th>
								<th scope="col">Permalink</th>
								<th scope="col">Opciones</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ listaVotaciones }" var="voto">
								<tr>
									<th scope="row"><c:out value="${voto.id}"></c:out></th>
									<td><c:out value="${voto.titulo}"></c:out></td>
									<td><c:out value="${voto.descripcion}"></c:out></td>
									<td><c:if test="${voto.hora != undefined}">
											<fmt:formatDate pattern="HH:mm" value="${voto.hora}" /> - <fmt:formatDate
												pattern="dd/MM/yyyy" value="${voto.limite}" />
										</c:if></td>
									<td><a href="votaciones/ver?pag=${voto.permantlink}"><c:out
												value="${voto.permantlink}"></c:out></a></td>
									<td><c:if test="${voto.permantlink != undefined}">
											<i class="fas fa-eye"
												onClick="location.replace('votaciones/ver?pag=${voto.permantlink}')"></i>
										</c:if> <%
									 	if ((session.getAttribute("usuario") != null) & (session.getAttribute("perfil") == "admin")) {
 										%> <i class="fas fa-edit"
										onClick="OpenEditModalTitle(${voto.id},'${voto.titulo}','${voto.descripcion}','${voto.limite}','<fmt:formatDate pattern = "HH:mm" value = "${voto.hora}" />');"></i>
										<i class="fas fa-calendar-check"
										onClick="location.replace('votaciones/activar/${voto.id}');"></i>
										<i class="fas fa-calendar-times"
										onClick="location.replace('votaciones/finalizar/${voto.id}');"></i>
										<i class="fas fa-trash-alt ${voto.id }-btnRemove-table"
										onClick="OpenConfirmModalTitle(${voto.id}, 'votaciones', null, null);"></i>
										<%
										}
										%></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</section>
	</div>

	<!-- Modal -->
	<div class="modal fade" id="insertModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Añadir nueva
						votacion</h5>
					<button type="button" class="close" data-dismiss="modal"
						id="closeModal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form action="/FormulaProject/votaciones/guardarVotacion"
					id="AcctionModal" method="POST" enctype="multipart/form-data">
					<div class="modal-body">
						<div class="form-group">
							<label for="tituloInput">Id</label> <input type="text"
								class="form-control" id="idInput" name="ids" disabled>
						</div>
						<div class="form-group">
							<label for="tituloInput">Titulo</label> <input type="text"
								class="form-control" id="tituloInput" name="titulo"
								placeholder="Introduce el titulo">
						</div>
						<div class="form-group">
							<label for="descripcionInput">Descripcion</label>
							<textarea class="form-control" id="descripcionInput"
								name="descripcion" rows="5"></textarea>
						</div>
						<div class="form-group">
							<label for="fechaInput">Fecha</label> <input type="date"
								class="form-control" id="fechaInput" name="fecha">
						</div>
						<div class="form-group">
							<label for="horaInput">Hora</label> <input type="time"
								class="form-control" id="horaInput" name="hora">
						</div>

						<%-- Modificación para añadir listado de pilotos --%>

						<div class="form-group">
							<label for="pilotosInput">Lista de pilotos a votar</label> 
							<select id="pilotos" name="pilotos" multiple>
								<c:forEach items="${listaPilotos}" var="pilotos">
									<option><c:out value="${pilotos.apellidos}"></c:out></option>
								</c:forEach>
							</select>
						</div>


						<%-- Modificación para añadir listado de pilotos --%>

					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary"
							data-dismiss="modal" id="buttonClose">Cerrar</button>
						<input type="submit" class="btn btn-primary"
							value="Guardar cambios" />
					</div>
				</form>
			</div>
		</div>
	</div>
	<script>
		
		function OpenEditModalTitle(id,titulo,descripcion,limite,hora){
			$('#AcctionModal').attr("action","/FormulaProject/votaciones/editVotacion?id="+id);
			
			$("#buttonClose").click(function () {
				CloseEditModalTitle(); 
			});
			$("#closeModal").click(function () {
				CloseEditModalTitle(); 
			});
			$('#idInput').val(id);
			$('#idInput').parent().show();
			$('#tituloInput').val(titulo);
			$('#descripcionInput').val(descripcion);
			$('#fechaInput').val(limite);
			$('#horaInput').val(hora);
			
			$('#insertModal').modal('show');
		}
		function CloseEditModalTitle(){
			$('#AcctionModal').attr("action","/FormulaProject/votaciones/guardarVotacion");
			$("#buttonClose").click(function () {
			    $('#insertModal').modal('hide'); 
			});
			$("#closeModal").click(function () {
			    $('#insertModal').modal('hide'); 
			});
			$('#idInput').parent().hide();
			$('#idInput').val("");
			$('#tituloInput').val("");
			$('#descripcionInput').val("");
			$('#insertModal').modal('hide');
			$('#fechaInput').val("");
			$('#horaInput').val("");
		}
		$('#idInput').parent().hide();
	</script>

</body>
</html>