<section style="padding-bottom: 15px;">

<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
	<% if (session.getAttribute("usuario") != null) { %>
		<nav class="navbar navbar-expand-lg navbar-light navBarMain">
		  <div class="container-fluid">
		   	<a class="navbar-brand">Gestion</a>
		    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
		      <span class="navbar-toggler-icon"></span>
		    </button>
		    <div class="collapse navbar-collapse" id="navbarText">
		      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item">
					<a class="nav-link active" aria-current="page" href="/FormulaProject">Home</a>
		        </li>
		        <li class="nav-item">
					<a class="nav-link" href="${baseURL}/gestorNoticias">Noticias</a>		          
		        </li>
		        <li class="nav-item">
					<a class="nav-link" href="${baseURL}/gestorVotaciones">Votaciones</a>
		        </li>
		        <li class="nav-item">
					<a class="nav-link" href="${baseURL}/gestorUsuarios">Usuarios</a>
		        </li>
		        <li class="nav-item">
					<a class="nav-link" href="${baseURL}/gestorEquipos">Equipos</a>
		        </li>
		        <li class="nav-item">
					<a class="nav-link" href="${baseURL}/gestorCircuitos">Circuitos</a>
		        </li>
		      </ul>
		    </div>
		  </div>
		</nav>
	<% } else { %>
		<nav class="navbar navbar-expand-lg navbar-light navBarMain">
		  <div class="container-fluid">
		   	<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarText" aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
		      <span class="navbar-toggler-icon"></span>
		    </button>
		    <div class="collapse navbar-collapse" id="navbarText">
		      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item">
					<a class="nav-link active" aria-current="page" href="/FormulaProject">Home</a>
		        </li>
		        <li class="nav-item">
					<a class="nav-link" href="${baseURL}/calendario">Calendario</a>		          
		        </li>
			    
		        <li class="nav-item">
					<a class="nav-link" href="${baseURL}/listaVotaciones">Votaciones</a>
		        </li>
		      </ul>
		      
		    </div>
		  </div>
		</nav>
	<% } %>	
	<% if (session.getAttribute("msgAlert") != null) { %>
	    <div id="alertCustom" 
	    	class="mt-2 alert ${ typeAlert }"
	    	role="alert">
	    	${ msgAlert }
	    </div>
    <% session.removeAttribute("msgAlert"); session.removeAttribute("typeAlert"); } %>
    <div id="alertCustom-ajax" 
	    	class="mt-2 alert"
	    	role="alert" style="display: none;">
	    </div>
</section>