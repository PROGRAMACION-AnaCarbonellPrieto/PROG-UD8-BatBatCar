package es.progcipfpbatoi.menu;

import es.progcipfpbatoi.controller.ViajesController;
import es.progcipfpbatoi.views.GestorIO;

/**
 * Clase que gestiona el menú de opciones. A partir de esta clase se ejecutan
 * las diferentes opciones del menú (casos de uso).
 * @author batoi
 */

public class Menu {

    private static final int OPCION_SALIR = 9;
    
    private ViajesController viajesController;
    
    public Menu() {
        this.viajesController = new ViajesController();
    }

    public void iniciar() {
        int opcionSeleccionada;
        
        do {     
            mostrarOpciones();
            opcionSeleccionada = solicitarOpcion();
            ejecutarOpcion(opcionSeleccionada);
        } while (opcionSeleccionada != OPCION_SALIR);
        
    }

    private void mostrarOpciones() {
        GestorIO.print("\nBatBatCar");
        GestorIO.print("=========");
        GestorIO.print("1.Establecer ususario (login)");
        GestorIO.print("2.Mostrar todos los viajes");
        GestorIO.print("3.Añadir viaje");
        GestorIO.print("4.Cancelar viaje");
        GestorIO.print("5.Realizar reserva");
        GestorIO.print("6.Modificar reserva");
        GestorIO.print("7.Cancelar reserva");
        GestorIO.print("8.Buscar viaje y realizar reserva");
        GestorIO.print("9.Salir");
    }
    
    private int solicitarOpcion() {
        return GestorIO.getInt(
                String.format("Seleccione una opción [%d-%d]", 1, OPCION_SALIR),
                1, OPCION_SALIR
        );
    }
    
    private void ejecutarOpcion(int opcionSeleccionada) {
        switch (opcionSeleccionada) {
            case 1 -> viajesController.iniciarSesion();
            case 2 -> viajesController.listarViajes();
            case 3 -> viajesController.anyadirViaje();
            case 4 -> viajesController.cancelarViaje();
            case 5 -> viajesController.reservarViaje();
            case 6 -> viajesController.modificarReserva();
            case 7 -> viajesController.cancelarReserva();
            case 8 -> viajesController.buscarYReservarViaje();
            case OPCION_SALIR -> GestorIO.print("Adios!");
        }
    }

}
