<%@ page language="java" contentType="text/html; charset=UTF-8" %>

<!-- Modal Editar usuario-->
<div class="modal fade" id="EditUserModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document" id="EditUserModalDialog">
    <div class="modal-content">
    	<div>
	      <div class="modal-header">
	        <h5 class="modal-title" id="EditUserModalTitle">Cambiar datos usuario</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick="CloseEditUserTitle();">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      	<form action="" method="POST" id="AcctionModalEditUser">
			  <div class="modal-body">
			  	<input type="text" id="redirectHidden" name="redirect" value="" hidden="true"/>
			  	<input type="text" id="userIdHidden" name="id" value="" hidden="true"/>
			  	<div class="form-group">
				  <label for="nombreInput">Nombre</label>
				  <input type="text" class="form-control" id="nombreInput" name="nombre" placeholder="Introduce el nombre">
				</div>
			  	<div class="form-group">
				  <label for="emailInput">Correo electronico</label>
				  <input type="email" class="form-control" id="emailInput" name="email" placeholder="Introduce correo electronico">
				</div>
				<div class="form-group">
				  <label for="exampleInputPassword1">Password</label>
				  <input type="password" 
				 		   class="form-control" 
						   id="exampleInputPassword1" 
						   name="password"
						   placeholder="Introduce un valor para modificar el Password">
				</div>
				<div class="modal-footer">
					<input type="button" class="btn btn-secondary" data-dismiss="modal" value="Cancelar" onClick="CloseEditUserTitle();"/>
					<input type="submit" class="btn btn-primary" id="EditarUserSubmit" value="Guardar" />
			  	</div>
			  </div>
			</form>
    	</div>
    	<div id="content-fullScreen-image" style="display: none;">
			<img id="content-img-dyn-fullscreen" align="right" class="card-img-top" src=""/>
    	</div>
    </div>
  </div>
</div>
<script type="text/javascript">
		function showEditUser(id,nombre,email) {
			$('#EditUserModal').modal('show');
			$('#userIdHidden').attr('value', id);
			$('#nombreInput').attr('value', nombre);
			$('#emailInput').attr('value', email);
			var id = $('#userIdHidden').val();
			var endUri = '/FormulaProject/usuarios/editarUser?id='+id;

			$('#redirectHidden').attr('value', window.location.pathname.split("FormulaProject")[1]);
			$('#AcctionModalEditUser').attr('action', endUri);
		}
		function CloseEditUserTitle(){
			$('#EditUserModal').modal('hide');
			$('#userIdHidden').attr('value', "");
			$('#userIdHidden').attr('value', "");
			$('#nombreInput').attr('value', "");
			$('#emailInput').attr('value', "");
			$('#AcctionModalEditUser').attr('action', "");
		}
</script>
