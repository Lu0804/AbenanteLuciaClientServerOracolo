/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abenanteluciaclient;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;
/**
 *
 * @author abenante.lucia
 */
public class Writer implements Runnable {

    Scanner scr = new Scanner(System.in);// per leggere la tastiera 
    Socket socket;

    @Override
    public void run() {

        try {
            OutputStream outputStream = socket.getOutputStream(); //l'uscita della socket (i byte che vanno la server)
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);//serve per writeUTF che poi manda la string
            while (true) { // per scrivere più messaggi
                System.out.println("scrivi");

                String msg = scr.nextLine();
                dataOutputStream.writeUTF(msg);

                if (msg.equals("exit")) { // per uscire
                    System.out.println("fine client");
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("errore");
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
