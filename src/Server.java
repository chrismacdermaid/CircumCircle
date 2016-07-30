import java.net.*;
import java.io.*;
public class Server {

	private static int port = 4309;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ServerSocket serverSocket = null;
		try{
		serverSocket = new ServerSocket(port);
		}catch (IOException e) {
			System.err.println("Could not listen on port: 4309.");
            System.exit(1);
		}
		Socket clientSocket = null;
		try{
			
			 while (true) {
			      Socket sock = serverSocket.accept();
			      new SocketThread(sock).start();
			    }
			
		//clientSocket = serverSocket.accept();
       // System.out.println("Got something");
        
        
        
		}catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
		
        
        //clientSocket.close();
        serverSocket.close();
	}

}
