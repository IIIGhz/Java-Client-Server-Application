import java.net.*;
import java.io.*;

/**
 * Java Client/Server Application - ServerThread Class
 * 
 * <p>
 * This class will communicate with the client by reading from and writing to the socket.
 * <p>
 * 
 * @author Michael Whalen
 * @version 1.0, 10/8/2019
 */

public class ServerThread implements Runnable {
	
	private Socket clientSocket;
	private String OS;

	/**
	 * Constructor
	 * @param clientSocket, OS.
	 */
	
	public ServerThread(Socket clientSocket, String OS) {
		this.clientSocket = clientSocket;
		this.OS = OS;
	}
	
	@Override
	public void run() {
		// Create I/O Stream
		try(PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
			
			String input, output; // Variables used for I/O

			// Create a new ServerProtocol object. Which will handle execution of the
			// commmand's on the server.
			ServerProtocol sp = new ServerProtocol(OS);

			while ((input = in.readLine()) != null) {
				// Process the command received from client
				output = sp.processInput(input);

				// Send command result to client
				System.out.println("Server: Sending response to client.");
				// Split single output String into separate Strings
				for (String line : output.split("\n")) {
					out.print(line + "\n");
					out.flush();
				}
				// Once the server is finished sending the cmd results it sends the <END> flag.
				out.println("<END>");
				System.out.println("Server: End of response to client.\n");
			}
			clientSocket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}