<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Vote No Filme</title>
</head>
<body>
	Movies
	<br/>
	<ul>
		<c:forEach items="${list}" var="movie">
	    	<li>
	    	<img src="${movie.image}"/>
	    	${movie.name} - ${movie.description} 
	    	</li>
		</c:forEach>
	</ul>
</body>
</html>