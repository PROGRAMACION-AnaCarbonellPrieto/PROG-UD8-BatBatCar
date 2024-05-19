/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.menu.opcines;

import es.progcipfpbatoi.controller.ViajesController;
import es.progcipfpbatoi.exceptions.UsuarioSinEstablecerException;

/**
 *
 * @author Ana Carbonell Prieto
 */
public class OpcionCancelarViaje extends Opcion {
    
    public OpcionCancelarViaje() {
        super("Cancelar viaje");
    }

    @Override
    public void ejecutar(ViajesController viajesController) throws UsuarioSinEstablecerException {
        viajesController.cancelarViaje();
    }
}
