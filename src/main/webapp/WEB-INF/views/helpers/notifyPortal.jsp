<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% if (session.getAttribute("msgAlert") != null) { %>
	<div id="toastPortal" class="toast" role="alert" aria-live="assertive" aria-atomic="true">
	  <div class="toast-header toast-header-${ typeAlert }">
	    <strong class="me-auto">Nueva notificación!</strong>
	    <small>Ahora</small>
	  </div>
	  <div class="toast-body">
		${ msgAlert }
	  </div>
	</div>
<% session.removeAttribute("msgAlert"); session.removeAttribute("typeAlert"); } %>