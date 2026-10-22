/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abenanteluciaclient;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import javax.swing.SwingUtilities;

/**
 *
 * @author abenante.lucia
 */
public class Reader implements Runnable {
// Thread che resta in ascolto dei messaggi del server.
// Quando ne arriva uno lo smsita all'Ascoltatore in base al tipo

    Socket socket;
    Ascoltatore ascoltatore;

    @Override
    public void run() {

        try {
            //legge i messaggi uno dopo l'altro finché la connessione cade
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);

            while (true) {
                // si blocca finché arriva un messaggio dal server
                String msgServer = dataInputStream.readUTF();

                // Divide il messaggio in due parti al primo "_":
                // parti[0] = tipo (RISPOSTA, FUTURO, INFO), parti[1] = testo
                String[] parti = msgServer.split("_", 2);
                String tipo = parti[0];
                String testo;
                // Se manca il testo dopo il tipo, usa una stringa vuota per evitare errori
                if (parti.length > 1) {
                    testo = parti[1];
                } else {
                    testo = "";
                }
                //a grafica si può modificare solo dal thread di Swing, 
				// quindi la chiamata all'Ascoltatore passa da invokeLater 
				//quando il thread Swing è libero esegui questo pezzo di codice
                SwingUtilities.invokeLater(() -> {
                    switch (tipo) {
                        case "RISPOSTA":
                            ascoltatore.onRisposta(testo);
                            break;
                        case "FUTURO":
                            ascoltatore.onFuturo(testo);
                            break;
                        default: // INFO
                            ascoltatore.onInfo(testo);
                            break;
                    }
                });
            }
        } catch (IOException e) {
            // readUTF lancia l'eccezione quando il server chiude la connessione
            System.out.println("Connessione chiusa");
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    // Imposta chi deve ricevere i messaggi
    public void setAscoltatore(Ascoltatore ascoltatore) {
        this.ascoltatore = ascoltatore;
    }
}
