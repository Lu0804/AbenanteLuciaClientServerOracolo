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
public class Reader implements Runnable{
Socket socket;
    @Override
    public void run() {
         try{
             InputStream inputStream = socket.getInputStream();// in entrta dai socket (i byte che arrivano dal server)
            DataInputStream dataInputStream = new DataInputStream(inputStream);// per poter usare readUTF()
            
            while(true){//per leggerre più messaggi
                //si blocca finchè un messaggio non arriva dal server
                String msgServer = dataInputStream.readUTF();
            System.out.println("Risposta dal server: " + msgServer );
            }
         }catch(IOException e){
             System.out.println("Connessione chiusa");
         }
         
         
    }
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
   
}
