package io.listening;

import java.io.*;
import java.sql.*;
import java.net.*;
import java.util.Date;
import java.awt.*;

import javax.swing.*;

public class Assign2_MultiThreadedServer extends JFrame implements Runnable {

	/**
	 * @author Ollie Dowling
	 * @version 1.0
	 * An example of a Multithreaded client server application
	 */
	private static final long serialVersionUID = 1L;
	// Text area for displaying contents
	private JTextArea jta1 = new JTextArea();
	private DataOutputStream toClient;

	public static void main(String[] args) {
		System.out.println("Connected");
		new Thread(new Assign2_MultiThreadedServer()).start();
	}

	public Assign2_MultiThreadedServer() {
		// Place text area on the frame
		setLayout(new BorderLayout());
		add(new JScrollPane(jta1), BorderLayout.CENTER);
		setTitle("Server");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); // shows the frame
	}

	public void run() {
		try {
     		// Create a server socket
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(8000);
			jta1.append("Server started at " + new Date() + '\n');

			// Listen for a connection request
			Socket socket = serverSocket.accept();//connects to the client
			System.out.println("Hello From this Thread");

			InetAddress address = InetAddress.getByName("www.rte.ie");

			jta1.append("Clients Host name is " + address.getHostName() + '\n');
			jta1.append("Clients IP Address is " + address.getHostAddress() + '\n');
			
			// Create data input and output streams
			DataInputStream inputFromClient = new DataInputStream(
					socket.getInputStream());
			DataOutputStream outputToClient = new DataOutputStream(
					socket.getOutputStream());
			// Create an output stream to send data to the server
			toClient = new DataOutputStream(socket.getOutputStream());

			while (true) {
				// Receive values from the client
				double anualInterestRate = inputFromClient.readDouble();
				double numOfYears = inputFromClient.readDouble();
				double loanAmount = inputFromClient.readDouble();

				// Compute values
				double rate = anualInterestRate * anualInterestRate * Math.PI;
				double amount = loanAmount * loanAmount * Math.PI;
				double year = numOfYears * numOfYears * Math.PI;

				// Send values back to the client
				outputToClient.writeDouble(rate);
				outputToClient.writeDouble(year);
				outputToClient.writeDouble(amount);
				toClient.writeDouble(anualInterestRate);
				toClient.writeDouble(numOfYears);
				toClient.writeDouble(loanAmount);
				toClient.flush();
				System.out.println("Hello again From this Thread");

				jta1.append("Interest Rate received from client: " + anualInterestRate + '\n');
				jta1.append("The amount of years recieved from client: " + numOfYears + '\n');
				jta1.append("Loan amount received from client: " + loanAmount + '\n');
				jta1.append("Interest Rate found: " + anualInterestRate + '\n');
				jta1.append("Number of years found: " + numOfYears + '\n');
				jta1.append("Loan amount found: " + loanAmount + '\n');

				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bankdatabase", "root", "");
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery("select * from registeredapplicants where regID = 1");

					String IPAddress = "";
					System.out.println("connected");
					if (rs.next()) {
						System.out.println("Successfully found IP Address for "
								+ "www.rte.ie from sql database. " + "Access Allowed");
						jta1.append("Successfully found IP Address for "
								+ "www.rte.ie from sql database. " + "Access Allowed");
						IPAddress = rs.getString("IPAddress");
						System.out.println(IPAddress);
					}
					else{

						System.out.println("Sorry. Your IP Address: 127.0.0.1 is not registered. "
								+ "Only registered client nodes may submit ");
					}

				} catch (Exception e) {
				}
			}
		}
		catch(IOException ex) {
			System.err.println(ex);

		}
	}
}