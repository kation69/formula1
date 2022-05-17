<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />

<html>
	<head>
		<jsp:include flush="true" page="header.jsp"/>
	</head>
<body>
	<jsp:include flush="true" page="jumbotron.jsp"/>
	<div class="container">
		
		<div id="#wizard-container">
			<div class="slide-container">
				<div class="wrapper" id="zero-step">
					<div class="wizard-card">
						<div class="wizard-card__image wizard-card__image--bg">
							<img src="<c:url value='/resources/img/wizard1.png'/>" style="width:"/>
						</div>
						<div class="wizard-card__level">Crea tu primera escudería</div>
						<div class="wizard-card__unit-name">Añade un nombre:</div>
						<form class="align-items-center pt-1 g-3 px-3">
							<div class="form-group">
								<input type="text" class="form-control" id="nombreEquipo" name="nombreFirst" alt="Nombre del equipo">
							</div>
							<div class="form-group">
								<input type="button" onclick="handleNext();" class="btn btn-danger mt-4" value="Siguiente">
							</div>
						</form>
						<div class="wizard-card__unit-description">
						</div>
			      	</div>
				</div>
				<div class="wrapper" id="one-step" style="display:none;">
					<div class="wizard-card">
						<div class="wizard-card__image wizard-card__image--bg">
							<img id="imagenCargar" class="img-group-text"/>
						</div>
						<div class="wizard-card__level">Personaliza tu escudería</div>
						<div class="wizard-card__unit-name">Sube tu logo:</div>						
						<form class="align-items-center pt-1 g-3 px-3" action="/FormulaProject/portalResponsable/creaEquipo" 
								method="POST"
								enctype="multipart/form-data" >
							<div class="form-group">
								<div class="input-group mb-3">
								  	<div class="custom-file">
								  		<input type="file" class="custom-file-input" id="inputGroupFile01" name="file">
								  		<div>
								  			<img id="imagenCargar" class="img-group-text"/>
								  		</div>
								  	</div>
						 		</div>
							</div>
							<input type="text" class="form-control" id="nombreEquipoHidden" name="nombre" alt="Nombre del equipo" hidden>							
							<div class="form-group">
								<label for="numVueltas">Twitter del equipo</label>
								<input type="text" class="form-control" id="twitter" name="twitter" alt="Twitter del equipo">
							</div>
							<div class="form-group">
								<input type="submit" class="btn btn-primary mt-4" value="Finalizar">
							</div>
						</form>
						<div class="wizard-card__unit-description">
						</div>
			      	</div>
				</div>
			</div> <!-- end container -->
		</div>
	</div>	
</body>
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

	function handleNext() {
		$( "#zero-step" ).hide( "slow", function() {
			$('#nombreEquipoHidden').val($('#nombreEquipo').val());
			$("#one-step").show();
		});
	}
</script>
</html>