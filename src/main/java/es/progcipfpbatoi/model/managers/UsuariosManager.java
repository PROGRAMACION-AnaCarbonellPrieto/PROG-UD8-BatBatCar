/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.model.managers;

import es.progcipfpbatoi.model.entities.Usuario;
import es.progcipfpbatoi.views.GestorIO;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Ana Carbonell Prieto
 */
public class UsuariosManager {
    private List<Usuario> usuarios;

    public UsuariosManager() {
        this.usuarios = new ArrayList<>();
        init();
    }
    
    public Usuario comprobarExistenciaUsuario(String nombre) {
        for (Usuario usuario: this.usuarios) {
            if (usuario.comprobarNombre(nombre)) return usuario;
        }
        return null;
    }
    
    public boolean comprobarContrasenyaUsuario(Usuario usuario, String contrasenya) {
        return usuario.comprobarContrasenya(contrasenya);
    }
    
    private void init() {
        this.usuarios.add(new Usuario("ana14", "1234"));
        this.usuarios.add(new Usuario("sergio123", "1234"));
        this.usuarios.add(new Usuario("roberto1979", "1234"));
        this.usuarios.add(new Usuario("raul00", "1234"));
        this.usuarios.add(new Usuario("alex32", "1234"));
        this.usuarios.add(new Usuario("maria456", "1234"));
        this.usuarios.add(new Usuario("elena12", "1234"));
    }
}
