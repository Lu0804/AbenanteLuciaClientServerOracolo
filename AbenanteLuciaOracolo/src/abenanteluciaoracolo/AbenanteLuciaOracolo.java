/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package abenanteluciaoracolo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author lucia
 */
public class AbenanteLuciaOracolo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
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

        new Thread(veggente).start(); // un solo thread per client
    }
    }
    
}
