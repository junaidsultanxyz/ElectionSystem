package com.junaid.server.controller;

import com.junaid.shared_library.sockets.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    private ServerSocket serverSocket;
    private boolean isRunning = false;
    private final Map<String, ClientHandler> clients = new ConcurrentHashMap<>();
    private int clientCounter = 0;
    
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        isRunning = true;
        System.out.println("Server started on port " + port);
        
        while (isRunning) {
            try {
                Socket clientSocket = serverSocket.accept();
                String clientId = "client-" + (++clientCounter);
                
                ClientHandler clientHandler = new ClientHandler(clientSocket, clientId, this);
                clients.put(clientId, clientHandler);
                
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
                
                System.out.println(clientId + " connected. Total clients: " + clients.size());
            } catch (IOException e) {
                if (isRunning) {
                    System.err.println("Error accepting client connection: " + e.getMessage());
                }
            }
        }
    }
    
    public void stop() throws IOException {
        isRunning = false;
        if (serverSocket != null) {
            serverSocket.close();
        }
        
        // Close all client connections
        for (ClientHandler client : clients.values()) {
            client.closeConnection();
        }
        clients.clear();
        System.out.println("Server stopped");
    }
    
    public void removeClient(String clientId) {
        clients.remove(clientId);
        System.out.println(clientId + " disconnected. Total clients: " + clients.size());
    }
    
    public Set<String> getConnectedClients() {
        return new HashSet<>(clients.keySet());
    }
    
    public boolean sendServerMessageToClient(String targetClientId, Object message) {
        ClientHandler targetClient = clients.get(targetClientId);
        if (targetClient != null) {
            targetClient.sendMessage(new Message("PRIVATE", message));
            return true;
        }
        return false;
    }
    
    
    public void broadcastMessage(String message) {
        for (ClientHandler client : clients.values()) {
            client.sendMessage(new Message("ANNOUNCEMENT", message));
        }
    }
    
    
}
