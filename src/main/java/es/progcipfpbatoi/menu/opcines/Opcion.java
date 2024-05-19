/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.menu.opcines;

import es.progcipfpbatoi.controller.ViajesController;
import es.progcipfpbatoi.exceptions.UsuarioSinEstablecerException;
import es.progcipfpbatoi.views.GestorIO;

/**
 *
 * @author Ana Carbonell Prieto
 */
public abstract class Opcion {
    private String titulo;
    private boolean finalizar;

    public Opcion(String titulo) {
        this.titulo = titulo;
        this.finalizar = false;
    }
    
    public void mostrar(int numOpcion) {
        GestorIO.print(String.format(numOpcion + "." + this.titulo));
    }
    
    public abstract void ejecutar(ViajesController viajesController) throws UsuarioSinEstablecerException;

    public boolean finalizar() {
        return finalizar;
    }

    public void setFinalizar(boolean finalizado) {
        this.finalizar = finalizado;
    }
}
