/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package abenanteluciaclient;
import java.io.*;
import java.net.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
/**
 *
 * @author abenante.lucia
 */
public class AbenanteLuciaClient {

    /**
     * @param args the command line argumentsS
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        String ip = "localhost"; //localhost-10.205.0.115
        int porta = 5555;

        Socket socket = new Socket(ip, porta);//connessione con il server 
       
        Writer writer = new Writer();
        writer.setSocket(socket);
        Reader reader = new Reader();
        reader.setSocket(socket);

        Thread threadWriter = new Thread(writer);
        Thread threadReader = new Thread(reader);

        threadWriter.start();
        threadReader.start();

        threadWriter.join(); //aspetta che finiscono 
        threadReader.join();

        socket.close();
        

    }

}
