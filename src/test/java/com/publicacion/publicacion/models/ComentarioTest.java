package com.publicacion.publicacion.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComentarioTest {

    @Test
    public void testGettersAndSetters() {
        Comentario comentario = new Comentario();
        comentario.setId(1L);
        comentario.setAutor("Juan");
        comentario.setMensaje("Muy testing");
        comentario.setNota(6);

        Publicacion publicacion = new Publicacion();
        comentario.setPublicacion(publicacion);

        assertEquals(1L, comentario.getId());
        assertEquals("Juan", comentario.getAutor());
        assertEquals("Muy testing", comentario.getMensaje());
        assertEquals(6, comentario.getNota());
        assertEquals(publicacion, comentario.getPublicacion());
    }
}
