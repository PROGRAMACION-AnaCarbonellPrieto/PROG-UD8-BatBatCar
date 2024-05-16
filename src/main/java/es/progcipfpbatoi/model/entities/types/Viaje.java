package es.progcipfpbatoi.model.entities.types;

import es.progcipfpbatoi.model.entities.Reserva;
import es.progcipfpbatoi.model.entities.Usuario;
import java.util.ArrayList;
import java.util.List;

/*
 * Clase que representa a un viaje estándar
*/

public class Viaje {
    private static int cantidadViajes;
    
    protected int codViaje;
    protected Usuario propietario;
    protected String ruta;
    private int duracion;
    protected int plazasOfertadas;
    protected int plazasReservadas;
    protected float precio;
    protected boolean cerrado;
    protected boolean cancelado;
    protected List<Reserva> reservas;

    public Viaje(Usuario propietario, String ruta, int duracion, int plazasOfertadas, float precio) {
        this.codViaje = crearNuevoCodigo();
        this.propietario = propietario;
        this.ruta = ruta;
        this.duracion = duracion;
        this.plazasOfertadas = plazasOfertadas;
        this.plazasReservadas = 0;
        this.precio = precio;
        this.cerrado = this.plazasOfertadas == this.plazasReservadas;
        this.cancelado = false;
        this.reservas = new ArrayList<>();
    }
    
    public Reserva realizarReserva(Usuario usuario, int plazas) {
        if (getPlazasDisponibles() < plazas) return null;
        
        for (Reserva reserva: reservas) {
            if (reserva.getUsuario().equals(usuario)) return null;
        }
        
        Reserva reserva = new Reserva(usuario, plazas);
        this.reservas.add(reserva);
        this.plazasReservadas += plazas;
        
        if (getPlazasDisponibles() == 0) {
            this.cerrado = true;
        }
        
        return reserva;
    }
    
    private int crearNuevoCodigo() {
        return ++this.cantidadViajes;
    }

    public int getCodViaje() {
        return this.codViaje;
    }

    public String getRuta() {
        return this.ruta;
    }

    public float getPrecio() {
        return this.precio;
    }

    public Usuario getPropietario() {
        return this.propietario;
    }
    
    public String getTipo() {
        return "Estándar";
    }

    public int getPlazasDisponibles() {
        return this.plazasOfertadas - this.plazasReservadas;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public boolean isCerrado() {
        return this.cerrado;
    }
    
    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }

    @Override
    public String toString() {
        return String.format(
                "Viaje de tipo %s del propietario %s con código %d y ruta %s con plazas ofertadas %d", 
                this.getTipo(),
                this.propietario.getNombre(),
                this.codViaje,
                this.ruta,
                this.plazasOfertadas
        );
    }
}


