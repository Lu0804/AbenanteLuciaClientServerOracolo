/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abenanteluciaoracolo;

import com.google.gson.Gson;

/**
 *
 * @author lucia
 */
public class ArchivioGson implements ArchivioFrasiInt {

    private final Gson gson = new Gson();

    @Override
    public Frasi deserializza(String json) {
        return gson.fromJson(json, Frasi.class); // testo JSON -> oggetto Frasi
    }

    @Override
    public String serializza(Frasi frasi) {
        return gson.toJson(frasi); // oggetto Frasi -> testo JSON
    }

}
