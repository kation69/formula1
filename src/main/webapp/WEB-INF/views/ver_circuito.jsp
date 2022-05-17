<!DOCTYPE html>
<%@ page language="java" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<jsp:include flush="true" page="header.jsp"/>
<body>
	<jsp:include flush="true" page="./jumbotron.jsp"/>
	<div class="container mt-3">
		<article>
			<jsp:include flush="true" page="./menu.jsp"/>
		</article>
		<article>
		
		<div class="row">
			<div class="col-12">
				<div class="card cardNoticias">
					<div class="card-header">
						<h3 class="card-title" style="display:inline;">${ nombre }</h3>
						<button type="button" class="btn btn-primary volver" id="volver" style="float:right;">Volver</button>
					</div>
					
					<div class="card-body">
						<div class="row">
							<div class="col-lg-6 col-sm-12 col-md-12">
							    <% if (session.getAttribute("imagen") != null) { %>
							    	<% if (session.getAttribute("imagen") != "") { %>
								<img src="<c:url value='/resources/imgCircuit/${ imagen }'/>" 
												 align="right" 
												 class="card-img-top"/>
							    	<% } %>
							    <% } %>
							</div>
							<div class="col-lg-6 col-sm-12 col-md-12">
								<h5 class="card-title">${ country } - ${ locality }</h5>
								<ul class="list-group list-group-flush" style="background-color: black">
								    <li class="list-group-item"><b>Num de vueltas: </b><c:out value="${numVueltas}"></c:out></li>
								    <li class="list-group-item"><b>Longitud: </b><c:out value="${longitude}"></c:out></li>
								    <li class="list-group-item"><b>Curvas rápidas: </b><c:out value="${curvasRapidas}"></c:out></li>
								    <li class="list-group-item"><b>Curvas medias: </b><c:out value="${curvasMedias}"></c:out></li>
								    <li class="list-group-item"><b>Curvas lentas: </b><c:out value="${curvasLentas}"></c:out></li>
								  </ul>
								  <h4 style="margin-top:3%;">Fecha asignacion: ${fecha}</h4>
							</div>
						</div>
		  			</div>
				</div>
			</div>
		</div>
		</article>
	</div>
</body>
</html>