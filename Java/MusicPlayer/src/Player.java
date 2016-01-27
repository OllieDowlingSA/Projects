import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import java.net.URL;
import java.applet.*;

import javax.imageio.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
/**
 * @author Ollie Dowling
 * @version 1.0
 * This is a music player program that uses
 * GridbagLayout and BorderLayout with he use of images and image icons, HTML fonts and audio.
 * This is an example of the use of JButtons, JCheckBoxes, JTextFields, UIManager, JComboBoxes and JPasswordFields.
 */
public class Player extends JFrame implements ActionListener, ItemListener {
	/**
	 * Constants
	 */
	private static final long serialVersionUID = 1L;
	private URL urlForImage = null;
	// Combo box
	private JComboBox<String> jCombo = new JComboBox<String>();
	// JButton Play
	private JButton jbtPlay = new JButton("<html><font color=black<font size=+0.5><b><i>Play</i></b></font></html>");
	// JButton Stop
	private JButton jbtStop = new JButton("<html><font color=black<font size=+0.5><b><i>Stop</i></b></font></html>");	
	private JButton jbtClear = new JButton("<html><font color=black<font size=+0.5><b><i>Clear</i></b></font></html>");
	// Three JCheckBoxes
	private JCheckBox chckbxRock = new JCheckBox("<html><font color=black<font size=+0.5><b>Rock</b></font></html>");	
	private JCheckBox chckbxPop = new JCheckBox("<html><font color=black<font size=+0.5><b>Pop</b></font></html>");
	private JCheckBox chckbxAll = new JCheckBox("<html><font color=black<font size=+0.5><b>All</b></font></html>");
	// Selected artist
	private String artist = "Please select a Track";
	// AudioClip 
	private AudioClip player=null;
	// A JLabel
	private JLabel L1 = new JLabel();
	// Two JPasswordField
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	// A JTextfield
	private JTextField textField;
	

	// Constructor for program
	public Player(String title) {
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jbtClear.setToolTipText("Clear all Audio");
		jbtPlay.setToolTipText("Play");
		jbtPlay.setEnabled(false);
		// Register listener
		jbtPlay.addActionListener(this);
		jbtStop.setToolTipText("Stop");
		jbtStop.addActionListener(this);
		jCombo.addItemListener(this);
		jbtStop.setEnabled(false);
		createGUI();
	}
	
	// Create the GUI
	public void createGUI()	{
		getContentPane().setLayout(new BorderLayout());
		Image iconImage = Toolkit.getDefaultToolkit ().getImage ("images/MusicIcon.png");
    	setIconImage (iconImage);
		Icon imgicon = new ImageIcon("images/MusicIcon2.png");
		jbtStop.setIcon(imgicon);
		jbtClear.setMinimumSize(new Dimension(53, 23));
		jbtClear.setMaximumSize(new Dimension(53, 23));
		jbtClear.setMargin(new Insets(2, 2, 2, 2));
		jbtClear.setIcon(imgicon);

		GridBagLayout gbl_p = new GridBagLayout();
		gbl_p.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		JPanel p = new JPanel(gbl_p);
		p.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 255, 255), Color.WHITE, Color.WHITE, Color.WHITE));

		JPanel p1 = new JPanel();
		p1.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));

		JPanel p2 = new JPanel();
		p2.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));

		JPanel p3 = new JPanel();
		p3.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.WHITE, Color.WHITE, Color.WHITE, Color.WHITE));
		jbtPlay.setIcon(imgicon);
		GridBagConstraints gbc_jbtPlay = new GridBagConstraints();
		gbc_jbtPlay.insets = new Insets(0, 0, 0, 5);
		gbc_jbtPlay.gridx = 0;
		gbc_jbtPlay.gridy = 0;
		p.add(jbtPlay, gbc_jbtPlay);

		textField = new JTextField();
		textField.setEditable(false);
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 7;
		gbc_textField.insets = new Insets(0, 0, 0, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 0;
		p.add(textField, gbc_textField);
		textField.setColumns(10);
		Border border5 = BorderFactory.createLineBorder(Color.BLACK);
		textField.setBorder(BorderFactory.createCompoundBorder(border5, 
				BorderFactory.createEmptyBorder(3, 3, -1, -1)));

		GridBagConstraints gbc_jbtStop = new GridBagConstraints();
		gbc_jbtStop.gridx = 10;
		gbc_jbtStop.gridy = 0;
		p.add(jbtStop, gbc_jbtStop);
		getContentPane().add(p, BorderLayout.NORTH);
		getContentPane().add(p1, BorderLayout.SOUTH);
		getContentPane().add(p2, BorderLayout.WEST);
		getContentPane().add(p3, BorderLayout.EAST);

		passwordField = new JPasswordField("Ollie");//sets a default password field
		Border border3 = BorderFactory.createLineBorder(Color.BLACK);
		passwordField.setBorder(BorderFactory.createCompoundBorder(border3, 
				BorderFactory.createEmptyBorder(3, 3, -1, -1)));

		passwordField_1 = new JPasswordField(8);
		passwordField_1.setEchoChar('*');
		Border border4 = BorderFactory.createLineBorder(Color.BLACK);
		passwordField_1.setBorder(BorderFactory.createCompoundBorder(border4, 
				BorderFactory.createEmptyBorder(3, 3, -1, -1)));

		passwordField_1.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) {
				JPasswordField input = (JPasswordField) e.getSource();
				char[] password1 = input.getPassword();
				if (isPasswordCorrect1(password1)) {
					UIManager.put("OptionPane.background", Color.white);
					UIManager.put("Panel.background", Color.white);
					ImageIcon icon = new ImageIcon("images/MusicIcon.png");//image icon to be used in JOptionpane
					JOptionPane.showMessageDialog(null, "<html><font color=black<font size=+0.5><b><i>Correct Password.  Welcome to My Music Player.", null, JOptionPane.INFORMATION_MESSAGE, icon);
					jCombo.setEnabled(true);
					jbtPlay.setEnabled(true);
					jbtStop.setEnabled(true);
					jbtClear.setEnabled(true);
					chckbxRock.setEnabled(true);
					chckbxPop.setEnabled(true);
					chckbxAll.setEnabled(true);
				} else {
					ImageIcon icon = new ImageIcon("images/MusicIcon.png");
					JOptionPane.showMessageDialog(null, "<html><font color=black<font size=+0.5><b><i>Invalid Password.  Please Try Again.", null, JOptionPane.INFORMATION_MESSAGE, icon);
				}
			}
		});

		JLabel lblUsername = new JLabel("<html><font color=blue<font size=+0.5><b><i>Username :</i></b></font></html>");

		JLabel lblPassword = new JLabel("<html><font color=blue<font size=+0.5><b><i>Password :</i></b></font></html>");

		JLabel lblLogin = new JLabel("<html><font color=black<font size=+0.5><b><i>LOGIN :</i></b></font></html>");

		GroupLayout gl_p3 = new GroupLayout(p3);
		gl_p3.setHorizontalGroup(
				gl_p3.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_p3.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_p3.createParallelGroup(Alignment.LEADING)
								.addComponent(lblLogin, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_p3.createSequentialGroup()
										.addComponent(lblUsername)
										.addContainerGap(42, Short.MAX_VALUE))
										.addGroup(gl_p3.createSequentialGroup()
												.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
												.addGap(6))
												.addGroup(gl_p3.createSequentialGroup()
														.addComponent(lblPassword)
														.addContainerGap(46, Short.MAX_VALUE))
														.addGroup(gl_p3.createSequentialGroup()
																.addComponent(passwordField_1, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
																.addGap(6))
																.addGroup(gl_p3.createSequentialGroup()
																		.addContainerGap(17, Short.MAX_VALUE))))
				);
		gl_p3.setVerticalGroup(
				gl_p3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p3.createSequentialGroup()
						.addGap(31)
						.addComponent(lblLogin)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(lblUsername)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblPassword)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(passwordField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addContainerGap(18, Short.MAX_VALUE))
				);
		p3.setLayout(gl_p3);

		JLabel lblChooseGenre = new JLabel("<html><font color=black<font size=+0.5><b><i>CHOOSE GENRE :</i></b></font></html>");

		chckbxRock.setEnabled(false);
		chckbxRock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {        
				if(e.getSource() == chckbxRock) {
					jCombo.addItem("The Clash");
					jCombo.addItem("ACDC");
					jCombo.removeItem("Daft Punk");
					jCombo.removeItem("Disclosure");
					chckbxPop.setSelected(false);
					chckbxAll.setSelected(false);
				}
			}
		});

		chckbxPop.setEnabled(false);
		chckbxPop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == chckbxPop) {
					jCombo.addItem("Daft Punk");
					jCombo.addItem("Disclosure");
					jCombo.removeItem("The Clash");
					jCombo.removeItem("ACDC");
					chckbxRock.setSelected(false);
					chckbxAll.setSelected(false);
				}
			}
		});

		chckbxAll.setEnabled(false);
		chckbxAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == chckbxAll) {
					jCombo.removeItem("Daft Punk");
					jCombo.removeItem("Disclosure");
					jCombo.addItem("Daft Punk");
					jCombo.addItem("The Clash");
					jCombo.addItem("Disclosure");
					jCombo.addItem("ACDC");
					chckbxRock.setSelected(false);
					chckbxPop.setSelected(false);
				}
			}
		});

		jbtClear.setEnabled(false);
		jbtClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(e.getSource() == jbtClear) {
					jCombo.removeItem("Daft Punk");
					jCombo.removeItem("The Clash");
					jCombo.removeItem("Disclosure");
					jCombo.removeItem("ACDC");
				}
			}
		});

		GroupLayout gl_p2 = new GroupLayout(p2);
		gl_p2.setHorizontalGroup(
				gl_p2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_p2.createSequentialGroup()
						.addGroup(gl_p2.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_p2.createSequentialGroup()
										.addContainerGap(30, Short.MAX_VALUE)
										.addComponent(lblChooseGenre))
										.addGroup(gl_p2.createSequentialGroup()
												.addGap(21)
												.addGroup(gl_p2.createParallelGroup(Alignment.LEADING)
														.addComponent(chckbxRock, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
														.addComponent(chckbxPop, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
														.addComponent(chckbxAll, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))))
														.addContainerGap())
														.addGroup(gl_p2.createSequentialGroup()
																.addContainerGap(29, Short.MAX_VALUE)
																.addComponent(jbtClear, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
																.addGap(22))
				);
		gl_p2.setVerticalGroup(
				gl_p2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p2.createSequentialGroup()
						.addGap(31)
						.addComponent(lblChooseGenre)
						.addGap(18)
						.addComponent(chckbxRock)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(chckbxPop)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(chckbxAll)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(jbtClear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(29, Short.MAX_VALUE))
				);
		p2.setLayout(gl_p2);
		GridBagConstraints gbc_jcboCountry = new GridBagConstraints();
		gbc_jcboCountry.gridwidth = 6;
		gbc_jcboCountry.gridx = 1;
		gbc_jcboCountry.gridy = 0;
		p1.add(jCombo, gbc_jcboCountry);
		// Initialise the combo box
		jCombo.addItem("Please select a Track");
		jCombo.setEnabled(false);
		// the I love music jpeg is displayed
		urlForImage = getClass().getResource("images/music1.jpg");
		try {
			L1.setIcon( new ImageIcon(ImageIO.read( urlForImage ) ) );
		} catch (Exception e) {}

		getContentPane().add(L1,BorderLayout.CENTER);
	}

	//Event Handler
	public void actionPerformed(ActionEvent e) {
		String filename = "Please Select a Track";

		if(e.getSource() == jbtPlay) {
			// Get the file name
			if (artist.equals("Please select a Track"))
				filename = "rat.wav";
			else if (artist.equals("ACDC"))
				filename = "rosie.wav";
			else if (artist.equals("Disclosure"))
				filename = "whitenoise.wav";
			else if (artist.equals("The Clash"))
				filename = "seven.wav";
			else if (artist.equals("Daft Punk"))
				filename = "getlucky.wav";
			//Creates an audio clip to play
			player=createAudioClip(filename);
			player.play();
		}   
		if(e.getSource() == jbtStop) {
			player.stop();
		}
	}

	/** 6. Handle ItemEvent */
	public void itemStateChanged(ItemEvent e) {
		// Gets the selected artist
		artist = (String)jCombo.getSelectedItem();

		// Get the image file name
		String filename = null;
		if (artist.equals("Please select a Track")){
			filename = "music1.jpg";
		}
		else if (artist.equals("ACDC")){
			textField.setText("Whole Lotta Rosie - ACDC (March 1977)");
			filename = "acdc.jpg";
		}
		else if (artist.equals("Disclosure")){
			textField.setText("White Noise - Disclosure (1 February 2013)");
			filename = "dis.jpg";
		}
		else if (artist.equals("Daft Punk")){
			textField.setText("Get Lucky - Daft Punk  (19 April 2013)");
			filename = "daftpunk.jpg";
		}
		else if (artist.equals("The Clash")){
			textField.setText("The Magnificent Seven - The Clash (10 April 1981)");
			filename = "clash.jpg";
		}
		urlForImage = getClass().getResource(filename);
		try {
			L1.setIcon( new ImageIcon(ImageIO.read( urlForImage ) ) );
		} catch (Exception e2) {}

	}
	//creates an audio clip from file
	public AudioClip createAudioClip(String filename) {
		AudioClip player=null;
		try {
			URL soundToPlay = this.getClass().getResource(filename);
			player = Applet.newAudioClip(soundToPlay);
		} catch (Exception e) {}
		return player;
	}

	//main
	public static void main(String[] args){
		   
		//instance of the class
		Player test = new Player("™Music Player - Ollie Dowling");
		// Display the frame
		test.setPreferredSize(new Dimension(600,290));
		test.pack();
		test.setVisible(true);
	}

	private static boolean isPasswordCorrect1(char[] inputPassword) {
		char[] actualPassword = { 'o', 'l', 'l', 'i', 'e' };
		if (inputPassword.length != actualPassword.length)
			return false; // Return false if lengths are unequal
		for (int i = 0; i < inputPassword.length; i++)
			if (inputPassword[i] != actualPassword[i])
				return false;
		return true;
	}
}



