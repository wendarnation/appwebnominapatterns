package com.awn.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.awn.services.IEmpleadoService;
import com.awn.services.EmpleadoService;
import com.awn.dto.EmpleadoDTO;
import com.awn.exception.DatosNoCorrectosException;
import com.awn.dao.*;

@WebServlet("/empresa")
public class EmpleadoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IEmpleadoService empleadoService;

	public EmpleadoController() {
		super();
		IEmpleadoDAO empleadoDAO = new EmpleadoDAO();
		this.empleadoService = new EmpleadoService(empleadoDAO);
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String opcion = request.getParameter("opcion");

		if ("mostrarEmpleados".equals(opcion)) {
			mostrarEmpleados(request, response);
		} else if ("buscarSalario".equals(opcion)) {
			buscarSalario(request, response);
		} else if ("buscarEmpleados".equals(opcion)) {
			buscarEmpleados(request, response);
		} else if ("inicio".equals(opcion)) {
			inicio(response);
		} else {
			paginaNoEncontrada(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String opcion = request.getParameter("opcion");

		if ("enviarCambios".equals(opcion)) {
			enviarCambios(request, response);
		} else if ("modificarEmpleado".equals(opcion)) {
			modificarEmpleado(request, response);
		} else if ("mostrarEmpleadosFiltrados".equals(opcion)) {
			mostrarEmpleadosFiltrados(request, response);
		} else if ("mostrarSalario".equals(opcion)) {
			mostrarSalario(request, response);
		} else {
			paginaNoEncontrada(request, response);
		}
	}

	private void mostrarEmpleados(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<EmpleadoDTO> empleados = null;
		try {
			empleados = empleadoService.obtenerEmpleados();
		} catch (DatosNoCorrectosException e) {
			e.printStackTrace();
		}
		request.setAttribute("empleados", empleados);
		request.getRequestDispatcher("/views/mostrarEmpleados.jsp").forward(request, response);
	}

	private void buscarSalario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/views/buscarSalario.jsp").forward(request, response);
	}

	private void mostrarSalario(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String dni = request.getParameter("dni");
		Integer salario = empleadoService.obtenerSalario(dni);
		request.setAttribute("salario", salario);
		request.setAttribute("dni", dni);
		request.getRequestDispatcher("/views/mostrarSalario.jsp").forward(request, response);
	}

	private void buscarEmpleados(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/views/buscarEmpleados.jsp").forward(request, response);
	}

	private void mostrarEmpleadosFiltrados(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nombre = request.getParameter("nombre");
		String dni = request.getParameter("dni");
		String sexo = request.getParameter("sexo");
		String categoriaStr = request.getParameter("categoria");
		String anyosStr = request.getParameter("anyos");

		List<EmpleadoDTO> empleados = null;

		try {
			Integer categoria = categoriaStr != null && !categoriaStr.isEmpty() ? Integer.parseInt(categoriaStr) : null;
			Integer anyos = anyosStr != null && !anyosStr.isEmpty() ? Integer.parseInt(anyosStr) : null;

			empleados = empleadoService.obtenerEmpleadosFiltrados(nombre, dni, sexo, categoria, anyos);
		} catch (DatosNoCorrectosException | NumberFormatException e) {
			e.printStackTrace();
		}

		request.setAttribute("empleados", empleados);
		request.getRequestDispatcher("/views/mostrarEmpleados.jsp").forward(request, response);
	}

	private void modificarEmpleado(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String dni = request.getParameter("dni");
		EmpleadoDTO empleado = null;

		try {
			empleado = empleadoService.obtenerEmpleado(dni);
		} catch (DatosNoCorrectosException e) {
			e.printStackTrace();
		}
		request.setAttribute("empleado", empleado);
		request.getRequestDispatcher("/views/modificarEmpleado.jsp").forward(request, response);
	}

	private void enviarCambios(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String dni = request.getParameter("dni");
		String nombre = request.getParameter("nombre");
		String sexoStr = request.getParameter("sexo");
		String categoriaStr = request.getParameter("categoria");
		String anyosStr = request.getParameter("anyos");

		try {
			Integer categoria = categoriaStr != null && !categoriaStr.trim().isEmpty() ? Integer.parseInt(categoriaStr) : null;
			Integer anyos = anyosStr != null && !anyosStr.trim().isEmpty() ? Integer.parseInt(anyosStr) : null;

			char sexo = sexoStr != null && sexoStr.length() == 1 ? sexoStr.charAt(0) : ' ';
			
			EmpleadoDTO empleadoDTO = new EmpleadoDTO(nombre, dni, sexo, categoria, anyos);
			if (empleadoService.actualizarEmpleado(empleadoDTO)) {
				response.sendRedirect("empresa?opcion=mostrarEmpleados&exito=true");
			} else {
				request.setAttribute("mensaje", "Datos no soportados");
				request.getRequestDispatcher("/views/error.jsp").forward(request, response);
			}
		} catch (DatosNoCorrectosException | NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("mensaje", "Error en los datos ingresados");
			request.getRequestDispatcher("/views/error.jsp").forward(request, response);
		}
	}

	private void inicio(HttpServletResponse response) throws IOException {
		response.sendRedirect("index.jsp");
	}

	private void paginaNoEncontrada(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("mensaje", "Página no encontrada");
		request.getRequestDispatcher("/views/error.jsp").forward(request, response);
	}
}
