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
				<div class="row">
					<div class="col-10">
						<h1>Herramientas</h1>
					</div>
					<c:if test="${msgError != null}">
						<div class="col-12 p-3">
							<div class="alert alert-danger" role="alert">
								${msgError}
							</div>
						</div>
					</c:if>
				</div>
				<div class="row justify-content-md-center me-5 ms-5" id="menuHerramientas">				
					<div class="col-6 p-2 m-3 buttonDiv" onclick="showCombustible();" style="${msgError != null ? 'pointer-events:none;' : ''}">
						<h1 class="display-6">Gasto de combustible</h1>
						<p>Herramienta que permite saber cuánta gasolina consume un coche en cada vuelta al circuito.</p>
						<i class="fas fa-gas-pump fa-4x" align="center"/></i>
					</div>
					<div class="col-6 p-2 m-3 buttonDiv" onclick="showKers();" style="${msgError != null ? 'pointer-events:none;' : ''}">
						<h1 class="display-6">Cálculo del KERS</h1>
						<p>Sistema de recuperación de energía en frenadas que se almacena en una batería.</p>
						<i class="fas fa-bolt fa-4x" align="center"/></i>
					</div>
				</div>
				
				<div class="row">
					<div class="col-12">
						<div class="card" id="card_combustible" style="display:none;">
							<div class="card-body">
						    	<h4 class="card-title" align="center">Gasto de combustible</h4>
								<div class="card-header">
								    <p class="card-text">Herramienta que permite saber cuánta gasolina consume un coche en cada vuelta al circuito. 
								    También se podrá saber la cantidad total de combustible que es necesario, lo que permite a los ingenieros saber la cantidad óptima de combustible con la que debe partir el coche al inicio de la carrera.</p>
							  	</div>												
							    <br/>
			   		 	      	<form action="/FormulaProject/portalResponsable/${equipoId}/gestorHerramientas/toolFuel" method="POST">
							    	<div class="row">
							    		<div class="col-6">
										  <div class="form-group">
										    <label for="labelselectCoche">Seleccione un coche disponible</label>
										    <select class="form-control" id="selectCoche_Combustible" name="selectCoche_Combustible">
						    					<c:forEach items="${ lstCoches }" var="coche">
											      <option>${coche.nombre}</option>
						    					</c:forEach>
										    </select>
										  </div>
							    		</div>
							    		<div class="col-6">
										  <div class="form-group">
										    <label for="labelselectCircuito">Seleccione un circuito</label>
										    <select class="form-control" id="selectCircuito_Combustible" name="selectCircuito_Combustible">
						    					<c:forEach items="${ lstCircuitos }" var="circuito">
											      <option>${circuito.circuitName}</option>
						    					</c:forEach>
										    </select>
										  </div>
							    		</div>
							    		<div class="col-6">
							    		</div>
							    		<div class="col-6">
							    		</div>
							    		<div class="col-12 mt-3" align="center">
							    			  <button type="submit" class="btn btn-primary">Calcular</button>
							    		</div>
							    	</div>
								</form>
							</div>
						</div>
						<div class="card" id="card_kers" style="display:none;">
							<div class="card-body">
							    <h4 class="card-title" alignt="center">Cálculo del KERS</h4>
							    <div class="card-header">
							    	<p class="card-text">Sistema de recuperación de energía en frenadas que se almacena en una batería. 
							    	La energía está a disposición del piloto en momento puntuales a su elección.
							    	El sistema MGU-K (Motor Generator Unit – Kinetic), es el encargado de capturar esta energía y guardarla en una batería.</p>
							    	<ul class="list-group">
									  <li class="list-group-item"><b>Capacidad máxima de la batería:</b>120 kW</li>
									  <li class="list-group-item">Por reglamentación, el límite de energía que se puede recuperar por vuelta es de 2MJ (que, redondeando, serían 0.6kWh)</li>
									</ul>
																    	
							    </div>
							    <br/>
			   		 	      	<form action="/FormulaProject/portalResponsable/${equipoId}/gestorHerramientas/toolKers" method="POST">
							    	<div class="row">
										<div class="col-6">
											<div class="form-group">
											    <label for="labelselectCoche">Seleccione un coche disponible</label>
											    <select class="form-control" id="selectCoche_kers" name="selectCoche_kers">
												    <c:forEach items="${ lstCoches }" var="coche">
												      <option>${coche.nombre}</option>
							    					</c:forEach>
											    </select>
										    </div>
							    		</div>
							    		<div class="col-6">
										  <div class="form-group">
										    <label for="labelselectCircuito">Seleccione un circuito</label>
										    <i class="fas fa-info-circle" data-toggle="tooltip" data-placement="top" title="Según el tipo de curva, el coche puede recuperar x kWh."/></i>
										    <select class="form-control" id="selectCircuito_kers" name="selectCircuito_kers">
										    	<c:forEach items="${ lstCircuitos }" var="circuito">
										    		<option>${circuito.circuitName}</option>
					    						</c:forEach>
										    </select>
										  </div>
							    		</div>
							    		<div class="col-6 mt-3">
										  <div class="form-group">
										    <label for="labelselectCoche">Modo de conducción</label>
										    <i class="fas fa-info-circle" data-toggle="tooltip" data-placement="top" title="Según el tipo de conducción, habrá una modificación en esa ganancia por cada curva, ya que al ser un motor híbrido parte de la energía recuperada se aplica para dar potencia a las ruedas a la salida de la curva."></i>										
										    <select class="form-control" id="selectModoCond_kers" name="selectModoCond_kers">
										      <option>Todos</option>
										      <option>Ahorrador +5%</option>
										      <option>Normal -25%</option>
										      <option>Deportivo -60%</option>
										    </select>
										  </div>
							    		</div>
							    		<div class="col-6">
							    		</div>
							    		<div class="col-6">
							    		</div>
							    		<div class="col-12 mt-3" align="center">
							    			  <button type="submit" class="btn btn-primary">Calcular</button>
							    		</div>
							    	</div>
								</form>
							</div>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	$(function () {
		  $('[data-toggle="tooltip"]').tooltip()
	})
	
	function showCombustible() {
		$('#menuHerramientas').hide('slow');
		$('#card_combustible').show('slow');
	}
	
	function showKers() {
		$('#menuHerramientas').hide('slow');
		$('#card_kers').show('slow');
		
	}
</script>
</html>