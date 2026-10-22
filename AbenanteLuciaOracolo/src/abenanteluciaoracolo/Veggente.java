/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abenanteluciaoracolo;

import java.io.*;
import java.net.Socket;
import java.util.Random;

/**
 *
 * @author lucia
 */
public class Veggente implements Runnable {
// Gestisce la conversazione con UN client. Legge i comandi, decide la risposta e la invia.
// Ogni client ha un proprio oggetto Veggente, quindi una propria personalità.

    Socket socket;//la porta che si passano
    int numero; //per riconoscere il client
    String personalita = "zoltar"; // default: ogni client parte con Zoltar

    // Frasi per le domande, divise per personalità.
    // Sono static perché uguali per tutti i client: ne basta una copia.
    //RISPOSTE DOMANDE
    static final String[] ZOLTAR_RISP = {
        "Sì, senza alcun dubbio.",
        "Le nebbie del destino sono fitte: riprova più tardi.",
        "I segni dicono di no.",
        "Tutto dipende dalle scelte che farai."
    };
    static final String[] CATTIVI_RISP = {
        "Vedo solo guai all'orizzonte, e sono tutti tuoi.",
        "Che domanda stupida, ma ti rispondo: no.",
        "Il tuo futuro è buio quanto il fondo della mia sfera.",
        "Non ho tempo per le tue sciocchezze."
    };
    static final String[] SARCASTICI_RISP = {
        "Oh certo, perché io so TUTTO. Ovviamente sì.",
        "Che originalità, nessuno me l'aveva mai chiesto.",
        "La sfera dice: 'forse'. Ma lei è sempre stata ottimista.",
        "Wow, che domanda profonda. Sono commosso."
    };

    // FRASI PER FUTURO
    static final String[] ZOLTAR_FUTURO = {
        "Il tuo desiderio si avvererà, ma non come pensi.",
        "Una persona importante entrerà presto nella tua vita.",
        "Ti attende un viaggio inatteso.",
        "La fortuna sorride a chi osa: fai la tua mossa."
    };
    static final String[] CATTIVI_FUTURO = {
        "Il tuo futuro è un disastro e te lo sei meritato.",
        "Nessuno ricorderà il tuo nome. Nemmeno io.",
        "Presto perderai qualcosa. Probabilmente la dignità.",
        "Sprecherai il tuo tempo, come sempre."
    };
    static final String[] SARCASTICI_FUTURO = {
        "Vedo successo, ricchezza e fama. In un altro universo, ovviamente.",
        "Il futuro ti riserva grandi cose, tipo il lunedì.",
        "Vedo una svolta epocale: domani ti alzerai dal letto.",
        "Incontrerai una persona speciale: il tuo frigorifero a mezzanotte."
    };

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

    // frase per una domanda, in base alla personalità di QUESTO client
    private String rispondi() {
        String[] array;
        switch (personalita) {
            case "cattivo":
                array = CATTIVI_RISP;
                break;
            case "sarcastico":
                array = SARCASTICI_RISP;
                break;
            default:
                array = ZOLTAR_RISP;
                break;
        }
        return array[random.nextInt(array.length)];// nextInt(n) restituisce un numero da 0 a n-1: è sempre un indice valido
    }

    // frase per il futuro, in base alla personalità di QUESTO client
    private String futuro() {
        String[] array;
        switch (personalita) {
            case "cattivo":
                array = CATTIVI_FUTURO;
                break;
            case "sarcastico":
                array = SARCASTICI_FUTURO;
                break;
            default:
                array = ZOLTAR_FUTURO;
                break;
        }
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
}
