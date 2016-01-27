import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * 
 * @author Ollie Dowling
 *
 */
public class C4JFrame extends JFrame implements ActionListener {

	// Named-constants for the game board
	private static final long serialVersionUID = 1L;
	private JButton		btn1, btn2, btn3, btn4, btn5, btn6, btn7;
	private Label		lblSpacer;
	private MenuItem		newMI, exitMI, REDMI, BLUEMI;
	int[][]			theArray;
	private	boolean			end=false;
	private boolean			gameStart;
	public static final int BLANK = 0;
	public static final int RED = 2;
	public static final int BLUE = 1;
	public static final int MAXROW = 6;	// 6 rows
	public static final int MAXCOL = 7;	// 7 columns
	private int redScore;
	private int blueScore;

	public static final String SPACE = "         "; // 9 spaces

	int activeColour = RED;

	public static void main(String[] args) {

		UIManager.put("Panel.background", Color.cyan);

		SourceDataLine soundLine = null;
		int BUFFER_SIZE = 64*1024;  // 64 KB
		C4JFrame frame = new C4JFrame();
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// Set up an audio input stream piped from the sound file.
		try {
			File soundFile = new File("song.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
			AudioFormat audioFormat = audioInputStream.getFormat();
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
			soundLine = (SourceDataLine) AudioSystem.getLine(info);
			soundLine.open(audioFormat);
			soundLine.start();
			int nBytesRead = 0;
			byte[] sampledData = new byte[BUFFER_SIZE];
			while (nBytesRead != -1) {
				nBytesRead = audioInputStream.read(sampledData, 0, sampledData.length);
				if (nBytesRead >= 0) {
					// Writes audio data to the mixer via this source data line.
					soundLine.write(sampledData, 0, nBytesRead);
				}
			}
		} catch (UnsupportedAudioFileException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		} finally {
			soundLine.drain();
			soundLine.close();
		}
	}

	public static void playBeep ()  // method for playing sound. "beep"
	{
		try
		{
			java.applet.AudioClip clip = java.applet.Applet.newAudioClip (new java.net.URL ("file:beep.wav"));
			clip.play ();
		}
		catch (java.net.MalformedURLException mue)
		{
			System.out.println (mue);
		}
	}

	public C4JFrame() {
		// Set up the icon image
		Image iconImage = Toolkit.getDefaultToolkit ().getImage ("MusicIcon.png");
		setIconImage (iconImage);
		setTitle("Connect4 by Ollie Dowling");
		MenuBar mbar = new MenuBar();
		Menu fileMenu = new Menu("File");
		newMI = new MenuItem("New game");
		newMI.addActionListener(this);
		fileMenu.add(newMI);
		exitMI = new MenuItem("Exit game");
		exitMI.addActionListener(this);
		fileMenu.add(exitMI);
		mbar.add(fileMenu);
		Menu optMenu = new Menu("Options");
		REDMI = new MenuItem("Red starts");
		REDMI.addActionListener(this);
		optMenu.add(REDMI);
		BLUEMI = new MenuItem("Blue starts");
		BLUEMI.addActionListener(this);
		optMenu.add(BLUEMI);
		mbar.add(optMenu);
		setMenuBar(mbar);


		// Build control panel.
		Panel panel = new Panel();

		Icon imgicon = new ImageIcon("MusicIcon2.png");

		btn1 = new JButton();
		btn1.addActionListener(this);
		btn1.setIcon(imgicon);
		panel.add(btn1);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn2 = new JButton();
		btn2.addActionListener(this);
		btn2.setIcon(imgicon);
		panel.add(btn2);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn3 = new JButton();
		btn3.addActionListener(this);
		btn3.setIcon(imgicon);
		panel.add(btn3);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn4 = new JButton();
		btn4.addActionListener(this);
		btn4.setIcon(imgicon);
		panel.add(btn4);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn5 = new JButton();
		btn5.addActionListener(this);
		btn5.setIcon(imgicon);
		panel.add(btn5);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn6 = new JButton();
		btn6.addActionListener(this);
		btn6.setIcon(imgicon);
		panel.add(btn6);
		lblSpacer = new Label(SPACE);
		panel.add(lblSpacer);

		btn7 = new JButton();
		btn7.addActionListener(this);
		btn7.setIcon(imgicon);
		panel.add(btn7);

		add(panel, BorderLayout.NORTH);
		initialize();
		// Set to a reasonable size.
		//setSize(1024, 768);
		setSize(1000, 750);
	} // Connect4

	public void initialize() {
		theArray=new int[MAXROW][MAXCOL];
		for (int row=0; row<MAXROW; row++)
			for (int col=0; col<MAXCOL; col++)
				theArray[row][col]=BLANK;
		gameStart=false;
	} // initialize

	public void paint(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillRect(100, 20, 100+100*MAXCOL, 100+100*MAXROW);
		for (int row=0; row<MAXROW; row++)
			for (int col=0; col<MAXCOL; col++) {
				if (theArray[row][col]==BLANK) g.setColor(Color.WHITE);
				if (theArray[row][col]==RED) g.setColor(Color.RED);
				if (theArray[row][col]==BLUE) g.setColor(Color.BLUE);
				//g.fillOval(160+100*col, 100+100*row, 100, 100);
				g.fillRect(160+100*col, 100+100*row, 90, 90);
				//g.fillRect(110, 50, 100+100*MAXCOL, 100+100*MAXROW);
			}
		check4(g);
		displayScore(g, 100);
	} // paint

	public void putDisk(int n) {
		// put a disk on top of column n
		// if game is won, do nothing
		if (end) return;
		gameStart=true;
		int row;
		n--;
		for (row=0; row<MAXROW; row++)
			if (theArray[row][n]>0) break;
		if (row>0) {
			theArray[--row][n]=activeColour;
			if (activeColour==RED)
				activeColour=BLUE;
			else
				activeColour=RED;
			repaint();
		}
	}

	public void displayWinner(Graphics g, int n) {
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier", Font.BOLD, 100));
		if (n==RED){
			redScore ++;
			g.drawString("Red Wins!", 250, 400);
		}
		else if (n==BLUE){
			blueScore ++;
			g.drawString("Blue Wins!", 250, 400);
		}
		end=true;
	}

	public void displayScore(Graphics g, int x){
		g.setColor(Color.BLACK);
		g.setFont(new Font("Courier", Font.BOLD, 25));
		g.drawString("Reds score = " + redScore, 150, 110);
		g.drawString("Blues score = " + blueScore, 650, 110);
	}

	public void check4(Graphics g) {
		// see if there are 4 disks in a row: horizontal, vertical or diagonal
		// horizontal rows
		for (int row=0; row<MAXROW; row++) {
			for (int col=0; col<MAXCOL-3; col++) {
				int curr = theArray[row][col];
				if (curr>0
						&& curr == theArray[row][col+1]
								&& curr == theArray[row][col+2]
										&& curr == theArray[row][col+3]) {
					displayWinner(g, theArray[row][col]);

				}
			}
		}
		// vertical columns
		for (int col=0; col<MAXCOL; col++) {
			for (int row=0; row<MAXROW-3; row++) {
				int curr = theArray[row][col];
				if (curr>0
						&& curr == theArray[row+1][col]
								&& curr == theArray[row+2][col]
										&& curr == theArray[row+3][col])
					displayWinner(g, theArray[row][col]);
			}
		}
		// diagonal lower left to upper right
		for (int row=0; row<MAXROW-3; row++) {
			for (int col=0; col<MAXCOL-3; col++) {
				int curr = theArray[row][col];
				if (curr>0
						&& curr == theArray[row+1][col+1]
								&& curr == theArray[row+2][col+2]
										&& curr == theArray[row+3][col+3])
					displayWinner(g, theArray[row][col]);
			}
		}

		// diagonal upper left to lower right
		for (int row=MAXROW-1; row>=3; row--) {
			for (int col=0; col<MAXCOL-3; col++) {
				int curr = theArray[row][col];
				if (curr>0
						&& curr == theArray[row-1][col+1]
								&& curr == theArray[row-2][col+2]
										&& curr == theArray[row-3][col+3])
					displayWinner(g, theArray[row][col]);
			}
		}
	} // end check4

	public void actionPerformed(ActionEvent e) {
		//	Toolkit.getDefaultToolkit().beep();
		playBeep ();
		if (e.getSource() == btn1)
			putDisk(1);
		else if (e.getSource() == btn2)
			putDisk(2);
		else if (e.getSource() == btn3)
			putDisk(3);
		else if (e.getSource() == btn4)
			putDisk(4);
		else if (e.getSource() == btn5)
			putDisk(5);
		else if (e.getSource() == btn6)
			putDisk(6);
		else if (e.getSource() == btn7)
			putDisk(7);
		else if (e.getSource() == newMI) {
			end=false;
			initialize();
			repaint();
		} else if (e.getSource() == exitMI) {
			System.exit(0);
		} else if (e.getSource() == REDMI) {
			// don't change colour to play in middle of game
			if (!gameStart) activeColour=RED;
		} else if (e.getSource() == BLUEMI) {
			if (!gameStart) activeColour=BLUE;
		}

	} // end ActionPerformed
} // class