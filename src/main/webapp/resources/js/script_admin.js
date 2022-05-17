$(document).ready(function() {
	// NOTICIAS
	$("#addNewNoticia").click(function() {
		$('#insertModal').modal('show');
	});
	$("#buttonClose").click(function() {
		$('#insertModal').modal('hide');
	});
	$("#closeModal").click(function() {
		$('#insertModal').modal('hide');
	});
	$("#closeModal").click(function() {
		$('#DateModal').modal('hide');
	});

	// USUARIOS
	$("#buttonClose").click(function() {
		$('#gestorUsuariosModal').modal('hide');
	});
	$("#closeModal").click(function() {
		$('#gestorUsuariosModal').modal('hide');
	});

	// CIRCUITOS
	$("#addCircuitoBtn").click(function() {
		$('#insertModal').modal('show');
		$("#zeroStep").show();
		$("#modalFooter").hide();
		resetModalCircuit();
	});

	$('#buttonNext').click(function() {
		$("#firstStep").hide("slow", function() {
			var rowCirc = $("[name=radioSelect]:checked").parent().parent();
			$("#nombreCircuito").prop('disabled', true).val(rowCirc.prev().prev().prev().children().html());
			$("#ciudadCircuito").prop('disabled', true).val(rowCirc.prev().prev().html());
			$("#paisCircuito").prop('disabled', true).val(rowCirc.prev().html());
			$("#secondStep").show("slow");
			$("#saveBtn").show("slow");
			$("#buttonNext").hide();
			$("#titleModal").text("Configuración del circuito");
			$("#imagenCargar").attr("src", "/FormulaProject/resources/imgCircuit/" + $("[name=radioSelect]:checked").val() + ".png");
			$("imgCircuitModal").attr("src", "<c:url value='/resources/imgCircuit/ss.png'/>");
		});
	});



	// PILOTOS
	$("#addNewPiloto").click(function() {
		$('#insertModal').modal('show');
	});
	$("#buttonClose").click(function() {
		$('#insertModal').modal('hide');
	});
	$("#closeModal").click(function() {
		$('#insertModal').modal('hide');
	});
	$("#closeModal").click(function() {
		$('#DateModal').modal('hide');
	});

});

// Circuitos functions
function selCircuitCustom() { // custom option
	$("#zeroStep").hide("slow", function() {
		$("#secondStep").show("slow");
		$("#modalFooter").show("slow");
		$("#buttonNext").hide();
		$("#saveBtn").show("slow");
		$("#inputFile_div").show();
		$("#imagenCargar").attr("src", "");

		$("#id_edit").parent().parent().hide();
		$("#titleModal").text("Configuración del circuito");

		//$("#form_circuitos_crear_edit").attr("action","/FormulaProject/circuito/insertarCircuito");
	});
}

function EditarCircuito(id, imgTrazado, circuitName, locality, country, longitude, numVueltas, curvasRapidas, curvasMedias, curvasLentas, imgTrazado) {
	$('#insertModal').modal('show');
	$("#buttonNext").hide();
	$("#saveBtn").show();
	$("#zeroStep").hide();
	$("#secondStep").show();
	$("#id_edit").parent().parent().hide();
	$("#id_edit").val(id);
	$("#inputFile_div").show();
	$("#titleModal").text("Editar circuito " + id.toString());
	$("#nombreCircuito").val(circuitName);
	$("#ciudadCircuito").val(locality);
	$("#paisCircuito").val(country);
	$("#numVueltas").val(numVueltas);
	$("#longitud").val(longitude);
	$("#curvasRapidasInput").val(curvasRapidas);
	$("#curvasMediaInput").val(curvasMedias);
	$("#curvasLentasInput").val(curvasLentas);
	$("#form_circuitos_crear_edit").attr("action", "/FormulaProject/circuito/editarCircuito?id=" + id.toString());
	$('#imagenCargar').attr("src", "/FormulaProject/" + imgTrazado);
}

function resetModalCircuit() {
	$('#insertModal').modal('hide');
	$("#nombreCircuito").prop('disabled', false).val("");
	$("#ciudadCircuito").prop('disabled', false).val("");
	$("#paisCircuito").prop('disabled', false).val("");
	$("#numVueltas").val("");
	$("#longitud").val("");
	$("#curvasLentasInput").val("");
	$("#curvasMediaInput").val("");
	$("#curvasRapidasInput").val("");
	$('#alertStep').hide();
	$("#inputFile_div").show();
	$('#spinner').hide();
	$("#secondStep").hide();
	$("#firstStep").hide();
	$("#deleteConfirm").hide();
	$("#saveBtn").hide();
	$("#buttonNext").hide();
	$("#imagenCargar").attr("src", "");
	$("#titleModal").text("Elige una opción");
	$("#form_circuitos_crear_edit").attr("action", "/FormulaProject/circuito/insertarCircuito");
}

function showImage(baseUrl, id) {
	$('#ConfirmModal').modal('show');
	$('#content-confirm-borrado').hide();
	$('#content-fullScreen-image').show();
	const uri = baseUrl + id;
	$('#content-img-dyn-fullscreen').attr('src', baseUrl + id);
	$('#ConfirmModalDialog').addClass('modal-lg');
}

function changeViewMode(type) {
	if (type == 'grid') {
		$("#table_visual").hide();
		$("#calendar_visual").hide();
		$("#table_calendar_visual").hide();
		$("#grid_mode").show();
	} else if (type == 'table') {
		$("#grid_mode").hide();
		$("#calendar_visual").hide();
		$("#table_calendar_visual").hide();
		$("#table_visual").show();
	} else if (type == 'calendar') {
		$("#grid_mode").hide();
		$("#table_visual").hide();
		$("#table_calendar_visual").hide();
		$("#calendar_visual").show();
	} else if (type == 'table_calendar') {
		$("#grid_mode").hide();
		$("#table_visual").hide();
		$("#calendar_visual").hide();
		$("#table_calendar_visual").show();
	}
}

// Modal functions
function OpenConfirmModalTitle(id, type) {
	$('#ConfirmModal').modal('show');
	$("#ConfirmModalTitle").text("Confirmas borrar el registro");
	$('#AcctionModalConfirm').attr("action", "/FormulaProject/" + type + "/borrar/" + id);
	$('#AcctionModalConfirm').attr("method", "POST");
	$('#content-fullScreen-image').hide();
	$('#content-confirm-borrado').show();
}

function CloseConfirmModalTitle() {
	$("#ConfirmModalTitle").text("");
	$('#AcctionModalConfirm').attr("action", "");
	$('#ConfirmModal').modal('hide');
	$('#ConfirmModalDialog').removeClass('modal-lg');
}
function CloseDateModalTitle() {
	$("#DateModalTitle").text("");
	$('#AcctionModalDate').attr("action", "");
	$('#DateModal').modal('hide');
}

// Assign date modal
function asignarFecha(id, nombre) {
	$('#DateModal').modal('show');
	$("#DateModalTitle").text("Asignar fecha al circuito: " + nombre);
	$('#AcctionModalDate').attr("action", "/FormulaProject/circuito/calendarizar/" + id);
	$('#AcctionModalDate').attr("method", "POST");
}

// AJAX Functions works with SaveAndResponseWrapper class
function invokeAjax(method, endpoint) {
	return new Promise(function(resolve, reject) {
		$.ajax({
			type: method,
			url: endpoint,
			success: function(result) {
				const obj = JSON.parse(result);
				if (obj.codeService == 200) {
					resolve(obj);
				} else {
					reject(obj);
				}
			},
			error: function(thrownError) {
				reject(thrownError);
			}
		});
	});
}

function setErgarstApi() { // ergast option
	$('#zeroStep').hide();
	$('#spinner').show();
	$.ajax({
		type: 'GET',
		url: '/FormulaProject/circuito/ergarst',
		success: function(result) {
			if (result == "500") {
				$('#alertStep').show();
				$('#spinner').hide();
				$('#alertStep_text').append('Se ha producido un error con el servicio.');
				$("#titleModal").text("Error, vuelva a intentarlo.");
			} else {
				$('#spinner').hide();
				$("#firstStep").show("slow");
				$("#modalFooter").show();
				$("#inputFile_div").hide();
				$("#buttonNext").show("slow");
				$("#titleModal").text("Selecciona un circuito");
				$("#id_edit").parent().parent().hide();
				var trHTML = '';
				var json = $.parseJSON(result);
				$.each(json, function(index, value) {
					trHTML += '<tr>' +
						'<td><a href="' + value.url + '" target="_blank">' + value.circuitName +
						'</a></td>' +
						'<td class="_localityCircuit">' + value.locality +
						'</td><td class="_localityCountry">' + value.country +
						'</td><td><div class="form-check">' +
						'<input class="form-check-input" type="radio" id="radioSelect" name="radioSelect"' +
						'value="' + value.circuitId + '" id="' + value + '">' +
						'</div></td></tr>';
				});
				$('#ergast_table').append(trHTML);
			}
		},
		error: function(thrownError) {
			$('#alertStep').show();
			$('#spinner').hide();
			$('#alertStep_text').append(thrownError);
			$("#titleModal").text("Error, vuelva a intentarlo.");
		}
	});
}

function BorrarSubmit() {
	var method = $('#AcctionModalConfirm').attr("method");
	var endpoint = $('#AcctionModalConfirm').attr("action");
	invokeAjax(method, endpoint).then(result => {
		const endArr = endpoint.split('/');
		const idUnique = endArr[4] + "-btnRemove";
		$('.' + idUnique + '-grid').parent().parent().parent().fadeOut('slow', this.remove);
		$('.' + idUnique + '-table').parent().parent().fadeOut('slow', this.remove);
		$('#content-fullScreen-image').hide();
		$('#ConfirmModal').modal('hide');
		setTypeNotifyAdmin(result.codeService);
		$('#alertCustom-ajax').show();
		$('#alertCustom-ajax').text(result.bodyResponse);
		$('#alertCustom-ajax').delay(3000).fadeOut('slow');
		$('#alertCustom-ajax').text();
	}).catch((thrownError) => {
		setTypeNotifyAdmin(thrownError.codeService);
		$('#alertCustom-ajax').show();
		$('#alertCustom-ajax').text(thrownError.bodyResponse);
		$('#ConfirmModal').modal('hide');
		$('#spinner').hide();
		$('#alertCustom-ajax').delay(3000).fadeOut('slow');
		$('#alertCustom-ajax').text();
	});
}

function setTypeNotifyAdmin(codeService) {
	if (codeService == 200) {
		$('#alertCustom-ajax').addClass('alert-success');
	} else if (codeService == 204) {
		$('#alertCustom-ajax').addClass('alert-warning');
	} else {
		$('#alertCustom-ajax').addClass('alert-danger');
	}
}
