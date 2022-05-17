<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<jsp:include flush="true" page="../header.jsp"/>
</head>
<body>

<!-- Modal Confirmacion-->
<div class="modal fade" id="ConfirmModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document" id="ConfirmModalDialog">
    <div class="modal-content">
    	<div id="content-confirm-borrado">
	      <div class="modal-header">
	        <h5 class="modal-title" id="ConfirmModalTitle">Confirmas borrar el registro?</h5>
	        <button type="button" class="close" data-dismiss="modal" aria-label="Close" onClick="CloseConfirmModalTitle();">
	          <span aria-hidden="true">&times;</span>
	        </button>
	      </div>
	      	<form action="" method="" id="AcctionModalConfirm">
				  <div class="modal-body">
					<div class="alert alert-danger" role="alert" id="modalTemplate_alert" style="display: none;"></div>				  
					<input type="checkbox" id="reload_page" style="display:none;">
					<p id="modal_text" style="display:none;"></p>
					<input type="button" class="btn btn-secondary" data-dismiss="modal" value="Cancelar" onClick="CloseConfirmModalTitle();"/>
					<input type="button" onClick="BorrarSubmit();" class="btn btn-primary" id="borrarSubmit" value="Borrar" />
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
</html>