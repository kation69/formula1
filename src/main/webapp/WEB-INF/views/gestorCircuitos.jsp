<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="helpers.CustomLabels_ES"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="p" uri="/WEB-INF/functions/circuit_function.tld" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

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
				
				<% if ((session.getAttribute("usuario") != null) & (session.getAttribute("perfil") == "admin")) { %>
					<div class="col-6">
						<h3>Gestor de circuitos</h3>
					</div>
					<div class="col-4" align="right">
						<div class="row">
							<div class="col-12" style="height:27px;">
								<p><small>Modo de visualización</small></p>
							</div>
							<div class="col-12">
								<i class="fas fa-th" onClick="changeViewMode('grid');"></i>
								<i class="fas fa-list" onClick="changeViewMode('table');"></i>
								<i class="fas fa-calendar-alt" onClick="changeViewMode('calendar');"></i>
								<i class="fas fa-calendar" onClick="changeViewMode('table_calendar');"></i>
							</div>
						</div>
					</div>
					
					<div class="col-2" align="right">		      	
						<button type="button" 
							class="btn btn-primary" 
							data-toggle="modal" 
							data-target="#exampleModal" 
							id="addCircuitoBtn">Añadir nuevo circuito</button>
							
					</div>
				<%} else {%>
					<div class="col-6">
						<h3>Calendario</h3>
					</div>
					<div class="col-4" align="right">
						<div class="row">
							<div class="col-12" style="height:27px;">
								<p><small>Modo de visualización</small></p>
							</div>
							<div class="col-12">
								<i class="fas fa-calendar-alt" onClick="changeViewMode('calendar');"></i>
								<i class="fas fa-calendar" onClick="changeViewMode('table_calendar');"></i>
							</div>
						</div>
					</div>
					
				<% }%>
			</div>
			<c:if test="${ circuitosPersist.size() == 0 }">
				<div class="alert alert-info" role="alert">No hay circuitos creados.</div>
			</c:if>
			<div id="grid_mode" class="row mt-3">
				<c:forEach items="${ circuitosPersist }" var="item">
					<div class="col-4 mb-2" style="width: 300px;">
						<div class="card cardCircuitos p-3" >
							<c:if test="${item.imgTrazado != undefined && item.imgTrazado != ''}">
					      		<img src="<c:url value='/resources/imgCircuit/${item.imgTrazado}'/>" 
								align="right" 
								class="card-img-top cursorPointer"
								onClick="showImage('${baseURL}', '/resources/imgCircuit/${item.imgTrazado}');"
								alt="Click para ampliar la imagen"/>
					      	</c:if>
						  <div class="card-body">
						    <h5 class="card-title">${item.circuitName}</h5>
						    <p class="card-text">${item.locality} - ${item.country}</p>
						  </div>
						  <ul class="list-group list-group-flush" style="background-color: black">
						    <li class="list-group-item"><b>Num de vueltas: </b><c:out value="${item.numVueltas}"></c:out></li>
						    <li class="list-group-item"><b>Longitud: </b><c:out value="${item.longitude}"></c:out></li>
						    <li class="list-group-item"><b>Curvas rápidas: </b><c:out value="${item.curvasRapidas}"></c:out></li>
						    <li class="list-group-item"><b>Curvas medias: </b><c:out value="${item.curvasMedias}"></c:out></li>
						    <li class="list-group-item"><b>Curvas lentas: </b><c:out value="${item.curvasLentas}"></c:out></li>
						  </ul>
						  <div class="card-body">
						    <p class="card-text">Fecha asignada: ${item.fecha}</p>
						  </div>
						  <div class="card-body" align="center">
						  	<i class="fas fa-eye xl"
						          		onClick="location.replace('circuito/ver/${fn:replace(item.circuitName, ' ', '')}')"></i>	
						  	<% if (session.getAttribute("usuario") != null) { 
							      		if (session.getAttribute("perfil") == "admin") { %>
							  	<i class="fas fa-edit xl" 
								      	onClick="EditarCircuito(${item.id},'${item.imgTrazado}','${item.circuitName}','${item.locality}','${item.country}',${item.longitude},${item.numVueltas},${item.curvasRapidas},${item.curvasMedias},${item.curvasLentas},'/resources/imgCircuit/${item.imgTrazado}');"></i>
								<i class="fas fa-calendar-alt xl" onClick="asignarFecha(${item.id},'${item.circuitName}');"></i>
								<c:if test="${item.fecha==undefined}">
						      		<i class="fas fa-trash-alt xl ${item.id }-btnRemove-grid"
							      	onClick="OpenConfirmModalTitle('${item.id}', 'circuito', null, null);"></i>
							    </c:if>
							 <%}} %>
						  </div>
						</div>
					</div>
				</c:forEach>
			</div>
			<div id="table_visual" class="row mt-3" style="display: none;">
				<div class="col-12" style="margin: 15px;">
					<table class="table table-bordered">
					  <thead>
					    <tr>
					      <th scope="col"></th>
					      <th scope="col">Nombre</th>
					      <th scope="col">Localidad</th>
					      <th scope="col">País</th>
					      <th scope="col">Longitud</th>
					      <th scope="col">Nº vueltas</th>
					      <th scope="col">Curvas rápidas</th>
					      <th scope="col">Curvas medias</th>
					      <th scope="col">Curvas lentas</th>
					      <th scope="col">Fecha</th>
					      <th scope="col">Opciones</th>
					    </tr>
					  </thead>
					  <tbody>
						<c:forEach items="${circuitosPersist }" var="item">
						    <tr>
						      <td>
						      <c:if test="${item.circuitId != undefined}">
						      	<img src="<c:url value='/resources/imgCircuit/${item.circuitId}.png'/>" align="right" class="img-fluid"/>
						      </c:if>
						      <c:if test="${item.circuitId == undefined}">
						      	<c:if test="${item.imgTrazado != undefined && item.imgTrazado != ''}">
						      		<img src="<c:url value='/resources/imgCircuit/${item.imgTrazado}'/>" align="right" class="img-fluid"/>
						      	</c:if>
						      </c:if>
							  </td>
						      <td>
						      	<c:if test="${item.url != undefined}">
						      		<a href="${item.url}"><c:out value="${item.circuitName}"></c:out></a>
						      	</c:if>
						      	<c:if test="${item.url == undefined}">
						      		<c:out value="${item.circuitName}"></c:out>
						      	</c:if>
						      </td>
						      <td><c:out value="${item.locality}"></c:out></td>
						      <td><c:out value="${item.country}"></c:out></td>
						      <td><c:out value="${item.longitude}"></c:out></td>
						      <td><c:out value="${item.numVueltas}"></c:out></td>
						      <td><c:out value="${item.curvasRapidas}"></c:out></td>
						      <td><c:out value="${item.curvasMedias}"></c:out></td>
						      <td><c:out value="${item.curvasLentas}"></c:out></td>
						      <td><c:out value="${item.fecha}"></c:out></td>
						      <td>      
						      	<i class="fas fa-eye"
						          		onClick="location.replace('circuito/ver/${fn:replace(item.circuitName, ' ', '')}')"></i>					      
					      		<% if (session.getAttribute("usuario") != null) { 
							      		if (session.getAttribute("perfil") == "admin") { %>
						      		<i class="fas fa-edit" 
								      	onClick="EditarCircuito(${item.id},'${item.imgTrazado}','${item.circuitName}','${item.locality}','${item.country}',${item.longitude},${item.numVueltas},${item.curvasRapidas},${item.curvasMedias},${item.curvasLentas},'/resources/imgCircuit/${item.imgTrazado}');"></i>
									<i class="fas fa-calendar-alt" onClick="asignarFecha('${item.id}','${item.circuitName}');"></i>
							      	<c:if test="${item.fecha==undefined}">
								      	<i class="fas fa-trash-alt ${item.id }-btnRemove-table"
								      	onClick="OpenConfirmModalTitle('${item.id}', 'circuito', null, null);"></i>
								    </c:if>
							    <%}} %>
						      </td>
						    </tr>
						</c:forEach>
					  </tbody>
					</table>
				</div>
			</div>
			<div id="calendar_visual" style="display: none;">
					<c:if test="${p:sortByFecha(circuitosPersist).size()>0}">
					<h3>Circuitos con fecha asignada</h3>
					<div class="row mt-3" >
					<c:set var="MES" value="1"/>
					<c:set var="NEW_CALENDAR" value="false"/>
					<c:set var="DIA" value=""/>
					<c:set var="ANIO" value="1"/>
					<c:set var="NEW_CALENDAR_PAGE" value="0"/>
					<c:set var="NEW_CALENDAR_PAGE_LAST_INIT" value="0"/>
					<c:set var="NEW_CALENDAR_DAY" value="true"/>
					<c:set var="LAST_DAY" value="0"/>
					<c:set var="CAUSA" value="0"/>
					
					<c:forEach items="${p:sortByFecha(circuitosPersist) }" var="rows">		
						<c:if test="${ANIO != fn:substring(rows.fecha,0,4) }">
							<c:set var="ANIO" value="${fn:substring(rows.fecha,0,4)}"/>
							<c:set var="NEW_CALENDAR" value="true"/>
							<c:set var="NEW_CALENDAR_DAY" value="true"/>
							<c:set var="CAUSA" value="ANIO"/>
							<c:if test="${DATOS_CALENDARIO!=undefined}">
								<c:set var="NEW_CALENDAR_PAGE_LAST_INIT" value="${DATOS_CALENDARIO.DIA_SEMANA_INICIO }"/>
							</c:if>
							<c:set var="DATOS_CALENDARIO" value="${p:datos_calendario(MES,ANIO)}"/>
						</c:if>
						<c:if test="${MES != fn:substring(rows.fecha,5,7) }">
							<c:set var="MES" value="${fn:substring(rows.fecha,5,7)}"/>
							<c:set var="NEW_CALENDAR" value="true"/>
							<c:set var="NEW_CALENDAR_DAY" value="true"/>
							<c:set var="CAUSA" value="MES"/>
							<c:if test="${DATOS_CALENDARIO!=undefined}">
								<c:set var="NEW_CALENDAR_PAGE_LAST_INIT" value="${DATOS_CALENDARIO.DIA_SEMANA_INICIO }"/>
							</c:if>
							<c:set var="DATOS_CALENDARIO" value="${p:datos_calendario(MES,ANIO)}"/>
						</c:if>
						<c:if test="${DIA == fn:substring(rows.fecha,8,11) and NEW_CALENDAR =='false' }">
							<c:set var="NEW_CALENDAR_DAY" value="false"/>
						</c:if>
						<c:if test="${DIA != fn:substring(rows.fecha,8,11) }">
							<c:set var="DIA" value="${fn:substring(rows.fecha,8,11) }"/>
							<fmt:parseNumber var = "DIA" integerOnly = "true" type = "number" value = "${DIA}" />
							<c:set var="NEW_CALENDAR_DAY" value="true"/>
						</c:if>
						<c:if test="${NEW_CALENDAR=='true' && NEW_CALENDAR_PAGE!='0'}">
							<c:if test="${LAST_DAY!=0}">
								<c:forEach items="1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31" var="dias">
									
									<c:if test="${LAST_DAY!=0 && (LAST_DAY-dias)<0 && dias!=DATOS_CALENDARIO.DIA_FIN}">
										<c:if test="${dias==8-NEW_CALENDAR_PAGE_LAST_INIT || dias==15-NEW_CALENDAR_PAGE_LAST_INIT || dias==22-NEW_CALENDAR_PAGE_LAST_INIT || dias==29-NEW_CALENDAR_PAGE_LAST_INIT}">
											<tr>
										</c:if>
											<td>${dias}</td>
										
									</c:if>
									<c:if test="${dias==DATOS_CALENDARIO.DIA_FIN && DATOS_CALENDARIO.DIA_SEMANA_FIN!='7'}">
										<td colspan="${6-DATOS_CALENDARIO.DIA_SEMANA_FIN}"></td>
									</c:if>
								</c:forEach>
							</c:if>
								</tbody>
							</table>
							</div>
							
							<c:set var="LAST_DAY" value="0"/>
							<!--<c:out value="NUEVO-${CAUSA}"/>-->
						</c:if>
						<c:if test="${NEW_CALENDAR=='true'}">
							<c:set var="NEW_CALENDAR" value="false"/>
							<c:set var="NEW_CALENDAR_PAGE" value="${NEW_CALENDAR_PAGE+1}"/>
							<c:if test="${NEW_CALENDAR_PAGE % 2==0}">
								<div class="col-6 mb-2" align="left" id="calendar_${NEW_CALENDAR_PAGE}">
							</c:if>
							<c:if test="${NEW_CALENDAR_PAGE % 2==1}">
								<div class="col-6 mb-2" align="right" id="calendar_${NEW_CALENDAR_PAGE}">
							</c:if>
							<table class="table table-bordered">
							  <thead>
							  	<tr><td colspan="7">${DATOS_CALENDARIO.NONBRE_MES}</td></tr>
							    <tr>
							      <th scope="col">Lunes</th>
							      <th scope="col">Martes</th>
							      <th scope="col">Miercoles</th>
							      <th scope="col">Jueves</th>
							      <th scope="col">Viernes</th>
							      <th scope="col">Sabado</th>
							      <th scope="col">Domingo</th>
							    </tr>
							  </thead>
							  <tbody>
						</c:if>
						<c:forEach items="1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31" var="dias">
							<c:if test="${(dias-DIA)<=0}">
								<c:if test="${(LAST_DAY-dias)<0 || NEW_CALENDAR_DAY=='false'}">
									<c:if test="${(dias==8-DATOS_CALENDARIO.DIA_SEMANA_INICIO || dias==15-DATOS_CALENDARIO.DIA_SEMANA_INICIO || dias==22-DATOS_CALENDARIO.DIA_SEMANA_INICIO || dias==29-DATOS_CALENDARIO.DIA_SEMANA_INICIO) && NEW_CALENDAR_DAY=='true'}">
										<tr>
									</c:if>
										<c:if test="${NEW_CALENDAR_DAY=='true'}">
											<c:if test="${dias=='1' && DATOS_CALENDARIO.DIA_SEMANA_INICIO!='0'}">
												<td colspan="${DATOS_CALENDARIO.DIA_SEMANA_INICIO}"></td>
											</c:if>
											<c:if test="${dias!='1'}">
												</td>
											</c:if>
											<c:if test="${dias<=DATOS_CALENDARIO.DIA_FIN}">
												<td>${dias}
												<c:set var="LAST_DAY" value="${dias}"/>
												<c:if  test="${dias== DIA}">
													<button class="calendario_dia" onclick="ViewModalCalendario(${rows.id},'${rows.imgTrazado}','${rows.circuitName}','${rows.locality}','${rows.country}',${rows.longitude},${rows.numVueltas},${rows.curvasRapidas},${rows.curvasMedias},${rows.curvasLentas},'${rows.fecha}')">${rows.circuitName}</button>
													
												</c:if>
											</c:if>
										</c:if>
										<c:if  test="${NEW_CALENDAR_DAY=='false'}">
											<button class="calendario_dia" onclick="ViewModalCalendario(${rows.id},'${rows.imgTrazado}','${rows.circuitName}','${rows.locality}','${rows.country}',${rows.longitude},${rows.numVueltas},${rows.curvasRapidas},${rows.curvasMedias},${rows.curvasLentas},'${rows.fecha}')">${rows.circuitName}</button>
													
											<c:set var="NEW_CALENDAR_DAY" value="true"/>
										</c:if>
									<c:if test="${dias==DATOS_CALENDARIO.DIA_FIN}">
										</td>
										<c:if test="${DATOS_CALENDARIO.DIA_SEMANA_FIN!='7'}">
											<td colspan="${6-DATOS_CALENDARIO.DIA_SEMANA_FIN}"></td>
										</c:if>
										<c:set var="LAST_DAY" value="0"/>
									</c:if>	
									<c:if test="${dias==7-DATOS_CALENDARIO.DIA_SEMANA_INICIO || dias==14-DATOS_CALENDARIO.DIA_SEMANA_INICIO || dias==21-DATOS_CALENDARIO.DIA_SEMANA_INICIO || dias==28-DATOS_CALENDARIO.DIA_SEMANA_INICIO}">
										</tr>
									</c:if>
								</c:if>
							</c:if>	
						</c:forEach>
					</c:forEach>
						
							<c:forEach items="1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31" var="dias">
								<c:if test="${(LAST_DAY-dias)<0 && dias<=DATOS_CALENDARIO.DIA_FIN}">
									<c:if test="${dias==8-DATOS_CALENDARIO.DIA_SEMANA_INICIO || dias==15-DATOS_CALENDARIO.DIA_SEMANA_INICIO || dias==22-DATOS_CALENDARIO.DIA_SEMANA_INICIO || dias==29-DATOS_CALENDARIO.DIA_SEMANA_INICIO}">
										<tr>
									</c:if>
									<c:if test="${dias<=DATOS_CALENDARIO.DIA_FIN}">
										<td>${dias}</td>
									</c:if>
									<c:if test="${dias==DATOS_CALENDARIO.DIA_FIN && DATOS_CALENDARIO.DIA_SEMANA_FIN!='7'}">
										<td colspan="${6-DATOS_CALENDARIO.DIA_SEMANA_FIN}"></td>
									</c:if>
								</c:if>
							</c:forEach>
								</tbody>
							</table>
							</div>						
					</div>
					
					</c:if>
					<c:if test="${p:WithOutFecha(circuitosPersist).size()>0}">
					<div class="col-12" style="margin: 15px;">
						<h3>Circuitos sin asignar fecha</h3>
						<table class="table table-bordered">
						  <thead>
						    <tr>
						      <th scope="col"></th>
						      <th scope="col">Nombre</th>
						      <th scope="col">Localidad</th>
						      <th scope="col">País</th>
						      <th scope="col">Longitud</th>
						      <th scope="col">Nº vueltas</th>
						      <th scope="col">Curvas rápidas</th>
						      <th scope="col">Curvas medias</th>
						      <th scope="col">Curvas lentas</th>
						      <th scope="col">Fecha</th>
						      <th scope="col">Opciones</th>
						    </tr>
						  </thead>
						  <tbody>
						  	<c:forEach items="${p:WithOutFecha(circuitosPersist) }" var="item">
							    <tr>
							      <td>
							      <c:if test="${item.circuitId != undefined}">
							      	<img src="<c:url value='/resources/imgCircuit/${item.circuitId}.png'/>" align="right" class="img-fluid"/>
							      </c:if>
							      <c:if test="${item.circuitId == undefined}">
							      	<c:if test="${item.imgTrazado != undefined && item.imgTrazado != ''}">
							      		<img src="<c:url value='/resources/imgCircuit/${item.imgTrazado}'/>" align="right" class="img-fluid"/>
							      	</c:if>
							      </c:if>
								  </td>
							      <td>
							      	<c:if test="${item.url != undefined}">
							      		<a href="${item.url}"><c:out value="${item.circuitName}"></c:out></a>
							      	</c:if>
							      	<c:if test="${item.url == undefined}">
							      		<c:out value="${item.circuitName}"></c:out>
							      	</c:if>
							      </td>
							      <td><c:out value="${item.locality}"></c:out></td>
							      <td><c:out value="${item.country}"></c:out></td>
							      <td><c:out value="${item.longitude}"></c:out></td>
							      <td><c:out value="${item.numVueltas}"></c:out></td>
							      <td><c:out value="${item.curvasRapidas}"></c:out></td>
							      <td><c:out value="${item.curvasMedias}"></c:out></td>
							      <td><c:out value="${item.curvasLentas}"></c:out></td>
							      <td><c:out value="${item.fecha}"></c:out></td>
							      <td>      
							      	<i class="fas fa-eye"
							          		onClick="location.replace('circuito/ver/${fn:replace(item.circuitName, ' ', '')}')"></i>					      
						      			<% if (session.getAttribute("usuario") != null) { 
							      		if (session.getAttribute("perfil") == "admin") { %>
							      		<i class="fas fa-edit xl" 
								      	onClick="EditarCircuito(${item.id},'${item.imgTrazado}','${item.circuitName}','${item.locality}','${item.country}',${item.longitude},${item.numVueltas},${item.curvasRapidas},${item.curvasMedias},${item.curvasLentas},'/resources/imgCircuit/${item.imgTrazado}');"></i>
										<i class="fas fa-calendar-alt xl" onClick="asignarFecha('${item.id}','${item.circuitName}');"></i>
								      	<c:if test="${item.fecha==undefined}">
								      		<i class="fas fa-trash-alt xl" 
								      		onClick="OpenConfirmModalTitle('${item.id}', 'circuito', null, null);"></i>
								      	</c:if>
								      	<%}} %>
							      </td>
							    </tr>
							</c:forEach>
						  </tbody>
						</table>
					</div>
					</c:if>
			</div>
			<div id="table_calendar_visual" class="row mt-3" style="display: none;">
					<c:if test="${p:sortByFecha(circuitosPersist).size()>0}">
					<div class="col-12" style="margin: 15px;">
						<h3>Circuitos con fecha asignada</h3>
						<table class="table table-bordered">
						  <thead>
						    <tr>
						      <th scope="col"></th>
						      <th scope="col">Nombre</th>
						      <th scope="col">Localidad</th>
						      <th scope="col">País</th>
						      <th scope="col">Longitud</th>
						      <th scope="col">Nº vueltas</th>
						      <th scope="col">Curvas rápidas</th>
						      <th scope="col">Curvas medias</th>
						      <th scope="col">Curvas lentas</th>
						      <th scope="col">Fecha</th>
						      <th scope="col">Opciones</th>
						    </tr>
						  </thead>
						  <tbody>
							<c:forEach items="${p:sortByFecha(circuitosPersist) }" var="item">
							    <tr>
							      <td>
							      <c:if test="${item.circuitId != undefined}">
							      	<img src="<c:url value='/resources/imgCircuit/${item.circuitId}.png'/>" align="right" class="img-fluid"/>
							      </c:if>
							      <c:if test="${item.circuitId == undefined}">
							      	<c:if test="${item.imgTrazado != undefined && item.imgTrazado != ''}">
							      		<img src="<c:url value='/resources/imgCircuit/${item.imgTrazado}'/>" align="right" class="img-fluid"/>
							      	</c:if>
							      </c:if>
								  </td>
							      <td>
							      	<c:if test="${item.url != undefined}">
							      		<a href="${item.url}"><c:out value="${item.circuitName}"></c:out></a>
							      	</c:if>
							      	<c:if test="${item.url == undefined}">
							      		<c:out value="${item.circuitName}"></c:out>
							      	</c:if>
							      </td>
							      <td><c:out value="${item.locality}"></c:out></td>
							      <td><c:out value="${item.country}"></c:out></td>
							      <td><c:out value="${item.longitude}"></c:out></td>
							      <td><c:out value="${item.numVueltas}"></c:out></td>
							      <td><c:out value="${item.curvasRapidas}"></c:out></td>
							      <td><c:out value="${item.curvasMedias}"></c:out></td>
							      <td><c:out value="${item.curvasLentas}"></c:out></td>
							      <td><c:out value="${item.fecha}"></c:out></td>
							      <td>
							      	<i class="fas fa-eye"
							          		onClick="location.replace('circuito/ver/${fn:replace(item.circuitName, ' ', '')}')"></i>					      
							      	<% if (session.getAttribute("usuario") != null) { 
							      		if (session.getAttribute("perfil") == "admin") { %>
							      		<i class="fas fa-edit xl" 
									      	onClick="EditarCircuito(${item.id},'${item.imgTrazado}','${item.circuitName}','${item.locality}','${item.country}',${item.longitude},${item.numVueltas},${item.curvasRapidas},${item.curvasMedias},${item.curvasLentas},'/resources/imgCircuit/${item.imgTrazado}');"></i>
										<i class="fas fa-calendar-alt xl" onClick="asignarFecha('${item.id}','${item.circuitName}');"></i>
									    <c:if test="${item.fecha==undefined}">
									      	<i class="fas fa-trash-alt xl" 
									      	onClick="OpenConfirmModalTitle('${item.id}', 'circuito', null, null);"></i>
									    </c:if>
								    <%}} %>
							      </td>
							    </tr>
						    </c:forEach>
						  </tbody>
						</table>
					</div>
					</c:if>
					<c:if test="${p:WithOutFecha(circuitosPersist).size()>0}">
					<div class="col-12" style="margin: 15px;">
						<h3>Circuitos sin asignar fecha</h3>
						<table class="table table-bordered">
						  <thead>
						    <tr>
						      <th scope="col"></th>
						      <th scope="col">Nombre</th>
						      <th scope="col">Localidad</th>
						      <th scope="col">País</th>
						      <th scope="col">Longitud</th>
						      <th scope="col">Nº vueltas</th>
						      <th scope="col">Curvas rápidas</th>
						      <th scope="col">Curvas medias</th>
						      <th scope="col">Curvas lentas</th>
						      <th scope="col">Fecha</th>
						      <th scope="col">Opciones</th>
						    </tr>
						  </thead>
						  <tbody>
						  	<c:forEach items="${p:WithOutFecha(circuitosPersist) }" var="item">
							    <tr>
							      <td>
							      <c:if test="${item.circuitId != undefined}">
							      	<img src="<c:url value='/resources/imgCircuit/${item.circuitId}.png'/>" align="right" class="img-fluid"/>
							      </c:if>
							      <c:if test="${item.circuitId == undefined}">
							      	<c:if test="${item.imgTrazado != undefined && item.imgTrazado != ''}">
							      		<img src="<c:url value='/resources/imgCircuit/${item.imgTrazado}'/>" align="right" class="img-fluid"/>
							      	</c:if>
							      </c:if>
								  </td>
							      <td>
							      	<c:if test="${item.url != undefined}">
							      		<a href="${item.url}"><c:out value="${item.circuitName}"></c:out></a>
							      	</c:if>
							      	<c:if test="${item.url == undefined}">
							      		<c:out value="${item.circuitName}"></c:out>
							      	</c:if>
							      </td>
							      <td><c:out value="${item.locality}"></c:out></td>
							      <td><c:out value="${item.country}"></c:out></td>
							      <td><c:out value="${item.longitude}"></c:out></td>
							      <td><c:out value="${item.numVueltas}"></c:out></td>
							      <td><c:out value="${item.curvasRapidas}"></c:out></td>
							      <td><c:out value="${item.curvasMedias}"></c:out></td>
							      <td><c:out value="${item.curvasLentas}"></c:out></td>
							      <td><c:out value="${item.fecha}"></c:out></td>
							      <td>      
							      	<i class="fas fa-eye"
							          		onClick="location.replace('circuito/ver/${fn:replace(item.circuitName, ' ', '')}')"></i>					      
						      			<% if (session.getAttribute("usuario") != null) { 
							      		if (session.getAttribute("perfil") == "admin") { %>
							      		<i class="fas fa-edit xl" 
								      	onClick="EditarCircuito(${item.id},'${item.imgTrazado}','${item.circuitName}','${item.locality}','${item.country}',${item.longitude},${item.numVueltas},${item.curvasRapidas},${item.curvasMedias},${item.curvasLentas},'/resources/imgCircuit/${item.imgTrazado}');"></i>
										<i class="fas fa-calendar-alt xl" onClick="asignarFecha('${item.id}','${item.circuitName}');"></i>
								      	<c:if test="${item.fecha==undefined}">	
								      		<i class="fas fa-trash-alt xl" 
								      		onClick="OpenConfirmModalTitle('${item.id}', 'circuito', null, null);"></i>
								      	</c:if>
								      	<%}} %>
							      </td>
							    </tr>
							</c:forEach>
						  </tbody>
						</table>
					</div>
					</c:if>
			</div>
		</section>
	</div>
	
	<% if (session.getAttribute("usuario") != null) { 
		if (session.getAttribute("perfil") == "admin") { %>	
	<!-- Modal -->
	<div class="modal fade bd-example-modal-lg" id="insertModal" 
		tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" 
		aria-hidden="true">
		<div class="modal-dialog modal-lg" role="document">
	    	<div class="modal-content">
				<div class="modal-header">
					<div class="row">
						<div class="col-12">
							<h5 class="modal-title" id="titleModal">Elige una opción</h5>
						</div>
					</div> 
					<button type="button" class="close" data-dismiss="modal" id="closeModal" aria-label="Close" onClick="resetModalCircuit();">
					<span aria-hidden="true">&times;</span>
					</button>
				</div>
	    		<form action="/FormulaProject/circuito/insertarCircuito" id="form_circuitos_crear_edit" method="POST" enctype="multipart/form-data">
		      		<div class="modal-body">
		      			<div id="alertStep" style="display: none;">
			      			<div id="alertStep_text" class="alert alert-danger" role="alert">
							</div>	      			
		      				<button type="button" 
		      						class="btn btn-primary" 
		      						onClick="resetModalCircuit();" >Cerrar</button>
		      			</div>
		      			<div id="spinner" style="display: none;">
		      				<div class="text-center">
		      					<div class="spinner-border" role="status">
		      						<span class="sr-only">Cargando...</span>
	      						</div>
      						</div>
		      			</div>
						<div id="zeroStep">
							<div class="row justify-content-md-center me-5 ms-5">
								<div class="col-7 p-4 m-3 buttonDiv" onclick="selCircuitCustom();">
									<h1 class="display-6">Circuito personalizado</h1>
									<p>Crea  tu circuito desde cero</p>
									<img src="<c:url value='/resources/img/setupCircuito1.png'/>" align="center" style="height:100px; "/>
								</div>
								<div class="col-7 p-4 m-3 buttonDiv" onclick="setErgarstApi();">
									<h1 class="display-6">Ergarst API</h1>
									<p>Generar circuito desde el repositorio Ergast Formula One</p>
									<img src="<c:url value='/resources/img/cloudErgarst1.png'/>" align="center" class="center-block"  style="height:100px;"/>
								</div>
							</div>
						</div>
			      		<div id="firstStep" style="height:340px;overflow: auto;margin-bottom:20px;display:none;">
							<table class="table table-bordered">
								<thead>
									<tr>
										<th scope="col">Nombre</th>
										<th scope="col">Localidad</th>
										<th scope="col">País</th>
									</tr>
								</thead>
								<tbody id="ergast_table">
								</tbody>
							</table>				  
					   	</div>
						<div id="secondStep" style="height:500px;overflow-y: scroll;display:none;">
							<div class="row" style="width:99%; padding: 10px; margin-left: 2px;">
								<div id="formInitCircuito">
									<h5>Datos básicos</h5>
									<div class="row">
										<div class="col-12">
											<div class="form-group">
												<label for="id">Id</label>
												<input type="text" class="form-control" id="id_edit" name="id_edit" disabled>
											</div>
										</div>
										<div class="col-12">
											<div class="form-group">
											<label for="nombre">Nombre del circuito</label>
											<input type="text" class="form-control" id="nombreCircuito" name="nombreCircuito" placeholder="">
											</div>
										</div>
										<div class="col-6">
											<div class="form-group">
											<label for="ciudadLabel">Ciudad</label>
											<input type="text" class="form-control" id="ciudadCircuito" name="ciudadCircuito" placeholder="">
											</div>
										</div>
										<div class="col-6">
											<div class="form-group">
											<label for="paisLabel">País</label>
											<input type="text" class="form-control" id="paisCircuito" name="paisCircuito" placeholder="">
											</div>
										</div>
									</div>
								</div>
								<h5 class="mt-3">Datos del circuito</h5>
								<div class="col-3">
									<div class="form-group">
									<label for="numVueltas">Nº Vueltas</label>
									<input type="number" class="form-control" id="numVueltas" name="numVueltas" placeholder="">
									</div>
								</div>
								<div class="col-6">
									<div class="form-group">
									<label for="longitud">Longitud (metros)</label>
									<input type="number" class="form-control" id="longitud" name="longitud" placeholder="">
									</div>
								</div>
								<div class="col-4">
									<label for="curvasLentasInput">Curvas lentas</label>
								</div>
								<div class="col-4">
									<label for="curvasMediaInput">Curvas medias</label>
								</div>
								<div class="col-4">
									<label for="curvasRapidasInput">Curvas rápidas</label>
								</div>
								<div class="col-4">
									<div class="form-group">
									<input type="number" class="form-control" id="curvasLentasInput" name="curvasLentas">
									</div>
								</div>
								<div class="col-4">
									<div class="form-group">
									<input type="number" class="form-control" id="curvasMediaInput" name="curvasMedias">
									</div>
								</div>
								<div class="col-4">
									<div class="form-group">
									<input type="number" class="form-control" id="curvasRapidasInput" name="curvasRapidas">
									</div>
								</div>
								<br></br>
								<div class="col-12"><label id="ref_adjuntar" for="curvasRapidasInput">Imagen de la trazada</label></div>
								<div class="col-12 mt-4">
									<div class="form-group form-image">
										<div class="input-group mb-3">
										  	<div id="inputFile_div" class="col-6 custom-file">
										  		<input type="file" class="custom-file-input" id="inputGroupFile01" name="file">
										  	</div>
										  	<div class="col-6">
								  				<img id="imagenCargar" class="img-group-text"/>
									  		</div>
								 		</div>
									</div>
								</div>
								
							</div>
						</div>	
					</div>				  
		  	      	<div id="modalFooter" class="modal-footer ">
		  	      		<button type="button" class="btn btn-primary" data-dismiss="modal" id="buttonNext" style="width:100%">Siguiente</button>
		  	      		<input type="submit" class="btn btn-primary" id="saveBtn" value="Guardar" style="width:100%;display: none;"/>
		  	      	</div>
				</form>
			</div>
		</div>
	</div>
	<!-- Modal Fecha-->
	<div class="modal fade" id="DateModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document" id="DateModalDialog">
	    <div class="modal-content">
	    	<div id="content-confirm-fecha">
		      <div class="modal-header">
		        <h5 class="modal-title" id="DateModalTitle">Asignar fecha</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick="CloseDateModalTitle();">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      	<form action="" method="POST" id="AcctionModalDate">
		      	  
				  <div class="modal-body">
				  	<div class="form-group">
					  <label for="tituloInput">Fecha</label>
					  <input type="date" class="form-control" id="fechaInput" name="fecha" placeholder="Introduce el fecha">
					</div>
				  </div>
					<div class="modal-footer">
						<input type="button" class="btn btn-secondary" data-dismiss="modal" value="Cancelar" onClick="CloseDateModalTitle();"/>
						<input type="submit" class="btn btn-primary" id="asignarSubmit" value="Asignar" />
					</div>
				</form>
	    	</div>
	    	<div id="content-fullScreen-image" style="display: none;">
				<img id="content-img-dyn-fullscreen" align="right" class="card-img-top" src=""/>
	    	</div>
	    </div>
	  </div>
	</div>
	
	<script>
		document.getElementById('inputGroupFile01').addEventListener('change', readFileAsString)
		function readFileAsString() {
		    var files = this.files;
		    if (files.length === 0) {
		        document.getElementById('imagenCargar').src="";
		        return;
		    }
		    var reader = new FileReader();
		    reader.onload = function(event) {
		        console.log('File content:', event.target.result);
		    };
		    reader.readAsDataURL(files[0]);
		    
		    reader.onload = function(){
		    	document.getElementById('imagenCargar').src=reader.result;
	        }
		}
		changeViewMode('grip');
	</script>
	<c:remove var="perfil" scope="session" />
	<%}}else{ %>
	<script>
		changeViewMode('calendar');
	</script>
	<%} %>
	<!-- Modal Calendario-->
	<div class="modal fade" id="CalendarModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog" role="document" id="CalendarModalDialog">
	  	<div class="card cardCircuitos p-3" >
		    <div class="modal-content">
		    	<div id="content-confirm-fecha">
			      <div class="modal-header">
			        <h5 class="card-title" id="nombre_card_modal">VACIO</h5>
			        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick="CloseCalendarModalTitle();">
			          <span aria-hidden="true">&times;</span>
			        </button>
			      </div>
		      		<img id="imagen_card_modal" src="" 
					align="right" 
					class="card-img-top" style="padding:10px;"
					alt="Click para ampliar la imagen"/>
			      	
				  <div class="card-body">
				    
				    <p class="card-text" id="lugar_card_modal">VACIO - VACIO</p>
				  </div>
				  <ul class="list-group list-group-flush" style="background-color: black">
				    <li class="list-group-item"><b>Num de vueltas: </b><a id="vueltas_card_modal">VACIO</a></li>
				    <li class="list-group-item"><b>Longitud: </b><a id="longitud_card_modal">VACIO</a></li>
				    <li class="list-group-item"><b>Curvas rápidas: </b><a id="Crapida_card_modal">VACIO</a></li>
				    <li class="list-group-item"><b>Curvas medias: </b><a id="Cmedia_card_modal">VACIO</a></li>
				    <li class="list-group-item"><b>Curvas lentas: </b><a id="Clenta_card_modal">VACIO</a></li>
				  </ul>
				  <div class="card-body">
				    <p class="card-text">Fecha asignada: <a id="fecha_card_modal">VACIO</a></p>
				  </div>
				  <div class="card-body" align="center">
				  	<i class="fas fa-eye xl" id="iconModalView"
				          		onClick="location.replace('circuito/ver/VACIO')"></i>	
				  	<% if (session.getAttribute("usuario") != null) { 
					      		if (session.getAttribute("perfil") == "admin") { %>
					  	<i class="fas fa-edit xl" id="iconModalEdit"
						      	></i>
						<i class="fas fa-calendar-alt xl" id="iconModalCalendar"></i>
					 <%}} %>
				  </div>
				</div>
	    	</div>
	    	<div id="content-fullScreen-image" style="display: none;">
				<img id="content-img-dyn-fullscreen" align="right" class="card-img-top" src=""/>
	    	</div>
	    </div>
	  </div>
	</div>
	<script type="text/javascript">
	function ViewModalCalendario(id,imagen,nombre,localidad,pais,longitud,vueltas,curvaRapidas,curvasMedias,curvasLentas,fecha){
		$('#CalendarModal').modal('show');
		$("#nombre_card_modal").text(nombre);
		$("#lugar_card_modal").text(localidad+" - "+pais);
		$("#vueltas_card_modal").text(vueltas);
		$("#longitud_card_modal").text(longitud);
		$("#Crapida_card_modal").text(curvaRapidas);
		$("#Cmedia_card_modal").text(curvasMedias);
		$("#Clenta_card_modal").text(curvasLentas);
		$("#fecha_card_modal").text(fecha);
		//$("#imagen_card_modal").attr("onclick","showImage('${baseURL}', '/resources/imgCircuit/"+imagen+"');");
		if(imagen!=""){
			$("#imagen_card_modal").show();
			$("#imagen_card_modal").attr("src",'${baseURL}/resources/imgCircuit/'+imagen);
		}
		else{
			$("#imagen_card_modal").hide();
		}
		$("#imagen_card_modal").attr("src",'${baseURL}/resources/imgCircuit/'+imagen);
		$("#iconModalView").attr("onclick","location.replace('circuito/ver/"+nombre.replaceAll(" ","")+"');");
		$("#iconModalEdit").attr("onclick","CloseCalendarModalTitle();EditarCircuito("+id+",'"+imagen+"','"+nombre+"','"+localidad+"','"+pais+"',"+longitud+","+vueltas+","+curvaRapidas+","+curvasMedias+","+curvasLentas+",'/resources/imgCircuit/"+imagen+"');");
		$("#iconModalCalendar").attr("onclick","CloseCalendarModalTitle();asignarFecha('"+id+"','"+nombre+"')");
	};
	
	function CloseCalendarModalTitle(){
		$('#CalendarModal').modal('hide');
		$("#nombre_card_modal").text("");
		$("#lugar_card_modal").text("");
		$("#vueltas_card_modal").text("");
		$("#longitud_card_modal").text("");
		$("#Crapida_card_modal").text("");
		$("#Cmedia_card_modal").text("");
		$("#Clenta_card_modal").text("");
		$("#fecha_card_modal").text("");
		//$("#imagen_card_modal").attr("src","");
	};
	</script>
</body>
</html>
