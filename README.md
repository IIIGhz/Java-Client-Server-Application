# Java Client/Server Application
The Java Client/Server Application features two separate applications, one run on the client machine and one run on a server. The client application queries the server for various system information and measures the response time for each request. This project has been developed 

## Getting Started
The following instructions get the client and server application up and running on your machine.

### Prerequisites
* Client Application - will run on any operating system (OS) with a Java Virtual Machine (JVM).
* Server Application - will run on any OS but will only work with the Linux OS. Multiple OS support coming soon.

### Running the Client/Server Applications

**Starting the Server:**
```java -cp bin/ Server 4444```
- The `4444` argument specifies the port that the Server will listen on.

**Starting the Client**
```java -cp bin/ Client 127.0.0.1 4444```
- The first argument `127.0.0.1` specifies the IP address of the Server. *In this case the Server is running on the same machine as the Client.
- The last argument `4444` specifies the port that the Server is listening on.

## Built With
* [Java](https://www.oracle.com/java/) - The web framework used
