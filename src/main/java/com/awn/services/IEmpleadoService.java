package com.awn.services;

import com.awn.dto.*;
import com.awn.exception.DatosNoCorrectosException;
import java.util.List;

public interface IEmpleadoService {

	boolean actualizarEmpleado(EmpleadoDTO empleadoDTO)
			throws DatosNoCorrectosException;

	List<EmpleadoDTO> obtenerEmpleados() throws DatosNoCorrectosException;

	List<EmpleadoDTO> obtenerEmpleadosFiltrados(String nombre, String dni, String sexo, Integer categoria, Integer anyos)
			throws DatosNoCorrectosException;

	Integer obtenerSalario(String dni);

	EmpleadoDTO obtenerEmpleado(String dni) throws DatosNoCorrectosException;
}
