<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<jsp:include flush="true" page="../header.jsp"/>
</head>
<body>

<!-- Modal Confirmacion-->
<div class="modal fade" id="SearchModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document" id="SearchUserCreatorModal">
    <div class="modal-content">
    	<div id="content-confirm-borrado">
	      <div class="modal-header">
	        <h5 class="modal-title" id="ConfirmModalTitle">Reasignar usuario creador</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick="CloseSearchModal();">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
			<form action="" id="SearchUserModal" method="POST">
				<div class="modal-body">
					<div class="alert alert-danger" role="alert" id="modalTemplate_alert" style="display: none;"></div>
					<div class="row">
						<div class="col-11">
					  		<div class="form-group">
					  			<input type="text" name="userId" id="userId" style="display: none;" />
								<input class="form-control form-control-sm" 
									type="text" 
									name="user_select"
									id="user_select"
									placeholder="busca un usuario ...">
								<div id="table-searched" style="display: none; position: absolute; z-index: 2;">
									<div class="list-group" id="table-searched-group-modal">
									</div>					
								</div>
							</div>
						</div>
						<div class="col-1">
							<i id="cancelUser_btn" onclick="cancelUser();" class="fas fa-window-close" style="display:none;"></i>
						</div>
					</div> 
				</div>
				<div class="modal-footer">
					<input type="button" class="btn btn-secondary" data-dismiss="modal" value="Cancelar" onClick="CloseSearchModal();"/>
					<input type="submit" class="btn btn-primary" id="reasignarSubmit" value="Reasignar" />
				</div>
			</form>
    	</div>
    	<div id="content-fullScreen-image" style="display: none;">
			<img id="content-img-dyn-fullscreen" align="right" class="card-img-top" src=""/>
    	</div>
    </div>
  </div>
</div>

</body>
<script>
$( document ).ready(function() {
	$('#user_select').on('keyup', function() {	
		console.log('here');
		$('#table-searched').show();
		var value = $(this).val();
		var patt = new RegExp(value, "i");
		$('#table-searched-group-modal').find('li').each(function() {
		    var $table = $('#table-searched-group-modal');
		    if (($(this).text().search(patt) >= 0)) {
		      $(this).show();
		    } else {
		    	$(this).hide();
		    }
		});
	});
});

function OpenSearchModal(id, type) {
	$('#SearchModal').modal('show');
	$('#SearchUserModal').attr("action", "/FormulaProject/portalResponsable/"+id+"/gestorEquipos/reasignCreador/"+type);
	$('#SearchUserModal').attr("method", "POST");
	$('#content-fullScreen-image').hide();
	$('#content-confirm-borrado').show();
	invokeAjax('GET', '/FormulaProject/usuarios/getUsersMemberTeam/'+id).then(result => {
		console.log(result);
		if (result.codeService == '200') {
			var res = JSON.parse(result.bodyResponse);
			var equipoId = $('#equipo-id-hidden').val();
			res.forEach(function( index ) {
				$('#table-searched-group-modal').append('<li onClick="setNewUser(\''+equipoId+'\',\''+index.id+'\',\''+index.usuario+'\');" class="list-group-item">'+index.usuario+'/'+index.email+'</li>');
			});								
		}
	}).catch((thrownError) => {
		console.log(thrownError);
	});
}
function setNewUser(idEquipo, idUser, usuario) {
	console.log("crearMiembro" + usuario);
	$('#user_select').val(usuario);
	$('#table-searched').hide();
	$('#cancelUser_btn').show();
	$('#userId').val(idUser);
	$('#user_select').prop("disabled", true);
}

function CloseSearchModal() {
	$("#ConfirmModalTitle").text("");
	$('#AcctionModalConfirm').attr("action", "");
	$('#SearchModal').modal('hide');
	$('#ConfirmModalDialog').removeClass('modal-lg');
	$('#table-searched-group-modal').empty();
}

function cancelUser() {
	$('#cancelUser_btn').hide();
	$('#user_select').attr('disabled', false);
	$('#user_select').val('');
}
</script>
</html>