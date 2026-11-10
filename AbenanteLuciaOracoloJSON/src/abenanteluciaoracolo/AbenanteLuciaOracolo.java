/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package abenanteluciaoracolo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class AbenanteLuciaOracolo {

   // Si usa l'interfaccia come tipo: se un domani cambi libreria JSON,
    // basta sostituire ArchivioGson con un'altra implementazione.
    static final ArchivioFrasiInt archivio = new ArchivioGson();
 
    // Legge il file come testo e lo trasforma in oggetti con l'archivio
   static Map<String, Personalita> carica(String nomeFile) throws IOException {
        String json = Files.readString(Path.of(nomeFile), StandardCharsets.UTF_8);
        // Toglie l'eventuale BOM (carattere invisibile all'inizio del file) e gli spazi:
        // Gson altrimenti lo legge come testo e dà "Expected BEGIN_OBJECT but was STRING"
        if (json.startsWith("\uFEFF")) {
            json = json.substring(1);
        }
        json = json.trim();
        Map<String, Personalita> mappa = archivio.deserializza(json);
        // Controllo: il JSON deve avere "zoltar", "cattivo", "sarcastico" come chiavi
        // principali (senza il vecchio livello "personalita")
        if (mappa == null || !mappa.containsKey("zoltar")) {
            throw new IOException("frasi.json non valido: manca la chiave \"zoltar\" al primo livello");
        }
        return mappa;
    }
 
    public static void main(String[] args) throws IOException {
 // La chiave è il nome della personalità ("zoltar", "cattivo", "sarcastico")
        Map<String, Personalita> frasi = carica("frasi.json");
        System.out.println("Frasi caricate dal file JSON.");
 
        int porta = 5555;
        ServerSocket serverSocket = new ServerSocket(porta);
        System.out.println("Il veggente è in attesa di client...");
 
        int contatore = 0;
 
        while (true) {
            Socket clientSocket = serverSocket.accept();
            contatore++;
            System.out.println("Client " + contatore + " connesso");
 
            Veggente veggente = new Veggente();
            veggente.setSocket(clientSocket);
            veggente.setNumero(contatore);
            veggente.setFrasi(frasi);
 
            new Thread(veggente).start();
        }
    }
}