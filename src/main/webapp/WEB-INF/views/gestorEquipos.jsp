<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />

<html>
	<jsp:include flush="true" page="./header.jsp"/>
	<jsp:include flush="true" page="./helpers/ModalTemplate.jsp"/>
	<jsp:include flush="true" page="./helpers/ModalSearch.jsp"/>	
	<script src="${baseURL}/resources/js/script_admin.js" ></script>
	
<body>
	<jsp:include flush="true" page="./jumbotron.jsp"/>
	<div class="container mt-3">
		<jsp:include flush="true" page="./menu.jsp"/>
		<section class="sectionStyle">
			<div class="row mb-4">
				<div class="col-6">
					<h3>Gestor de equipos</h3>
				</div>
			</div>
			<c:if test="${ lstEquipos.size() == 0 }">
				<div class="alert alert-info" role="alert">No hay equipos creadas.</div>
			</c:if>
			<div id="grid_mode" class="row mt-3">
				<c:forEach items="${ lstEquipos }" var="equipo">
					<div class="col-4 mb-2" style="width: 300px;">
						<div class="card cardCircuitos p-3" >
							<c:if test="${equipo.equipo.logo != undefined && equipo.equipo.logo != ''}">
					      		<img src="<c:url value='/resources/imgEquipos/${equipo.equipo.logo}'/>" 
								align="right" 
								class="card-img-top cursorPointer"
								onClick="showImage('${baseURL}', '/resources/imgEquipos/${equipo.equipo.logo}');"
								alt="Click para ampliar la imagen"/>
					      	</c:if>
						  <div class="card-body">
						    <h5 class="card-title">${equipo.equipo.nombre}</h5>
						    <p><b>Twitter: </b>${ equipo.equipo.twitter }</p>
						    <p><b>Usuario creador: </b>${ equipo.user.usuario }</p>
						  </div>
						  <div class="card-body" align="center">
							<i onClick="OpenSearchModal('${equipo.equipo.id}', '1');" class="fas fa-exchange-alt xl" /></i>
						  	<a href="${baseURL}/portalResponsable/${equipo.equipo.id}">
						  	<i class="fas fa-sign-in-alt xl" /></i></a>
						  </div>
						</div>
					</div>
				</c:forEach>
			</div>
		</section>
	</div>
</body>
</html>