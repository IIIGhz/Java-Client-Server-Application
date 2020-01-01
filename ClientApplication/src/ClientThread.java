import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Java Client/Server Application - ClientThread Class
 * <p>
 * This class is used to create a client thread that will establish a connection
 * with the server, send a command to the server, and print the response from
 * the server, along with the response time.
 * <p>
 * 
 * @author Michael Whalen
 * @version 1.0, 10/8/2019
 */

public class ClientThread implements Runnable {

	private String hostName;
	private int portNumber;
	private int command;

	// Time tracking variables
	private double startTime;
	private double endTime;
	private double responseTime;

	/**
	 * Default constructor
	 */
	public ClientThread() {
	}

	/**
	 * Constructor
	 * 
	 * @param hostName   The hostname of the server.
	 * @param portNumber The port number that the server is listening on.
	 * @param command    The command that will be sent to the server.
	 */

	public ClientThread(String hostName, int portNumber, int command) {
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.command = command;
	}

	/**
	 * Used to return the hostname of the server
	 * 
	 * @return Hostname of the server
	 */

	private String getHostname() {
		return this.hostName;
	}

	/**
	 * Used to return the port number that the server is listening on.
	 * 
	 * @return The port number that the server is listening on.
	 */

	private int getPortNumber() {
		return this.portNumber;
	}

	public double getResponseTime() {
		return this.responseTime;
	}

	@Override
	public void run() {
		// Establish connection with the server and create the I/O streams
		System.out.println("Connecting to server...");
		try (Socket socket = new Socket(hostName, portNumber);
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

			System.out.println("Connected to server at: " + getHostname() + ":" + getPortNumber());
			System.out.println("Sending request to server: " + command + "\n");

			String responseFromServer;

			// Send command to the server and set startTime.
			startTime = System.currentTimeMillis();
			out.println(this.command);

			// Get the response from the server.
			// When the client receives the <END> flag, the client is finished.
			StringBuilder sb = new StringBuilder();

			while ((responseFromServer = in.readLine()) != null) {
				if (responseFromServer.contains("<END>")) {
					// Set end time, calculate total response time, and print.
					endTime = System.currentTimeMillis();
					responseTime = endTime - startTime;
					System.out.println("\nResponse Time: " + responseTime + " ms\n");
					break;
				} else {
					// Build server response.
					sb.append(responseFromServer + "\n");
				}
			}
			// Print full response from server.
			System.out.println(sb.toString());

			// Close the connection to the server.
			socket.close();

		} catch (UnknownHostException e) {
			System.err.println("Don't know about the host " + getHostname());
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Could not establish a connection with " + getHostname() + ":" + getPortNumber());
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}
}
