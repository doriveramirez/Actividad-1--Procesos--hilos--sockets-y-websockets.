/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AlumnadoTarde
 */
public class ClientCommunication extends Thread {

    Socket socket;

    public ClientCommunication(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        serveClient(socket);
    }

    private static void serveClient(Socket socket) {
        BufferedReader br = null;
        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            Scanner sc = new Scanner(System.in);

            String line;
            String lineToSendToClient;

            do {
                line = br.readLine();
                System.out.println("Client said: " + line);
                lineToSendToClient = "Nothing relevant has sent.";
                if (line.equalsIgnoreCase("color")) {
                    int number = (int) (Math.random() * 5 + 1);
                    switch (number) {
                        case 1:
                            lineToSendToClient = "yellow";
                            break;
                        case 2:
                            lineToSendToClient = "red";
                            break;
                        case 3:
                            lineToSendToClient = "black";
                            break;
                        case 4:
                            lineToSendToClient = "grey";
                            break;
                        case 5:
                            lineToSendToClient = "white";
                            break;

                    }
                }
                if (line.equalsIgnoreCase("phrase")) {
                    int number = (int) (Math.random() * 5 + 1);
                    switch (number) {
                        case 1:
                            lineToSendToClient = "You Can't Judge a Book By Its Cover";
                            break;
                        case 2:
                            lineToSendToClient = "If You Can't Stand the Heat, Get Out of the Kitchen";
                            break;
                        case 3:
                            lineToSendToClient = "Put a Sock In It";
                            break;
                        case 4:
                            lineToSendToClient = "Throw In the Towel";
                            break;
                        case 5:
                            lineToSendToClient = "Don't Look a Gift Horse In The Mouth";
                            break;
                    }
                }
                bw.write(lineToSendToClient);
                bw.newLine();
                bw.flush();
            } while (line != "FIN");

        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
