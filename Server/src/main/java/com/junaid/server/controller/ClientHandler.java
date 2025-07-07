package com.junaid.server.controller;


import com.junaid.server.model.Voter;
import com.junaid.server.service.LoginService;
import com.junaid.server.ui.MainFrame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    
    private PrintWriter out;        // to send string message to client
    private BufferedReader in;      // to get string message from client
    
    private ObjectInputStream objIn;
    private ObjectOutputStream objOut;
    
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
            
            objOut = new ObjectOutputStream(clientSocket.getOutputStream());
            objIn = new ObjectInputStream(clientSocket.getInputStream());

            out.println("Welcome! You are connected as " + clientId);
            
            String response;
            while (isConnected && (response = in.readLine()) != null) {
                System.out.println("[" + clientId + "] : " + response);
                MainFrame.testServerLogs.append(response);
                
                String[] response_chunk = response.split(",");
                    
                    if (response_chunk[0].equalsIgnoreCase("LOGIN")){
                        Voter voter = LoginService.validateLogin(response_chunk[1], response_chunk[2]);
                        if (voter != null){
                            sendMessage(LoginService.sendApproved());
                            sendObject(voter);
                        }
                        else
                            sendMessage(LoginService.sendRejected());
                    }
                    
                    if (response_chunk[0].equalsIgnoreCase("VOTING")){
                        
                    }
                    
                    if (response_chunk[0].equalsIgnoreCase("RESULT")){
                        
                    }
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
    
    public void sendObject (Object obj){
        try {
            if (obj != null && isConnected){
                objOut.writeObject(obj);
                objOut.flush();
            }
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    public void closeConnection() {
        isConnected = false;
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (objIn != null) objIn.close();
            if (objOut != null) objOut.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing connection for " + clientId + ": " + e.getMessage());
        }
    }
}


//server.broadcastMessage(inputLine, clientId);