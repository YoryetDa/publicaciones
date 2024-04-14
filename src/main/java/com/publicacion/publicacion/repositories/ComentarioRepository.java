package com.publicacion.publicacion.repositories;

import com.publicacion.publicacion.models.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
}
