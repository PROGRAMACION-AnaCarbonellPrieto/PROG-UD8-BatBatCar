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
public class OpcionBuscarViaje extends Opcion {
    
    public OpcionBuscarViaje() {
        super("Buscar viaje y realizar reserva");
    }

    @Override
    public void ejecutar(ViajesController viajesController) throws UsuarioSinEstablecerException {
        viajesController.buscarYReservarViaje();
    }
}
