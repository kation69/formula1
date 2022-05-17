<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL"
	value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />

<html>
<head>
<jsp:include flush="true" page="header.jsp" />
</head>
<jsp:include flush="true" page="./helpers/ModalTemplate.jsp" />
<script src="${baseURL}/resources/js/script_responsable.js"></script>
<body>
	<jsp:include flush="true" page="jumbotron.jsp" />
	<div class="container mt-3">
		<jsp:include flush="true" page="helpers/notifyPortal.jsp" />
		<div class="row">
			<div class="col-2" align="left">
				<jsp:include flush="true" page="menuPortal.jsp" />
			</div>
			<div class="col-10 sectionOne">
				<article>
					<div class="row" id="table_visual" style="margin: 15px;">
						<div class="col-10">
							<h1>Pilotos</h1>
						</div>
						<div class="col-2">
							<c:if test="${editPermissions}">
								<button type="button" class="btn btn-primary"
									data-toggle="modal" data-target="#exampleModal"
									id="addNewPiloto">Añadir piloto</button>
							</c:if>
						</div>
					</div>
					<div class="row" id="table_visual" style="margin-right: 15px;">
						<div class="col-12" style="margin: 15px;">
							<c:if test="${ listaPilotos.size() > 0 }">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th scope="col">#</th>
											<th scope="col">Nombre</th>
											<th scope="col">Apellidos</th>
											<th scope="col">Siglas</th>
											<th scope="col">Dorsal</th>
											<th scope="col">Foto</th>
											<th scope="col">Pais</th>
											<th scope="col">Twitter</th>
											<th scope="col">Opciones</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${ listaPilotos }" var="piloto">
											<tr>
												<th scope="row"><c:out value="${piloto.id}"></c:out></th>
												<td><c:out value="${piloto.nombre}"></c:out></td>
												<td><c:out value="${piloto.apellidos}"></c:out></td>
												<td><c:out value="${piloto.siglas}"></c:out></td>
												<td><c:out value="${piloto.dorsal}"></c:out></td>
												<td style="text-align: right;"><c:if
														test="${piloto.foto != ''}">
														<img class="image_peque"
															src="${baseURL}/resources/imgPilotos/${piloto.foto}" />
													</c:if></td>
												<td><c:out value="${piloto.pais}"></c:out></td>
												<td><c:out value="${piloto.twitter}"></c:out></td>
												<td>
												<c:if test="${editPermissions}">	
												<i class="fas fa-edit"
													onClick="OpenEditModalTitle('${piloto.id}','${piloto.nombre}','${piloto.apellidos}','${piloto.siglas}','${piloto.dorsal}','${piloto.foto}','${piloto.pais}','${piloto.twitter}');"></i>
													<i class="fas fa-trash-alt ${piloto.id}-btnRemove-table"
													onClick="OpenConfirmModalTitle('${piloto.id}', 'portalResponsable/gestorPilotos', null, null);"></i>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:if>
							<c:if test="${ listaPilotos.size() == 0 }">
								<div class="alert alert-info" role="alert">No existen
									pilotos.</div>
							</c:if>
						</div>
					</div>
				</article>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="insertModal" tabindex="-1" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="exampleModalLabel">Añadir nuevo
						piloto</h5>
					<button type="button" class="close" data-dismiss="modal"
						id="closeModal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<form
					action="/FormulaProject/portalResponsable/${equipoId}/gestorPilotos/insertarPiloto"
					id="AcctionModal" method="POST" enctype="multipart/form-data">
					<div class="modal-body">
						<div class="form-group">
							<label for="idInput">Id</label> <input type="text"
								class="form-control" id="idInput" name="ids" disabled>
						</div>
						<div class="form-group">
							<label for="nombreInput">Nombre</label> <input type="text"
								class="form-control" id="nombreInput" name="nombre"
								placeholder="Introduce el nombre del piloto">
						</div>
						<div class="form-group">
							<label for="apellidosInput">Apellidos</label> <input type="text"
								class="form-control" id="apellidosInput" name="apellidos"
								placeholder="Introduce los apellidos del piloto">
						</div>
						<div class="form-group">
							<label for="siglasInput">Siglas</label> <input
								class="form-control" id="siglasInput" name="siglas"
								placeholder="Introduce las siglas del piloto">
						</div>
						<div class="form-group">
							<label for="dorsalInput">Dorsal </label> <input type="number"
								class="form-control" id="dorsalInput" name="dorsal" min="1"
								max="99" step="1">
						</div>
						<div class="form-group">
							<div class="input-group mb-3">
								<div class="custom-file">
									<input type="file" class="custom-file-input"
										id="inputGroupFile01" name="file">
									<div>
										<img id="imagenCargar" class="img-group-text" style="width:20%;max-width:100px" hspace="150" />
									</div>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label for="paisInput">Pais</label> <input type="text" size="25"
								class="form-control" id="paisInput" name="pais"
								placeholder="Introduce el pais del piloto">
						</div>
						<div class="form-group">
							<label for="twitterInput">Twitter</label> <input type="text"
								class="form-control" id="twitterInput" name="twitter"
								placeholder="Introduce la cuenta de Twitter del piloto">
						</div>
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
		if (document.getElementById('inputGroupFile01')!=undefined){
			document.getElementById('inputGroupFile01').addEventListener('change', readFileAsString);
		}
		
		function readFileAsString() {
		    var files = this.files;
		    if (files.length === 0) {
		        console.log('No file is selected');
		        document.getElementById('imagenCargar').src="";
		        return;
		    }
		    //document.getElementById('buttonSubmit').disabled;
		    var reader = new FileReader();
		    reader.onload = function(event) {
		        console.log('File content:', event.target.result);
		    };
		    reader.readAsDataURL(files[0]);
		    
		    reader.onload = function(){
		    	document.getElementById('imagenCargar').src=reader.result;
	        }
		}
		
		function OpenEditModalTitle(id,nombre,apellidos,siglas,dorsal,foto,pais,twitter){
			$('#AcctionModal').attr("action","/FormulaProject/portalResponsable/${id}/gestorPilotos/editarPiloto/"+id);
			
			$("#buttonClose").click(function () {
				CloseEditModalTitle(); 
			});
			$("#closeModal").click(function () {
				CloseEditModalTitle(); 
			});
			$('#idInput').val(id);
			$('#idInput').parent().show();
			$('#nombreInput').val(nombre);
			$('#apellidosInput').val(apellidos);
			$('#siglasInput').val(siglas);
			$('#dorsalInput').val(dorsal);
			$('#imagenCargar').attr("src","../../FormulaProject/resources/imgPilotos/"+foto);
			$('#paisInput').val(pais);
			$('#twitter').val(twitter);
			
			$('#insertModal').modal('show');
		}
		function CloseEditModalTitle(){
			$('#AcctionModal').attr("action","/FormulaProject/portalResponsable/${id}/gestorPilotos/editarPiloto");
			$("#buttonClose").click(function () {
			    $('#insertModal').modal('hide'); 
			});
			$("#closeModal").click(function () {
			    $('#insertModal').modal('hide'); 
			});

			$('#insertModal').modal('hide');
			$('#idInput').parent().hide();
			$('#idInput').val("");
			$('#nombreInput').val("");
			$('#apellidosInput').val("");
			$('#siglasInput').val("");
			$('#dorsalInput').val("");
			$('#inputGroupFile01').val("");
			$('#imagenCargar').attr("src","");
			$('#paisInput').val("");
			$('#twitterInput').val("");
			
		}
		$('#idInput').parent().hide();
	</script>
</body>
</html>