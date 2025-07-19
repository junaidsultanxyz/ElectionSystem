package com.junaid.server.controller;


import com.junaid.server.repository.DAO;
import com.junaid.shared_library.election.*;
import com.junaid.shared_library.sockets.LoginRequest;

import com.junaid.shared_library.sockets.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    
    private ObjectInputStream in;
    private ObjectOutputStream out;
    
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
            
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());

            sendMessage(new Message("CONNECTION", "You are connected as " + clientId));
            
            Message response;
            while (isConnected && (response = (Message) in.readObject()) != null) {
                    
                if (response.getType().equalsIgnoreCase("LOGIN")){
                    LoginRequest loginRequest = (LoginRequest) response.getMessage();
                    
                    Voter voter = null;
                    try {
                        voter = DAO.validateLogin(loginRequest.getCnic(), loginRequest.getPassword());
                    }
                    catch (SQLException ex) {
                        System.out.println("[AUTH]: Login Failed {" + loginRequest.getCnic() + "}");
                        System.getLogger(ClientHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                    
                    if (voter != null){
                        loginRequest.setStatus(LoginRequest.LoginStatus.APPROVED);
                        sendMessage(new Message("LOGIN", voter));
                    }
                    else{
                        loginRequest.setStatus(LoginRequest.LoginStatus.REJECTED);
                        sendMessage(new Message("LOGIN", null));
                    }
                }
                
                
                
                if (response.getType().equalsIgnoreCase("PARTY")){
                    try {
                        ArrayList<Party> parties = DAO.displayAllParties();
                        sendMessage(new Message("PARTY", parties));
                        System.out.println("parties sent to client");
                    }
                    catch (SQLException ex) {
                        System.out.println("error while getting parties");
                        System.getLogger(ClientHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                }
                
                
                
                if (response.getType().equalsIgnoreCase("VOTE")){
                    if (response.getMessage() == null){
                        System.out.println("unexpected error happened");
                        sendMessage(new Message("VOTE", null));
                        
                    }
                    else {
                        Vote[] votes = (Vote[]) response.getMessage();
                        DAO.castVote(votes[0].getVoterCNIC(), votes[0].getPartyCode(), votes[0].getVoteType(), votes[0].getTimestamp()); // mna vote
                        DAO.castVote(votes[1].getVoterCNIC(), votes[1].getPartyCode(), votes[1].getVoteType(), votes[1].getTimestamp()); // mpa vote
                        
                        System.out.println("[SERVER]: vote casted successful");
                        
                        sendMessage(new Message("VOTE", votes));
                    }
                }
                   
            }
        }
        catch (IOException e) {
            System.err.println("Error handling client " + clientId + ": " + e.getMessage());
        } catch (ClassNotFoundException ex) {
            System.getLogger(ClientHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        finally {
            closeConnection();
            server.removeClient(clientId);
        }
    }
    
    public void sendMessage(Message message) {
        if (out != null && isConnected) {
            try {
                out.writeObject(message);
                out.flush();
            } 
            catch (IOException ex) {
                System.getLogger(ClientHandler.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
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