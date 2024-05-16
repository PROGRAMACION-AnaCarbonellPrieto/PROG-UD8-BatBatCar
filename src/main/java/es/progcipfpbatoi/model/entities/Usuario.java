package es.progcipfpbatoi.model.entities;

/**
 *
 * Clase que representa a un usuario de la aplicación
 */
public class Usuario {
    private String nombre;
    private String contrasenya;

    public Usuario(String nombre, String contrasenya) {
        this.nombre = nombre;
        this.contrasenya = contrasenya;
    }

    public String getNombre() {
        return this.nombre;
    }
    
    public boolean comprobarContrasenya(String contrasenya) {
        return this.contrasenya.equals(contrasenya);
    }

    public boolean comprobarNombre(String nombre) {
        return this.nombre.equals(nombre);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        
        if (obj == null || obj.getClass() != getClass()) return false;
        
        Usuario usuario = (Usuario) obj;
        return usuario.nombre.equals(this.nombre);
    }
}
