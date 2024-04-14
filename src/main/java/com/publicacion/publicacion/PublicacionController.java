package com.publicacion.publicacion;
import com.publicacion.publicacion.models.Publicacion;

// Importaciones necesarias para el funcionamiento del controlador
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @GetMapping //publicaciones
    public List<Publicacion> getAllPublicaciones() {
        List<Publicacion> listaPublicaciones = publicacionRepository.findAll();
        // Se asegura de calcular el promedio para cada publicación antes de devolverlas
        listaPublicaciones.forEach(publicacion -> publicacion.calcularPromedioCalificaciones());
        return listaPublicaciones;
    }

    // Endpoint para obtener una publicación específica por su ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPublicacionById(@PathVariable Long id) {
        Optional<Publicacion> publicacion = publicacionRepository.findById(id);
        if (publicacion.isPresent()) {
            Publicacion actualPublicacion = publicacion.get();
            double promedio = actualPublicacion.calcularPromedioCalificaciones(); // Calcula el promedio de calificaciones.
            return ResponseEntity.ok(new PublicacionDetallada(actualPublicacion, promedio)); // Retorna la publicación y su promedio.
        } else {
            return ResponseEntity.notFound().build(); // Si no se encuentra, retorna un 404 Not Found.
        }
    }
    // Clase auxiliar para representar la publicación con detalles adicionales
    private static class PublicacionDetallada {
        private Publicacion publicacion;
        private double promedioCalificaciones;

        public PublicacionDetallada(Publicacion publicacion, double promedioCalificaciones) {
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
     // Endpoint para actualizar una publicación existente
    @PutMapping("/{id}")
    public ResponseEntity<Publicacion> updatePublicacion(@PathVariable Long id, @RequestBody Publicacion publicacionDetails) {
        // Intenta encontrar la publicación existente por ID
        Optional<Publicacion> publicacionData = publicacionRepository.findById(id);

        if (publicacionData.isPresent()) {
            Publicacion updatedPublicacion = publicacionData.get();
            // Actualiza el título y el contenido de la publicación con los valores recibidos
            updatedPublicacion.setTitulo(publicacionDetails.getTitulo());
            updatedPublicacion.setContenido(publicacionDetails.getContenido());
            // Guarda la publicación actualizada en la base de datos
            publicacionRepository.save(updatedPublicacion);
            // Retorna la publicación actualizada con código de estado 200 OK
            return ResponseEntity.ok(updatedPublicacion);
        } else {
            // Retorna un código de estado 404 Not Found si la publicación no se encuentra
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint para eliminar una publicación por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePublicacion(@PathVariable Long id) {
        try {
            // Intenta eliminar la publicación por ID
            publicacionRepository.deleteById(id);
            // Retorna un código de estado 204 No Content si la eliminación es exitosa
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            // Retorna un código de estado 500 Internal Server Error si ocurre algún error durante la eliminación
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Crear publicacion 
    @PostMapping 
    public ResponseEntity<Publicacion> createPublicacion(@RequestBody Publicacion newPublicacion) {
        try {
            // Guarda la nueva publicación en la base de datos
            Publicacion savedPublicacion = publicacionRepository.save(newPublicacion);
            // Retorna la publicación guardada con un código de estado 201 Created
            return new ResponseEntity<>(savedPublicacion, HttpStatus.CREATED);
        } catch (Exception e) {
            // Retorna un código de estado 500 Internal Server Error si ocurre algún error durante la creación
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
