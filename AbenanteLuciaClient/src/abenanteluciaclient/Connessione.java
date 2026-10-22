/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abenanteluciaclient;

import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author lucia
 */
public class Connessione {

    // Gestisce la comunicazione con il server, così la form non deve conoscere
// socket, Reader, Writer e il formato dei messggi.
    Socket socket;
    Writer writer;// invia i messaggi al server
    Reader reader;  // riceve i messaggi dal server

    public void connetti(String ip, int porta, Ascoltatore ascoltatore) throws IOException {
        socket = new Socket(ip, porta);

        writer = new Writer();
        writer.setSocket(socket);

        reader = new Reader();
        reader.setSocket(socket);
        reader.setAscoltatore(ascoltatore);
        new Thread(reader).start();
    }

    // Invia una domanda nel formato del protocollo: DOMANDA_testo
    public void domanda(String testo) {
        writer.invia("DOMANDA_" + testo);
    }

    // Chiede la previsione del futuro: FUTURO
    public void futuro() {
        writer.invia("FUTURO");
    }

    // Comunica la personalità scelta: PERSONA_cattivo
    public void persona(String scelta) {
        writer.invia("PERSONA_" + scelta);
    }
// Avvisa il server che il client sta uscendo.

    public void chiudi() {
        if (writer != null) { // se la connessione è fallita writer è null
            writer.invia("EXIT");
        }
    }
}
