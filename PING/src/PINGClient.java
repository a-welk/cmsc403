//** Alex Welk - CMSC440 PING Program **//
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;

class PINGClient {

    public static void main(String argv[]) throws Exception
    {
	// socket variables
	DatagramSocket clientSocket = null;
	DatagramPacket sendPacket = null;
	DatagramPacket receivePacket = null;
	byte[] receiveData = new byte[1024];
		int minSize = 150;
		int maxSize = 300;
		int packetSize;
	byte[] sendData = new byte[1024];
	InetAddress IPAddress;

	// client variables
	String clientSentence, serverSentence;
	BufferedReader inFromUser;

	// command-line arguments
	int port;
	String server;
	int clientID;
	int numPackets;
	int wait;
	int sequenceNum = 1;
	int numReceived = 0;
	double total = 0;
	byte totalByte = 0;

	// process command-line arguments
	if (argv.length < 5) {
	    System.out.println ("Usage: java UDPServer hostname port\n");
	    System.exit (-1);
	}
	server = argv[0];
	port = Integer.parseInt(argv[1]);
	clientID = Integer.parseInt(argv[2]);
	numPackets = Integer.parseInt(argv[3]);
	wait = Integer.parseInt(argv[4]);
      
	// Create client socket to destination
	try {
		IPAddress = InetAddress.getByName(server);
	}catch (UnknownHostException e) {
		IPAddress = InetAddress.getByAddress(server.getBytes());
	}

	try {
		clientSocket = new DatagramSocket();
		clientSocket.setSoTimeout(wait * 1000);
	}catch (SocketException e) {
		System.out.println("ERR - failed to create socket");
		System.exit(-1);
	}



	//starting output
		System.out.println("PINGClient started with service IP: " + IPAddress + ", port: " + port + ", client ID: " + clientID + ", packets: " + numPackets + ", wait: " + wait);


	//starting payload to determine how many more bytes are needed for rest
		String payload = "Hostname: " + IPAddress.getHostName() + "\n Class-name: VCU-CMSC440-SPRING-2023 \nUser-name: Welk, Alex \nRest: " ;
		byte[] sendByte = new byte[1024];

	//creating RTT array to store values of RTT for each packet
		double [] RTT = new double[numPackets];

		// Create packet and send to server
		for(int i = 0; i < numPackets; i++) {
			long time = System.currentTimeMillis();
			packetSize = (int)(Math.random()*(maxSize - minSize +1) + minSize);

			String header = "---------- Ping Request Packet Header ---------- \nVersion: 1 \n ClientID: " + clientID + "SequenceNo: " + sequenceNum
					+ "\n Timestamp: " + time + "\nSize: " + packetSize + "\n---------- Ping Request Packet Payload ----------\n";
			//gets size needed for rest
			int restSize = packetSize - (payload.getBytes().length);
			//gets rest from randomString method
			String rest = randomString(restSize);
			//reassign payload with rest included
			payload = "Hostname: " + IPAddress.getHostName() + "\n Class-name: VCU-CMSC440-SPRING-2023 \nUser-name: Welk, Alex \nRest: " + rest + "\n";
			String sendString = header + payload;
			sendByte = sendString.getBytes();
			totalByte += sendByte.length;
			//prints output for each packet sent
			pingString(1, clientID, sequenceNum, time, packetSize, IPAddress.getHostName(), rest);
			sendPacket = new DatagramPacket(sendByte, sendByte.length, IPAddress, port);
			clientSocket.send(sendPacket);
			sequenceNum++;
			//wait for the inputted wait time for a received packet
			Thread.sleep(wait*1000);

			try {
				receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
				clientSocket.receive(receivePacket);

				long receiveTime = System.currentTimeMillis();
				//compares now time to time when packet send was started to determine RTT
				RTT[i] = receiveTime - time;
				numReceived++;
				System.out.println("RTT: " + RTT[i] + " seconds");
			}catch (SocketTimeoutException e) {
				System.out.println("--------------- Ping Response Packet Timed-Out ---------------");
				receivePacket = new DatagramPacket(receiveData,
						receiveData.length);
			}
		}

		//finding min and max RTTs
		double min = Arrays.stream(RTT).min().getAsDouble();
		double max = Arrays.stream(RTT).max().getAsDouble();
		//determining average RTT
		for(int i = 0; i < RTT.length; i++) {
			total += RTT[i];
		}
		double averageRTT = total / numReceived;
		double packetLoss = (100 -(numReceived / numPackets)*100);

		int averagePayload = totalByte / numPackets;


	// Create receiving packet and receive from server
	serverSentence = new String(receivePacket.getData(), 0,
				    receivePacket.getLength());

	//summary print
System.out.println("Summary: " + numPackets + " :: " + numReceived + " :: " + packetLoss + "% :: "
			+ " :: " + min + " :: " + max + " :: " + averageRTT + " :: " + averagePayload);

	// close the socket
	clientSocket.close();

    } // end main

	//method to print for each packet
	public static void pingString(int version, int clientID, int sequenceNum, double time, int size, String hostName, String rest) {
		System.out.println("---------- Ping Request Packet Header ----------");
		System.out.println("Version: " + version);
		System.out.println("Client ID: " + clientID);
		System.out.println("Sequence No.: " + sequenceNum);
		System.out.println("Time: " + time);
		System.out.println("Payload Size: " + size);
		System.out.println("---------- Ping Request Packet Payload ----------");
		System.out.println("Host: " + hostName);
		System.out.println("Class-name: VCU-CMSC440-SPRING-2023");
		System.out.println("User-name: Welk, Alex");
		System.out.println("Rest: " + rest); //?
	}

	//method to get random string for rest
	public static String randomString(int size) {
		String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvwxyz";
		StringBuilder string = new StringBuilder(Math.abs(size));
		for(int i = 0; i < size; i++) {
			int index = (int)(alphaNumeric.length()*Math.random());
			string.append(alphaNumeric.charAt(index));
		}
		return string.toString();
	}

} // end class
