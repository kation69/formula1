<!DOCTYPE html>
<%@ page language="java" %>

<html>
	<head>
		<jsp:include flush="true" page="header.jsp"/>
	</head>
<body>
	<jsp:include flush="true" page="jumbotron.jsp"/>
	<div class="container mt-3">
		<jsp:include flush="true" page="helpers/notifyPortal.jsp" />
		<div class="row">
			<div class="col-2" align="left">		
				<jsp:include flush="true" page="menuPortal.jsp"/>
			</div>
			<div class="col-10 sectionOne">
				<article>
					<h1>PORTAL RESPONSABLE</h1>
					<div class="rol">
						<div class="col-12" align="center">
							<!-- <img src="<c:url value='/resources/imgEquipo/${equipoImg}.png'/>" align="right" class="img-fluid"/> -->
						</div>
					</div>
					
				</article>
			</div>
		</div>
	</div>	
</body>
</html>