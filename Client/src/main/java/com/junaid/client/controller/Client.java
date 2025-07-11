package com.junaid.client.controller;

import com.junaid.client.model.Voter;
import com.junaid.client.service.LoginRequest;
import static com.junaid.client.service.LoginRequest.LoginStatus.*;
import com.junaid.client.ui.MainFrame;
import com.junaid.client.ui.model.OptionPane;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;

public class Client {
    private Socket clientSocket;

    private ObjectInputStream in;  
    private ObjectOutputStream out;
    
    private boolean isConnected = false;
    private Thread messageListener;
    
    private static Voter currentUser;
    
    public boolean startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);

            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            
            isConnected = true;
            
            System.out.println("[CLIENT] : Connected to server at " + ip + ":" + port);
            
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
                    String[] response_chunk = response.split(",");
                    System.out.println(response);
                    
                    if (response_chunk[0].equals("LOGIN")){
                        System.out.println("inside if of client response");
                        
                        if (response_chunk[1].equals("REJECTED")){
                            LoginRequest.setStatus(INVALID);
                            System.out.println("login rejected at client");
                        }
                        
                        if (response_chunk[1].equals("APPROVED")){
                            LoginRequest.setStatus(APPROVED);
                            System.out.println("login accepted at client");
                            
                            Object userObj = in.readObject();
                            if (userObj instanceof Voter voter){
                                currentUser = voter;
                                MainFrame.login();
                            }
                            else {
                                OptionPane.showMessage("Error while getting credentials");
                            }
                        }
                    }
                    
                    if (response_chunk[0].equalsIgnoreCase("VOTING")){
                        
                    }
                    
                    if (response_chunk[0].equalsIgnoreCase("RESULT")){
                        
                    }
                }
            } catch (IOException e) {
                if (isConnected) {
                    System.err.println("[CLIENT] : Error reading from server: " + e.getMessage());
                }
            } catch (ClassNotFoundException ex) {
                System.getLogger(Client.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
        messageListener.start();
    }
    
    public void sendMessage(Message message) {
        if (isConnected && out != null) {
            try {
                out.writeObject(message);
                out.flush();
            }
            catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        } else {
            System.err.println("[CLIENT] : Not connected to server");
        }
    }
    
    public void stopConnection() {
        isConnected = false;
        
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (clientSocket != null) clientSocket.close();
            System.out.println("[CLIENT] : Disconnected from server");
        }
        catch (IOException e) {
            System.err.println("[CLIENT] : Error closing connection: " + e.getMessage());
        }
    }
    
    public boolean isConnected() {
        return isConnected && clientSocket != null && !clientSocket.isClosed();
    }
    
    public static Voter getCurrentUser(){
        return currentUser;
    }
}


//startConnection(ip, port);


//if (isConnected()) {
//    stopConnection();
//}


//sendMessage(String);

