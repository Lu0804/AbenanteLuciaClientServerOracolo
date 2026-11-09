/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abenanteluciaoracolo;

import com.google.gson.Gson;
import java.io.*;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author lucia
 */
public class Veggente implements Runnable {

    Socket socket;//la porta che si passano
    int numero; //per riconoscere il client
    String personalita = "zoltar"; // default: ogni client parte con Zoltar

    // Le frasi (domande e futuro, per ogni personalità) lette dal file JSON.
    // Le imposta il main del server: il Veggente non legge il file da solo.
    Frasi frasi;

    static final Random random = new Random();// Generatore di numeri casuali per scegliere una frase

    @Override
    // Metodo eseguito dal thread: legge i messaggi del client e risponde finché il client esce.
    public void run() {
        try {
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF("INFO_Benvenuto! Zoltar è pronto. Fai una domanda o chiedi il futuro.");

            boolean continua = true;// diventa false quanod client scrive exit

            while (continua) {
                // Si ferma finché il client non invia un messaggio
                String msg = in.readUTF();
                System.out.println("Client " + numero + ": " + msg);
// Divide al primo "_" in massimo 2 pezzi: comando e parametro.
                // Il limite 2 lascia intera la domanda anche se contiene altri "_"
                String[] parti = msg.split("_", 2); // separatore "_", massimo 2 pezzi
                String comando = parti[0].toUpperCase();

                switch (comando) {
// Cambia la personalità di questo client, se è una di quelle valide
                    case "PERSONA":
                        if (parti.length < 2) {
                            out.writeUTF("INFO_Scrivi: PERSONA_zoltar / cattivo / sarcastico");
                        } else {
                            String scelta = parti[1].trim().toLowerCase();
                            switch (scelta) {
                                case "zoltar":
                                case "cattivo":
                                case "sarcastico":
                                    personalita = scelta;
                                    out.writeUTF("INFO_Personalità impostata: " + personalita);
                                    break;
                                default:
                                    out.writeUTF("INFO_Personalità non valida.");
                                    break;
                            }
                        }
                        break;
// Risponde con una frase casuale, solo se la domanda non è vuota
                    case "DOMANDA":
                        if (parti.length < 2 || parti[1].trim().isEmpty()) {
                            out.writeUTF("INFO_Scrivi la domanda dopo DOMANDA");
                        } else {
                            out.writeUTF("RISPOSTA_" + rispondi());
                        }
                        break;
                    // Invia una previsione casuale per il ticket
                    case "FUTURO":
                        out.writeUTF("FUTURO_" + futuro());
                        break;

                    case "EXIT":
                        out.writeUTF("INFO_Arrivederci!");
                        continua = false;
                        break;

                    default:
                        out.writeUTF("INFO_Comando sconosciuto.");
                        break;
                }
            }
        } catch (IOException e) {
            // Succede se il client chiude all'improvviso
            System.out.println("Client " + numero + " disconnesso");
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
            }  // Viene eseguito sempre: chiude il socket per liberare le risorse
            System.out.println("Client " + numero + " chiuso");
        }
    }
    // Restituisce le frasi della personalità attuale di QUESTO client.
    // Se per caso la chiave non esiste nel JSON, usa "zoltar" come ripiego.

    private Personalita frasiAttuali() {
        Personalita p = frasi.personalita.get(personalita);
        if (p == null) {
            p = frasi.personalita.get("zoltar");
        }
        return p;
    }

    // frase per una domanda, in base alla personalità di QUESTO client
    private String rispondi() {
        String[] array = frasiAttuali().risposte;
        return array[random.nextInt(array.length)];// nextInt(n) restituisce un numero da 0 a n-1: è sempre un indice valido
    }

    // frase per il futuro, in base alla personalità di QUESTO client
    private String futuro() {
        String[] array = frasiAttuali().futuro;
        return array[random.nextInt(array.length)];
    }
// Imposta il socket di questo client (chiamato dal main del server)

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    // Imposta il numero di questo client (chiamato dal main del server)

    public void setNumero(int numero) {
        this.numero = numero;
    }

    // Imposta le frasi lette dal JSON (chiamato dal main del server)
    public void setFrasi(Frasi frasi) {
        this.frasi = frasi;
    }
}
