<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
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
			<div class="row mb-4">
				<div class="col-6">
					<h3>Gestor de noticias</h3>
				</div>
				<div class="col-4" align="right">
					<div class="row">
						<div class="col-12" style="height:27px;">
							<p><small>Modo de visualización</small></p>
						</div>
						<div class="col-12">
							<i class="fas fa-th" onClick="changeViewMode('grid');"></i>
							<i class="fas fa-list" onClick="changeViewMode('table');"></i>
						</div>
					</div>
				</div>
				<div class="col-2" align="right">
					<button type="button" 
							class="btn btn-primary" 
							data-toggle="modal" 
							data-target="#exampleModal" 
							id="addNewNoticia">Añadir nueva noticia</button>
				</div>
			</div>
			<c:if test="${ listaNoticias.size() == 0 }">
				<div class="alert alert-info" role="alert">No hay noticias creadas.</div>
			</c:if>
			<div id="grid_mode" class="row mt-3">
				<c:forEach items="${ listaNoticias }" var="noticia">
					<div class="col-4 mb-2" style="width: 300px;">
						<div class="card cardCircuitos p-3" >
							<c:if test="${noticia.imagen != undefined && noticia.imagen != ''}">
					      		<img src="<c:url value='/resources/imgNoticias/${noticia.imagen}'/>" 
								align="right" 
								class="card-img-top cursorPointer"
								onClick="showImage('${baseURL}', '/resources/imgNoticias/${noticia.imagen}');"
								alt="Click para ampliar la imagen"/>
					      	</c:if>
						  <div class="card-body">
						    <h5 class="card-title">${noticia.titulo}</h5>
						    <c:if test="${fn:length(noticia.cuerpo)>100}">
						    	<p class="card-text">${fn:substring( noticia.cuerpo,0,150)}...</p>
						    </c:if>
						    <c:if test="${fn:length(noticia.cuerpo)<100}">
							    <p class="card-text">${noticia.cuerpo} </p>
						    </c:if>
						  </div>
						  <div class="card-body" align="center">
						  	<c:if test="${noticia.permantlink != undefined}">
					          	<i class="fas fa-eye xl"
					          		onClick="location.replace('noticias/ver?pag=${noticia.permantlink}')"></i>
				          	</c:if>
						  	<i class="fas fa-edit xl" 
							      	onClick="OpenEditModalTitle(${noticia.id},'${noticia.titulo}','${noticia.cuerpo}','${noticia.imagen}');"></i>
					      	<i class="fas fa-trash-alt xl ${noticia.id }-btnRemove-grid"
						      	onClick="OpenConfirmModalTitle('${noticia.id}', 'noticias', null, null);"></i>
						  </div>
						</div>
					</div>
				</c:forEach>
			</div>
			<div class="row" id="table_visual" style="display: none;">
				<div class="col-12" style="margin: 15px;">
					<table class="table table-bordered">
					  <thead>
					    <tr>
					      <th scope="col">#</th>
					      <th scope="col">Titulo</th>
					      <th scope="col">Texto</th>
					      <th scope="col">Nombre Imagen</th>
					      <th scope="col">Imagen</th>
					      <th scope="col">Permalink</th>
					      <th scope="col">Opciones</th>
					    </tr>
					  </thead>
					  <tbody>	  
						<c:forEach items="${ listaNoticias }" var="noticia">
						    <tr>
						      <th scope="row"><c:out value="${noticia.id}"></c:out></th>
						      <td><c:out value="${noticia.titulo}"></c:out></td>
						      <td><c:out value="${noticia.cuerpo}"></c:out></td>
						      <td style="text-align:center;"><c:if test="${noticia.imagen != ''}">
						      		<img class="image_peque"src="${baseURL}/resources/imgNoticias/${noticia.imagen}"/></c:if></td>
						      <td><c:out value="${noticia.imagen}"></c:out></td>
						      <td><a href="noticias/ver?pag=${noticia.permantlink}"><c:out value="${noticia.permantlink}"></c:out></a></td>
						      <td>
					          	<c:if test="${noticia.permantlink != undefined}">
						          	<i class="fas fa-eye"
						          		onClick="location.replace('noticias/ver?pag=${noticia.permantlink}')"></i>
					          	</c:if>
					          	<i class="fas fa-edit" 
							      	onClick="OpenEditModalTitle(${noticia.id},'${noticia.titulo}','${noticia.cuerpo}','${noticia.imagen}');"></i>
						      	<i class="fas fa-trash-alt ${noticia.id }-btnRemove-table"
							      	onClick="OpenConfirmModalTitle(${noticia.id}, 'noticias', null, null);"></i>
						      </td>
						    </tr>
						</c:forEach>
					  </tbody>
					</table>
				</div>
			</div>
		</section>
	</div>
	
	<!-- Modal -->
	<div class="modal fade" id="insertModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">Añadir nueva noticia</h5>
	        <button type="button" class="close" data-dismiss="modal" id="closeModal" aria-label="Close">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      	<form action="/FormulaProject/noticias/insertarNoticia" id="AcctionModal" method="POST" enctype="multipart/form-data">
			  <div class="modal-body">
			  	<div class="form-group">
				  <label for="tituloInput">Id</label>
				  <input type="text" class="form-control" id="idInput" name="ids" disabled>
				</div>
				<div class="form-group">
				  <label for="tituloInput">Titulo</label>
				  <input type="text" class="form-control" id="tituloInput" name="titulo" placeholder="Introduce el titulo">
				</div>
				<div class="form-group">
				  <label for="cuerpoInput">Cuerpo de la noticia</label>
				  <textarea class="form-control" id="cuerpoInput" name="cuerpo" rows="5"></textarea>
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
	<script>
		document.getElementById('inputGroupFile01').addEventListener('change', readFileAsString)
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
		
		function OpenEditModalTitle(id,titulo,cuerpo,imagen){
			$('#AcctionModal').attr("action","/FormulaProject/noticias/editNoticia?id="+id);
			
			$("#buttonClose").click(function () {
				CloseEditModalTitle(); 
			});
			$("#closeModal").click(function () {
				CloseEditModalTitle(); 
			});
			$('#idInput').val(id);
			$('#idInput').parent().show();
			$('#tituloInput').val(titulo);
			$('#cuerpoInput').val(cuerpo);
			//$('#inputGroupFile01').val(imagen);
			$('#imagenCargar').attr("src","../../FormulaProject/resources/imgNoticias/"+imagen);
			
			$('#insertModal').modal('show');
		}
		function CloseEditModalTitle(){
			$('#AcctionModal').attr("action","/FormulaProject/noticias/insertarNoticia");
			$("#buttonClose").click(function () {
			    $('#insertModal').modal('hide'); 
			});
			$("#closeModal").click(function () {
			    $('#insertModal').modal('hide'); 
			});
			$('#idInput').parent().hide();
			$('#idInput').val("");
			$('#tituloInput').val("");
			$('#cuerpoInput').val("");
			$('#inputGroupFile01').val("");
			$('#imagenCargar').attr("src","");
			$('#insertModal').modal('hide');
		}
		$('#idInput').parent().hide();
	</script>
	
</body>
</html>