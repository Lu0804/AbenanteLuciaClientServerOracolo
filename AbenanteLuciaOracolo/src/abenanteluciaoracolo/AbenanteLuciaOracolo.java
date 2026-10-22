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
        // Classe principale del server: accetta i client e assegna a ciascuno un Veggente.
        int porta = 5555;
        // Apre il server sulla porta indicata
        ServerSocket serverSocket = new ServerSocket(porta);
        System.out.println("Il veggente è in attesa di client...");

        int contatore = 0;// numera i client per riconoscerli nelle stampe
        while (true) {// Il ciclo infinito permette di accettare un numero illimitato di client
            Socket clientSocket = serverSocket.accept();// Si ferma qui finché non arriva un nuovo client
            contatore++;
            System.out.println("Client " + contatore + " connesso");
            // Ogni client ha il suo Veggente, con il suo socket e la sua personalità
            Veggente veggente = new Veggente();
            veggente.setSocket(clientSocket);
            veggente.setNumero(contatore);
            // Un thread per client: così i client lavorano in parallelo
            // e il main può tornare subito ad accettarne altri
            new Thread(veggente).start(); // un solo thread per client
        }
    }

}
