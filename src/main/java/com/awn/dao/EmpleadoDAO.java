package com.awn.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.awn.conexion.Conexion;
import com.awn.model.Empleado;
import com.awn.exception.DatosNoCorrectosException;

public class EmpleadoDAO implements IEmpleadoDAO {

    @Override
    public boolean actualizarEmpleado(Empleado empleado) {
        String sql = "UPDATE EMPLEADOS SET nombre = ?, sexo = ?, categoria = ?, anyos = ? WHERE dni = ?";
        
		try (Connection con = Conexion.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, empleado.getNombre());
            st.setString(2, String.valueOf(empleado.getSexo()));
            st.setInt(3, empleado.getCategoria());
            st.setInt(4, empleado.getAnyos());
            st.setString(5, empleado.getDni());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean actualizarSalario(String dni, double nuevoSalario) {
        String sql = "UPDATE nominas SET sueldoFinal = ? WHERE dni = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
             
            st.setDouble(1, nuevoSalario);
            st.setString(2, dni);

            int rowsUpdated = st.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Empleado> obtenerEmpleados() throws DatosNoCorrectosException {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM EMPLEADOS";
        try (Connection con = Conexion.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                empleados.add(new Empleado(rs.getString("nombre"), rs.getString("dni"),
                        rs.getString("sexo").charAt(0), rs.getInt("categoria"), rs.getInt("anyos")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public List<Empleado> obtenerEmpleadosFiltrados(String nombre, String dni, String sexo, Integer categoria, Integer anyos) throws DatosNoCorrectosException {
        List<Empleado> empleados = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM EMPLEADOS WHERE 1=1");
        try (Connection con = Conexion.getConnection();
             PreparedStatement st = prepareFiltradoStatement(con, sql, nombre, dni, sexo, categoria, anyos)) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                empleados.add(new Empleado(rs.getString("nombre"), rs.getString("dni"),
                        rs.getString("sexo").charAt(0), rs.getInt("categoria"), rs.getInt("anyos")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    private PreparedStatement prepareFiltradoStatement(Connection con, StringBuilder sql, String nombre, String dni,
                                                      String sexo, Integer categoria, Integer anyos) throws SQLException {
        int paramIndex = 1;
        if (nombre != null && !nombre.trim().isEmpty()) sql.append(" AND nombre LIKE ?");
        if (dni != null && !dni.trim().isEmpty()) sql.append(" AND dni LIKE ?");
        if (sexo != null && !sexo.trim().isEmpty()) sql.append(" AND sexo = ?");
        if (categoria != null) sql.append(" AND categoria = ?");
        if (anyos != null) sql.append(" AND anyos = ?");
        PreparedStatement st = con.prepareStatement(sql.toString());
        if (nombre != null && !nombre.trim().isEmpty()) st.setString(paramIndex++, "%" + nombre + "%");
        if (dni != null && !dni.trim().isEmpty()) st.setString(paramIndex++, "%" + dni + "%");
        if (sexo != null && !sexo.trim().isEmpty()) st.setString(paramIndex++, sexo);
        if (categoria != null) st.setInt(paramIndex++, categoria);
        if (anyos != null) st.setInt(paramIndex, anyos);
        return st;
    }

    @Override
    public Integer obtenerSalario(String dni) {
        String sql = "SELECT sueldoFinal FROM NOMINAS WHERE dni = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, dni);
            ResultSet rs = st.executeQuery();
            if (rs.next()) return rs.getInt("sueldoFinal");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int modificarEmpleado(String dni, String campo, String valor) {
        String sql = "UPDATE EMPLEADOS SET " + campo + " = ? WHERE DNI = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, valor);
            st.setString(2, dni);
            return st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Empleado obtenerEmpleado(String dni) throws DatosNoCorrectosException {
        String sql = "SELECT * FROM EMPLEADOS WHERE dni = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, dni);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Empleado(rs.getString("nombre"), rs.getString("dni"),
                        rs.getString("sexo").charAt(0), rs.getInt("categoria"), rs.getInt("anyos"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
