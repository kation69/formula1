<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="helpers.CustomLabels_ES"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="p" uri="/WEB-INF/functions/circuit_function.tld" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<html>
	<head>
		<jsp:include flush="true" page="header.jsp"/>
	</head>
<body>
	<jsp:include flush="true" page="jumbotron.jsp"/>
	<div class="container mt-3">
		<article class="sectionOne pb-10">
			<div class="row">
				<div class="col-12">
					<h1 align="center">ERROR - NOT FOUND</h1>
					<img src="<c:url value='/resources/img/flagsCircuit.png'/>" 
								align="right" 
								class="card-img-top cursorPointer"
								alt=""/>
				</div>
			</div>
		</article>
	</div>	
</body>
</html>