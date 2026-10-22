/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abenanteluciaclient;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;

/**
 *
 * @author abenante.lucia
 */
public class Writer {
// Si occupa solo di inviare messaggi al server.
// Non è un thread: i metodi vengono chiamati quando l'utente preme un bottone.

    Socket socket; // il collegamento con il server
    DataOutputStream dataOutputStream;// permette di inviare stringhe con writeUTF

    // Riceve il socket e prepara il flusso di uscita (i byte che vanno al servr)
    public void setSocket(Socket socket) throws IOException {
        this.socket = socket;
        OutputStream outputStream = socket.getOutputStream(); // i byte che vanno al server
        dataOutputStream = new DataOutputStream(outputStream); // serve per writeUTF
    }

    // Invia una stringa al server.
    // Se l'invio fallisce stampa un errore invece di bloccare il programma.
    public void invia(String msg) {
        try {
            dataOutputStream.writeUTF(msg);
        } catch (IOException e) {
            System.out.println("errore invio");
        }
    }

}
