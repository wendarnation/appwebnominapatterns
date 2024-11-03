package com.awn.dto;

public class EmpleadoDTO {

    private String nombre;
    private String dni;
    private char sexo;
    private Integer categoria;
    private Integer anyos;

    public EmpleadoDTO() {
        
    }

    public EmpleadoDTO(String nombre, String dni, char sexo, Integer categoria, Integer anyos) {
        this.nombre = nombre;
        this.dni = dni;
        this.sexo = sexo;
        this.categoria = categoria;
        this.anyos = anyos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public char getSexo() {
        return sexo;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public Integer getAnyos() {
        return anyos;
    }

    public void setAnyos(Integer anyos) {
        this.anyos = anyos;
    }

    @Override
    public String toString() {
        return "EmpleadoDTO{" +
                "nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                ", sexo='" + sexo + '\'' +
                ", categoria=" + categoria +
                ", anyos=" + anyos +
                '}';
    }
}
