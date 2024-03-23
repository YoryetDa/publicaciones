package com.publicacion.publicacion;

public class Comentario {

    // ID único del comentario.
    private int id;

    // ID de la publicación a la que este comentario pertenece.
    private int publicacionId;

    // Nombre o identificador del autor del comentario.
    private String autor;

    // Mensaje o contenido del comentario.
    private String mensaje;
    
    // Calificación asociada al comentario.
    private int nota; 


    /**
     * Constructor vacío.
     * Es necesario para la creación de instancias mediante reflexión,
     * lo que es común en frameworks como Spring o cuando se deserializa desde JSON.
     */
    public Comentario() {
    }

    /**
     * Constructor con parámetros para facilitar la creación de instancias de Comentario.
     * 
     * @param id El ID único del comentario.
     * @param publicacionId El ID de la publicación a la cual el comentario está asociado.
     * @param autor El autor del comentario.
     * @param mensaje El contenido del comentario.
     * @param nota La calificación del comentario
     */
    public Comentario(int id, int publicacionId, String autor, String mensaje, int nota) {
        this.id = id;
        this.publicacionId = publicacionId;
        this.autor = autor;
        this.mensaje = mensaje;
        this.nota = nota;

    }

    // Métodos getters y setters para acceder y modificar los campos de la instancia.

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublicacionId() {
        return publicacionId;
    }

    public void setPublicacionId(int publicacionId) {
        this.publicacionId = publicacionId;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }
}
