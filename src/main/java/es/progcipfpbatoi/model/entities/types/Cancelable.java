/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package es.progcipfpbatoi.model.entities.types;

/**
 *
 * @author Ana Carbonell Prieto
 */
public interface Cancelable {
    public void cancelarReserva(int codReserva);
    public String cancelableToString();
}
