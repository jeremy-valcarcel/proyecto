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

	<div class="container my-5">
		<h1>
			Welcome,
			<c:out value="${usuario.nombre }"></c:out>
		</h1>
		<div class="text-end">
			<a href="/logout" class="btn btn-outline-dark">Logout</a>
		</div>

		<a href="/project/new" class="btn btn-outline-warning">Create new
			project</a>

		<hr>

		<!-- Mostrar proyectos en los que el usuario no está participando -->
		<h3>Available Projects:</h3>
		<table class="table border table-striped">
			<thead>
				<tr>
					<th>Project</th>
					<th>Team Lead</th>
					<th>Due Date</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${proyectosNoParticipando}" var="proyect">
					<c:if test="${proyect.encargado.id != usuario.id}">
						<tr>
							<td><a href="/project/${proyect.id}"><c:out
										value="${proyect.title}"></c:out></a></td>
							<td><c:out value="${proyect.encargado.nombre}"></c:out></td>
							<td><fmt:formatDate value="${proyect.date}"
									pattern="MMMM dd, yyyy" var="fechaFormateada" /> <c:out
									value="${fechaFormateada}"></c:out></td>
							<td><a href="/project/${proyect.id}/${usuario.id}/unirse"
								class="btn btn-outline-dark">Join Team</a></td>
						</tr>
					</c:if>
				</c:forEach>
			</tbody>
		</table>

		<h3>Your Projects:</h3>
		<table class="table border table-striped">
			<thead>
				<tr>
					<th>Project</th>
					<th>Lead</th>
					<th>Due Date</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${proyectosParticipando}" var="proyec">
					<tr>
						<td><a href="/project/${proyec.id}"><c:out
									value="${proyec.title}"></c:out></a></td>
						<td><c:out value="${proyec.encargado.nombre}"></c:out></td>
						<td><fmt:formatDate value="${proyec.date}"
								pattern="MMMM dd, yyyy" var="fechaFormateada" /> <c:out
								value="${fechaFormateada}"></c:out></td>
						<td><c:if test="${proyec.encargado.id == usuario.id}">
								<a href="/project/edit/${proyec.id}"
									class="btn btn-outline-dark">Edit</a>
							</c:if> <c:if test="${proyec.encargado.id != usuario.id}">
								<a href="/project/${proyec.id}/${usuario.id}/cancelar"
									class="btn btn-outline-dark">Leave team</a>
							</c:if></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>

</body>
</html>