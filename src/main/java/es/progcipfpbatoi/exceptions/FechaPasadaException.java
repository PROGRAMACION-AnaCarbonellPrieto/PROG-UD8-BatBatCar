/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.exceptions;

/**
 *
 * @author Ana Carbonell Prieto
 */
public class FechaPasadaException extends Exception {

    public FechaPasadaException() {
        super("La fecha introducida es anterior a la fecha actual.");
    }
}
