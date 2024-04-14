package com.publicacion.publicacion;
import com.publicacion.publicacion.models.Comentario;

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
        if (this.comentarios.isEmpty()) {
            return 0.0; // Si no hay comentarios, retorna 0.0 para evitar división por cero
        }
        double suma = this.comentarios.stream()
                                      .mapToInt(Comentario::getNota) // Asegúrate de que Comentario tiene un método getNota()
                                      .sum();
        return suma / this.comentarios.size();
    }
}