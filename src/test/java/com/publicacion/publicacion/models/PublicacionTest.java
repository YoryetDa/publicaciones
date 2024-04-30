package com.publicacion.publicacion.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class PublicacionTest {

    @Test
    public void gettersAndSettersTest() {
        Publicacion publicacion = new Publicacion();
        publicacion.setId(1L);
        publicacion.setTitulo("Test Title");
        publicacion.setContenido("Test Content");

        Comentario comentario = new Comentario();
        comentario.setId(1L);
        comentario.setAutor("Juan");
        comentario.setMensaje("Muy buen artículo");
        comentario.setNota(5);

        List<Comentario> comentarios = new ArrayList<>();
        comentarios.add(comentario);
        publicacion.setComentarios(comentarios);

        assertEquals(1L, publicacion.getId());
        assertEquals("Test Title", publicacion.getTitulo());
        assertEquals("Test Content", publicacion.getContenido());
        assertEquals(1, publicacion.getComentarios().size());
        assertEquals(comentario, publicacion.getComentarios().get(0));
    }

    @Test
    public void calcularPromedioCalificacionesTest() {
        Publicacion publicacion = new Publicacion();
        Comentario comentario1 = new Comentario();
        comentario1.setNota(5);
        Comentario comentario2 = new Comentario();
        comentario2.setNota(3);

        List<Comentario> comentarios = new ArrayList<>();
        comentarios.add(comentario1);
        comentarios.add(comentario2);
        publicacion.setComentarios(comentarios);

        double expected = 4.0;
        double actual = publicacion.calcularPromedioCalificaciones();
        assertEquals(expected, actual, 0.001, "El promedio calculado debe ser correcto");
    }

    @Test
    public void calcularPromedioCalificacionesConListaVaciaTest() {
        Publicacion publicacion = new Publicacion();
        publicacion.setComentarios(new ArrayList<>()); // Lista vacía de comentarios

        double expected = 0.0;
        double actual = publicacion.calcularPromedioCalificaciones();
        assertEquals(expected, actual, 0.001, "El promedio con lista vacía debe ser 0.0");
    }
}
