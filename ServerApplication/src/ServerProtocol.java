import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Java Client/Server Application - ServerProtocol Class
 * <p>
 * This class will handle all of the commands the client sends the server.
 * The server protocol supports Windows and Linux based machines.
 * <p>
 * 
 * @author Michael Whalen
 * @version 1.0, 10/8/2019
 */

public class ServerProtocol {

	String OS;

	/**
	 * Default constructor
	 */

	public ServerProtocol(String OS) {
		this.OS = OS;
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

		if (OS.contentEquals("linux")) {
			if (input.equals("1")) {
				output = getInfo("date", OS); // Linux: get host current Date and Time
				System.out.println("Client: Requesting current date and time.");
			} else if (input.equals("2")) {
				output = getInfo("uptime", OS); // Linux: get host uptime
				System.out.println("Client: Requesting uptime.");
			} else if (input.equals("3")) {
				output = getInfo("free", OS); // Linux: get host memory usage
				System.out.println("Client: Requesting memory usage.");
			} else if (input.equals("4")) {
				output = getInfo("netstat", OS); // Linux: get host Netstat
				System.out.println("Client: Requesting netstat.");
			} else if (input.equals("5")) {
				output = getInfo("who", OS); // Linux: get host current users
				System.out.println("Client: Requesting current users.");
			} else if (input.equals("6")) {
				output = getInfo("ps -A", OS); // Linux: get host running processes
				System.out.println("Client: Requesting running processes.");
			}
		} else if (OS.contentEquals("windows")) {
			if (input.equals("1")) {
				output = getInfo("date /t", OS); // Windows: get host current Date and Time
				System.out.println("Client: Requesting current date and time.");
			} else if (input.equals("2")) {
				output = getInfo("systeminfo | find \"System Boot Time:\"", OS); // Windows: get host uptime
				System.out.println("Client: Requesting uptime.");
			} else if (input.equals("3")) {
				output = getInfo("systeminfo |find \"Available Physical Memory\"", OS); // Windows: get host memory usage
				System.out.println("Client: Requesting memory usage.");
			} else if (input.equals("4")) {
				output = getInfo("netstat", OS); // Windows: get host Netstat
				System.out.println("Client: Requesting netstat.");
			} else if (input.equals("5")) {
				output = getInfo("query user", OS); // Windows: get host current users
				System.out.println("Client: Requesting current users.");
			} else if (input.equals("6")) {
				output = getInfo("tasklist", OS); // Windows: get host running processes
				System.out.println("Client: Requesting running processes.");
			}
		}
		return output;
	}

	/**
	 * This method runs the appropriate linux/windows command on the server, and returns the
	 * results.
	 * 
	 * @param cmd The linux/windows command that the server needs to run.
	 * @return The results of the command.
	 */

	private static String getInfo(String cmd, String OS) {

		Process process = null;
		
		try {
			if (OS.contentEquals("linux")) {
				process = Runtime.getRuntime().exec(cmd);
			} else if (OS.contentEquals("windows")) {
				process = Runtime.getRuntime().exec("cmd /C " + cmd);
			}

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
