package com.publicacion.publicacion.models;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "publicaciones")
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String contenido;

    @OneToMany(mappedBy = "publicacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comentario> comentarios = new ArrayList<>();

    public Publicacion() {
    }

    // Getters y setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public List<Comentario> getComentarios() { return comentarios; }
    public void setComentarios(List<Comentario> comentarios) { this.comentarios = comentarios; }

    // Método para calcular el promedio de las calificaciones
    public double calcularPromedioCalificaciones() {
        if (comentarios.isEmpty()) {
            return 0.0; // Retorna 0.0 si no hay comentarios para evitar división por cero
        }
        double suma = comentarios.stream()
                                 .mapToInt(Comentario::getNota)
                                 .sum();
        return suma / (double) comentarios.size();
    }
}
