/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.model.entities.types;

import es.progcipfpbatoi.model.entities.Usuario;
import java.time.LocalDateTime;

/**
 *
 * @author Ana Carbonell Prieto
 */
public class ViajeCancelable extends Viaje implements Cancelable {

    public ViajeCancelable(Usuario propietario, String ruta, int duracion, LocalDateTime fechaSalida, int plazasOfertadas, float precio) {
        super(propietario, ruta, duracion, fechaSalida, plazasOfertadas, precio);
    }
    
    @Override
    public void cancelarReserva(int codReserva) {
        for (int i = 0; i < this.reservas.size(); i++) {
            if (reservas.get(i).getCodReserva() == codReserva) {
                this.plazasReservadas -= this.reservas.get(i).getPlazas();
                this.reservas.remove(i);
                
                if (cerrado == true && getPlazasDisponibles() > 0) {
                    cerrado = false;
                }
            }
        }
    }
    
    @Override
    public String cancelableToString() {
        return String.format(
                "Reserva del viaje de tipo %s de %s código %d ruta %s",
                this.getTipo(),
                this.propietario.getNombre(),
                this.codViaje,
                this.ruta
        );
    }
    
    @Override
    public String getTipo() {
        return "Viaje Cancelable";
    }
}
