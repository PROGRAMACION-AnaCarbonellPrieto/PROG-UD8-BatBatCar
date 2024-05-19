/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.menu.opcines;

import es.progcipfpbatoi.controller.ViajesController;
import es.progcipfpbatoi.views.GestorIO;

/**
 *
 * @author Ana Carbonell Prieto
 */
public class OpcionSalir extends Opcion {

    public OpcionSalir() {
        super("Salir");
    }

    @Override
    public void ejecutar(ViajesController viajesController) {
        GestorIO.print("Adios!");
        setFinalizar(true);
    }
}
