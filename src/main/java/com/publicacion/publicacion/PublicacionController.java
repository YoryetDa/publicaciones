package com.publicacion.publicacion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {

    private final List<Publicacion> publicaciones = new ArrayList<>();

    public PublicacionController() {
        // Añadir ejemplos de publicaciones con comentarios
        publicaciones.add(new Publicacion(1, "Cómo hacer pan en casa", "Una guía detallada para hacer pan casero."));
        publicaciones.add(new Publicacion(2, "Top 10 rutas de senderismo", "Descubre las mejores rutas de senderismo del mundo."));
        publicaciones.add(new Publicacion(3, "El futuro de la IA", "Explorando cómo la Inteligencia Artificial cambiará el mundo."));
        publicaciones.add(new Publicacion(4, "Viaje a Marte: Lo que necesitas saber", "Todo sobre la próxima misión tripulada a Marte."));
        publicaciones.add(new Publicacion(5, "La revolución de la comida vegana", "Cómo la comida vegana está ganando popularidad."));
        publicaciones.add(new Publicacion(6, "Aprende a programar en Python", "Una introducción amigable al lenguaje Python."));
        publicaciones.add(new Publicacion(7, "Historias de éxito: Emprendedores que inspiran", "Conoce las historias detrás de algunos de los emprendedores más exitosos."));
        publicaciones.add(new Publicacion(8, "Los secretos de la fotografía nocturna", "Técnicas y consejos para mejorar tus fotografías nocturnas."));
        publicaciones.add(new Publicacion(9, "El arte de la meditación", "Beneficios y técnicas para introducirte en la meditación."));
        publicaciones.add(new Publicacion(10, "Guía de supervivencia en la naturaleza", "Consejos esenciales para sobrevivir en la naturaleza."));

        // Añadir comentarios a algunas publicaciones
        publicaciones.get(0).getComentarios().add(new Comentario(1, 1, "Juan", "¡Muy útil, gracias!",7));
        publicaciones.get(1).getComentarios().add(new Comentario(2, 2, "Ana", "He visitado dos de estas rutas y son increíbles.",5));
        publicaciones.get(2).getComentarios().add(new Comentario(3, 3, "Carlos", "La IA es definitivamente el futuro.",6));
        publicaciones.get(2).getComentarios().add(new Comentario(4, 3, "Yoryet", "La IA quitara nuestros empleos",1));
        
        // Añadir comentarios adicionales con diversas notas a la publicación con ID 1
        publicaciones.get(0).getComentarios().add(new Comentario(5, 1, "Marta", "Excelente receta, fácil de seguir.", 8));
        publicaciones.get(0).getComentarios().add(new Comentario(6, 1, "Pedro", "No me salió como esperaba.", 4));
        publicaciones.get(0).getComentarios().add(new Comentario(7, 1, "Luisa", "¡Perfecto para principiantes!", 9));

        // Añadir comentarios adicionales con diversas notas a la publicación con ID 2
        publicaciones.get(1).getComentarios().add(new Comentario(8, 2, "Ana", "Increíble ruta, vistas hermosas.", 10));
        publicaciones.get(1).getComentarios().add(new Comentario(9, 2, "Jorge", "Un poco exigente para mí.", 6));
        publicaciones.get(1).getComentarios().add(new Comentario(10, 2, "Clara", "Buen ejercicio y hermosos paisajes.", 7));

        // Añadir comentarios adicionales con diversas notas a la publicación con ID 3
        publicaciones.get(2).getComentarios().add(new Comentario(11, 3, "Sofía", "Muy interesante, quiero saber más.", 8));
        publicaciones.get(2).getComentarios().add(new Comentario(12, 3, "Manuel", "Preocupante pero fascinante.", 7));

    }
    // Este método devuelve todas las publicaciones sin el cálculo del promedio de calificaciones.

    @GetMapping
    public List<Publicacion> getPublicaciones() {
        return publicaciones;
    }

    // Devuelve una publicación específica por su ID con sus comentarios y calificaciones, incluyendo el promedio de calificaciones
    @GetMapping("/{id}")
    public ResponseEntity<?> getPublicacionDetalladaPorId(@PathVariable int id) {
        PublicacionDetallada publicacionDetallada = publicaciones.stream()
            .filter(publicacion -> publicacion.getId() == id)
            .map(this::convertirAPublicacionDetallada)
            .findFirst()
            .orElse(null);

        if (publicacionDetallada == null) {
            // Si no se encuentra la publicación, se retorna un mensaje de error con el estado 404 Not Found
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("La publicación con el ID " + id + " no existe.");
        }

        // Si se encuentra la publicación, se retorna con el estado 200 OK
        return ResponseEntity.ok(publicacionDetallada);
    }
    // Convierte una publicación a PublicacionDetallada, que incluye el promedio de calificaciones
    private PublicacionDetallada convertirAPublicacionDetallada(Publicacion publicacion) {
        double promedio = publicacion.calcularPromedioCalificaciones();
        return new PublicacionDetallada(publicacion, promedio);
    }
    // Clase interna para manejar la representación detallada de una publicación, incluyendo su promedio de calificaciones
    private static class PublicacionDetallada {
        private Publicacion publicacion;
        private double promedioCalificaciones;

        PublicacionDetallada(Publicacion publicacion, double promedioCalificaciones) {
            this.publicacion = publicacion;
            this.promedioCalificaciones = promedioCalificaciones;
        }

        // Getters
        public Publicacion getPublicacion() {
            return publicacion;
        }

        public double getPromedioCalificaciones() {
            return promedioCalificaciones;
        }
    }
}