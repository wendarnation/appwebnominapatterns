package com.awn.dao;

import com.awn.model.Empleado;
import com.awn.exception.DatosNoCorrectosException;
import java.util.List;

public interface IEmpleadoDAO {
    boolean actualizarEmpleado(Empleado empleado) throws DatosNoCorrectosException;
    List<Empleado> obtenerEmpleados() throws DatosNoCorrectosException;
    List<Empleado> obtenerEmpleadosFiltrados(String nombre, String dni, String sexo, Integer categoria, Integer anyos)
            throws DatosNoCorrectosException;
    Integer obtenerSalario(String dni);
    int modificarEmpleado(String dni, String campo, String valor);
    Empleado obtenerEmpleado(String dni) throws DatosNoCorrectosException;
    boolean actualizarSalario(String dni, double nuevoSalario);
}
