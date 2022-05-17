<!DOCTYPE html>
<%@ page language="java" %>

<html>
<jsp:include flush="true" page="WEB-INF/views/header.jsp"/>
<body>
	<div class="container">
		<jsp:include flush="true" page="WEB-INF/views/jumbotron.jsp"/>
	
		<article>
          	<jsp:include flush="true" page="WEB-INF/views/menu.jsp"/>
		</article>
		<article>
			<% if (session.getAttribute("usuario") != null) { %>			
	          	<jsp:include flush="true" page="WEB-INF/views/dashboard.jsp"/>	
			<% } else { %>
	          	<jsp:include flush="true" page="WEB-INF/views/noticias.jsp"/>	
			<% } %>
		</article>
	</div>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.min.js" integrity="sha384-QJHtvGhmr9XOIpI6YVutG+2QOK9T+ZnN4kzFN1RtK3zEFEIsxhlmWl5/YESvpZ13" crossorigin="anonymous"></script>
	
</body>
</html>