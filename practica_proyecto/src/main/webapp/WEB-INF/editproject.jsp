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
<link rel="stylesheet" href="/webjars/bootstrap/5.2.3/css/bootstrap.min.css">
<script type="text/javascript" src="/js/app.js"></script>
<!-- cambiar para que coincida con Tu archivo/estructura de nombres -->
<link rel="stylesheet" href="/css/main.css">
<script type="text/javascript" src="/js/app.js"></script>

</head>
<body>
	<div class="container my-3">
	<a href="/dashboard" class="btn btn-outline-warning col-1">go back</a>
	<br />
	<h3> Edit project</h3>
		<div class="col-6">
			<form:form method="POST" action="/project/edit/${proyecto.id}"
				modelAttribute="proyecto">
				<input type="hidden" name="_method" value="put" class="form-control"/>
				<form:input type="hidden" path="encargado" value="${usuario.id}" />
				<div>
					<form:label path="title">Project Title:</form:label>
					<form:input type="text" path="title" class="form-control"/>
					<form:errors class="text-danger" path="title" />
				</div>
				<div>
					<form:label path="description">Description:</form:label>
					<form:input type="text" path="description" class="form-control"/>
					<form:errors class="text-danger" path="description" />
				</div>
				<div>
					<form:label path="date">Project date:</form:label>
					<form:input type="date" path="date" class="form-control"/>
					<form:errors class="text-danger" path="date" />
				</div>
				<input type="submit" value="Edit Project!" />
			</form:form>
		</div>
	</div>

</body>
</html>