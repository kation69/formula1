$( document ).ready(function() {
// PILOTOS
	$("#addNewPiloto").click(function() {
		$('#insertModal').modal('show');
	});
	$("#buttonClose").click(function () {
	    $('#insertModal').modal('hide'); 
	});
	$("#closeModal").click(function () {
	    $('#insertModal').modal('hide'); 
	});
	$("#closeModal").click(function() {
		$('#DateModal').modal('hide');
	});
	
	// COCHES
	$("#addNewCar").click(function() {
		$('#insertModal').modal('show');
	});
	$("#buttonClose").click(function () {
	    $('#insertModal').modal('hide'); 
	});
	$("#closeModal").click(function () {
	    $('#insertModal').modal('hide'); 
	});
	$("#addNewMember").click(function () {
		$('#searchUserMember').show();
		// Call service list user responsables
		invokeAjax('GET', '/FormulaProject/usuarios/getUsersResp').then(result => {
			if (result.codeService == '200') {
				var res = JSON.parse(result.bodyResponse);
				var equipoId = $('#equipo-id-hidden').val();
				res.forEach(function( index ) {
					$('#table-searched-group').append('<li onClick="crearMiembro(\''+equipoId+'\',\''+index.id+'\');" class="list-group-item">'+index.usuario+'/'+index.email+'</li>');
				});								
			}
		}).catch((thrownError) => {
			console.log(thrownError);
		});
	});
	$('#search-member-input').on('keyup', function() {	
		$('#table-searched').show();
		var value = $(this).val();
		var patt = new RegExp(value, "i");
		$('#table-searched-group').find('li').each(function() {
		    var $table = $('#table-searched-group');
		    if (($(this).text().search(patt) >= 0)) {
		      $(this).show();
		    } else {
		    	$(this).hide();
		    }
		});
    });
	
});

function crearMiembro(idEquipo, idUser) {
	var dataJson = "{'userId: "+idUser+"}"
	var endpoint = '/FormulaProject/portalResponsable/'+idEquipo+'/creaMiembro/'+idUser;
	invokeAjax('GET', endpoint).then(result => {
		if (result.codeService == 200) {
			window.location.reload();			
		}
	})
	.catch((thrownError) => {
		console.log(thrownError);
	});
}


// Modal functions
function OpenConfirmModalTitle(id, type, msg, reload) {
	$('#ConfirmModal').modal('show');
	$("#ConfirmModalTitle").text("Confirmas borrar el registro");
	$('#AcctionModalConfirm').attr("action","/FormulaProject/"+type+"/borrar/"+id);
	$('#AcctionModalConfirm').attr("method","POST");
	$('#content-fullScreen-image').hide();
	$('#content-confirm-borrado').show();
	var reloadTemp = reload == null ? false : reload;
	$('#reload_page').prop('checked', reloadTemp);
	if (msg != null) {
		$('#modal_text').text(msg);
		$('#modal_text').show();
	}
}

function CloseConfirmModalTitle() {
	$("#ConfirmModalTitle").text("");
	$('#AcctionModalConfirm').attr("action","");
	$('#ConfirmModal').modal('hide');
	$('#ConfirmModalDialog').removeClass('modal-lg');
	$('#modal_text').text("");
	$('#modal_text').hide();
}

function CloseDateModalTitle() {
	$("#DateModalTitle").text("");
	$('#AcctionModalDate').attr("action","");
	$('#DateModal').modal('hide');
}

// AJAX Functions works with SaveAndResponseWrapper class
function invokeAjax(method, endpoint) {
	return new Promise(function(resolve, reject) {
		$.ajax({
			type : method,
			url : endpoint,
			success : function(result) {
				const obj = JSON.parse(result);
				if (obj.codeService == 200) {
					resolve(obj);
				} else {
					reject(obj);
				}
			},
			error : function(thrownError) {
				reject(thrownError);
			}
		 });
	});
}

function BorrarSubmit() {
	var method = $('#AcctionModalConfirm').attr("method");
	var endpoint = $('#AcctionModalConfirm').attr("action");
	invokeAjax(method, endpoint).then(result => {
		$('#content-fullScreen-image').hide();
		$('#ConfirmModal').modal('hide');

		if ($('#reload_page').is(':checked')) {
			window.location.reload();	
		} else {
			const endArr = endpoint.split('/');
			const idUnique = endArr[5] + "-btnRemove";
			$('.' + idUnique + '-grid').parent().parent().parent().fadeOut('slow', this.remove);
			$('.' + idUnique + '-table').parent().parent().fadeOut('slow', this.remove);
		}
	}).catch((thrownError) => {
		$('#modalTemplate_alert').show();
		$('#spinner').hide();
		$('#modalTemplate_alert').text("Ha ocurrido un error");
	});
}