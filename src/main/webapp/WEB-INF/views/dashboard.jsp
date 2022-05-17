<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@page import="java.util.Objects"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<article class="sectionStyle">
	<h3>Dashboard</h3>
	<div class="row">
		<div class="col-xl-5 col-lg-5 col-md-12 col-xs-12 p-1 m-3 sectionOne" style="overflow-y: scroll; height: 500px;" >	
			<div class="p-3">
				<h4>Últimas noticias</h4>
				<c:forEach items="${ listaNoticias }" var="noticia">
				<div class="col-12 p-1">
					<div class="card">
						<c:if test="${noticia.imagen!=''}">
						<img src="<c:url value='/resources/imgNoticias/${noticia.imagen}'/>" 
							 align="right" 
							 class="img-fluid card-img-top card-text"/>
						</c:if>
						<div class="card-body">
						    <h5 class="card-title">${ noticia.titulo }</h5>
						    <p class="card-text">${ noticia.cuerpo }</p>
						    <a href="noticias/ver?pag=${ noticia.permantlink }" class="btn btn-primary">Ver noticia</a>
					    </div>
					</div>
				</div>
				</c:forEach>
			</div>
		</div>
		<div class="sectionOne col-xl-3 col-lg-3 col-md-12 col-xs-12 p-1 m-2" style="background-color: #F0C929;">	
			<div class="p-3">
				<h4>Novedades</h4>
				<ul class="listaDrop list-group">
					<li class="list-group-item list-group-item-action list-group-item-primary disabled">This is a primary list group item</li>
					<li class="itemDraggable list-group-item list-group-item-success">This is a success list group item</li>
					<li class="itemDraggable list-group-item list-group-item-danger">This is a danger list group item</li>
					<li class="itemDraggable list-group-item list-group-item-warning">This is a warning list group item</li>
				</ul>
			</div>
		</div>
		<div class="col-xl-3 col-lg-3 col-md-12 col-xs-12 p-1 m-2">	
			<div class="row p-1">
				<div class="sectionOne col-12" style="background-color: #FBE6C2;">	
					<div class="p-3">
						<h4>Notificaciones</h4>
						<ul class="listaDrop list-group">
							<li class="list-group-item list-group-item-primary disabled">This is a secondary list group item</li>
						</ul>
					</div>
				</div>
			</div>
			<div class="row mt-4 p-1">
				<div class="sectionOne col-12" style="background-color: #dc3545;">	
					<div class="p-3">
						<h4>Revisar</h4>
						<ul class="listaDrop list-group">
							<li class="list-group-item list-group-item-primary disabled">This is a secondary list group item</li>
							<li class="list-group-item d-flex justify-content-between align-items-center">Cras justo odio
								<span class="badge badge-primary badge-pill" style="color: black;">14</span>
						    </li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</article>
<script>
	
	$( '.itemDraggable' ).draggable({
		  helper: 'clone'
	});

	$( '.listaDrop' ).droppable({
	  accept: '.itemDraggable',
	  hoverClass: 'hovering',
	  drop: function( ev, ui ) {
	    ui.draggable.detach();
	    $( this ).append( ui.draggable );
	  }
	});
</script>