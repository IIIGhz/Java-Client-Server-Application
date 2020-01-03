import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Java Client/Server Application - Server Class
 *
 * <p>
 * This Server class will loop forever, listening for new client connection
 * request. A new server thread is spawned for each client connection.
 * <p>
 *
 * @author Michael Whalen
 * @version 1.0, 10/8/2019
 */

public class Server {

    /**
     * @param args The port the server will listen on.
     * @throws IOException Thrown for failed interrupted I/O operations.
     */

    public static void main(String[] args) throws IOException {

        // Check to see what OS the Server App is being run on.
		String OS = checkOS();

        // Check to see if a port number was specified in the arguments.
        if (args.length != 1) {
            System.err.println("Error: Specify a port number!");
            System.exit(1);
        }

        // Declare the int variable for the port number.
        // This is specified by the argument.
        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            // Listen for connection request
            System.out.println("Listening on port: " + portNumber);
            System.out.println(OS);

            while (listening) {
                new Thread(new ServerThread(serverSocket.accept(), OS)).start();
                System.out.println("Client connected to the server.");
            }

        } catch (IOException e) {
            System.out.println(
                    "Exception caught when trying to listen on port " + portNumber + " or listening for a connection");
            System.out.println(e.getMessage());
        }
    }

    private static String checkOS() {
        String detectedOS = System.getProperty("os.name").toLowerCase();
        String OS = "";

        if (detectedOS.indexOf("win") >= 0) {
            OS = "windows";
        } else if (detectedOS.indexOf("nix") >= 0 || detectedOS.indexOf("nux") >= 0 || detectedOS.indexOf("aix") > 0) {
            OS = "linux";
        }
		return OS;
	}
}
