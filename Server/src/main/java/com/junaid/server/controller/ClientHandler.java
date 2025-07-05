package com.junaid.server.controller;


import com.junaid.server.ui.MainFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private PrintWriter out;        // to send message to client
    private BufferedReader in;      // to get message from client
    private final String clientId;
    private final Server server;
    private boolean isConnected = true;
    
    public ClientHandler(Socket socket, String clientId, Server server) {
        this.clientSocket = socket;
        this.clientId = clientId;
        this.server = server;
    }
    
    @Override
    public void run() {
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            
            
            out.println("Welcome! You are connected as " + clientId);
            
            String response;
            while (isConnected && (response = in.readLine()) != null) {
                System.out.println("[" + clientId + "] : " + response);
                MainFrame.testServerLogs.append(response);
            }
        }
        catch (IOException e) {
            System.err.println("Error handling client " + clientId + ": " + e.getMessage());
        }
        finally {
            closeConnection();
            server.removeClient(clientId);
        }
    }
    
    public void sendMessage(String message) {
        if (out != null && isConnected) {
            out.println(message);
        }
    }
    
    public void closeConnection() {
        isConnected = false;
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing connection for " + clientId + ": " + e.getMessage());
        }
    }
}


//server.broadcastMessage(inputLine, clientId);
