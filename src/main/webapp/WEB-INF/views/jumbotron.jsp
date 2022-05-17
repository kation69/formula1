<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
<div id="headerJumbo" style="padding: 0px 80px 0px 80px;">
	<article>
		<div class="row vertical-center">
			<div class="col-8">
				<div class="row">
				<div class="col-1"><img src="<c:url value='/resources/img/logo.jpeg'/>" style="width:50px;"/></div>				
				<div class="col-11 pl-4"><a href="/FormulaProject" style="text-decoration: none;color:black;"><h1 class="display-4">Formula Project</h1></a></div>
				</div>
			</div>
			<div class="col-4" align="right">	
				<% if (session.getAttribute("usuario") == null) {
					if (request.getRequestURI().endsWith("/index.jsp") | request.getRequestURI().contains("/views/ver_") | request.getRequestURI().contains("/views/gestor")){  %>
				<a class="btn btn-primary" data-toggle="collapse" id="btnIniciarSesion">
				    Iniciar sesión</a>
				    <%session.setAttribute("perfil", "");%>
				    
			    <% }} else { %>
			    
			    <i class="fas fa-user xs mx-2" onClick="showEditUser('${usuario.id}','${usuario.nombre}','${usuario.email}');"></i>
			      <span class="navbar-text">
					Hola, ${ usuario.usuario } 
					<a href="${baseURL}/cerrarSesion" >Cerrar sesion</a>	
			      </span>
			      
				<% }  %>
			</div>
		</div>
	</article>
</div>
<%@ include file="helpers/ModalEditarUsuario.jsp" %>
<!-- Login Modal -->
<div class="modal fade" id="loginModal" tabindex="-1" role="dialog" aria-labelledby="loginModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="loginModalLabel">Inicia sesión</h5>
        <button type="button" class="close" data-dismiss="modal" id="loginCloseModal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
			<div class="row" align="center">
				<div class="col-12">					
					<form class="align-items-center pt-1 g-3 px-3" action="${baseURL }/usuarios/loginUser" method="POST">
					  <div class="form-group">
						<label for="usuarioLabel" class="form-label">Usuario</label>
						<input type="text" class="form-control" id="usuario" name="usuario" alt="Usuario">
					  </div>
					  <div class="form-group pt-1">
						<label for="passLabel" class="form-label">Contraseña</label>
						<input class="form-control" type="password" name="password">
					  </div>
					  <div class="form-group py-4">
						<input type="submit" class="btn btn-primary" value="Acceder">
					  </div>
					</form>
				</div>
				<div class="col-12">
					<div align="center">
						<p><a href="usuarios/altaUsuario">¿Eres un nuevo usuario? Crea tú cuenta</a></p>
					</div>
				</div>
			</div>
		</div>
	  </div>
	  <div class="modal-footer">
	  </div>
  </div>
</div>