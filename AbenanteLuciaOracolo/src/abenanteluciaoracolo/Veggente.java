/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package abenanteluciaoracolo;
import java.io.*;
import java.net.Socket;
/**
 *
 * @author lucia
 */
public class Veggente implements Runnable {
//il veggente è tipo il writer cambia che invece di scrivere su tatsiera prende delle risposte in una array
    //quindi avrà come sempre setSocket setNumero e visto che è un thread run
    //pee capire se sta scrivendo una domanda o sta scgelidno la personalità si usa SPLIT, Divide la stringa al primo spazio, in al massimo 2 pezzi
    // esempio part[0] PERSONA e part[1]sarcastico ="PERSONA" "sarcastico"
    //controllo errori
    

    Socket socket;//la porta che si passano
    int numero; //per riconoscere il client
    String personalita = null; // ogni client ha la sua personlità del veggente 

    @Override
    public void run() {

    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
