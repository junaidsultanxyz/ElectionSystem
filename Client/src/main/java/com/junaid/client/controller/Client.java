package com.junaid.client.controller;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    private final int PORT  = 8080;
    private final String IP = "localhost";
    
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    
    public Client(Socket socket){
        try{
            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));            
        }
        catch (IOException e){
            System.out.println(e.getMessage());
            closeAll(socket, reader,writer);
        }
    }
    
    public void startListening(){
        Thread listenerThread = new Thread(() -> {
            try {
                while (true) {
//                    Object obj = reader.readObject();
//                    if (obj instanceof ServerMessage) {
//                        handleServerMessage((ServerMessage) obj); // update GUI via SwingUtilities.invokeLater()
//                    }
                }
            } catch (Exception e) {

            }
        });
        listenerThread.setDaemon(true); // set the thread to run in background
        listenerThread.start();
    }
    
    
    public void sendMessage(String message) {
        new Thread(() -> {
            try {
                writer.write(message);
                writer.newLine();
                writer.flush();
            } 
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }).start();
    }

    
    
    
    private void closeAll(Socket socket, BufferedReader reader, BufferedWriter writer){
        try {
            if (writer != null)
                writer.close();
            if (reader != null)
                reader.close();
            if (socket != null)
                socket.close();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    
}
