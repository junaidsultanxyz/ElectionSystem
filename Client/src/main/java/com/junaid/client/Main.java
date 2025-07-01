
package com.junaid.client;

import com.junaid.client.controller.Client;
import com.junaid.client.ui.MainFrame;
import java.io.IOException;


public class Main {
    private static final int PORT  = 8080;
    private static final String IP = "localhost";
    
    static Client client;
    
    public static void main(String[] args) throws IOException {
        client = new Client();
        
        
        Thread connectionThread = new Thread(() -> {
            client.startConnection(IP, PORT);
        });
        
        
        connectionThread.setDaemon(true);
        connectionThread.start();
        
        MainFrame.startGUI();
    }
    
    public static Client getClient(){
        return client;
    }
}
