<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Filmes Cadastrados</title>
	<link href="<c:url value='/css/bootstrap.min.css'/>" media="all" rel="stylesheet" type="text/css" />
	<link href="<c:url value='/css/movie.css'/>" media="all" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<c:url value='/js/jquery-2.0.2.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bootstrap.js'/>"></script>
</head>
<body>
	<h2>Filmes Cadastrados</h2>
	<c:forEach items="${list}" var="movie">
		<div class="movie-list-item">
	    	<img src="${movie.image}" class="movie-art"/>
	    	<h4 class="movie-title">${movie.name}</h4>
	    	<span class="movie-description">${movie.description}</span>
		</div>
	</c:forEach>
</body>
</html>