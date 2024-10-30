Alex Welk - V00935464 - CMSC 440 PING Programming Assignment

To compile: Open two seperate terminals and compile each program in
their own terminal using javac PINGServer.java and javac PINGClient.java

To execute you want to first launch the PINGServer by doing:
java PINGServer followed by command line arguments for port and loss %
Then you execute the PINGClient by doing:
java PINGClient follows by command line arguments for IP address, port, client ID
number of packets, and wait condition

proper syntax for the execution:
java PINGServer <port #> <loss>
java PINGClient <hostname / ip address> <port #> <client ID> <number of ping requests> <wait>