<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Vote No Filme</title>
	<link href="css/movie.css" media="all" rel="stylesheet" type="text/css" />
	<script type="text/javascript">
		function vote(id) {
			var field = document.getElementById("selected");
			field.value = id;
			document.forms[0].submit();
		}
	</script>

</head>
<body>
	Movies
	<br/>
	<form action="votar">
		<div class="box-left">
	    	<img src="${movie1.image}" class="movieArt"/>
	    	<span class="movieTitle">${movie1.name}</span>
	    	<span class="movieDescription">${movie1.description}</span>
	    	<input type="button" onclick="vote(${movie1.id})" value="Votar"/>
		</div>
		<div  class="box-right">
	    	<img src="${movie2.image}" class="movieArt"/>
	    	<span class="movieTitle">${movie2.name}</span>
	    	<span class="movieDescription">${movie2.description}</span>
    		<input type="button" onclick="vote(${movie2.id})" value="Votar"/>
		</div>
	
		<input type="hidden" name="vote.selected" id="selected" value=""/>
		<input type="hidden" name="vote.movie1" value="${movie1.id}"/>
		<input type="hidden" name="vote.movie2" value="${movie2.id}"/>
	</form>
</body>
</html>