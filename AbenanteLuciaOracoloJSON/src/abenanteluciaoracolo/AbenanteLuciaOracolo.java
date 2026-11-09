package abenanteluciaoracolo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class AbenanteLuciaOracolo {

    // Si usa l'interfaccia come tipo: se un domani cambi libreria JSON,
    // basta sostituire ArchivioGson con un'altra implementazione.
    static final ArchivioFrasiInt archivio = new ArchivioGson();

    // Legge il file come testo e lo trasforma in oggetti con l'archivio
    static Frasi carica(String nomeFile) throws IOException {
        String json = Files.readString(Path.of(nomeFile), StandardCharsets.UTF_8);
        return archivio.deserializza(json);
    }

    public static void main(String[] args) throws IOException {
        Frasi frasi = carica("frasi.json");
        System.out.println("Frasi caricate dal file JSON.");

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
            veggente.setFrasi(frasi);

            new Thread(veggente).start();
        }
    }
}