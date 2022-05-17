<!DOCTYPE html>
<%@ page language="java" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>

<jsp:include flush="true" page="header.jsp"/>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#volver').click(function() {
				window.location.href =document.referrer;
			});
			
		});
	</script>
	<jsp:include flush="true" page="./jumbotron.jsp"/>
	<div class="container mt-3">
		<article>
			<jsp:include flush="true" page="./menu.jsp"/>
		</article>
		<article>
		
		<div class="row">
			<div class="col-12">
				<div class="card cardPiloto">
					<div class="card-header">
						<h3 class="card-title" style="display:inline;">${ siglas }</h3>
						<button type="button" class="btn btn-primary volver" id="volver" style="float:right;">Volver</button>
					</div>
					<div class="card-body">
						<div class="row">
							<div class="col-lg-6 col-sm-12 col-md-12">
							    <% if (session.getAttribute("foto") != null) { %>
							    	<% if (session.getAttribute("foto") != "") { %>
								<img src="<c:url value='/resources/imgPilotos/${ foto }'/>" 
												 align="right" 
												 class="card-img-top"/>
							    	<% } %>
							    <% } %>
							</div>
							<div class="col-lg-6 col-sm-12 col-md-12">
								<p class="card-text">
									${ apellidos }
								</p>
							</div>
						</div>
		  			</div>
				</div>
			</div>
		</div>
		<div class="row justify-content-md-center mt-3">
		    <div class="col col-lg-2">
		    </div>
		    <div class="col-md-auto">
		    	<button type="button" class="btn btn-primary volver" id="volver">Volver</button>
	    	</div>
		    <div class="col col-lg-2">
		    </div>
	    </div>
		</article>
	</div>
</body>
</html>