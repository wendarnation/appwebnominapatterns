package com.awn.services;

import com.awn.dao.IEmpleadoDAO;
import com.awn.dto.EmpleadoDTO;
import com.awn.model.*;
import com.awn.exception.DatosNoCorrectosException;
import java.util.List;
import java.util.stream.Collectors;

public class EmpleadoService implements IEmpleadoService {

    private final IEmpleadoDAO empleadoDAO;

    public EmpleadoService(IEmpleadoDAO empleadoDAO) {
        this.empleadoDAO = empleadoDAO;
    }

    @Override
    public boolean actualizarEmpleado(EmpleadoDTO empleadoDTO) throws DatosNoCorrectosException {

        Empleado empleado = new Empleado(
            empleadoDTO.getNombre(),
            empleadoDTO.getDni(),
            empleadoDTO.getSexo(),
            empleadoDTO.getCategoria(),
            empleadoDTO.getAnyos()
        );
        boolean empleadoActualizado = empleadoDAO.actualizarEmpleado(empleado);

        double nuevoSalario = calcularSalario(empleadoDTO.getCategoria(), empleadoDTO.getAnyos());

        boolean salarioActualizado = empleadoDAO.actualizarSalario(empleadoDTO.getDni(), nuevoSalario);

        return empleadoActualizado && salarioActualizado;
    }

    @Override
    public List<EmpleadoDTO> obtenerEmpleados() throws DatosNoCorrectosException {
        return empleadoDAO.obtenerEmpleados().stream()
                .map(emp -> new EmpleadoDTO(
                        emp.getNombre(),
                        emp.getDni(),
                        emp.getSexo(),
                        emp.getCategoria(),
                        emp.getAnyos()))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmpleadoDTO> obtenerEmpleadosFiltrados(String nombre, String dni, String sexo, Integer categoria, Integer anyos)
            throws DatosNoCorrectosException {
        return empleadoDAO.obtenerEmpleadosFiltrados(nombre, dni, sexo, categoria, anyos).stream()
                .map(emp -> new EmpleadoDTO(
                        emp.getNombre(),
                        emp.getDni(),
                        emp.getSexo(),
                        emp.getCategoria(),
                        emp.getAnyos()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer obtenerSalario(String dni) {
        return empleadoDAO.obtenerSalario(dni);
    }

    @Override
    public EmpleadoDTO obtenerEmpleado(String dni) throws DatosNoCorrectosException {
        Empleado empleado = empleadoDAO.obtenerEmpleado(dni);
        if (empleado == null) {
            throw new DatosNoCorrectosException("Empleado no encontrado");
        }
        return new EmpleadoDTO(
                empleado.getNombre(),
                empleado.getDni(),
                empleado.getSexo(),
                empleado.getCategoria(),
                empleado.getAnyos()
        );
    }
    
    private double calcularSalario(Integer categoria, Integer anyos) {
        int[] SUELDO_BASE = {50000, 70000, 90000, 110000, 130000, 150000, 170000, 190000, 210000, 230000};
        double salario = SUELDO_BASE[categoria - 1] + 5000 * anyos;
        return salario;
    }
}
