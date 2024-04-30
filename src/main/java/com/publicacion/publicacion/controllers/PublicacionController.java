package com.publicacion.publicacion.controllers;
import com.publicacion.publicacion.models.Publicacion;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.publicacion.publicacion.repositories.PublicacionRepository;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


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
    public ResponseEntity<CollectionModel<EntityModel<Publicacion>>> getAllPublicaciones() {
        List<EntityModel<Publicacion>> publicaciones = publicacionRepository.findAll().stream()
            .map(publicacion -> EntityModel.of(publicacion,
                    linkTo(methodOn(PublicacionController.class).getPublicacionById(publicacion.getId())).withSelfRel(),
                    linkTo(methodOn(PublicacionController.class).deletePublicacion(publicacion.getId())).withRel("delete"),
                    linkTo(methodOn(PublicacionController.class).updatePublicacion(publicacion.getId(), publicacion)).withRel("update")))
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(CollectionModel.of(publicaciones,
            linkTo(methodOn(PublicacionController.class).getAllPublicaciones()).withSelfRel()));
    }

    // Endpoint para obtener una publicación específica por su ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PublicacionDetallada>> getPublicacionById(@PathVariable Long id) {
        Optional<Publicacion> publicacion = publicacionRepository.findById(id);
        return publicacion.map(p -> {
            PublicacionDetallada detallada = new PublicacionDetallada(p, p.calcularPromedioCalificaciones());
            EntityModel<PublicacionDetallada> resource = EntityModel.of(detallada,
                linkTo(methodOn(PublicacionController.class).getPublicacionById(id)).withSelfRel(),
                linkTo(methodOn(PublicacionController.class).updatePublicacion(id, p)).withRel("update"),
                linkTo(methodOn(PublicacionController.class).deletePublicacion(id)).withRel("delete"));
            return ResponseEntity.ok(resource);
        }).orElse(ResponseEntity.notFound().build());
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
     public ResponseEntity<EntityModel<Publicacion>> updatePublicacion(@PathVariable Long id, @RequestBody Publicacion publicacionDetails) {
         Optional<Publicacion> publicacionData = publicacionRepository.findById(id);
     
         return publicacionData.map(publicacion -> {
             publicacion.setTitulo(publicacionDetails.getTitulo());
             publicacion.setContenido(publicacionDetails.getContenido());
             publicacionRepository.save(publicacion);
             EntityModel<Publicacion> resource = EntityModel.of(publicacion,
                 linkTo(methodOn(PublicacionController.class).updatePublicacion(id, publicacion)).withSelfRel(),
                 linkTo(methodOn(PublicacionController.class).getPublicacionById(id)).withRel("self"),
                 linkTo(methodOn(PublicacionController.class).deletePublicacion(id)).withRel("delete"));
             return ResponseEntity.ok(resource);
         }).orElse(ResponseEntity.notFound().build());
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
    public ResponseEntity<EntityModel<Publicacion>> createPublicacion(@RequestBody Publicacion newPublicacion) {
        try {
            Publicacion savedPublicacion = publicacionRepository.save(newPublicacion);
            EntityModel<Publicacion> resource = EntityModel.of(savedPublicacion,
                linkTo(methodOn(PublicacionController.class).createPublicacion(newPublicacion)).withSelfRel(),
                linkTo(methodOn(PublicacionController.class).getPublicacionById(savedPublicacion.getId())).withRel("self"));
            return new ResponseEntity<>(resource, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
