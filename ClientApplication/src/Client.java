import java.io.*;
import java.util.*;

/**
 * Java Client/Server Application - Client Class
 * <p>
 * This is the main class for the Client application. The program starts by
 * presenting a menu to the user and then asking the user what command they
 * would like to run. Once the client receives a command from the user, it
 * creates the client threads and uses the start(), join() methods to kick off
 * the execution of the command.
 * <p>
 * Once all of the client have finished executing the command the program will
 * display the mean response times for each group of clients.
 * <p>
 *
 * @author Michael Whalen
 * @version 1.0, 10/8/2019
 */

public class Client {

    /**
     * The main method for the Client application
     *
     * @param args hostname and port number
     * @throws IOException          Thrown for failed interrupted I/O operations.
     * @throws InterruptedException
     */

    public static void main(String[] args) throws IOException, InterruptedException {

        // Array for increasing number of client threads
        int[] numThreads = new int[]{1, 5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
        ClientThread[] clients = null;
        ArrayList<Double> groupMedResponseTimes = new ArrayList<Double>();

        boolean ok = true; // Control variable for while loop

        // Check to see if there are two arguments specified.
        if (args.length != 2) {
            System.err.println(
                    "Error: hostname and port number required! " + "\nUsage: java Client <hostname> <port number>");
            System.exit(1);
        }

        // Assign the arguments to their respective variables.
        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        // display menu in a loop
        while (ok) {
            displayMenu();
            int command = getInput(); // validate input
            groupMedResponseTimes.clear(); // Clears the arrayList

            if (command == 7) {
                ok = false;
                System.out.println("Goodbye!");
                break; // exit the program!
            }

            for (int k = 0; k < 12; k++) {
                Thread[] threads = new Thread[numThreads[k]]; // Array to store the threads.
                clients = new ClientThread[numThreads[k]]; // Array to store ClientThread instances.

                System.out.println("\nRunning command with " + numThreads[k] + " clients");
                Thread.sleep(1000); // Pause

                // For loop to create an array of threads.
                for (int i = 0; i < numThreads[k]; i++) {
                    clients[i] = new ClientThread(hostName, portNumber, command);
                    threads[i] = new Thread(clients[i]); // Create new client thread
                }

                // For loop to start the threads and join them.
                for (int j = 0; j < numThreads[k]; j++) {
                    threads[j].start();
                }

                for (int m = 0; m < numThreads[k]; m++) {
                    try {
                        threads[m].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                // Compute the group of clients mean response time and add result to array.
                groupMedResponseTimes.add(computeGroupMeanResponseTimes(clients));
            }
            // Print all of the client groups mean response times.
            printTotalMeanResponseTimes(groupMedResponseTimes, numThreads);
        }
    }

    /**
     * This method displays a menu to the user.
     */
    public static void displayMenu() {
        System.out.println("Please select one of the following:");
        System.out.println("1. Host current Date and Time");
        System.out.println("2. Host uptime");
        System.out.println("3. Host memory use");
        System.out.println("4. Host Netstat");
        System.out.println("5. Host current users");
        System.out.println("6. Host running processes");
        System.out.println("7. Quit");
        System.out.print("\nEnter your command: ");
    }

    /**
     * This method gets the input from the user. It also provides input
     * verification, checking for invalid input.
     *
     * @return the command the user would like to run.
     */
    public static int getInput() {
        Scanner input = new Scanner(System.in);
        int choice = 0;
        boolean valid = false;

        while (!valid) {
            if (input.hasNextInt()) // verify the input is an integer
            {
                choice = input.nextInt();
                if (choice >= 1 && choice <= 7) { // if not between 1-7
                    valid = true;
                } else {
                    System.out.println("Invalid choice. Choices are: 1-7\n");
                    displayMenu(); // redisplay the menu
                }
            } else // if not, display error message and prompt again
            {
                System.out.println("Invalid choice. Choices are: 1-7\n");
                input.next();
                displayMenu(); // redisplay the menu
            }
        }
        return choice;
    }

    /**
     * This method finds the mean response time for a group of clients.
     * 1, 5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100
     *
     * @param clients A group of clients.
     * @return The mean response time.
     */
    public static double computeGroupMeanResponseTimes(ClientThread[] clients) {
        double totalResponse = 0;

        for (ClientThread client : clients) {
            totalResponse += client.getResponseTime();
        }
        return totalResponse / clients.length;
    }

    /**
     * This method will take all of the mean response times for each group
     * of clients and display them to the user.
     *
     * @param total      Each group of clients mean response time.
     * @param numThreads The number of clients in each group.
     */
    public static void printTotalMeanResponseTimes(ArrayList<Double> total, int[] numThreads) {
        int i = 0;

        for (Double responseTime : total) {
            System.out.printf("%5d client(s)  %.1f ms %n", numThreads[i], responseTime);
            i++;
        }
        System.out.println();
    }
}
