/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.model.entities.types;

import es.progcipfpbatoi.model.entities.Reserva;
import es.progcipfpbatoi.model.entities.Usuario;

/**
 *
 * @author Ana Carbonell Prieto
 */
public class ViajeFlexible extends Viaje implements Cancelable {
    
    public ViajeFlexible(Usuario propietario, String ruta, int duracion, int plazasOfertadas, float precio) {
        super(propietario, ruta, duracion, plazasOfertadas, precio);
    }
    
    public void cancelarViaje() {
        this.cancelado = true;
    }
    
    public Reserva cambiarPlazasReservadas(int codReserva, int plazas) {
        for (int i = 0; i < this.reservas.size(); i++) {
            if (this.reservas.get(i).getCodReserva() == codReserva) {
                int plazasNuevas = plazas - this.reservas.get(i).getPlazas();
                
                if (this.getPlazasDisponibles() < plazasNuevas) break;
                
                Reserva reserva = new Reserva(this.reservas.get(i).getUsuario(), plazas);
                this.reservas.add(reserva);
                this.reservas.remove(i);
                this.plazasReservadas += plazasNuevas;
                
                if (cerrado == true && getPlazasDisponibles() > 0) {
                    cerrado = false;
                } else if (getPlazasDisponibles() == 0) {
                    cerrado = true;
                }
                
                return reserva;
            }
        }
        return null;
    }
    
    public void cambiarPrecio(float precio) {
        this.precio = precio;
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
        return "Viaje Flexible";
    }
}
