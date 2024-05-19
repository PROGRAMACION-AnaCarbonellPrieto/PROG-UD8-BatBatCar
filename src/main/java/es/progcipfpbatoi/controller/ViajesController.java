package es.progcipfpbatoi.controller;

import es.progcipfpbatoi.exceptions.CredencialesInvalidasException;
import es.progcipfpbatoi.exceptions.FechaPasadaException;
import es.progcipfpbatoi.exceptions.MaximoIntentosAlcanzadosException;
import es.progcipfpbatoi.exceptions.ReservaNoValidaException;
import es.progcipfpbatoi.exceptions.UsuarioSinEstablecerException;
import es.progcipfpbatoi.exceptions.ViajeNoValidoException;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public void anyadirViaje() throws UsuarioSinEstablecerException {
        isLogeado();
        
        mostrarTiposViajes();
        int opcionSeleccionada = GestorIO.getInt("Seleccione el tipo de viaje", 1, 4);
        
        try {
            crearViaje(opcionSeleccionada);
        } catch (FechaPasadaException e) {
            GestorIO.print(e.getMessage());
        }
    }
    
    private void mostrarTiposViajes() {
        GestorIO.print("1- Viaje Estándar");
        GestorIO.print("2- Viaje Cancelable");
        GestorIO.print("3- Viaje Exclusivo");
        GestorIO.print("4- Viaje Flexible");
    }
    
    private void crearViaje(int opcionSeleccionada) throws FechaPasadaException {
        Viaje viaje = null;
        String ruta = GestorIO.getRuta("Introduzca la ruta a realizar");
        LocalDate fecha = GestorIO.getFecha("Introduza la fecha");
        LocalTime hora = GestorIO.getHora("Introduza la hora");
        int duracion = GestorIO.getInt("Introduzca la duración del viaje en minutos");
        float precio = GestorIO.getFloat("Introduzca el precio de cada plaza");
        int plazasOfertadas = GestorIO.getInt("Introduza el número de plazas disponibles");
        
        LocalDateTime fechaSalida = LocalDateTime.of(fecha, hora);
        if (LocalDateTime.now().isAfter(fechaSalida)) throw new FechaPasadaException();
        
        switch (opcionSeleccionada) {
            case 1 -> viaje = new Viaje(this.usuario, ruta, duracion, fechaSalida, plazasOfertadas, precio);
            case 2 -> viaje = new ViajeCancelable(this.usuario, ruta, duracion, fechaSalida, plazasOfertadas, precio);
            case 3 -> viaje = new ViajeExclusivo(this.usuario, ruta, duracion, fechaSalida, plazasOfertadas, precio);
            case 4 -> viaje = new ViajeFlexible(this.usuario, ruta, duracion, fechaSalida, plazasOfertadas, precio);
        }
        
        this.viajesManager.add(viaje);
        GestorIO.print(viaje + " añadido con éxito");
    }
    
    public void cancelarViaje() throws UsuarioSinEstablecerException {
        isLogeado();
        
        List<Viaje> viajesCancelables = listarViajesCanceclables();
        if (viajesCancelables.isEmpty()) {
            GestorIO.print("Todavía no ha añadido ningún viaje.");
            return;
        }
        (new ListadoViajesView(viajesCancelables)).visualizar();
        
        int viajeSeleccionado = GestorIO.getInt("Introduzca el código del viaje a seleccionar");
        try {
            Viaje viaje = buscarViaje(viajeSeleccionado, viajesCancelables);
            viajesManager.cancel(viaje);
            GestorIO.print("El viaje se ha cancelado correctamente.");
        } catch (ViajeNoValidoException e) {
            GestorIO.print(e.getMessage());
        }
    }
    
    private List<Viaje> listarViajesCanceclables() {
        List<Viaje> viajes = viajesManager.findAll();
        List<Viaje> viajesCancelables = new ArrayList<>();
        
        for (Viaje viaje: viajes) {
            if (
                viaje.getPropietario().equals(usuario) &&
                !viaje.isCancelado() &&
                LocalDateTime.now().isBefore(viaje.getFechaSalida())
            ) {
                viajesCancelables.add(viaje);
            }
        }
        return viajesCancelables;
    }
    
    private Viaje buscarViaje(int viajeSeleccionado, List<Viaje> viajes) throws ViajeNoValidoException {
        for (Viaje viaje: viajes) {
            if (viaje.getCodViaje() == viajeSeleccionado) {
                return viaje;
            }
        }
        throw new ViajeNoValidoException();
    }
    
    public void reservarViaje() throws UsuarioSinEstablecerException {
        isLogeado();
        
        List<Viaje> viajesReservables = listarViajesReservables();
        if (viajesReservables.isEmpty()) {
            GestorIO.print("No hay viajes para reservar.");
            return;
        }
        (new ListadoViajesView(viajesReservables)).visualizar();
        crearReserva(viajesReservables);
    }
    
    private void crearReserva(List<Viaje> viajes) {
        int viajeSeleccionado = GestorIO.getInt("Introduzca el código del viaje a seleccionar");
        int plazas = GestorIO.getInt("Introduzca el número de plazas a reservar");
        
        try {
            Viaje viaje = buscarViaje(viajeSeleccionado, viajes);
            Reserva reserva;
            if ((reserva = viaje.realizarReserva(this.usuario, plazas)) == null) {
                GestorIO.print("Error al intentar reservar el viaje.");
                return;
            }

            mostrarReserva(reserva);
        } catch (ViajeNoValidoException e) {
            GestorIO.print(e.getMessage());
        }  
    }
    
    private List<Viaje> listarViajesReservables() {
        List<Viaje> viajes = viajesManager.findAll();
        List<Viaje> viajesReservables = new ArrayList<>();
        
        for (Viaje viaje: viajes) {
            if (
                !viaje.getPropietario().equals(usuario) &&
                !viaje.isCancelado() &&
                !viaje.isCerrado() &&
                LocalDateTime.now().isBefore(viaje.getFechaSalida())
            ) {
                viajesReservables.add(viaje);
            }
        }
        return viajesReservables;
    }
    
    private void mostrarReserva(Reserva reserva) {
        GestorIO.print("Reserva realizada con éxito. A continuación se mostrará el ticket de confirmación.");
        (new ReservaView(reserva)).visualizar();
    }
    
    public void modificarReserva() throws UsuarioSinEstablecerException {
        isLogeado();
        
        List<Cancelable> viajes = listarViajesReservados();
        if (viajes.isEmpty()) {
            GestorIO.print("Todavía no hay viajes reservados que se puedan modificar.");
            return;
        }
        
        mostrarReservas(viajes);
        int reservaSeleccionada = GestorIO.getInt("Introduzca el código de reserva a modificar");
        
        try {
            ViajeFlexible viaje = (ViajeFlexible) buscarReserva(reservaSeleccionada, viajes);
            int plazas = GestorIO.getInt("Introduzca el número de plazas a reservar");
            Reserva reserva = viaje.cambiarPlazasReservadas(reservaSeleccionada, plazas);

            if (reserva == null) {
                GestorIO.print("No quedan suficientes plazas.");
                return;
            }

            mostrarReserva(reserva);
        } catch (ReservaNoValidaException e) {
            GestorIO.print(e.getMessage());
        }
    }
    
    private List<Cancelable> listarViajesReservados() {
        List<Viaje> viajesTotales = viajesManager.findAll();
        List<Cancelable> viajes = new ArrayList<>();
        
        for (Viaje viaje: viajesTotales) {
            if (
                !(viaje instanceof ViajeFlexible) ||
                viaje.isCancelado() ||
                LocalDateTime.now().isAfter(viaje.getFechaSalida())
            ) continue;
            
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
    
    private Viaje buscarReserva(int reservaSeleccionada, List<Cancelable> cancelables) throws ReservaNoValidaException {
        for (Cancelable cancelable: cancelables) {
            Viaje viaje = (Viaje) cancelable;
            for (Reserva reserva: viaje.getReservas()) {
                if (reserva.getCodReserva() == reservaSeleccionada) {
                    return viaje;
                }
            }
        }
        throw new ReservaNoValidaException();
    }
    
    public void cancelarReserva() throws UsuarioSinEstablecerException {
        isLogeado();
        
        List<Cancelable> viajes = listarReservasCancelables();
        if (viajes.isEmpty()) {
            GestorIO.print("Todavía no hay viajes reservados que se puedan cancelar.");
            return;
        }
        
        mostrarReservas(viajes);
        int reservaSeleccionada = GestorIO.getInt("Introduzca el código de reserva a cancelar");
        
        try {
            Cancelable viaje = (Cancelable) buscarReserva(reservaSeleccionada, viajes);
            viaje.cancelarReserva(reservaSeleccionada);
            GestorIO.print(viaje.cancelableToString() + " cancelada con éxito.");
        } catch (ReservaNoValidaException e) {
            GestorIO.print(e.getMessage());
        }
    }
    
    private List<Cancelable> listarReservasCancelables() {
        List<Viaje> viajesTotales = viajesManager.findAll();
        List<Cancelable> viajes = new ArrayList<>();
        
        for (Viaje viaje: viajesTotales) {
            if (
                !(viaje instanceof ViajeCancelable ||
                viaje instanceof ViajeFlexible) ||
                viaje.isCancelado() ||
                LocalDateTime.now().isAfter(viaje.getFechaSalida())
            ) continue;
            
            for (Reserva reserva: viaje.getReservas()) {
                if (reserva.getUsuario().equals(this.usuario)) {
                    viajes.add((Cancelable) viaje);
                }
            }
        }
        return viajes;
    }
    
    public void buscarYReservarViaje() throws UsuarioSinEstablecerException {
        isLogeado();
        
        String ciudad = GestorIO.getString("Introduzca la ciudad a la que desea viajar");
        List<Viaje> viajes = buscarViajes(ciudad);
        if (viajes.isEmpty()) {
            GestorIO.print("Todavía no hay viajes con ese destino.");
            return;
        }
        
        (new ListadoViajesView(viajes)).visualizar();
        if (!GestorIO.confirmar("¿Quiere realizar una reserva?")) return;
        crearReserva(viajes);
    }
    
    private List<Viaje> buscarViajes(String ciudad) {
        List<Viaje> viajesTotales = viajesManager.findAll();
        List<Viaje> viajes = new ArrayList<>();
        
        for (Viaje viaje: viajesTotales) {
            if (
                !viaje.getPropietario().getNombre().equals(usuario.getNombre()) &&
                LocalDateTime.now().isBefore(viaje.getFechaSalida()) &&
                viaje.getRuta().toLowerCase().matches(".+-" + ciudad.toLowerCase() + ".*")
            ) {
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
            
            try {
                Usuario usuario = this.usuariosManager.comprobarCredenciales(nombre, contrasenya);
                this.usuario = usuario;
                GestorIO.print("Bienvenido " + nombre);
                return;
            } catch (CredencialesInvalidasException e) {
                GestorIO.print(e.getMessage());
                intentos++;
            }
        } while (intentos < 3);
        throw new MaximoIntentosAlcanzadosException();
    }
    
    private void isLogeado() throws UsuarioSinEstablecerException {
        if (usuario == null) {
            throw new UsuarioSinEstablecerException();
        }
    }
}
