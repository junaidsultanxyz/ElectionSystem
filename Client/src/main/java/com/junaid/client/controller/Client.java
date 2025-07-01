package com.junaid.client.controller;

import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean isConnected = false;
    private Thread messageListener;
    
    
    public boolean startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            isConnected = true;
            
            System.out.println("[CLIENT] : Connected to server at " + ip + ":" + port);
            
            // Start the message listener thread
            startMessageListener();
            
            return true;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "[CLIENT] : Failed to connect to server: " + e.getMessage());
            return false;
        }
    }
    
    private void startMessageListener() {
        messageListener = new Thread(() -> {
            try {
                String response;
                while (isConnected && (response = in.readLine()) != null) {
                    System.out.println("[Server] : " + response);
                    
                    final String msg = response; // make effectively final
                    
                }
            } catch (IOException e) {
                if (isConnected) {
                    System.err.println("[CLIENT] : Error reading from server: " + e.getMessage());
                }
            }
        });
        messageListener.start();
    }
    
    public void sendMessage(String message) {
        if (isConnected && out != null) {
            out.println(message);
        } else {
            System.err.println("[CLIENT] : Not connected to server");
        }
    }
    
    public void stopConnection() {
        isConnected = false;
        
        try {
            if (messageListener != null && messageListener.isAlive()) {
                messageListener.interrupt();
            }
            
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
            
            System.out.println("[CLIENT] : Disconnected from server");
        } catch (IOException e) {
            System.err.println("[CLIENT] : Error closing connection: " + e.getMessage());
        }
    }
    
    public boolean isConnected() {
        return isConnected && clientSocket != null && !clientSocket.isClosed();
    }
    
}


//startConnection(ip, port);


//if (isConnected()) {
//    stopConnection();
//}


//sendMessage(String);

