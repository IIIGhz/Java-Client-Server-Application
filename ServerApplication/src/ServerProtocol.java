import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Java Client/Server Application - ServerProtocol Class
 * <p>
 * This class will handle all of the commands the client sends the server.
 * <p>
 * 
 * @author Michael Whalen
 * @version 1.0, 10/8/2019
 */

public class ServerProtocol {

	/**
	 * Default constructor
	 */

	public ServerProtocol() {
	}

	/**
	 * This method takes the input from the client and send the appropriate linux
	 * command to the getInfo method.
	 * 
	 * @param input The command sent from the client. (1-7)
	 * @return The results of the linux command.
	 */

	public String processInput(String input) {

		String output = null;

		if (input.equals("1")) {
			output = getInfo("date"); // Linux: get host current Date and Time
			System.out.println("Client: Requesting current date and time.");
		} else if (input.equals("2")) {
			output = getInfo("uptime"); // Linux: get host uptime
			System.out.println("Client: Requesting uptime.");
		} else if (input.equals("3")) {
			output = getInfo("free"); // Linux: get host memory usage
			System.out.println("Client: Requesting memory usage.");
		} else if (input.equals("4")) {
			output = getInfo("netstat"); // Linux: get host Netstat
			System.out.println("Client: Requesting netstat.");
		} else if (input.equals("5")) {
			output = getInfo("who"); // Linux: get host current users
			System.out.println("Client: Requesting current users.");
		} else if (input.equals("6")) {
			output = getInfo("ps -A"); // Linux: get host running processes
			System.out.println("Client: Requesting running processes.");
		}
		return output;
	}

	/**
	 * This method runs the appropriate linux command on the server, and returns the
	 * results.
	 * 
	 * @param cmd The linux command that the server needs to run.
	 * @return The results of the command.
	 */

	private static String getInfo(String cmd) {

		try {
			Process process = Runtime.getRuntime().exec(cmd);

			StringBuilder cmdResult = new StringBuilder();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String line;
			while ((line = reader.readLine()) != null) {
				cmdResult.append(line + "\n");
			}
			return cmdResult.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
