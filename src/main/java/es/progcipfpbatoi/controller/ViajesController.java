package es.progcipfpbatoi.controller;

import es.progcipfpbatoi.model.entities.Reserva;
import es.progcipfpbatoi.model.entities.Usuario;
import es.progcipfpbatoi.model.entities.types.Cancelable;
import es.progcipfpbatoi.model.entities.types.Viaje;
import es.progcipfpbatoi.model.entities.types.ViajeCancelable;
import es.progcipfpbatoi.model.entities.types.ViajeExclusivo;
import es.progcipfpbatoi.model.entities.types.ViajeFlexible;
import es.progcipfpbatoi.model.managers.UsuariosManager;
import es.progcipfpbatoi.model.managers.ViajesManager;
import es.progcipfpbatoi.views.GestorIO;
import es.progcipfpbatoi.views.ListadoReservasView;
import es.progcipfpbatoi.views.ListadoViajesView;
import es.progcipfpbatoi.views.ReservaView;
import java.util.ArrayList;
import java.util.List;

public class ViajesController {
    private Usuario usuario;
    private ViajesManager viajesManager;
    private UsuariosManager usuariosManager;

    public ViajesController() {
        this.viajesManager = new ViajesManager();
        this.usuariosManager = new UsuariosManager();
        this.usuario = null;
    }
    
    /**
     * Lista todos los viajes del sistema.
     */
    public void listarViajes() {
        List<Viaje> viajes = viajesManager.findAll();
        (new ListadoViajesView(viajes)).visualizar();
    }

    /**
     * Añade un viaje al sistema, preguntando previamente por toda la información necesaria para crearlo.
     */
    public void anyadirViaje() {
        if (!isLogeado()) return;
        
        mostrarTiposViajes();
        int opcionSeleccionada = GestorIO.getInt("Seleccione el tipo de viaje", 1, 4);
        crearViaje(opcionSeleccionada);
    }
    
    private void mostrarTiposViajes() {
        GestorIO.print("1- Viaje Estándar");
        GestorIO.print("2- Viaje Cancelable");
        GestorIO.print("3- Viaje Exclusivo");
        GestorIO.print("4- Viaje Flexible");
    }
    
    private void crearViaje(int opcionSeleccionada) {
        Viaje viaje = null;
        String ruta = GestorIO.getRuta("Introduzca la ruta a realizar (Ej: Alcoy-Alicante)");
        int duracion = GestorIO.getInt("Introduzca la duración del viaje en minutos");
        float precio = GestorIO.getFloat("Introduzca el precio de cada plaza");
        int plazasOfertadas = GestorIO.getInt("Introduza el número de plazas disponibles");
        
        switch (opcionSeleccionada) {
            case 1 -> viaje = new Viaje(this.usuario, ruta, duracion, plazasOfertadas, precio);
            case 2 -> viaje = new ViajeCancelable(this.usuario, ruta, duracion, plazasOfertadas, precio);
            case 3 -> viaje = new ViajeExclusivo(this.usuario, ruta, duracion, plazasOfertadas, precio);
            case 4 -> viaje = new ViajeFlexible(this.usuario, ruta, duracion, plazasOfertadas, precio);
        }
        
        this.viajesManager.add(viaje);
        GestorIO.print(viaje + " añadido con éxito");
    }
    
    public void cancelarViaje() {
        if (!isLogeado()) return;
        
        List<Viaje> viajesCancelables = listarViajesCanceclables();
        if (viajesCancelables.isEmpty()) {
            GestorIO.print("Todavía no ha añadido ningún viaje.");
            return;
        }
        
        int viajeSeleccionado = GestorIO.getInt("Introduzca el código del viaje a seleccionar");
        Viaje viaje = buscarViaje(viajeSeleccionado, viajesCancelables);
        
        if (viaje == null) {
            GestorIO.print("Error al intentar cancelar el viaje.");
            return;
        }
        
        viajesManager.cancel(viaje);
        GestorIO.print("El viaje se ha cancelado correctamente.");
    }
    
    private List<Viaje> listarViajesCanceclables() {
        List<Viaje> viajes = viajesManager.findAll();
        List<Viaje> viajesCancelables = new ArrayList<>();
        
        for (Viaje viaje: viajes) {
            if (viaje.getPropietario().equals(usuario) && !viaje.isCancelado()) {
                viajesCancelables.add(viaje);
            }
        }
        
        (new ListadoViajesView(viajesCancelables)).visualizar();
        return viajesCancelables;
    }
    
    private Viaje buscarViaje(int viajeSeleccionado, List<Viaje> viajes) {
        for (Viaje viaje: viajes) {
            if (viaje.getCodViaje() == viajeSeleccionado) {
                return viaje;
            }
        }
        return null;
    }
    
    public void reservarViaje() {
        if (!isLogeado()) return;
        
        List<Viaje> viajesReservables = listarViajesReservables();
        if (viajesReservables.isEmpty()) {
            GestorIO.print("No hay viajes para reservar.");
            return;
        }
        crearReserva(viajesReservables);
    }
    
    private void crearReserva(List<Viaje> viajes) {
        int viajeSeleccionado = GestorIO.getInt("Introduzca el código del viaje a seleccionar");
        int plazas = GestorIO.getInt("Introduzca el número de plazas a reservar");
        Viaje viaje = buscarViaje(viajeSeleccionado, viajes);
        
        Reserva reserva;
        if (viaje == null || (reserva = viaje.realizarReserva(this.usuario, plazas)) == null) {
            GestorIO.print("Error al intentar reservar el viaje.");
            return;
        }
        
        mostrarReserva(reserva);
    }
    
    private List<Viaje> listarViajesReservables() {
        List<Viaje> viajes = viajesManager.findAll();
        List<Viaje> viajesReservables = new ArrayList<>();
        
        for (Viaje viaje: viajes) {
            if (!viaje.getPropietario().equals(usuario) && !viaje.isCancelado() && !viaje.isCerrado()) {
                viajesReservables.add(viaje);
            }
        }
        
        (new ListadoViajesView(viajesReservables)).visualizar();
        return viajesReservables;
    }
    
    private void mostrarReserva(Reserva reserva) {
        GestorIO.print("Reserva realizada con éxito. A continuación se mostrará el ticket de confirmación.");
        (new ReservaView(reserva)).visualizar();
    }
    
    public void modificarReserva() {
        if (!isLogeado()) return;
        
        List<Cancelable> viajes = listarViajesReservados();
        if (viajes.isEmpty()) {
            GestorIO.print("Todavía no hay viajes reservados que se puedan modificar.");
            return;
        }
        
        mostrarReservas(viajes);
        int reservaSeleccionada = GestorIO.getInt("Introduzca el código de reserva a modificar");
        ViajeFlexible viaje = (ViajeFlexible) buscarReserva(reservaSeleccionada, viajes);
        
        if (viaje == null) {
            GestorIO.print("La reserva indicada no existe.");
            return;
        }
        
        int plazas = GestorIO.getInt("Introduzca el número de plazas a reservar");
        Reserva reserva = viaje.cambiarPlazasReservadas(reservaSeleccionada, plazas);
        
        if (reserva == null) {
            GestorIO.print("No quedan suficientes plazas.");
            return;
        }
        
        mostrarReserva(reserva);
    }
    
    private List<Cancelable> listarViajesReservados() {
        List<Viaje> viajesTotales = viajesManager.findAll();
        List<Cancelable> viajes = new ArrayList<>();
        
        for (Viaje viaje: viajesTotales) {
            if (!(viaje instanceof ViajeFlexible) || viaje.isCancelado()) continue;
            
            for (Reserva reserva: viaje.getReservas()) {
                if (reserva.getUsuario().equals(this.usuario)) {
                    viajes.add((Cancelable) viaje);
                }
            }
        }
        return viajes;
    }
    
    private void mostrarReservas(List<Cancelable> cancelables) {
        List<Viaje> viajes = new ArrayList<>();
        
        for (Cancelable viaje: cancelables) {
            viajes.add((Viaje) viaje);
        }
        
        (new ListadoReservasView(viajes, this.usuario)).visualizar();
    }
    
    private Viaje buscarReserva(int reservaSeleccionada, List<Cancelable> cancelables) {
        for (Cancelable cancelable: cancelables) {
            Viaje viaje = (Viaje) cancelable;
            for (Reserva reserva: viaje.getReservas()) {
                if (reserva.getCodReserva() == reservaSeleccionada) {
                    return viaje;
                }
            }
        }
        return null;
    }
    
    public void cancelarReserva() {
        if (!isLogeado()) return;
        
        List<Cancelable> viajes = listarReservasCancelables();
        if (viajes.isEmpty()) {
            GestorIO.print("Todavía no hay viajes reservados que se puedan cancelar.");
            return;
        }
        
        mostrarReservas(viajes);
        int reservaSeleccionada = GestorIO.getInt("Introduzca el código de reserva a cancelar");
        Cancelable viaje = (Cancelable) buscarReserva(reservaSeleccionada, viajes);
        
        if (viaje == null) {
            GestorIO.print("La reserva indicada no existe.");
            return;
        }
        
        viaje.cancelarReserva(reservaSeleccionada);
        GestorIO.print(viaje.cancelableToString() + " cancelada con éxito.");
    }
    
    private List<Cancelable> listarReservasCancelables() {
        List<Viaje> viajesTotales = viajesManager.findAll();
        List<Cancelable> viajes = new ArrayList<>();
        
        for (Viaje viaje: viajesTotales) {
            if (!(viaje instanceof ViajeCancelable || viaje instanceof ViajeFlexible) || viaje.isCancelado()) continue;
            
            for (Reserva reserva: viaje.getReservas()) {
                if (reserva.getUsuario().equals(this.usuario)) {
                    viajes.add((Cancelable) viaje);
                }
            }
        }
        return viajes;
    }
    
    public void buscarYReservarViaje() {
        if (!isLogeado()) return;
        
        String ciudad = GestorIO.getString("Introduzca la ciudad a la que desea viajar");
        List<Viaje> viajes = buscarViaje(ciudad);
        if (viajes.isEmpty()) {
            GestorIO.print("Todavía no hay viajes con ese destino.");
            return;
        }
        
        (new ListadoViajesView(viajes)).visualizar();
        if (!GestorIO.confirmar("¿Quiere realizar una reserva?")) return;
        crearReserva(viajes);
    }
    
    private List<Viaje> buscarViaje(String ciudad) {
        List<Viaje> viajesTotales = viajesManager.findAll();
        List<Viaje> viajes = new ArrayList<>();
        
        for (Viaje viaje: viajesTotales) {
            if (viaje.getPropietario().getNombre().equals(usuario.getNombre())) continue;
            
            if (viaje.getRuta().toLowerCase().matches(".+-" + ciudad.toLowerCase() + ".*")) {
                viajes.add(viaje);
            }
        }
        
        return viajes;
    }
    
    public void iniciarSesion() {
        int intentos = 0;
        do {
            String nombre = GestorIO.getString("Username");
            String contrasenya = GestorIO.getString("Password");

            Usuario usuario = this.usuariosManager.comprobarExistenciaUsuario(nombre);
            
            if (usuario != null && this.usuariosManager.comprobarContrasenyaUsuario(usuario, contrasenya)) {
                this.usuario = usuario;
                GestorIO.print("Bienvenido " + nombre);
                return;
            } else if (usuario == null) {
                GestorIO.print("Error, el usuario introducido no existe.");
            } else {
                GestorIO.print("Error, la contraseña introducida es errónea.");
            }
            intentos++;
        } while (intentos < 3);
        GestorIO.print("Se ha alcanzado el número máximo de intentos. Adiós!");
        System.exit(0);
    }
    
    private boolean isLogeado() {
        if (usuario == null) {
            GestorIO.print("No ha iniciado sesión.");
            return false;
        }
        return true;
    }
}
