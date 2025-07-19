
package com.junaid.client;

import com.junaid.client.controller.Client;
import com.junaid.client.ui.MainFrame;
import java.io.IOException;
import javax.swing.SwingUtilities;


public class Main {
    private static final int PORT  = 8080;
    private static final String IP = "localhost";
    
    static Client client;
    static MainFrame ui;
    
    public static void main(String[] args) throws IOException {
        client = new Client();
        
        
        Thread connectionThread = new Thread(() -> {
            client.startConnection(IP, PORT);
        });
        
        
        connectionThread.setDaemon(true);
        connectionThread.start();
        
        ui = new MainFrame();
        ui.setVisible(true);
        SwingUtilities.invokeLater(() -> {
            ui.setVisible(true);
        });
    }
    
    public static Client getClient(){
        return client;
    }
    
    public static MainFrame getUI(){
        return ui;
    }
}
