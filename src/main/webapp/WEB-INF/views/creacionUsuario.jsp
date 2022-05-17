<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<jsp:include flush="true" page="./header.jsp"/>
<body>
	<jsp:include flush="true" page="jumbotron.jsp"/>
	<div class="container mt-3">
        <jsp:include flush="true" page="menu.jsp"/>
        
		<article class="sectionStyle">
			<div class="container" align="center">
				<h3 align="center" style="color:red">${errorRegistro}</h3>
				<div class="row justify-content-center">
					<div class="col-6">
						<form action="insertarUsuario" method="POST" >
							<div class="form-group">
						    	<label for="inputUsuario">Usuario</label>				
								<input type="text" 
									class="form-control" 
									id="inputUsuario"  
									aria-describedby="emailHelp" 
									placeholder="Nombre de usuario" 
									name="usuario">
							</div>
							<div class="form-group">
						    	<label for="inputNombre">Nombre</label>				
								<input type="text" 
									class="form-control" 
									id="inputNombre"  
									aria-describedby="nombreHelp" 
									placeholder="Nombre" 
									name="nombre">
							</div>
							<div class="form-group">
							  <label for="exampleInputEmail1">Email address</label>
							  <input type="email" 
							  		class="form-control" 
							  		id="exampleInputEmail1" 
							  		name="email"
							  		aria-describedby="emailHelp" 
							  		placeholder="Introduce tu email">
							  <small id="emailHelp" class="form-text text-muted">No compartiremos nunca tu email</small>
							</div>
							<div class="form-group">
							  <label for="exampleInputPassword1">Password</label>
							  <input type="password" 
							 		   class="form-control" 
									   id="exampleInputPassword1" 
									   name="password"
									   placeholder="Password">
							</div>
							<div class="form-group">
							    <label for="exampleFormControlSelect1">Elige un rol</label>
							    <select class="form-control" name="rol" id="exampleFormControlSelect1">
								  <option value="admin">Admin</option>
								  <option value="responsable">Responsable de equipo</option>
							    </select>
							  </div>
						    <br></br>
							<p><input class="btn btn-primary" type="submit" value="Registrarse"></p>
							<div align="center">
								<p><a href="/FormulaProject">Ya tengo usuario, acceder</a></p>
							</div>
						</form>
					</div>
				</div>	
			</div>
		</article>
	</div>

</body>
</html>