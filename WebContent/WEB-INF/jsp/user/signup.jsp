<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
	<title>Vote No Filme - Cadastro</title>
	<link href="<c:url value='/css/bootstrap.min.css'/>" media="all" rel="stylesheet" type="text/css" />
	<link href="<c:url value='/css/movie.css'/>" media="all" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="<c:url value='/js/jquery-2.0.2.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/bootstrap.js'/>"></script>

</head>
<body>
	<h2>Cadastro</h2>

	<c:if test="${not empty message}">
		<div class="alert alert-error">
			Desculpe, encontramos um erro em sua solicitação: ${message}
		</div>
	</c:if>

	<form action="<c:url value='/usuario/salvar'/>">
		<table style="width: 400px">
			<tr>
				<td>
					<label for="username">Nome</label>
				</td>
				<td>
					<input type="text" name="user.name" id="username" value="${user.name}"/>
				</td>
			</tr>
			<tr>
				<td>
					<label for="email">E-mail</label>
				</td>
				<td>
					<input type="text" name="user.email" id="email" value="${user.email}"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right">
					<input type="submit" class="btn btn-success" value="Concluir"/>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>