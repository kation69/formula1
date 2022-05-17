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
	<script src="${baseURL}/resources/js/script_responsable.js" ></script>
	<script src="${baseURL}/resources/js/script_admin.js" ></script>
<body>
	<jsp:include flush="true" page="jumbotron.jsp"/>
	<div class="container mt-3">
			<jsp:include flush="true" page="helpers/notifyPortal.jsp" />
		<div class="row">
			<div class="col-2" align="left">		
				<jsp:include flush="true" page="menuPortal.jsp"/>
			</div>
			<div class="col-10 sectionOne p-4">
				<article>
					<div class="row">
						<div class="col-10">
							<h1>Coches</h1>
						</div>
						<div class="col-2" align="right">
							<c:if test="${editPermissions}">
									<button type="button" 
									class="btn btn-primary" 
									data-toggle="modal" 
									data-target="#exampleModal" 
									id="addNewCar">Añadir coche</button>
							</c:if>
						</div>
					</div>
					<div class="row" id="table_visual" style="margin-right: 15px;">
						<div class="col-12" style="margin: 15px;">
							<c:if test="${ cochesDetail.size() > 0 }">
								<table class="table table-bordered">
									<thead>
										<tr>
											<th scope="col">#</th>
											<th scope="col">Nombre</th>
											<th scope="col">Codigo</th>
											<th scope="col">Consumo</th>
											<th scope="col">ERS Curva Lenta</th>
											<th scope="col">ERS Curva Media</th>
											<th scope="col">ERS Curva Rapida</th>
											<th scope="col">Imagen</th>
											<th scope="col">Opciones</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${ cochesDetail }" var="coche">
											<tr>
												<th scope="row"><c:out value="${coche.id}"></c:out></th>
												<td><c:out value="${coche.nombre}"></c:out></td>
												<td><c:out value="${coche.codigo}"></c:out></td>
												<td><c:out value="${coche.consumo}"></c:out></td>
												<td><c:out value="${coche.ersCurvasLentas}"></c:out></td>
												<td><c:out value="${coche.ersCurvasMedias}"></c:out></td>
												<td><c:out value="${coche.ersCurvasRapidas}"></c:out></td>
												<td><c:if test="${coche.imagen != undefined && coche.imagen != ''}">
											      		<img src="<c:url value='/resources/imgCoches/${coche.imagen}'/>" 
														align="right" 
														class="card-img-top cursorPointer"
														onClick="showImage('${baseURL}', '/resources/imgCoches/${coche.imagen}');"
														alt="Click para ampliar la imagen"/>
											      	</c:if>
												</td>
												<td>
													<c:if test="${editPermissions}">						
														<i class="fas fa-edit"
														onClick="OpenEditModalTitle('${coche.id}','${coche.nombre}','${coche.codigo}','${coche.consumo}','${coche.ersCurvasLentas}','${coche.ersCurvasMedias}','${coche.ersCurvasRapidas}','${coche.imagen}');"></i>
														<i class="fas fa-trash-alt ${coche.id }-btnRemove-table"
														onClick="OpenConfirmModalTitle(${coche.id}, 'portalResponsable/gestorCoches', null, null);"></i>
													</c:if>
												</td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</c:if>
							<c:if test="${ cochesDetail.size() == 0 }">
								<div class="alert alert-info" role="alert">No existen coches.</div>
							</c:if>
						</div>
					</div>					
				</article>
			</div>
		</div>
	</div>
	<!-- Modal -->
	<div class="modal fade" id="insertModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Añadir nuevo coche</h5>
	        <button type="button" class="close" data-dismiss="modal" id="closeModal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      	<form action="/FormulaProject/portalResponsable/${id}/gestorCoches/insertarCoche" id="AcctionModal" method="POST" enctype="multipart/form-data">
			  <div class="modal-body">
			  	<div class="form-group">
				  <label for="idInput">Id</label>
				  <input type="text" class="form-control" id="idInput" name="ids" disabled>
				</div>
				<div class="form-group">
				  <label for="nombresInput">Nombre</label>
				  <input type="text" class="form-control" id="nombresInput" name="nombre" placeholder="Introduce el nombre">
				</div>
				<div class="form-group">
				  <label for="codigoInput">Código Modelo</label>
				  <input type="text" class="form-control" id="codigoInput" name="codigo"  placeholder="Introduce el codigo">
				</div>
				<div class="form-group">
				  <label for="consumoInput">Consumo (L/100 Km)</label>
				  <input type="number" class="form-control" id="consumoInput" name="consumo" value="34" min="20" max="100" step="0.1">
				</div>
				<div class="form-group">
				  <label for="ERS_Curvas_LentasInput">ERS Curvas Lentas (kWh)</label>
				  <input type="number" class="form-control" id="ERS_Curvas_LentasInput" name="ERS_Curvas_Lentas" value="0.01" min="0.01" max="0.06" step="0.01">
				</div>
				<div class="form-group">
				  <label for="ERS_Curvas_MediasInput">ERS Curvas Medias (kWh)</label>
				  <input type="number" class="form-control" id="ERS_Curvas_MediasInput" name="ERS_Curvas_Medias"  value="0.01" min="0.01" max="0.06" step="0.01">
				</div><div class="form-group">
				  <label for="ERS_Curvas_RapidasInput">ERS Curbas Rapidas (kWh)</label>
				  <input type="number" class="form-control" id="ERS_Curvas_RapidasInput" name="ERS_Curvas_Rapidas"  value="0.01" min="0.01" max="0.06" step="0.01">
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
					<input id="buttonSubmit" type="submit" class="btn btn-primary" value="Añadir coche" />
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
		    document.getElementById('buttonSubmit').disabled;
		    var reader = new FileReader();
		    reader.onload = function(event) {
		        console.log('File content:', event.target.result);
		    };
		    reader.readAsDataURL(files[0]);
		    
		    reader.onload = function(){
		    	document.getElementById('imagenCargar').src=reader.result;
	        }
		}
		
		function OpenEditModalTitle(id,nombre,codigo,consumo,ERSLento,ERSRapido,ERSMedio,imagen){
			$('#AcctionModal').attr("action","/FormulaProject/portalResponsable/${id}/gestorCoches/editarCoche/"+id);
			
			$("#buttonClose").click(function () {
				CloseEditModalTitle(); 
			});
			$("#closeModal").click(function () {
				CloseEditModalTitle(); 
			});
			$('#idInput').val(id);
			$('#idInput').parent().show();
			$('#nombresInput').val(nombre);
			$('#codigoInput').val(codigo);
			$('#consumoInput').val(consumo);

			$('#ERS_Curvas_RapidasInput').val(ERSRapido);
			$('#ERS_Curvas_LentasInput').val(ERSLento);
			$('#ERS_Curvas_MediasInput').val(ERSMedio);
			

			$('#ERS_Curbas_RapidasInput').val(ERSRapido);
			$('#ERS_Curbas_LentasInput').val(ERSLento);
			$('#ERS_Curbas_MediasInput').val(ERSMedio);
			if(imagen!=""){
				$('#imagenCargar').attr("src","/FormulaProject/resources/imgCoches/"+imagen);
			}
			else{
				$('#imagenCargar').attr("src","");
			}
			$('#buttonSubmit').val("Guardar cambios");

			$('#insertModal').modal('show');
		}
		function CloseEditModalTitle(){
			$('#AcctionModal').attr("action","/FormulaProject/portalResponsable/${id}/gestorCoches/insertarCoche");
			$("#buttonClose").click(function () {
			    $('#insertModal').modal('hide'); 
			});
			$("#closeModal").click(function () {
			    $('#insertModal').modal('hide'); 
			});

			$('#insertModal').modal('hide');
			$('#idInput').parent().hide();
			$('#idInput').val("");
			$('#nombresInput').val("");
			$('#codigoInput').val("");
			$('#consumoInput').val("34");

			$('#ERS_Curvas_RapidasInput').val("0.01");
			$('#ERS_Curvas_LentasInput').val("0.01");
			$('#ERS_Curvas_MediasInput').val("0.01");

			$('#ERS_Curbas_RapidasInput').val("0.01");
			$('#ERS_Curbas_LentasInput').val("0.01");
			$('#ERS_Curbas_MediasInput').val("0.01");
			$('#inputGroupFile01').val("");
			$('#imagenCargar').attr("src","");
			$('#buttonSubmit').val("Añadir coche");

		}
		$('#idInput').parent().hide();
	</script>
</body>
</html>