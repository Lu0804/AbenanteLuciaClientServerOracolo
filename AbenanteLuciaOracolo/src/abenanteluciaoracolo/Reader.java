/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abenanteluciaoracolo;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import static java.lang.System.in;
import java.net.Socket;
/**
 *
 * @author lucia
 */
public class Reader implements Runnable {
     Socket socket;
    int numero;

    @Override
    public void run() {
        try {    // flusso in entrata del socket (i byte che arrivano dal client)
            InputStream inputStream = socket.getInputStream();
            // serve per usare readUTF
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            while (true) { // per leggere più messaggi
                // si blocca finché arriva un messaggio dal client
                String msgClient = dataInputStream.readUTF();
                // NUOVO: stampa anche il numero del client per capire chi ha scritto
                System.out.println("Messaggio dal client " + numero + ": " + msgClient);
            
            }
        } catch (IOException e) {
            System.out.println("Client " + numero + " disconnesso");
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
    
}
