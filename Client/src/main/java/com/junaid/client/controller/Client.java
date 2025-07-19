package com.junaid.client.controller;

import com.junaid.client.Main;
import com.junaid.client.SessionData;
import com.junaid.client.ui.MainFrame;
import com.junaid.client.util.Popup;

import com.junaid.shared_library.election.*;
import com.junaid.shared_library.sockets.*;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Client {
    private Socket clientSocket;
    
    private ObjectInputStream in;  
    private ObjectOutputStream out;
    
    private boolean isConnected = false;
    private Thread messageListener;
    
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
                Message response;
                System.out.println("is connected inside listener: " + isConnected());
                while (isConnected && (Message) in.readObject() != null) {
                    
                    response = (Message) in.readObject();
                    
                    String type = (String) response.getType();
                    Object message = response.getMessage();
                    
                    System.out.println("msg type: " + type);
                    
                    if (type.equalsIgnoreCase("PARTY")){
                        System.out.println("party msg received");

                        if (message == null){
                            System.out.println("error occured while getting vote msg");
                        }
                        else if (message instanceof ArrayList parties){
                            SessionData.setPartyData(parties);
                            System.out.println("parties load success");
                            System.out.println(parties.toString());
                        }
                        else {
                            System.out.println("unknown errors while loading parties");
                        }
                    }
                    else if (type.equalsIgnoreCase("LOGIN")){
                        if (message == null){
                            System.out.println("[AUTH]: login rejected");
                        }
                        else if (message instanceof Voter user){
                            SessionData.setCurrentUser(user);

                            Main.getUI().login();
                            Main.getUI().LoadVotingScreen();
                            Main.getUI().LoadVotingTable();
                            Main.getUI().LoadVotingScreen();
                        }
                    }
                    else if (type.equalsIgnoreCase("VOTE")){
                        if (message == null){
                            System.out.println("error occured while getting vote msg");
                        }
                        else if (message instanceof Vote[] votes){
                            Popup.showMessage("vote has been successfully casted", "Vote Casted");

                            SessionData.setVoteData(votes);

                            Main.getUI().LoadPostVoteScreen();
                        }
                    }
                }
            } catch (IOException ex) {
                System.getLogger(Client.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
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
        }
        else{
            if (isConnected()) System.out.println("connected");
            
            if (out == null) System.out.println("out is null");
            if (in == null) System.out.println("in is null");
            
            System.err.println("[CLIENT] awdwad: Not connected to server");
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
}


//startConnection(ip, port);


//if (isConnected()) {
//    stopConnection();
//}


//sendMessage(String);

