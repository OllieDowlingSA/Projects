package ie.ollie.io;

import ie.ollie.ab1.Vehicle;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
/**
 * 
 * @author Ollie Dowling
 *
 */
public class FileHandler 
{
	private Object nameAndEmail;
	private FileOutputStream outByteStream;
	private ObjectOutputStream OOStream;
	private FileInputStream inByteStream;
	private ObjectInputStream OIStream;
	private File aFile;

	public void setNameAndEmail()
	{
		//UI Manager used for Background Colour
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);	
		ImageIcon icon = new ImageIcon("bike.png");
		this.nameAndEmail =  JOptionPane.showInputDialog(null,"<html><font color=blue><i>Please Enter your name and Email address</i></html>","",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
		aFile = new File("vechicle.dat");
	}

	/**
	 * @return a file
	 */
	public boolean isFileEmpty()
	{
		return (aFile.length() <= 0);
	}

	/**
	 * @return name and email
	 */
	public Object getNameAndEmail()
	{
		return this.nameAndEmail;
	}

	/**
	 * @param motor write to file
	 */
	public void writeToFile(List<Vehicle> motor)	
	{
		try
		{
			outByteStream = new FileOutputStream(aFile);
			OOStream = new ObjectOutputStream(outByteStream);

			OOStream.writeObject(motor);

			outByteStream.close();
			OOStream.close();
		}
		catch(IOException e)
		{   
			JOptionPane.showMessageDialog(null,"I/O Error" + e + "\nPlease Contact your Administrator :-)");
		}
	}

	/**
	 * @return temp 
	 */
	@SuppressWarnings("unchecked")
	public List<Vehicle> readFromFile()//reads from file
	{
		List<Vehicle> temp = null;

		try
		{
			inByteStream = new FileInputStream(aFile);
			OIStream = new ObjectInputStream(inByteStream);

			if(!this.isFileEmpty())
				temp = (List<Vehicle>)OIStream.readObject();

			inByteStream.close();
			OIStream.close();
		}
		catch(IOException e)
		{
			JOptionPane.showMessageDialog(null,"I/O Error" + e + "\nPlease Contact your Administrator :-)");
		}

		catch(Exception e)
		{
			JOptionPane.showMessageDialog(null,"General Error" + e + "\nPlease Contact your Administrator :-)");
		}
		return temp;
	}
}
