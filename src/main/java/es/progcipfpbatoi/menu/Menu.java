package es.progcipfpbatoi.menu;

import es.progcipfpbatoi.menu.opcines.Opcion;
import es.progcipfpbatoi.controller.ViajesController;
import es.progcipfpbatoi.exceptions.UsuarioSinEstablecerException;
import es.progcipfpbatoi.views.GestorIO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que gestiona el menú de opciones. A partir de esta clase se ejecutan
 * las diferentes opciones del menú (casos de uso).
 * @author batoi
 */

public class Menu {
    private String titulo;
    private List<Opcion> opciones;
    private ViajesController viajesController;
    
    public Menu(String titulo) {
        this.titulo = titulo;
        this.opciones = new ArrayList<>();
        this.viajesController = new ViajesController();
    }
    
    public void anyadir(Opcion opcion) {
        this.opciones.add(opcion);
    }
    
    private void mostrar() {
        GestorIO.print("\n" + this.titulo + "\n=========");
        for (int i = 0; i < this.opciones.size(); i++) {
            this.opciones.get(i).mostrar(i + 1);
        }
        
    }
    
    private Opcion getOpcion() {
        int opcionSeleccionada = GestorIO.getInt(
                String.format("Seleccione una opción [%d-%d]", 1, this.opciones.size()),
                1, this.opciones.size()
        );
        return this.opciones.get(opcionSeleccionada - 1);
    }

    public void ejecutar() {
        Opcion opcionSeleccionada;
        
        do {     
            mostrar();
            opcionSeleccionada = getOpcion();
            try {
                opcionSeleccionada.ejecutar(this.viajesController);
            } catch (UsuarioSinEstablecerException e) {
                GestorIO.print(e.getMessage());
            }
        } while (!opcionSeleccionada.finalizar());
        
    }

}
