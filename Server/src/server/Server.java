
package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.sound.sampled.Port;


public class Server {
    private ServerSocket serverSocket;
    public static final int port = 8085;
    
    public Server(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
    }
    
    public void startServer(){
        try{
            while (!serverSocket.isClosed())
            {
                Socket socket = serverSocket.accept();
                System.out.println("New Client Connected");
                ClientHandler clientHandler = new ClientHandler();
                
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch(IOException e){
            System.out.println("[ERROR]: " + e.getMessage());
        }
    }
    
    public void closeServer(){
        try {
            if (serverSocket != null){
                serverSocket.close();
            }
        }
        catch(IOException e){
            System.out.println("[ERROR]: " + e.getMessage());
        }
    }
    
    
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
