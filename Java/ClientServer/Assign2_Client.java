package io.listening;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import net.miginfocom.swing.MigLayout;

import javax.swing.GroupLayout.Alignment;

public class Assign2_Client extends JFrame implements Runnable {

	public boolean running = false; 
	/**
	 * @author Ollie Dowling 
	 * @version 1.0
	 * An example of a Multithreaded client server application
	 */
	private static final long serialVersionUID = 1L;

	// Text area to display contents
	private JTextArea jta = new JTextArea();
	private JButton btn = new JButton("Submit");
	private final Panel panel = new Panel();
	private JTextField jtf1 = new JTextField();
	private JTextField jtf2 = new JTextField();
	private JTextField jtf3 = new JTextField();	
	// IO streams
	private DataOutputStream toServer;
	public DataInputStream fromServer;
	JLabel ai = new JLabel("Annual Interest Rate");
	JLabel ny = new JLabel("Number of Years");
	JLabel la = new JLabel("Loan Amount");


	public static void main(String[] args) {
		List<Assign2_Client> clients = new ArrayList<Assign2_Client>();

		System.out.println("This is currently running on the main thread, " +
				"the id is: " + Thread.currentThread().getId());
		

		Date start = new Date();

		{
			clients.add(new Assign2_Client()); 
		}

		for (Assign2_Client client : clients)
		{
			while (client.running)
			{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		Date end = new Date();
		long difference = end.getTime() - start.getTime();
		System.out.println ("This whole process took: " + difference/1000 + " seconds.");
	}

	public Assign2_Client() {

		Thread thread = new Thread(this);  
		thread.start(); 
		// Panel p to hold the label and text field
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(btn, BorderLayout.WEST);
		p.add(jta, BorderLayout.SOUTH);
		getContentPane().setLayout(new MigLayout("", "[484px]", "[23px][238px]"));
		getContentPane().add(p, "cell 0 0,growx,aligny top");
		JScrollPane scrollPane = new JScrollPane(jta);

		JButton btnNewThread = new JButton("New Thread");
		btnNewThread.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				(new Thread(new Assign2_Client())).start();
			}
		});
		
		p.add(btnNewThread, BorderLayout.EAST);

		getContentPane().add(scrollPane, "cell 0 1,grow");

		scrollPane.setRowHeaderView(panel);

		jtf1 = new JTextField();
		jtf1.setColumns(10);

		jtf2 = new JTextField();
		jtf2.setColumns(10);

		jtf3 = new JTextField();
		jtf3.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(ai)
								.addComponent(ny)
								.addComponent(la))
								.addGap(28)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(jtf3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(jtf2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(jtf1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addContainerGap(34, Short.MAX_VALUE))
				);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addGap(49)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(ai)
								.addComponent(jtf1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(ny)
										.addComponent(jtf2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
												.addComponent(la)
												.addComponent(jtf3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addContainerGap(91, Short.MAX_VALUE))
				);
		panel.setLayout(gl_panel);
		btn.addActionListener(new Listener());

		setTitle("Client");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true); // shows the frame.
	}

	public void run() {

		try {
			this.running = true;
			System.out.println("This is currently running on a separate thread, " +
					"the id is: " + Thread.currentThread().getId());
			try 
			{
				// pause this thread for 4 seconds
				Thread.sleep(4000);
			} 
			catch (InterruptedException e) 
			{
				Thread.currentThread().interrupt();
			}
			this.running = false;

			// Create a socket to connect to the server
			@SuppressWarnings("resource")
			Socket socket = new Socket("localhost", 8000);

			// Create an input stream to receive data from the server
			fromServer = new DataInputStream(socket.getInputStream());

			// Create an output stream to send data to the server
			toServer = new DataOutputStream(socket.getOutputStream());
			System.out.println("Hello from a Thread");
		}

		catch (IOException ex) {
			try {
				((Appendable) jtf1).append(ex.toString() + '\n');
				((Appendable) jta).append(ex.toString() + '\n');
				((Appendable) jtf2).append(ex.toString() + '\n');
				((Appendable) jtf3).append(ex.toString() + '\n');
			} catch (IOException e) {
				System.err.println(e);
				e.printStackTrace();

			}
		}

	}

	private class Listener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				// Get the values from the text field
				double anualInterestRate = Double.parseDouble(jtf1.getText().trim());
				double numOfYears = Double.parseDouble(jtf2.getText().trim());   
				double loanAmount = Double.parseDouble(jtf3.getText().trim());

				// Send the values to the server
				toServer.writeDouble(anualInterestRate);
				toServer.writeDouble(numOfYears);
				toServer.writeDouble(loanAmount);
				toServer.flush();

				jta.append("Interest Rate received from server: " + anualInterestRate + '\n');
				jta.append("The amount of years recieved from server: " + numOfYears + '\n');
				jta.append("Loan amount received from server: " + loanAmount + '\n');
				jta.append("Interest Rate found: " + anualInterestRate + '\n');
				jta.append("Number of years found: " + numOfYears + '\n');
				jta.append("Loan amount found: " + loanAmount + '\n');


			}
			catch (IOException ex) {
				System.err.println(ex);
			}
		}
	}
}
