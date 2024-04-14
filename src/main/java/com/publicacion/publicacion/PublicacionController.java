package com.publicacion.publicacion;

// Importaciones necesarias para el funcionamiento del controlador
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.publicacion.publicacion.models.Publicacion;
import com.publicacion.publicacion.repositories.PublicacionRepository;

import java.util.List;
import java.util.Optional;

// Anotación para indicar que esta clase es un controlador REST
@RestController
// Ruta base para todos los endpoints en este controlador
@RequestMapping("/publicaciones")
public class PublicacionController {

    // Inyección del repositorio que gestiona las operaciones de base de datos para las publicaciones
    @Autowired
    private PublicacionRepository publicacionRepository;

    // Endpoint para obtener todas las publicaciones almacenadas
    @GetMapping
    public List<Publicacion> getAllPublicaciones() {
        return publicacionRepository.findAll();  // Consulta todas las publicaciones usando JPA
    }

    // Endpoint para obtener una publicación específica por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPublicacionById(@PathVariable Long id) {
        // Intenta encontrar la publicación por ID
        Optional<Publicacion> publicacion = publicacionRepository.findById(id);

        // Si la publicación existe, calcula el promedio de calificaciones y devuelve los detalles
        if (publicacion.isPresent()) {
            Publicacion actualPublicacion = publicacion.get();
            double promedio = actualPublicacion.calcularPromedioCalificaciones();  // Calcula el promedio de calificaciones
            return ResponseEntity.ok(new PublicacionDetallada(actualPublicacion, promedio));  // Retorna la publicación y su promedio
        } else {
            return ResponseEntity.notFound().build();  // Si no se encuentra, retorna un 404 Not Found
        }
    }

    // Clase interna para formatear la respuesta con detalles de una publicación y su promedio de calificaciones
    private static class PublicacionDetallada {
        private Publicacion publicacion;
        private double promedioCalificaciones;

        PublicacionDetallada(Publicacion publicacion, double promedioCalificaciones) {
            this.publicacion = publicacion;
            this.promedioCalificaciones = promedioCalificaciones;
        }

        public Publicacion getPublicacion() {
            return publicacion;
        }

        public double getPromedioCalificaciones() {
            return promedioCalificaciones;
        }
    }

    // Endpoint para crear una nueva publicación
    @PostMapping
    public Publicacion createPublicacion(@RequestBody Publicacion publicacion) {
        return publicacionRepository.save(publicacion);  // Guarda la nueva publicación en la base de datos
    }

    // Endpoint para actualizar una publicación existente
    @PutMapping("/{id}")
    public ResponseEntity<Publicacion> updatePublicacion(@PathVariable Long id, @RequestBody Publicacion publicacionDetails) {
        Optional<Publicacion> publicacionData = publicacionRepository.findById(id);  // Busca la publicación por ID

        if (publicacionData.isPresent()) {
            Publicacion updatedPublicacion = publicacionData.get();  // Obtiene la publicación existente
            updatedPublicacion.setTitulo(publicacionDetails.getTitulo());  // Actualiza el título
            updatedPublicacion.setContenido(publicacionDetails.getContenido());  // Actualiza el contenido
            publicacionRepository.save(updatedPublicacion);  // Guarda los cambios en la base de datos
            return ResponseEntity.ok(updatedPublicacion);  // Retorna la publicación actualizada
        } else {
            return ResponseEntity.notFound().build();  // Si no se encuentra, retorna un 404 Not Found
        }
    }

    // Endpoint para eliminar una publicación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePublicacion(@PathVariable Long id) {
        try {
            publicacionRepository.deleteById(id);  // Elimina la publicación por ID
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Retorna un 204 No Content
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);  // Maneja posibles errores internos
        }
    }
}
