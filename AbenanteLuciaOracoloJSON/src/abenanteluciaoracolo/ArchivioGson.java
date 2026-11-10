/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abenanteluciaoracolo;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;



/**
 *
 * @author lucia
 */
public class ArchivioGson implements ArchivioFrasiInt {
 
    private final Gson gson = new Gson();
 
    // Gson ha bisogno del tipo completo della mappa (i generici si perdono a runtime)
    private final Type tipoMappa = new TypeToken<Map<String, Personalita>>() {
    }.getType();
 
    @Override
    public Map<String, Personalita> deserializza(String json) {
        return gson.fromJson(json, tipoMappa); // da testo JSON a mappa
    }
 
    @Override
    public String serializza(Map<String, Personalita> frasi) {
        return gson.toJson(frasi, tipoMappa); // da mappa a testo JSON
    }

}
