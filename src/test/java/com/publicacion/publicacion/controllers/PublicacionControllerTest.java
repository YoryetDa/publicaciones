package com.publicacion.publicacion.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.publicacion.publicacion.models.Publicacion;
import com.publicacion.publicacion.repositories.PublicacionRepository;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(PublicacionController.class)
public class PublicacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PublicacionRepository publicacionRepository;

    @Test
    public void getAllPublicacionesTest() throws Exception {
        // Arrange
        Publicacion publicacion = new Publicacion();
        publicacion.setId(1L);
        publicacion.setTitulo("Test Publicacion");
        publicacion.setContenido("Contenido de prueba");
        List<Publicacion> allPublicaciones = Arrays.asList(publicacion);

        when(publicacionRepository.findAll()).thenReturn(allPublicaciones);

        // Act & Assert
        mockMvc.perform(get("/publicaciones")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titulo", is("Test Publicacion")))
                .andExpect(jsonPath("$[0].contenido", is("Contenido de prueba")));
    }
}
