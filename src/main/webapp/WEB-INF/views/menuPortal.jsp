<section>
<%@ page import="java.util.Objects"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />

<ul class="iconBar">
	<a href="${baseURL}/portalResponsable/${equipoId}">
		<li class="activeIconBar cursorPointer">
			<i class="fa fa-home"></i>
			<span>Inicio</span>
		</li>
	</a>
	<a href="${baseURL}/portalResponsable/${equipoId}/tuEquipo">
		<li class="cursorPointer">
		  	<i class="fas fa-address-card"></i><br/>
		  	<span>Tú equipo</span>	
	  	</li>
	</a>
	<a href="${baseURL}/portalResponsable/${equipoId}/gestorMiembros">
		<li class="cursorPointer">
		  	<i class="fas fa-users-cog"></i><br/>
		  	<span>Miembros</span>	
	  	</li>
	</a>
	<a href="${baseURL}/portalResponsable/${equipoId}/gestorPilotos">
	  	<li class="cursorPointer">
		  	<i class="fas fa-user-astronaut"></i><br/>
	  		<span>Pilotos</span>	
		</li>
  	</a>
	<a href="${baseURL}/portalResponsable/${equipoId}/gestorCoches">
		<li class="cursorPointer">
		  	<i class="fas fa-car"></i><br/>
		  	<span>Coches</span>	
	  	</li> 
	</a>
	<a href="${baseURL}/portalResponsable/${equipoId}/gestorHerramientas">
	  	<li class="cursorPointer">
		  	<i class="fas fa-tools"></i><br/>
		  	<span>Herramientas</span>	
		</li>
  	</a>
  	<% if (session.getAttribute("rolUser").equals("admin")) { %>
	  	<a href="${baseURL}/gestorEquipos">
		  	<li class="cursorPointer returnAdminBtn">
			  	<i class="fas fa-tools"></i><br/>
			  	<span>Volver portal admin</span>	
			</li>
	  	</a>
	 <% } %>
</ul>
</section>