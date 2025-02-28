package es.progcipfpbatoi.model.managers;

import es.progcipfpbatoi.model.entities.Usuario;
import es.progcipfpbatoi.model.entities.types.Viaje;
import es.progcipfpbatoi.model.entities.types.ViajeCancelable;
import es.progcipfpbatoi.model.entities.types.ViajeExclusivo;
import es.progcipfpbatoi.model.entities.types.ViajeFlexible;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Gestor de viajes. Manejará la lista de los viajes tanto para almancenar nueva 
 * información sobre ella como para recuperar la totalidad o parte de la información
 * como también información relacionada con dicha lista.
 * @author batoi
 */

public class ViajesManager {
    private List<Viaje> viajes;

    public ViajesManager() {
        this.viajes = new ArrayList<>();
        init();
    }

    /**
     * Añade un nuevo viaje al repositorio
     * @param viaje
     */
    public void add(Viaje viaje) {
        this.viajes.add(viaje);
    }
    
    /**
     * Cancela un viaje
     * @param viaje
     */
    public void cancel(Viaje viaje){
        viaje.setCancelado(true);
    }

    /**
     * Obtiene el número de viajes actualmente registrados
     * @return
     */
    public int getNumViajes() {
        return this.viajes.size();
    }
    
    /**
     * Obtiene todos los viajes
     * @return
     */
    public List<Viaje> findAll() {
        return viajes;
    }

    private void init() {
        this.add(new Viaje(new Usuario("sergio123", "1234"), "Madrid-Murica-Alicante", 360, LocalDateTime.of(2109, 3, 28, 10, 34), 1, 5));
        this.add(new ViajeExclusivo(new Usuario("roberto1979", "1234"), "Alicante-Valencia", 90, LocalDateTime.of(2027, 1, 14, 10, 34), 4, 6));
        this.add(new ViajeCancelable(new Usuario("raul00", "1234"), "Madrid-Barcelona", 300, LocalDateTime.of(2025, 1, 1, 10, 10), 1, 10));
        this.add(new ViajeFlexible(new Usuario("alex32", "1234"), "Alcoy-Cocentaina", 10, LocalDateTime.of(2025, 4, 28, 16, 0), 2, 2));
        this.add(new Viaje(new Usuario("sergio123", "1234"), "Alcoy-Alicante", 50, LocalDateTime.of(2014, 4, 29, 4, 30), 3, 3));
        this.add(new Viaje(new Usuario("maria456", "1234"), "Alicante-Alcoy", 50, LocalDateTime.of(2026, 12, 31, 7, 59), 2, 3));
        this.add(new Viaje(new Usuario("elena12", "1234"), "Castellón-Gandía", 60, LocalDateTime.of(1970, 1, 2, 3, 46), 0, 3));
    }
}
