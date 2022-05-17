<!DOCTYPE html>
<%@ page language="java" %>

<html>
	<head>
		<jsp:include flush="true" page="header.jsp"/>
	</head>
<body>
	<jsp:include flush="true" page="jumbotron.jsp"/>
	<div class="container mt-3">
		<jsp:include flush="true" page="menu.jsp"/>
		<article>
			<% if (session.getAttribute("usuario") != null) { %>			
	          	<jsp:include flush="true" page="dashboard.jsp"/>	
			<% } else { %>
	          	<jsp:include flush="true" page="noticias.jsp"/>	
			<% } %>
		</article>
	</div>	
</body>
</html>