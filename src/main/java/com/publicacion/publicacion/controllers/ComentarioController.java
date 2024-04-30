package com.publicacion.publicacion.controllers;

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

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import com.publicacion.publicacion.models.Comentario;
import com.publicacion.publicacion.repositories.ComentarioRepository;
import com.publicacion.publicacion.repositories.PublicacionRepository;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/publicaciones/{publicacionId}/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Crear un comentario en una publicación específica
    @PostMapping
    public ResponseEntity<EntityModel<Comentario>> addComentarioToPublicacion(@PathVariable Long publicacionId, @RequestBody Comentario comentario) {
        return publicacionRepository.findById(publicacionId).map(publicacion -> {
            comentario.setPublicacion(publicacion);
            Comentario savedComentario = comentarioRepository.save(comentario);
            return ResponseEntity.ok(EntityModel.of(savedComentario,
                linkTo(methodOn(ComentarioController.class).addComentarioToPublicacion(publicacionId, comentario)).withSelfRel(),
                linkTo(methodOn(ComentarioController.class).getAllComentariosByPublicacionId(publicacionId)).withRel("all-comments"),
                linkTo(methodOn(PublicacionController.class).getPublicacionById(publicacionId)).withRel("publicacion")));
        }).orElse(ResponseEntity.notFound().build());
    }
    // Listar todos los comentarios de una publicación específica
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Comentario>>> getAllComentariosByPublicacionId(@PathVariable Long publicacionId) {
        if (!publicacionRepository.existsById(publicacionId)) {
            return ResponseEntity.notFound().build();
        }
        List<EntityModel<Comentario>> comentarios = comentarioRepository.findByPublicacionId(publicacionId).stream()
            .map(comentario -> EntityModel.of(comentario,
                linkTo(methodOn(ComentarioController.class).updateComentario(publicacionId, comentario.getId(), comentario)).withRel("update"),
                linkTo(methodOn(ComentarioController.class).deleteComentario(publicacionId, comentario.getId())).withRel("delete")))
            .collect(Collectors.toList());
        return ResponseEntity.ok(CollectionModel.of(comentarios,
            linkTo(methodOn(ComentarioController.class).getAllComentariosByPublicacionId(publicacionId)).withSelfRel()));
    }

    // Actualizar un comentario específico
    @PutMapping("/{comentarioId}")
    public ResponseEntity<EntityModel<Comentario>> updateComentario(@PathVariable Long publicacionId, @PathVariable Long comentarioId, @RequestBody Comentario comentarioDetails) {
        if (!publicacionRepository.existsById(publicacionId)) {
            return ResponseEntity.notFound().build();
        }
        return comentarioRepository.findById(comentarioId).map(comentario -> {
            comentario.setMensaje(comentarioDetails.getMensaje());
            comentario.setNota(comentarioDetails.getNota());
            comentarioRepository.save(comentario);
            return ResponseEntity.ok(EntityModel.of(comentario,
                linkTo(methodOn(ComentarioController.class).updateComentario(publicacionId, comentarioId, comentario)).withSelfRel(),
                linkTo(methodOn(ComentarioController.class).deleteComentario(publicacionId, comentarioId)).withRel("delete"),
                linkTo(methodOn(ComentarioController.class).getAllComentariosByPublicacionId(publicacionId)).withRel("all-comments")));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Eliminar un comentario
    @DeleteMapping("/{comentarioId}")
    public ResponseEntity<?> deleteComentario(@PathVariable Long publicacionId, @PathVariable Long comentarioId) {
        if (!publicacionRepository.existsById(publicacionId)) {
            return ResponseEntity.notFound().build();
        }
        return comentarioRepository.findById(comentarioId).map(comentario -> {
            comentarioRepository.delete(comentario);
            return ResponseEntity.ok().build();  // Consider using ResponseEntity.noContent().build() for DELETE operations.
        }).orElse(ResponseEntity.notFound().build());
}
}
