<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.awn.dto.EmpleadoDTO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
EmpleadoDTO empleadoDTO = (EmpleadoDTO) request.getAttribute("empleado");
%>
<html>
<head>
<title>Modificar Empleado</title>
<link rel="stylesheet" type="text/css"
	href="<c:url value='/css/modificarEmpleado.css'/>">
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">

</head>
<body>
	<div class="container">
		<div class="h1Container">
			<h1>Modificar Empleado</h1>
		</div>

		<div class="formContainer">
			<form action="empresa" method="post">
				<input type="hidden" name="dni" value="<%=empleadoDTO.getDni()%>">

				<label for="nombre">Nombre:</label> <input type="text" name="nombre"
					id="nombre" value="<%=empleadoDTO.getNombre()%>">
				<label for="sexo">Sexo:</label> <select name="sexo" id="sexo">
					<option value="" <%=empleadoDTO.getSexo() == ' ' ? "selected" : ""%>>Cualquiera</option>
					<option value="M" <%=empleadoDTO.getSexo() == 'M' ? "selected" : ""%>>Masculino</option>
					<option value="F" <%=empleadoDTO.getSexo() == 'F' ? "selected" : ""%>>Femenino</option>
				</select>
				 <label for="categoria">Categoría:</label> <input
					type="number" name="categoria" id="categoria"
					value="<%=empleadoDTO.getCategoria()%>">
					 <label
					for="antiguedad">Años Trabajados:</label> <input type="number"
					name="anyos" id="antiguedad" value="<%=empleadoDTO.getAnyos()%>"> <input type="hidden" name="opcion" value="enviarCambios">
				<input type="submit" value="Enviar"> <a
					href="empresa?opcion=inicio"><i class="fas fa-arrow-left"></i>
					Volver al Inicio</a>
			</form>
		</div>
	</div>



</body>
</html>