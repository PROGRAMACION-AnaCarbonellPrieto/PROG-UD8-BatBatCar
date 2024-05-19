/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.model.entities.types;

import es.progcipfpbatoi.model.entities.Reserva;
import es.progcipfpbatoi.model.entities.Usuario;
import java.time.LocalDateTime;

/**
 *
 * @author Ana Carbonell Prieto
 */
public class ViajeExclusivo extends Viaje {
    
    public ViajeExclusivo(Usuario propietario, String ruta, int duracion, LocalDateTime fechaSalida, int plazasOfertadas, float precio) {
        super(propietario, ruta, duracion, fechaSalida, plazasOfertadas, precio);
    }

    @Override
    public Reserva realizarReserva(Usuario usuario, int plazas) {
        Reserva reserva;
        if ((reserva = super.realizarReserva(usuario, plazas)) == null) return null;
        
        this.cerrado = true;
        return reserva;
    }
    
    @Override
    public String getTipo() {
        return "Viaje Exclusivo";
    }
}
