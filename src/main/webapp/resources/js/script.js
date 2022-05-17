$( document ).ready(function() {
	// INICIAR SESIÓN
	$("#btnIniciarSesion").click(function () {
	    $('#loginModal').modal('show');
	});
	$("#loginCloseModal").click(function () {
	    $('#loginModal').modal('hide');
	});
	$("#submit").click(function () {
	    $('#insertModal').modal('show');
	});
	
	$(document).on('click', '#alertCustom', function(){
		$("#alertCustom").hide("slow");
	})
	$('#alertCustom').delay(5000).hide("slow");
	$('#toastPortal').delay(5000).hide("slow");
	$('button.volver').click(function() {
		window.location.href =document.referrer;
	});
	
});
