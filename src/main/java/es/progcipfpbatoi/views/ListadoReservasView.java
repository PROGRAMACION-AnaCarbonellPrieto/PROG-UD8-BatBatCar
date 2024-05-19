/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.views;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import es.progcipfpbatoi.model.entities.Reserva;
import es.progcipfpbatoi.model.entities.Usuario;
import es.progcipfpbatoi.model.entities.types.Viaje;
import java.util.List;

/**
 *
 * @author Ana Carbonell Prieto
 */
public class ListadoReservasView {
    private final List<Viaje> viajes;
    private final Usuario usuario;

    private static final int ANCHO_TABLA = 100;

    public ListadoReservasView(List<Viaje> viajes, Usuario usuario) {
        this.viajes = viajes;
        this.usuario = usuario;
    }

    private AsciiTable buildASCIITable() {
        AsciiTable view = new AsciiTable();
        view.addRule();
        view.addRow("*", "*", "*", "*", "*", "*");
        view.addRule();
        view.addRow(null, null, null, null, null, "Reservas de viajes");
        view.addRule();
        view.addRow("Cod. Reserva", "Cod. Viaje", "Propietario Viaje", null, "Plazas Reservadas", "Fecha");
        view.addRule();
        generarFilasReservas(view);
        view.setTextAlignment(TextAlignment.CENTER);
        return view;
    }

    @Override
    public String toString() {
        return buildASCIITable().render(ANCHO_TABLA);
    }

    public void visualizar() {
        System.out.println(buildASCIITable().render(ANCHO_TABLA));
    }

    private void generarFilasReservas(AsciiTable tabla) {
        for (Viaje viaje: viajes) {
            for (Reserva reserva: viaje.getReservas()) {
                if (!reserva.getUsuario().equals(this.usuario)) continue;
                
                tabla.addRow(
                        reserva.getCodReserva(),
                        viaje.getCodViaje(),
                        viaje.getPropietario().getNombre(),
                        null,
                        reserva.getPlazas(),
                        reserva.getFechaFormateada()
                );
                tabla.addRule();
            }
                
        }
    }
}
