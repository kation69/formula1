<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />

<html>
	<head>
		<jsp:include flush="true" page="header.jsp"/>
		<jsp:include flush="true" page="./helpers/ModalTemplate.jsp"/>
		<script src="${baseURL}/resources/js/script_responsable.js" ></script>
	</head>
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
					<div class="col-6">
						<h1>Gestión miembros</h1>
						<h1>${editPermission}</h1>
					</div>
					<div id="searchUserMember" class="col-4" style="display: none;">
						<input class="form-control form-control-sm" 
								type="text" 
								id="search-member-input"
								placeholder="busca un usuario ...">
						<div id="table-searched" style="display: none; position: absolute; z-index: 2;">
							<div class="list-group" id="table-searched-group">
							</div>					
						</div>
					</div>
					<input type="text" id="equipo-id-hidden" value="${equipoId}" style="display:none;" />
					<div class="col-2" align="right">
						<c:if test="${editPermissions}">
							<button type="button" 
									class="btn btn-primary" 
									data-toggle="modal" 
									data-target="#exampleModal" 
									id="addNewMember">Añadir nuevo miembro</button>
						</c:if>
					</div>
					<div class="col-12" style="margin: 15px;">
						<table class="table table-bordered">
						  <thead>
						    <tr>
						      <th scope="col">#</th>
						      <th scope="col">Usuario</th>
						      <th scope="col">Nombre</th>
						      <th scope="col">Email</th>
						    </tr>
						  </thead>
						  <tbody>	  
							<c:forEach items="${ lstMiembros }" var="miembro">
							    <tr id="table-tr-member">
							      <th scope="row"><c:out value="${miembro.id}"></c:out></th>
							      <td><c:out value="${miembro.usuario}"></c:out></td>
							      <td><c:out value="${miembro.nombre}"></c:out></td>
							      <td><c:out value="${miembro.email}"></c:out></td>
							      <td>
		       					    <c:if test="${userid != miembro.userId && miembro.permRemove}">
										<i class="fas fa-trash-alt xl ${miembro.id }-btnRemove-table"
						      				onClick="OpenConfirmModalTitle('${miembro.id}', 'portalResponsable/gestorMiembros', null, null);"></i>
							      	</c:if>
							      </td>
							    </tr>
							</c:forEach>
							<% session.removeAttribute("userid");%>
						  </tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>