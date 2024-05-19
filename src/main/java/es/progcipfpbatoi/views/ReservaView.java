/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.views;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import es.progcipfpbatoi.model.entities.Reserva;

/**
 *
 * @author Ana Carbonell Prieto
 */
public class ReservaView {
    private final Reserva reserva;

    private static final int ANCHO_TABLA = 75;

    public ReservaView(Reserva reserva) {
        this.reserva = reserva;
    }

    private AsciiTable buildASCIITable() {
        AsciiTable view = new AsciiTable();
        view.addRule();
        view.addRow("*", "*");
        view.addRule();
        view.addRow(null, "Reservas con código " + this.reserva.getCodReserva());
        view.addRule();
        view.addRow("Usuario", this.reserva.getUsuario().getNombre());
        view.addRule();
        view.addRow("Plazas", this.reserva.getPlazas());
        view.addRule();
        view.addRow("Fecha", this.reserva.getFechaFormateada());
        view.addRule();
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
}
