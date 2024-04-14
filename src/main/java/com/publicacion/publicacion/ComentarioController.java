package com.publicacion.publicacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.publicacion.publicacion.models.Comentario;
import com.publicacion.publicacion.repositories.ComentarioRepository;
import com.publicacion.publicacion.repositories.PublicacionRepository;
import java.util.*;

@RestController
@RequestMapping("/publicaciones/{publicacionId}/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    // Crear un comentario en una publicación específica
    @PostMapping
    public ResponseEntity<Comentario> addComentarioToPublicacion(@PathVariable Long publicacionId, @RequestBody Comentario comentario) {
        return publicacionRepository.findById(publicacionId).map(publicacion -> {
            comentario.setPublicacion(publicacion);
            comentarioRepository.save(comentario);
            return ResponseEntity.ok(comentario);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Listar todos los comentarios de una publicación específica
    @GetMapping
    public ResponseEntity<List<Comentario>> getAllComentariosByPublicacionId(@PathVariable Long publicacionId) {
        if (!publicacionRepository.existsById(publicacionId)) {
            return ResponseEntity.notFound().build();
        }
        List<Comentario> comentarios = comentarioRepository.findByPublicacionId(publicacionId);
        return ResponseEntity.ok(comentarios);
    }

    // Actualizar un comentario específico
    @PutMapping("/{comentarioId}")
    public ResponseEntity<Comentario> updateComentario(@PathVariable Long publicacionId, @PathVariable Long comentarioId, @RequestBody Comentario comentarioDetails) {
        if (!publicacionRepository.existsById(publicacionId)) {
            return ResponseEntity.notFound().build();
        }
        return comentarioRepository.findById(comentarioId).map(comentario -> {
            comentario.setMensaje(comentarioDetails.getMensaje());
            comentario.setNota(comentarioDetails.getNota());
            comentarioRepository.save(comentario);
            return ResponseEntity.ok(comentario);
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
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
