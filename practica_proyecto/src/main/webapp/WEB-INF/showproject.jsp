<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!-- c:out ; c:forEach etc. -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- Formateo fechas (dates) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- Formulario form:form -->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!-- para errores de renderizado en rutas PUT -->
<%@ page isErrorPage="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>AQUI EL TITULO DE TU PLNATILLA</title>
<!-- BOOTSTRAP  -->
<link rel="stylesheet"
	href="/webjars/bootstrap/5.2.3/css/bootstrap.min.css">
<script type="text/javascript" src="/js/app.js"></script>
<!-- cambiar para que coincida con Tu archivo/estructura de nombres -->
<link rel="stylesheet" href="/css/main.css">
<script type="text/javascript" src="/js/app.js"></script>

</head>
<body>

	<a href="/dashboard">back to dashboard</a>

	<h1>detalles del project</h1>

	<table class="table border table-striped">
		<thead>
			<tr>
				<th>Project</th>
				<th><c:out value="${proyecto.title}"></c:out></th>
			</tr>
			<tr>
				<th>description</th>
				<th><c:out value="${proyecto.description}"></c:out></th>
			</tr>
			<tr>
				<th>Due Date</th>
				<th><fmt:formatDate value="${proyecto.date}"
						pattern="MMMM dd, yyyy" var="fechaFormateada" /> <c:out
						value="${fechaFormateada}"></c:out></th>
			</tr>
		</thead>
		<tbody>
			<tr>



			</tr>
		</tbody>
	</table>
	<c:if test="${proyecto.encargado.id == usuario.id}">
		<form action="/project/${proyecto.id}/delete" method="post">
			<input type="hidden" name="_method" value="delete" />
			<button class="btn btn-outline-dark">Delete</button>
		</form>
	</c:if>

	
	
		<c:choose>
			<c:when test="${proyecto.ayudantes.contains(usuario)}">
				<a href="/project/${proyecto.id}/${usuario.id}/cancelar"
					class="btn btn-outline-dark"> Elevan team </a>
			</c:when>
			<c:otherwise>
				<a href="/project/${proyecto.id}/${usuario.id}/unirse"
					class="btn btn-outline-dark"> Join team </a>
			</c:otherwise>
		</c:choose>
		
		<a href="/projects/${proyecto.id}/tasks">See tasks</a>
	




</body>
</html>