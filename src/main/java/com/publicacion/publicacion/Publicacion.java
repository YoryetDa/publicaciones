package com.publicacion.publicacion;

import java.util.ArrayList;
import java.util.List;
public class Publicacion {

    private int id;
    private String titulo;
    private String contenido;
    private List<Comentario> comentarios = new ArrayList<>();
    private List<Integer> calificaciones = new ArrayList<>();

    // Constructor vacío
    public Publicacion() {
    }

    // Constructor con parámetros
    public Publicacion(int id, String titulo, String contenido) {
        this.id = id;
        this.titulo = titulo;
        this.contenido = contenido;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<Integer> getCalificaciones() {
        return calificaciones;
    }

    public void setCalificaciones(List<Integer> calificaciones) {
        this.calificaciones = calificaciones;
    }

    // Método para calcular el promedio de las calificaciones
    // calcula el promedio de las calificaciones guardads en calificaciones,
    // suma de todas las calificaciones dividida por la cantidad.
    public double calcularPromedioCalificaciones() {
        if (comentarios.isEmpty()) {
            return 0.0; // si la variable esta vacia que devuelva 0.0 (tolerancia a errores)
        }
        double suma = comentarios.stream()
                                 .mapToInt(Comentario::getNota)
                                 .sum();
        return suma / comentarios.size();
    }
}