/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.menu.opcines;

import es.progcipfpbatoi.controller.ViajesController;

/**
 *
 * @author Ana Carbonell Prieto
 */
public class OpcionMostrarViajes extends Opcion {

    public OpcionMostrarViajes() {
        super("Mostrar todos los viajes");
    }

    @Override
    public void ejecutar(ViajesController viajesController) {
        viajesController.listarViajes();
    }
}
