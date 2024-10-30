//** Alex Welk - CMSC440 PING Program **//
import java.net.*;
import java.util.Random;

class PINGServer {

    public static void main(String argv[]) throws Exception
    {
	// socket variables
	DatagramSocket serverSocket;
	byte[] receiveData = new byte[1024];
	byte[] sendData = new byte[1024];
	InetAddress IPAddress;
	int clientPort;

	// server variables
	String serverSentence;

	// command-line arguments
	int port;
	int loss;

	// process command-line arguments
	if (argv.length < 2) {
	    System.out.println ("Usage: java UDPServer port\n");
	    System.exit (-1);
	}
	port = Integer.parseInt(argv[0]);
	loss = Integer.parseInt(argv[1]); //% of packet drop
		//testing for valid port number
		if(port > 65536 || port < 0) {
			System.out.println("Invalid port");
			System.exit(-1);
		}

	// Create welcoming socket using given port
		try {
			serverSocket = new DatagramSocket(port);
		}catch(SocketException e) {
			System.out.println("ERR - cannot create PINGServer socket using port number " + port);
			serverSocket = new DatagramSocket(port);
			System.exit(-1);
		}

	System.out.println("PINGServer started with server IP: 10.0.0.2, port: " + port + "..."); //starting print statement

	// While loop to handle arbitrary sequence of clients making requests
	while (true) {

	    // Waits for some client to send a packet
	    DatagramPacket receivePacket = new DatagramPacket(receiveData,receiveData.length);
	    serverSocket.receive(receivePacket);
	    // Question: What could happen if we didn't 
	    // only convert receivePacket.getLength() bytes?
		String clientSentence = new String(receivePacket.getData(), 0, receivePacket.getLength());

	    // Convert to all caps
	    serverSentence = clientSentence.toUpperCase();

		if((Math.random()*100) + 1 < loss) {
			continue;
		}else {
			// Write output line to socket
			IPAddress = receivePacket.getAddress();
			clientPort = receivePacket.getPort();
			sendData = serverSentence.getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, clientPort);
			serverSocket.send(sendPacket);
			System.out.println(serverSentence);

		}

	}
	//  end while; loop back to accept a new client connection

    } // end main

} // end class
