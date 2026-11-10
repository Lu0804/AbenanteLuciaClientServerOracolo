/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package abenanteluciaoracolo;

import java.util.Map;

/**
 *
 * @author lucia
 */
public interface ArchivioFrasiInt {

    /**
     * Interfaccia: elenca le due operazioni per trasformare le frasi da/verso
     * testo.
     *
     */

    // Da testo JSON a oggetti Java
    Map<String, Personalita> deserializza(String json);

    // Da oggetti Java a testo JSON
String serializza(Map<String, Personalita> frasi);

}
