<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Vote No Filme</title>
	<link href="<c:url value='/css/bootstrap.min.css'/>" media="all" rel="stylesheet" type="text/css" />
	<link href="<c:url value='/css/movie.css'/>" media="all" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<c:url value='/js/jquery-2.0.2.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bootstrap.js'/>"></script>
	<script type="text/javascript">
		function vote(id) {
			var field = document.getElementById("selected");
			field.value = id;
			document.forms[0].submit();
		}
	</script>

</head>
<body>
	<h2>Escolha seu Filme Preferido</h2>

	<div class="pagination">
	  <ul>
	  	<c:forEach var="step" begin="1" end="${totalSteps}">
	  		<c:choose>
		  		<c:when test="${step < currentStep}">
			  		<li class="disabled"><span>${step}</span></li>
		  		</c:when>
		  		<c:when test="${step == currentStep}">
			  		<li class="enabled current-step"><span>${step}</span></li>
		  		</c:when>
		  		<c:otherwise>
			  		<li class="enabled"><span>${step}</span></li>
		  		</c:otherwise>
	  		</c:choose>
		</c:forEach>
	    <li class="disabled"><span>Resultados</span></li>
	  </ul>
	</div>

	<c:if test="${not empty message}">
		<div class="alert alert-error">
			Desculpe, encontramos um erro em sua solicitação: ${message}
		</div>
	</c:if>

	<form action="votar">

		<div class="box box-left">
	    	<img src="${movie1.image}" class="movie-art"/>
	    	<h4 class="movie-title">${movie1.name}</h4>
	    	<span class="movie-description">${movie1.description}</span>
	    	<input type="button" class="vote-btn btn btn-success" onclick="vote(${movie1.id})" value="Votar"/>
		</div>
		<div  class="box box-right">
	    	<img src="${movie2.image}" class="movie-art"/>
	    	<h4 class="movie-title">${movie2.name}</h4>
	    	<span class="movie-description">${movie2.description}</span>
    		<input type="button" class="vote-btn btn btn-success" onclick="vote(${movie2.id})" value="Votar"/>
		</div>

		<input type="hidden" name="selected" id="selected" value=""/>
	</form>

</body>
</html>