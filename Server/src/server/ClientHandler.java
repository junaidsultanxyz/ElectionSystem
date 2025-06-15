package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class ClientHandler implements Runnable{
    private Socket socket;
    
    private BufferedReader reader;
    private BufferedWriter writer;
    
    private String clientName;
    
    public ClientHandler(Socket socket){
        try {
            this.socket = socket;
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientName = reader.readLine();
            
            ClientCollection.clientHandler.add(this);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void run() {
     
    }
    
}
