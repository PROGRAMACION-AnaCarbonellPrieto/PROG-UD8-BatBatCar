/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.menu.opcines;

import es.progcipfpbatoi.controller.ViajesController;
import es.progcipfpbatoi.exceptions.MaximoIntentosAlcanzadosException;
import es.progcipfpbatoi.views.GestorIO;

/**
 *
 * @author Ana Carbonell Prieto
 */
public class OpcionLogin extends Opcion {

    public OpcionLogin() {
        super("Establecer ususario (login)");
    }

    @Override
    public void ejecutar(ViajesController viajesController) {
        try {
            viajesController.iniciarSesion();
        } catch (MaximoIntentosAlcanzadosException e) {
            GestorIO.print(e.getMessage());
            setFinalizar(true);
        }
    }
}
