package com.publicacion.publicacion.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComentarioTest {

    @Test
    public void testGettersAndSetters() {
        Comentario comentario = new Comentario();
        comentario.setId(1L);
        comentario.setAutor("Juan");
        comentario.setMensaje("Muy interesante");
        comentario.setNota(5);

        Publicacion publicacion = new Publicacion();
        comentario.setPublicacion(publicacion);

        assertEquals(1L, comentario.getId());
        assertEquals("Juan", comentario.getAutor());
        assertEquals("Muy interesante", comentario.getMensaje());
        assertEquals(5, comentario.getNota());
        assertEquals(publicacion, comentario.getPublicacion());
    }
}
