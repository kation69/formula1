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
						<h1>INFORME DEL KERS</h1>
					</div>
					<div class="col-2">
						<button class="btn btn-warning" onclick="generatePDF();">Descargar</button>
					</div>
				</div>
				<div class="row justify-content-md-center me-5 ms-5 mt-3" id="tool_kers_body">
					<div class="col-12">
						<div class="card">
							<div class="card-header">Resultados del cálculo:<div id="ref_title_pdf" style="display:none;"><b>USO DEL KERS</b></div></div>
							<div class="card-body">
								<p class="card-text">Los resultados son: </p>
								<div class="row">
					  				<c:set var = "ers_restribuido" value="#"/>
					  				<c:set var = "ers_vueltas" value="#"/>
					  				<c:set var = "ers_tipos" value="#"/>
					  				<c:set var = "ers_color" value="#"/>
					  				<c:set var = "vueltas_color" value="#"/>
									<c:forEach items="${ lstERS }" var="ers">
										<c:set var = "ers_tipos" value="${ers_tipos},'${ers.tipo}'"/>
										<c:set var = "ers_restribuido" value="${ers_restribuido},${ers.ERS}"/>
										<c:set var = "ers_vueltas" value="${ers_vueltas},${ers.num_vueltas}"/>
										<c:set var = "ers_color" value="${ers_color},'${ers.color}'"/>
										<c:set var = "vueltas_color" value="${vueltas_color},'${ers.vuelta_color}'"/>
									</c:forEach>
									<div class="col-6 colResFirst">
										<table class="table table-bordered">
										  <thead>
										    <tr>
										      <th scope="col">Tipo</th>
										      <th scope="col">Carga Vuelta</th>
										      <th scope="col">Vueltas Necesarias</th>
										    </tr>
										  </thead>
									  		<tbody>
												<c:forEach items="${ lstERS }" var="ers">
													<tr>
													<td><c:out value="${ers.tipo}"></c:out></td>
													<td><c:out value="${Math.round(ers.ERS*1000)/1000}"></c:out></td>
													<td><c:out value="${Math.round(ers.num_vueltas*1000)/1000}"></c:out></td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									</div>
									
									<div class="col-6 colResSecond">
										<canvas id="canvas_estadisticas"></canvas>
											<script type="text/javascript">
											
											grupos= {
										        "labelX": "ERS",
										        "labelY": "Cantidad",
										        "labelY1": "Vueltas",
										        "label": ["ERS","Vueltas"],
										        "grupo_datos0": [${fn:replace(ers_restribuido, '#,', '')}],
										        "grupo_datos1": [${fn:replace(ers_vueltas, '#,', '')}],
										        "grupo_cantidad": [${fn:replace(ers_tipos, '#,', '')}],
										        "grupo_color0": [${fn:replace(ers_color, '#,', '')}],
									        	"grupo_color1": [${fn:replace(vueltas_color, '#,', '')}]
										    };
											function Barras(id,grupo, L_min, L_max) {
												var canvas = document.getElementById(id);
									            var ctx = canvas.getContext('2d');
												var datas = [];
											    for (var i = 0; i < grupo["label"].length; i++) {
											    	if(i==0){
											    		datas.push({
												            label: grupo["label"][i],
												            data: grupo["grupo_datos" + i.toString()],
												            backgroundColor: grupo["grupo_color" + i.toString()],
												            borderWidth: 1,
												            hitRadius: 10,
												            yAxisID: 'y'
												        });
											    	}
											    	else{
											    		datas.push({
												            label: grupo["label"][i],
												            data: grupo["grupo_datos" + i.toString()],
												            backgroundColor: grupo["grupo_color" + i.toString()],
												            borderWidth: 1,
												            hitRadius: 10,
												            yAxisID: 'y'+i.toString()
												        });
											    	}
										    		
											    }
											    var canvas = new Chart(ctx, {
											        type: 'bar',
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
											                    type: 'linear',
												                responsive: true,
												                position: 'left',
											                    display: true,
											                    title: {
											                        display: true,
											                        labelString: grupo["labelY"],
											                        fontSize: 15
											                    }
											                },
											                y1: {
											                    type: 'linear',
											                    display: true,
												                responsive: true,
											                    position: 'right',
											                    title: {
											                        display: true,
											                        labelString: grupo["labelY1"],
											                        fontSize: 15
											                    },
											                    // grid line settings
											                    grid: {
											                      drawOnChartArea: false, // only want the grid lines for one axis to show up
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
											                text: "Caculo KERS",
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
						    	<input type="button" class="btn btn-primary" value="Más información" onClick="masInfoCoche();"/>
								<div class="card mt-3" style="width: 18rem; display: none;" id="masInfo_coche_kers" >
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
						    	<input type="button" class="btn btn-primary" value="Más información" onClick="masInfoCircuito();"/>
						    	<div class="card mt-3" style="width: 18rem; display: none;" id="masInfo_circuito_kers">
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
        if($("#masInfo_coche_kers").is(":visible")) {
			$('#masInfo_coche_kers').hide();        	
        } else {    	
			$('#masInfo_coche_kers').show();
        }
	}
	function masInfoCircuito() {
        if($("#masInfo_circuito_kers").is(":visible")) {
			$('#masInfo_circuito_kers').hide();        	
        } else {    	
			$('#masInfo_circuito_kers').show();
        }
	}
	function generatePDF() {
		$('#masInfo_coche_kers').show();
		$('#masInfo_circuito_kers').show();
		$('.colResSecond').removeClass('col-6');
		$('.colResSecond').addClass('col-5');
		$('.colResFirst').removeClass('col-6');
		$('.colResFirst').addClass('col-5');
		$('.colResSecond').attr('style','margin-left:-5.3%');
		$('#ref_title_pdf').show();
		$('.btn').hide();
		// Choose the element that our invoice is rendered in.
		const element = document.getElementById('tool_kers_body');
		// Choose the element and save the PDF for our user.
		html2pdf().from(element).save();
		setTimeout(
		  function() 
		  {
			$('.colResFirst').removeClass('col-5');
			$('.colResFirst').addClass('col-6');
			$('.colResSecond').removeClass('col-5');
			$('.colResSecond').addClass('col-6');
			$('.colResSecond').removeAttr('style');
			$('.btn').show();
			$('#ref_title_pdf').hide();
			$('#masInfo_coche_combus').hide();        	
			$('#masInfo_circuito_combus').hide();        	
		    //do something special
		  }, 1300);
	}
</script>
</html>