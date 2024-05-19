/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.model.entities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Ana Carbonell Prieto
 */
public class Reserva {
    private static int cantidadReservas;
    
    private int codReserva;
    private Usuario usuario;
    private int plazas;
    private LocalDateTime fecha;

    public Reserva(Usuario usuario, int plazas) {
        this.codReserva = crearNuevoCodigo();
        this.usuario = usuario;
        this.plazas = plazas;
        this.fecha = LocalDateTime.now();
    }
    
    private int crearNuevoCodigo() {
        return ++this.cantidadReservas;
    }

    public void setPlazas(int plazas) {
        this.plazas = plazas;
    }

    public int getPlazas() {
        return plazas;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public int getCodReserva() {
        return codReserva;
    }
    
    public String getFechaFormateada() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd-MM-yyyy 'a las' HH:mm");
        return this.fecha.format(formato);
    }
}
