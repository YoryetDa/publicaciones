package com.publicacion.publicacion.repositories;

import com.publicacion.publicacion.models.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepository extends JpaRepository<Publicacion, Long> {
}
