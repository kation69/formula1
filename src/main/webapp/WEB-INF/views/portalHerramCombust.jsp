<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}" />

<html>
	<head>
		<jsp:include flush="true" page="header.jsp"/>
	</head>
	<script src="${baseURL}/resources/js/script_responsable.js" ></script>
	<script src="${baseURL}/resources/js/html2pdf.bundle.min.js" ></script>
	<script src="${baseURL}/resources/js/chart-2.8.0.js" ></script>		
<body>
	<jsp:include flush="true" page="jumbotron.jsp"/>
	<div class="container mt-3">
		<div class="row">
			<jsp:include flush="true" page="helpers/notifyPortal.jsp" />
			<div class="col-2" align="left">		
				<jsp:include flush="true" page="menuPortal.jsp"/>
			</div>
			<div class="col-10 sectionOne p-4">
				<div class="row">
					<div class="col-10">
						<h1>GASTO DE COMBUSTIBLE</h1>
					</div>
					<div class="col-2">
						<button class="btn btn-warning" onclick="generatePDF();">Descargar</button>
					</div>
				</div>
				<div class="row justify-content-md-center me-5 ms-5 mt-3" id="tool_combust_body">
					<div class="col-12">
						<div class="card">
							<div class="card-header">Resultados del cálculo: <div id="ref_title_pdf" style="display:none;"><b>GASTO DE COMBUSTIBLE</b></div></div>
							<div class="card-body">
								<p class="card-text">Detalles avanzados del consumo: </p>
								<div class="row">
										<div class="colResFirst col-6">
											<table class="table table-bordered">
											  <thead>
											    <tr>
											      <th scope="col">Tipo</th>
											      <th scope="col">Consumo Total</th>
											    </tr>
											  </thead>
										  		<tbody>
													<c:forEach items="${ consumos_totales }" var="consumo">
														<tr>
														<td><c:out value="${consumo.nombre}"></c:out></td>
														<td><c:out value="${Math.round(consumo.consumo*100)/100}"></c:out></td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
										</div>
										<div class="colResSecond col-6">
											<canvas id="canvas_estadisticas"></canvas>
												<c:set var = "consumo_restribuido" value="#"/>
												<c:forEach items="${ consumos }" var="consumo">
													<c:set var = "consumo_restribuido" value="${consumo_restribuido},${consumo.consumo}"/>
												</c:forEach>
												<script type="text/javascript">
												
												grupos= {
											        "tipo": ["line","bar"],
											        "labelX": "Consumo",
											        "labelY": "Cantidad",
											        "label": ["Media","Consumo"],
											        "grupo_datos1": [${fn:replace(consumo_restribuido, '#,', '')}],
											        "grupo_datos0": [${consumo_medio.consumo},${consumo_medio.consumo},${consumo_medio.consumo}],
											        "grupo_cantidad": ["Ahorrador","Normal","Deportido"],
											        "grupo_color1": ["rgba(107, 223, 95, 0.6)","rgba(207, 139, 108, 0.6)","rgba(169, 197, 253, 0.6)"],
										        	"grupo_color0": ["rgba(255, 30, 123, 1)","rgba(255, 30, 123, 1)","rgba(255, 30, 123, 1)"]
											    };
												function Barras(id,grupo, L_min, L_max) {
													var canvas = document.getElementById(id);
										            var ctx = canvas.getContext('2d');
													var datas = [];
												    for (var i = 0; i < grupo["label"].length; i++) {
												    	if(grupo["tipo"][i]=="line"){
												    		datas.push({
													            label: grupo["label"][i],
													            data: grupo["grupo_datos" + i.toString()],
													            backgroundColor: grupo["grupo_color" + i.toString()],
													            borderWidth: 2,
													            borderColor:grupo["grupo_color" + i.toString()][0],
													            hitRadius: 10
													        });
												    	}
												    	else{
												    		datas.push({
													            label: grupo["label"][i],
													            data: grupo["grupo_datos" + i.toString()],
													            backgroundColor: grupo["grupo_color" + i.toString()],
													            borderWidth: 1,
													            stack: 'combined',
													            hitRadius: 10,
													            type:grupo["tipo"][i]
													        });
												    	}
												    }
												    var canvas = new Chart(ctx, {
												        type: 'line',
												        data: {
												            labels: grupo["grupo_cantidad"].slice(L_min, L_max),
												            datasets: datas
												        },
												        options: {
												        	interaction: {
												        	      intersect: false,
												        	      mode: 'index',
												        	    },
												        	layout: {
												                padding: {
												                    left: 0,
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
												                    stacked: true,
													                responsive: true,
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
												                text: "Consumos por vuelta",
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
								</div>
							</div>
						</div>
					</div>
					<div class="col-6 mt-2">
						<div class="card">
							<div class="card-header">Información del coche</div>
							<c:if test="${coche.imagen!='' && coche.imagen!=undefined}">
				      		<img src="<c:url value='/resources/imgCoches/${coche.imagen}'/>" 
								align="right" 
								class="card-img-top cursorPointer"
								onClick="showImage('${baseURL}', '/resources/imgCoches/${coche.imagen}');"
								alt="Click para ampliar la imagen" style="height:250px;" />
							</c:if>
							<div class="card-body">
<!--						    	<p class="card-text">Some quick example text to build on the card title and make up the bulk of the card's content.</p>-->
						    	<input type="button" class="btn btn-primary" value="Más información" onClick="masInfoCoche();"/>
								<div class="card mt-3" style="width: 18rem; display: none;" id="masInfo_coche_combus" >
								  <ul class="list-group list-group-flush">
								    <li class="list-group-item"><b>Nombre: </b>${coche.nombre}</li>
								    <li class="list-group-item"><b>Código: </b>${coche.codigo}</li>
								    <li class="list-group-item"><b>Consumo: </b>${coche.consumo}</li>
								    <li class="list-group-item"><b>ERS Curva Lenta: </b>${coche.ersCurvasLentas}</li>
								    <li class="list-group-item"><b>ERS Curva Media: </b>${coche.ersCurvasMedias}</li>
								    <li class="list-group-item"><b>ERS Curva Rápida: </b>${coche.ersCurvasRapidas}</li>
								  </ul>
								</div>
					    	</div>
						</div>		
					</div>
					<div class="col-6 mt-2">
						<div class="card">
							<div class="card-header">Información del circuito</div>
							<c:if test="${circuito.imgTrazado!=''&& circuito.imgTrazado!=undefined}">
				      		<img src="<c:url value='/resources/imgCircuit/${circuito.imgTrazado}'/>" 
								align="right" 
								class="card-img-top cursorPointer"
								onClick="showImage('${baseURL}', '/resources/imgCircuit/${circuito.imgTrazado}');"
								alt="Click para ampliar la imagen" style="height:250px;" />
							</c:if>
							<div class="card-body">
<!--  						    	<p class="card-text"></p>-->
						    	<input type="button" class="btn btn-primary" value="Más información" onClick="masInfoCircuito();"/>
						    	<div class="card mt-3" style="width: 18rem; display: none;" id="masInfo_circuito_combus">
								  <ul class="list-group list-group-flush">
								    <li class="list-group-item"><b>Nombre: </b>${circuito.circuitName }</li>
								    <li class="list-group-item"><b>Num de vueltas: </b>${circuito.numVueltas }</li>
								    <li class="list-group-item"><b>Longitud: </b>${circuito.longitude }</li>
								    <li class="list-group-item"><b>Curvas rápidas: </b>${circuito.numVueltas }</li>
								    <li class="list-group-item"><b>Curvas medias: </b>${circuito.curvasRapidas }</li>
								    <li class="list-group-item"><b>Curvas lentas: </b>${circuito.curvasLentas }</li>
								    <li class="list-group-item"><b>Fecha asignada: </b>${circuito.fecha }</li>
								  </ul>
								</div>
					    	</div>
						</div>		
					</div>
					<div class="col-6">
					</div>				
				</div>
			</div>
		</div>
	</div>
</body>
<script>
	function masInfoCoche() {
        if($("#masInfo_coche_combus").is(":visible")) {
			$('#masInfo_coche_combus').hide();        	
        } else {    	
			$('#masInfo_coche_combus').show();
        }
	}
	function masInfoCircuito() {
        if($("#masInfo_circuito_combus").is(":visible")) {
			$('#masInfo_circuito_combus').hide();        	
        } else {    	
			$('#masInfo_circuito_combus').show();
        }
	}
	function generatePDF() {
		$('#masInfo_coche_combus').show();
		$('#masInfo_circuito_combus').show();
		$('.colResFirst').removeClass('col-6');
		$('.colResFirst').addClass('col-4');
		$('.colResSecond').removeClass('col-6');
		$('.colResSecond').addClass('col-5');
		$('.colResSecond').attr('style','margin-left:-5.3%');
		$('#ref_title_pdf').show();
		$('.btn').hide();
		// Choose the element that our invoice is rendered in.
		const element = document.getElementById('tool_combust_body');
		// Choose the element and save the PDF for our user.
		html2pdf().from(element).save();
		setTimeout(
		  function() 
		  {
			$('.colResFirst').removeClass('col-4');
			$('.colResFirst').addClass('col-6');
			$('.colResSecond').removeClass('col-5');
			$('.colResSecond').addClass('col-6');
			$('.colResSecond').removeAttr('style');
			$('.btn').show();
			$('#ref_title_pdf').hide();
			$('#masInfo_coche_combus').hide();        	
			$('#masInfo_circuito_combus').hide();        	
		    //do something special
		  }, 1000);
	}
</script>		
</html>