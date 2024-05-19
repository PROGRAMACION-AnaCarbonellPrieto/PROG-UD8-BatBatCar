/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package es.progcipfpbatoi.exceptions;

/**
 *
 * @author Ana Carbonell Prieto
 */
public class UsuarioSinEstablecerException extends Exception {

    public UsuarioSinEstablecerException() {
        super("Se requiere iniciar sesión para realizar la acción.");
    }
}
