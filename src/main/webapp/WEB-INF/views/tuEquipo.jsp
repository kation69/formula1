<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />

<html>
	<head>
		<jsp:include flush="true" page="header.jsp"/>
	</head>
	<jsp:include flush="true" page="./helpers/ModalTemplate.jsp"/>
	<jsp:include flush="true" page="./helpers/ModalSearch.jsp"/>
	<script src="${baseURL}/resources/js/script_responsable.js" ></script>
<body>
	<jsp:include flush="true" page="jumbotron.jsp"/>
	<div class="container mt-3">
		<div class="row">
			<jsp:include flush="true" page="helpers/notifyPortal.jsp" />
			<div class="col-2" align="left">		
				<jsp:include flush="true" page="menuPortal.jsp"/>
			</div>
			<div class="col-10 sectionOne p-4">
				<h1>${ equipoDetail.nombre }</h1>
				<div class="row">
					<div class="col-12" align="center">
						<div class="card TuEquipo">
							<div class="card-body">
							    <h4 class="card-title">Ficha del equipo</h4>
							    <br/>							    
							    <div class="row">
							    	<div class="col-6">
										<img src="<c:url value='/resources/imgEquipos/${equipoDetail.logo}'/>" 
											 class="img-fluid"/>
							    	</div>
							    	<div class="col-6">
										<ul class="list-group">
											<li class="list-group-item"><b>Nombre:</b> ${equipoDetail.nombre}</li>
											<li class="list-group-item"><b>Fecha de creación:</b> ${equipoDetail.fechaCreacion}</li>
											<li class="list-group-item">
												<i class="fab fa-twitter"></i> ${equipoDetail.twitter} 
											</li>
									  	</ul>
		  								<c:if test="${privilegesCreater}">						  	
										    <div class="card-footer mt-5">
											    <h5>Opciones de administración</h5>
											  	<table>
											  		<tr><td>
														  <button class="btn btn-primary" 
														  	onClick="OpenEditModalTitle('${equipoDetail.id}','${equipoDetail.nombre}','${equipoDetail.fechaCreacion}','${equipoDetail.twitter}','${equipoDetail.logo}','${usuarioCreador}');">
															<i class="fas fa-pencil" ></i>Editar equipo</button>
												  	</td></tr>
											  		<tr><td>
														  <button class="btn btn-primary" 
														  	onClick="OpenSearchModal('${equipoDetail.id}', '0');">
															<i class="fas fa-exchange-alt" ></i>Reasignar creador</button>
												  	</td></tr>
												  	<tr><td>
												  		<button class="btn btn-danger" 
												  			onClick="OpenConfirmModalTitle('${equipoDetail.id}', 'portalResponsable/gestorEquipos', '${confirmBorrarEquipo_lbl}', true);">
												  				<i class="fas fa-trash-alt" ></i>Eliminar equipo</button>
													</td></tr>
											  	</table>
										    </div>
									   	</c:if>
									   	<c:if test="${!privilegesCreater  && !accessAdmin}">
									   		<div class="card-footer mt-5">
											    <h5>Opciones de administración</h5>
											  	<table>
												  	<tr><td>
												  		<button class="btn btn-danger" 
												  			onClick="OpenConfirmModalTitle('${userMemberId}', 'portalResponsable/gestorMiembros', '${confirmAbandonar_label}', true);">
												  				<i class="fas fa-sign-out-alt" ></i>Abandonar equipo</button>
													</td></tr>
											  	</table>
										    </div>
									   	</c:if>
							    	</div>
							    </div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="insertModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Editar equipo</h5>
	        <button type="button" class="close" data-dismiss="modal" id="closeModal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      	<form action="" id="AcctionModal" method="POST" enctype="multipart/form-data">
			  <div class="modal-body">
			  	<div class="form-group">
				  <label for="tituloInput">Id</label>
				  <input type="text" class="form-control" id="idInput" name="idInput" disabled>
				</div>
				<div class="form-group">
				  <label for="cuerpoInput">Fecha de creación</label>
				  <input type="text" class="form-control" id="fechaCreacion" name="fechaCreacion" disabled>
				</div>
				<div class="form-group">
				  <label for="cuerpoInput">Usuario creador</label>
				  <input type="text" class="form-control" id="usuarioCreador" name="usuarioCreador" disabled>
				</div>
				<div class="form-group">
				  <label for="tituloInput">Nombre del equipo</label>
				  <input type="text" class="form-control" id="nombreEquipo" name="nombreEquipo" placeholder="Introduce un nombre">
				</div>
				<div class="form-group">
				  <label for="cuerpoInput">Twitter</label>
				  <input type="text" class="form-control" id="twitterEquipo" name="twitterEquipo" placeholder="Introduce twitter">
				</div>
				<div class="form-group form-image">
					<div class="input-group mb-3">
					  	<div class="custom-file">
					  		<input type="file" class="custom-file-input" id="inputGroupFile01" name="file">
					  		<div>
					  			<img id="imagenCargar" class="img-group-text"/>
					  		</div>
					  	</div>
			 		</div>
				</div>
			  </div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-dismiss="modal" id="buttonClose">Cerrar</button>
					<input type="submit" class="btn btn-primary" value="Guardar cambios" />
				</div>
			</form>
	    </div>
	  </div>
	</div>
</body>
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

	function OpenEditModalTitle(id, nombre, fechaCreacion, twitter, logo, usuarioCreador) {
		$('#AcctionModal').attr("action","/FormulaProject/portalResponsable/"+id+"/gestorEquipos/editarEquipo");
		
		$("#buttonClose").click(function () {
			CloseEditModalTitle(); 
		});
		$("#closeModal").click(function () {
			CloseEditModalTitle(); 
		});
		$('#idInput').val(id);
		$('#idInput').parent().show();
		$('#nombreEquipo').val(nombre);
		$('#twitterEquipo').val(twitter);
		$('#fechaCreacion').val(fechaCreacion);
		$('#usuarioCreador').val(usuarioCreador);
		console.log("logo");
		console.log(logo);
		if(logo != "") {
			$('#imagenCargar').attr("src","../../resources/imgEquipos/"+logo);
		} else {
			$('#imagenCargar').attr("src","");			
		}		
		$('#insertModal').modal('show');
	}
	function CloseEditModalTitle(){
		$('#AcctionModal').attr("action","");
		$("#buttonClose").click(function () {
		    $('#insertModal').modal('hide'); 
		});
		$("#closeModal").click(function () {
		    $('#insertModal').modal('hide'); 
		});
		$('#idInput').parent().hide();
		$('#idInput').val("");
		$('#nombreEquipo').val("");
		$('#twitterEquipo').val("");
		$('#fechaCreacion').val("");
		$('#usuarioCreador').val("");
		$('#inputGroupFile01').val("");
		$('#imagenCargar').attr("src","");
		$('#insertModal').modal('hide');
	}

</script>
</html>