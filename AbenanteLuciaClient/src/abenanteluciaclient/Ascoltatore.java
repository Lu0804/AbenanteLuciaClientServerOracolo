/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package abenanteluciaclient;

/**
 *
 * @author lucia
 */
public interface Ascoltatore {

    // Interfaccia: elenca i metodi che chi vuole ricevere i messaggi del server deve avere.
// Serve a separare il Reader dalla form: il Reader conosce solo questa interfaccia.
    void onRisposta(String testo);  // Viene chiamato quando il server manda la risposta a una domanda

    void onFuturo(String testo); // Viene chiamato per la previsione del futuro

    void onInfo(String testo);  // Viene chiamato quando il server manda un messaggio informativo

}
