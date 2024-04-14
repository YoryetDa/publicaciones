package com.publicacion.publicacion;

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

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionRepository publicacionRepository;
    // Obtener todas las publicaciones
    @GetMapping
    public List<Publicacion> getAllPublicaciones() {
        return publicacionRepository.findAll();
    }
    // Obtener una publicación por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getPublicacionById(@PathVariable Long id) {
        Optional<Publicacion> publicacion = publicacionRepository.findById(id);

        if (publicacion.isPresent()) {
            Publicacion actualPublicacion = publicacion.get();
            double promedio = actualPublicacion.calcularPromedioCalificaciones();
            return ResponseEntity.ok(new PublicacionDetallada(actualPublicacion, promedio));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Clase interna para manejar la representación detallada de una publicación
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
    // Crear una nueva publicación
    @PostMapping
    public Publicacion createPublicacion(@RequestBody Publicacion publicacion) {
        return publicacionRepository.save(publicacion);
    }
    // Actualizar una publicación existente
    @PutMapping("/{id}")
    public ResponseEntity<Publicacion> updatePublicacion(@PathVariable Long id, @RequestBody Publicacion publicacionDetails) {
        Optional<Publicacion> publicacionData = publicacionRepository.findById(id);

        if (publicacionData.isPresent()) {
            Publicacion updatedPublicacion = publicacionData.get();
            updatedPublicacion.setTitulo(publicacionDetails.getTitulo());
            updatedPublicacion.setContenido(publicacionDetails.getContenido());
            publicacionRepository.save(updatedPublicacion);
            return ResponseEntity.ok(updatedPublicacion);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Eliminar una publicación
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePublicacion(@PathVariable Long id) {
        try {
            publicacionRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}