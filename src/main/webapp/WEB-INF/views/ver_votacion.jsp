<!DOCTYPE html>
<%@ page language="java"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />
<html>
<script src="${baseURL}/resources/js/chart-2.8.0.js" ></script>								
<%session.setAttribute("votacion_actual", session.getAttribute("votacion_"+session.getAttribute("id_votacion")));%>
<jsp:include flush="true" page="header.jsp" />
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#volver').click(function() {
				window.location.href = document.referrer;
			});

		});
	</script>
	<jsp:include flush="true" page="./jumbotron.jsp" />
	<div class="container mt-3">
		<article>
			<jsp:include flush="true" page="./menu.jsp" />
		</article>
		<article>
		<div class="row">
			<div class="col-12">
				
				<div class="card cardVotaciones">
					<div class="card-header">
						<c:if test="${ votacion_actual!='true' || perfil=='admin'}">
							<h3 class="card-title" style="display:contents;">${ titulo }</h3>
						</c:if>
						<c:if test="${ votacion_actual=='true' && perfil!='admin' && votacion_acabada!='true'}">
							<h3 class="card-title" style="display:contents;">Estadisticas - ${ titulo }</h3>
						</c:if>
						<c:if test="${ votacion_actual=='true' && perfil!='admin' && votacion_acabada=='true'}">
							<h3 class="card-title" style="display:contents;">Acabada - ${ titulo }</h3>
							
						</c:if>
						<c:if test="${ votacion_actual=='true' && votacion_acabada=='false' && perfil!='admin'}">
						<button type="button" class="btn btn-primary" onClick="verestadisticas()" id="verestadisticas" style="float:right; margin-right:10px; display:none;">Ver estadisticas</button>
						
						<button type="button" class="btn btn-primary" onClick="volveravotar()" id="volveravotar" style="float:right; margin-right:10px;">Volver a votar</button>
						</c:if>
						<button type="button" class="btn btn-primary volver" id="volver" style="float:right; margin-right:10px;">Volver</button>
						<c:if test="${ perfil=='admin'}">
							<div  style="float:right; margin-right:10px;">
								<p style="display:contents;"><small>Modo de visualización</small></p>
								<i class="far fa-address-card fa-xl" onClick="$('div.estadisticas').hide('slow');$('#AcctionModal').show('slow');"></i>
								<i class="fas fa-chart-bar fa-xl" onClick="$('#AcctionModal').hide('slow');$('div.estadisticas').show('fast');"></i>
							</div>
						</c:if>
						
						
					</div>
					<div class="card-body">
					
						<c:if test="${votacion_acabada=='false' || perfil=='admin'}">
							<div class="row">
								<div class="col-lg-6 col-sm-12 col-md-12">
									<%
									if (session.getAttribute("imagen") != null) {
									%>
									<%
									if (session.getAttribute("imagen") != "") {
									%>
									<img src="<c:url value='/resources/imgVotaciones/${ imagen }'/>"
										align="right" class="card-img-top" />
									<%
									}
									%>
									<%
									}
									%>
								</div>
								<div class="col-lg-6 col-sm-12 col-md-12">
									<p class="card-text">${ descripcion }</p>
								</div>
	
							</div>
							<form action="/FormulaProject/votaciones/votar/${id_votacion}"
								id="AcctionModal" method="POST" enctype="multipart/form-data">
								<div class="modal-body">
									<div class="form-group">
										<label for="correoInput">Correo</label> <input type="email"
											class="form-control" id="correoInput" name="correo"
											placeholder="Introduce el correo electronico">
									</div>
									
									
									<div>
										<select name="piloto">
											<c:forEach items = "${listaPilotos}" var = "piloto">
											<option value="${listaPilotos}">${listaPilotos}</option>
											<option><c:out value="${piloto.nombre}"></c:out></option>
											</c:forEach>
										</select>
									
									
									<c:forEach items = "${ listaPilotos }" var = "piloto">
									<div>
										<img id="${piloto.apellidos}" src="<c:url value='/resources/imgPilotos/ ${piloto.foto}'/>">
									</div>
									<div>
										<input type="radio" id="${piloto.apellidos}" name="pilotos" value="${piloto.apellidos}">
										  <label for="${piloto.apellidos}">${piloto.apellidos}</label>
									</div>
								
									
									</c:forEach>
									
									
									
									</div>
									
									
									<div>
										<img id="bottas" src="<c:url value='/resources/imgPilotos/gasly.png'/>">
									</div>
									<div>
										<input type="radio" id="bottas" name="pilotos" value="bottas">
										  <label for="bottas">Gasly</label>
									</div>
									<div>
										<img id="gasly" src="<c:url value='/resources/imgPilotos/raikkonen.png'/>">
									</div>
									<div>
										<input type="radio" id="gasly" name="pilotos" value="gasly">
										  <label for="gasly">Raikkonen</label>
									</div>
									<div>
										<input type="radio" id="otro" name="pilotos" value="otro_piloto">
										<label for="otro">Otro</label>
										<input id="otroPiloto" name="otro_piloto"  
													placeholder="Introduce nombre de piloto"
													onfocus="marca(this);" style="margin:5px;min-width:30%">
										<script>function marca(otroPiloto){
											$(otroPiloto).parent().find("input[type='radio']").prop("checked", true);
										}</script>
									</div>
	
								</div>
								<div class="modal-footer">
									<input type="submit" class="btn btn-primary"
										value="Enviar" />
								</div>
							</form>
							
							<c:if test="${ votacion_actual=='true' || perfil=='admin'}">
							<script>
								$("#AcctionModal").hide();
							</script>
							</c:if>
						</c:if>
						<c:if test="${ votacion_actual=='true' || perfil=='admin'}">
							<c:if test="${ perfil!='admin'}">
								<div class="estadisticas">
							</c:if>
								<c:if test="${cantidades_grupo.size() == 0 }">
									<div class="alert alert-info" role="alert">No hay noticias creadas.</div>
								</c:if>
								<canvas id="canvas_estadisticas"></canvas>
								<script type="text/javascript">
								grupos= {
							        "Titulo": "Grados del volante",
							        "ID": "Graf_AnguloGiro",
							        "tipo": "LineaBasica",
							        "labelX": "Pilotos",
							        "labelY": "Cantidad",
							        "color0": 'rgba(255, 159, 64, 0.7)',
							        "coleccion": ["AnguloGiro"],
							        "label": ["Estadisticas"],
							        "grupo_datos0": ${cantidades_grupo},
							        "grupo_cantidad": ${listado_pilotos},
							        "grupo_color0": ["rgba(255,255,1,1)","rgba(255,1,255,1)","rgba(1,255,255,1)"]
							    };
								function Barras(id,grupo, L_min, L_max) {
									var canvas = document.getElementById(id);
						            var ctx = canvas.getContext('2d');
									var datas = [];
								    for (var i = 0; i < grupo["label"].length; i++) {
								        datas.push({
								            label: grupo["label"][i],
								            data: grupo["grupo_datos" + i.toString()].slice(L_min, L_max),
								            backgroundColor: grupo["grupo_color" + i.toString()].slice(L_min, L_max),
								            borderWidth: 1,
								            hitRadius: 10
								        });
								    }
								    var canvas = new Chart(ctx, {
								        type: 'bar',
								        data: {
								            labels: grupo["grupo_cantidad"].slice(L_min, L_max),
								            datasets: datas
								        },
								        options: {
								            indexAxis: 'y',
								            layout: {
								                padding: {
								                    left: 50,
								                    right: 0,
								                    top: 0,
								                    bottom: 0
								                }
								            },
								            scales: {
								                x: {
									                responsive: true,
								                    display: true,
								                    title: {
								                        display: true,
								                        labelString: grupo["labelX"],
								                        fontSize: 15
								                    }
								                },
								                y: {
									                responsive: true,
								                	min:0,
								                    suggestedMin: 0,
								                    suggestedMax: 10,
								                    display: true,
								                    title: {
								                        display: true,
								                        labelString: grupo["labelY"],
								                        fontSize: 15
								                    }
								                }
								            },
								            legend: {
								                display: true,
								                labels: {
								                    fontSize: 15
								                }
								            },
								            title: {
								                display: true,
								                text: grupo["Titulo"],
								                fontSize: 20
								            },
								            hover: {
								                mode: "nearest",
								                intersect: true
								            },
								            tooltips: {
								                mode: "index",
								                intersect: false
								            }
								        }
								    });
								    
								}
								Barras("canvas_estadisticas",grupos,0,8);
							</script>
							</div>
						</c:if>
						<%session.removeAttribute("estadisticas");%>
					</div>
				</div>
			</div>
		</div>
		</article>
	</div>
	<div class="row justify-content-md-center mt-3">
		<div class="col col-lg-2"></div>
		<div class="col-md-auto">
			<button type="button" class="btn btn-primary" id="volver">Volver</button>
		</div>
		<div class="col col-lg-2"></div>
	</div>
	<script type="text/javascript">
		function volveravotar(){
			$('div.estadisticas').hide('slow');
			$('#AcctionModal').show('slow');
			$('#volveravotar').hide('slow');
			$('#verestadisticas').show('slow');
		}
		function verestadisticas(){
			$('div.estadisticas').show('slow');
			$('#AcctionModal').hide('slow');
			$('#verestadisticas').hide('slow');
			$('#volveravotar').show('slow');
		}
	</script>
</body>
</html>
<%session.setAttribute("votacion_actual", "");%>
<%session.setAttribute("votacion_acabada", "false");%>