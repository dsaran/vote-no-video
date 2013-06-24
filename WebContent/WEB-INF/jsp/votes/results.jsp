<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Vote No Filme</title>
	<link href="<c:url value='/css/bootstrap.min.css'/>" media="all" rel="stylesheet" type="text/css" />
	<link href="<c:url value='/css/movie.css'/>" media="all" rel="stylesheet" type="text/css" />
	<script src="<c:url value='/js/amcharts.js'/>" type="text/javascript"></script>
	<script type="text/javascript" src="<c:url value='/js/jquery-2.0.2.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bootstrap.js'/>"></script>
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script type="text/javascript">
    google.load('visualization', '1.0', {'packages':['corechart']});

	var options = {backgroundColor: "#F2F2F2", chartArea:{width:"80%"}, colors: ["#252422"], legend: {position: "none"}, isHtml: true };

	function drawGlobalChart() {
      	var data = new google.visualization.DataTable();
		data.addColumn('string', 'Nome');
		data.addColumn('number', 'Votos');
		//Set chart data
		data.addRows([
			<c:forEach items="${globalResults}" var="summary">
			["${summary.movie.name}", ${summary.count}],
			</c:forEach>
		]);
		data.sort([{column: 1, desc: true}]);

		var chart = new google.visualization.BarChart(document.getElementById('globalresults'));
		chart.draw(data, options);
    }
	function drawUserChart() {
      	var data = new google.visualization.DataTable();
		data.addColumn('string', 'Nome');
		data.addColumn('number', 'Votos');
		//Set chart data
		data.addRows([
			<c:forEach items="${userResults}" var="summary">
			["${summary.movie.name}", ${summary.count}],
			</c:forEach>
		]);
		data.sort([{column: 1, desc: true}]);

		var chart = new google.visualization.BarChart(document.getElementById('userresults'));
		chart.draw(data, options);
    }
    function drawCharts() {
    	drawGlobalChart();
    	drawUserChart();
   	}
    // Set a callback to run when the Google Visualization API is loaded.
    google.setOnLoadCallback(drawCharts);
	</script>
</head>

<body>
	<c:if test="${not empty message}">
		<div class="alert alert-error">
			Desculpe, encontramos um erro em sua solicitação: ${message}
		</div>
	</c:if>

	<h2>Resultado Geral</h2>
	<div id="globalresults" class="result-chart box"></div>


	<h2>Seus Resultados</h2>
	<div id="userresults" class="result-chart box"></div>

</body>
</html>
