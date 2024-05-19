package es.progcipfpbatoi;

/**
 * Clase principal.
 * 
 */

import es.progcipfpbatoi.menu.Menu;
import es.progcipfpbatoi.menu.opcines.OpcionAnyadirViaje;
import es.progcipfpbatoi.menu.opcines.OpcionBuscarViaje;
import es.progcipfpbatoi.menu.opcines.OpcionCancelarReserva;
import es.progcipfpbatoi.menu.opcines.OpcionCancelarViaje;
import es.progcipfpbatoi.menu.opcines.OpcionLogin;
import es.progcipfpbatoi.menu.opcines.OpcionModificarReserva;
import es.progcipfpbatoi.menu.opcines.OpcionMostrarViajes;
import es.progcipfpbatoi.menu.opcines.OpcionRealizarReserva;
import es.progcipfpbatoi.menu.opcines.OpcionSalir;

public class BatBatCar {

    public static void main(String[] args) {
        Menu menu = new Menu("BatBatCar");
        menu.anyadir(new OpcionLogin());
        menu.anyadir(new OpcionMostrarViajes());
        menu.anyadir(new OpcionAnyadirViaje());
        menu.anyadir(new OpcionCancelarViaje());
        menu.anyadir(new OpcionRealizarReserva());
        menu.anyadir(new OpcionModificarReserva());
        menu.anyadir(new OpcionCancelarReserva());
        menu.anyadir(new OpcionBuscarViaje());
        menu.anyadir(new OpcionSalir());
        menu.ejecutar();
    }
}
