package com.publicacion.publicacion.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.isA;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.publicacion.publicacion.models.Comentario;
import com.publicacion.publicacion.models.Publicacion;
import com.publicacion.publicacion.repositories.ComentarioRepository;
import com.publicacion.publicacion.repositories.PublicacionRepository;

import java.util.Optional;

@WebMvcTest(ComentarioController.class)
public class ComentarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComentarioRepository comentarioRepository;

    @MockBean
    private PublicacionRepository publicacionRepository;

    @Test
    public void testAddComentarioToPublicacion() throws Exception {
        // 1. Preparación de los objetos de dominio
        Publicacion publicacion = new Publicacion();
        publicacion.setId(1L);
        Comentario comentario = new Comentario();
        comentario.setId(1L);
        comentario.setMensaje("Muy testing");
        comentario.setNota(5);
    
        // 2. Configuración de Mocks
        when(publicacionRepository.findById(1L)).thenReturn(Optional.of(publicacion));
        when(comentarioRepository.save(isA(Comentario.class))).thenReturn(comentario);
    
        // 3. Ejecución del test
        mockMvc.perform(post("/publicaciones/{publicacionId}/comentarios", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"mensaje\":\"Muy testing\", \"nota\":5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("Muy testing"));
    }
}
