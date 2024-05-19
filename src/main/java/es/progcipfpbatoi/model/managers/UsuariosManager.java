/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.model.managers;

import es.progcipfpbatoi.exceptions.CredencialesInvalidasException;
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
    
    public Usuario comprobarCredenciales(String nombre, String contrasenya) throws CredencialesInvalidasException {
        for (Usuario usuario: this.usuarios) {
            if (usuario.comprobarNombre(nombre) && usuario.comprobarContrasenya(contrasenya)) return usuario;
        }
        throw new CredencialesInvalidasException();
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
