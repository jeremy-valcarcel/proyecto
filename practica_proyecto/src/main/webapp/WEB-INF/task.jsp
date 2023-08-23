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
	<div class="col-mt-5 m-5">
		<div class="d-flex justify-content-between align-items-center mb-4">
			<h1>
				Project:
				<c:out value="${proyecto.title}"></c:out>
			</h1>
			<a href="/dashboard" class="btn btn-outline-danger">Back to
				Dashboard</a>
		</div>
		<p class="fs-6">
			Project Lead:
			<c:out value="${proyecto.encargado.nombre}"></c:out>
		</p>

		<div class="row">
			<div class="col-md-6">
				<div class="card mb-3">
					<div class="card-header">Muro de Taks</div>
					<div class="card-body message-wall">
						<ul class="list-group message-list">
							<c:forEach var="tak" items="${proyecto.tasks}">
								<li class="list-group-item"><strong>Added by
										${tak.autor.nombre} ${tak.autor.apellido} at <fmt:formatDate
											value="${tak.createdAt}" pattern="HH:mm MMM d"
											var="fechaFormateada" /> <c:out value="${fechaFormateada}:"></c:out>
								</strong> ${tak.tareas}.</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<form action="/projects/${proyecto.id }/tasks" method="post">
					<div class="form-group m-3">
						<label for="task">Add a Task ticket for this team:</label>
						<textarea name="task" id="task" class="form-control"></textarea>
						<div class="text-danger">
							<c:out value="${errores}"></c:out>
						</div>
						<button type="submit" class="btn btn-primary my-3">Enviar
							Taks</button>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>