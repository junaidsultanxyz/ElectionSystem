package com.junaid.server;

import com.junaid.server.controller.Server;
import com.junaid.server.ui.MainFrame;
import java.io.IOException;


public class Main {
    private static final int PORT  = 8080;
//    private static final String IP = "localhost";
    
    static Server server;
    
    public static void main(String[] args) throws IOException {
        server = new Server();
        
        Thread connectionThread = new Thread(() -> {
            try {
                server.start(PORT);
            }
            catch (IOException ex) {
                System.getLogger(Main.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });
        
        connectionThread.setDaemon(true);
        connectionThread.start();
        
        MainFrame.startGUI();
    }
    
    public static Server getServer(){
        return server;
    }
}
