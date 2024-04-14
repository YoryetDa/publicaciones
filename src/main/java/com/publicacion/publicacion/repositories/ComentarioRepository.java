package com.publicacion.publicacion.repositories;

import com.publicacion.publicacion.models.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPublicacionId(Long publicacionId);

}
