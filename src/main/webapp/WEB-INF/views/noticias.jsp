
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Objects"%>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<html>
<jsp:include flush="true" page="./header.jsp"/>
<body>
	<div class="container">
		<article class="sectionStyle" >
			<h3>Noticias</h3>
			<div class="row">
			<c:forEach items="${ listaNoticias }" var="noticia">
				<div class="col-xl-3 col-lg-6 col-md-12 col-sm-12">
					<div class="card cardNoticias">
						<c:if test="${noticia.imagen!=''}">
						<img src="<c:url value='/resources/imgNoticias/${noticia.imagen}'/>" 
							 align="right" 
							 class="img-fluid card-img-top card-text"/>
						</c:if>
						<div class="card-body">
						    <h5 class="card-title">${ noticia.titulo }</h5>
						    <c:if test="${fn:length(noticia.cuerpo)>20}">
						    	<p class="card-text">${fn:substring( noticia.cuerpo,0,20)}...</p>
						    </c:if>
						    <c:if test="${fn:length(noticia.cuerpo)<10}">
						    	<p class="card-text">${noticia.cuerpo}</p>
						    </c:if>
						    <a href="noticias/ver?pag=${ noticia.permantlink }" class="btn btn-primary">Ver noticia</a>
					    </div>
					</div>
				</div>
			</c:forEach>
			</div>
		</article>
	</div>
</body>
</html>