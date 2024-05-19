/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.exceptions;

/**
 *
 * @author Ana Carbonell Prieto
 */
public class MaximoIntentosAlcanzadosException extends RuntimeException {

    public MaximoIntentosAlcanzadosException() {
        super("Se ha alcanzado el número máximo de intentos.");
    }
}
