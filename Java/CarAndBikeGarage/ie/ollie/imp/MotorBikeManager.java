package ie.ollie.imp;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import ie.ollie.ab1.Vehicle;
import ie.ollie.io.FileHandler;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.Border;


/**
 * @author Ollie Dowling
 *
 */
public class MotorBikeManager  
{

	private static Icon myIcon = new ImageIcon("..\\DataStructuresRepeatV.1\\images\\Car.jpg");
	private static Icon anIcon = new ImageIcon();
	private FileHandler fileHandler = new FileHandler();
	private List<Vehicle> VehicleList;

	public MotorBikeManager()
	{
		//set name and email
		fileHandler.setNameAndEmail();

		if(!fileHandler.isFileEmpty())
			VehicleList = (List<Vehicle>) fileHandler.readFromFile();
		else
		{
			//populates the array
			VehicleList = new ArrayList<Vehicle>();
			MotorBike aMotorBike = new MotorBike(1948,"Motorbike",100.0000, 3, 456666, "Ollie Dowling", "Mexico","This Bike is Reliable");
			VehicleList.add(aMotorBike);

			Car aCAR = new Car(1972,"Car",122.0000,33,45,"Ollie Dowling","Old", "This Car is Reliable");
			VehicleList.add(aCAR);
		}
	}
	/**
	 * @return the users option
	 */
	public int menuMain()
	{
		//UI Manager used for Background Colour
		UIManager.put("OptionPane.background", Color.white);
		UIManager.put("Panel.background", Color.white);

		int option = 0;
		//displays the menu options with tooltips border styles an html
		String opt1 = new String("1. Add a Vehicle :");
		String opt2 = new String("2. Search for a Specific Vehicle :");
		String opt3 = new String("3. List All Vehicles :");
		String opt4 = new String("4. Remove A Vehicle :");
		String opt5 = new String("5. Exit :");
		String msg = new String("<html><font color=blue><i><u>Enter Option</u></i></font></html>:");

		JTextField opt = new JTextField("");
		opt.setToolTipText("Enter a number 1 - 5 here");
		Border border = BorderFactory.createLineBorder(Color.BLUE);
		opt.setBorder(BorderFactory.createCompoundBorder(border, 
				BorderFactory.createEmptyBorder(8, 8, 8, 8)));

		Object message[] = new Object[8];

		message[0] = myIcon;
		message[1] = opt1;
		message[2] = opt2;
		message[3] = opt3;
		message[4] = opt4;
		message[5] = opt5;
		message[6] = msg;
		message[7] = opt;

		//displays the automobile manager
		int response = JOptionPane.showConfirmDialog(null,message,"Automobile Manager",JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE , anIcon);

		if(response == JOptionPane.CANCEL_OPTION);
		else
		{
			try {
				option = Integer.parseInt( opt.getText());
			}
			catch (Exception e)
			{
				//displays an invalid input error in joptionpane
				JOptionPane.showMessageDialog(null,"Data Input Error" + e + "\nPlease Try Again");
			}
		}
		return option;
	}	
	/**
	 * Add a vehicle menu
	 */
	public void menuAddVehicle()
	{
		//displays the strings in html format
		String msgYear = new String("<html><font color=blue<font size=-1><b><i>Vehicle Year :</i></b></font></html>");
		String msgTypeOfVehicle = new String("<html><font color=blue><font size=-1><b><i>Vehicle Type :</i></b></font></html>");
		String msgPrice = new String("<html><font color=blue<font size=-1><b><i>Vehicle Price :</i></b></font></html>");
		String msgOwners = new String("<html><font color=blue><font size=-1><b><i>Number of Owners :</i></b></font></html>");
		String msgKmTraveled = new String("<html><font color=blue<font size=-1><b><i>Km Traveled :</i></b></font></html>");
		String msgPreviousOwner = new String("<html><font color=blue><font size=-1><b><i>Previous Owner :</i></b></font></html>");
		String msgCountry = new String("<html><font color=blue><font size=-1><b><i>Country of Origin : ------<font color=red><font size=+1> Leave Blank For Car</i></b></font></html>");
		String msgmodel = new String("<html><font color=blue><font size=-1><b><i>Model Old or New : ----- <font color=red><font size=+1>Leave Blank For Motorbike</i></b></font></html>");
		String msgExtraInfo = new String("<html><font color=blue<font size=-1><b><i>Please enter some relevant Information about this Vehicle</i></b></font></html>");
		String msgNote = new String("<html><font color=blue><i>Please Leave Country of Origin field blank when entering data for Type Car,  and Model Old or New blank when adding a Motorbike.</i></font></html>");

		//text field for the year with a blue border and tool tip
		JTextField year = new JTextField("");
		year.setToolTipText("What year was the Vehicle Made");
		Border border = BorderFactory.createLineBorder(Color.BLUE);
		year.setBorder(BorderFactory.createCompoundBorder(border, 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//text field for the type of vehicle with a blue border and tool tip
		JTextField typeOfVehicle = new JTextField("");
		typeOfVehicle.setToolTipText("What type of Vehicle do you Have");
		Border border1 = BorderFactory.createLineBorder(Color.BLUE);
		typeOfVehicle.setBorder(BorderFactory.createCompoundBorder(border1, 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//text field for the price with a blue border and tool tip
		JTextField price = new JTextField("");
		price.setToolTipText("How much does the Vehicle Cost");
		Border border2 = BorderFactory.createLineBorder(Color.BLUE);
		price.setBorder(BorderFactory.createCompoundBorder(border2, 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//text field for the owners with a blue border and tool tip
		JTextField owners = new JTextField("");
		owners.setToolTipText("How many owners had the Vehicle");
		Border border3 = BorderFactory.createLineBorder(Color.BLUE);
		owners.setBorder(BorderFactory.createCompoundBorder(border3, 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//text field for the km travelled with a blue border and tool tip
		JTextField kmTraveled = new JTextField("");
		kmTraveled.setToolTipText("How many Km has the Vehicle Traveled");
		Border border4 = BorderFactory.createLineBorder(Color.BLUE);
		kmTraveled.setBorder(BorderFactory.createCompoundBorder(border4, 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));
		//text field for the owner with a blue border and tool tip
		JTextField previousOwner = new JTextField("");
		previousOwner.setToolTipText("Who was the Previous Owner");
		Border border5 = BorderFactory.createLineBorder(Color.BLUE);
		previousOwner.setBorder(BorderFactory.createCompoundBorder(border5, 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//text field for the country with a blue border and tool tip
		JTextField country = new JTextField("");
		country.setToolTipText("Which Country does the Vehicle come from");
		Border border6 = BorderFactory.createLineBorder(Color.BLUE);
		country.setBorder(BorderFactory.createCompoundBorder(border6, 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//text field for the model with a blue border and tool tip
		JTextField model = new JTextField("");
		model.setToolTipText("Is the Model Old or New");
		Border border7 = BorderFactory.createLineBorder(Color.BLUE);
		model.setBorder(BorderFactory.createCompoundBorder(border7, 
				BorderFactory.createEmptyBorder(5, 5, 5, 5)));

		//text field for the info with a blue border and tool tip
		JTextField extraInfo = new JTextField();	
		extraInfo.setToolTipText("Enter some info");
		Border border8 = BorderFactory.createLineBorder(Color.BLUE);
		extraInfo.setBorder(BorderFactory.createCompoundBorder(border8, 
				BorderFactory.createEmptyBorder(15, 15, 15, 15)));


		Object message[] = new Object[20];

		message[0] = myIcon;
		message[1] = msgYear;
		message[2] = year;
		message[3] = msgTypeOfVehicle;
		message[4] = typeOfVehicle;
		message[5] = msgPrice;
		message[6] = price;	
		message[7] = msgOwners;
		message[8] = owners;
		message[9] = msgKmTraveled;
		message[10] = kmTraveled;
		message[11] = msgPreviousOwner;
		message[12] = previousOwner;
		message[13] = msgCountry;
		message[14] = country;
		message[15] = msgmodel;
		message[16] = model;
		message[17] = msgExtraInfo;
		message[18] = extraInfo;
		message[19] = msgNote;

		//displays the vehicle data entry menu
		int response = JOptionPane.showConfirmDialog(null,message,"Vehicle Data Entry",JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE ,anIcon);

		if(response == JOptionPane.CANCEL_OPTION);
		else
		{
			try {

				if(model.getText().length() <= 0)
				{
					MotorBike m = new MotorBike();
					//add a bike
					m.setYear( Integer.parseInt( year.getText()) );
					m.setTypeOfVehicle( typeOfVehicle.getText() );
					m.setPrice( Double.parseDouble( price.getText()) );
					m.setOwners( Integer.parseInt( owners.getText()) );					
					m.setKmTraveled(Integer.parseInt( kmTraveled.getText()) );
					m.setPreviousOwner( previousOwner.getText() );
					m.setCountry( country.getText() );
					m.setExtraInfo( extraInfo.getText() );
					addVehicleToList(m);
				}

				Car c = new Car();
				//add a car
				c.setYear( Integer.parseInt( year.getText()) );
				c.setTypeOfVehicle( typeOfVehicle.getText() );
				c.setPrice( Double.parseDouble( price.getText()) );
				c.setOwners( Integer.parseInt( owners.getText()) );						
				c.setKmTraveled( Integer.parseInt( kmTraveled.getText()) );
				c.setPreviousOwner( previousOwner.getText() );						
				c.setmodel(model.getText());
				c.setExtraInfo( extraInfo.getText() );
				addVehicleToList(c);
			}

			catch (InvalidPriceException e)
			{
				JOptionPane.showMessageDialog(null,e);
			}

			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null,"Data Input Error" + e + "\nPlease Try Again");
			}
		}
	}

	/**
	 * @param d the vehicle to add
	 */
	private void addVehicleToList(Vehicle d )
	{
		try {
			VehicleList.add(d);
		}

		catch (Exception sqle)
		{
			JOptionPane.showMessageDialog(null,"Insert Into List Error" + sqle);	
		}	
	}

	/**
	 * getting the details of a vehicle
	 */
	public void menuPlayVehicle()
	{

		ImageIcon icon = new ImageIcon("bike.png");//displaying a new icon in pane
		String txtYear = (String) JOptionPane.showInputDialog(null,"<html><font color=blue><i>Please Enter the specific Year of the vehicle to view</i></html>","",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
		int year = Integer.parseInt(txtYear);
		boolean found = false;
		int i = 0;

		while(!found && (i < VehicleList.size()) )
		{
			if(VehicleList.get(i).getYear() == year)
			{
				VehicleList.get(i).playDetails();
				found = true;
			}
			i++;
		}

		if(!found) JOptionPane.showInputDialog(null,"<html><font color=red><i>Sorry, That Vehicle does not exist, Please try Another..</i></html>","",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
	}

	/**
	 * listing the vehicles
	 */
	public void menuListVehicles()
	{
		ImageIcon icon = new ImageIcon("bike.png");//new icon for pane
		JOptionPane.showMessageDialog(null,VehicleList, "", JOptionPane.INFORMATION_MESSAGE, icon);
	}

	/**
	 * removes a vehicle
	 */
	public void menuRemoveAVehicle()
	{
		ImageIcon icon = new ImageIcon("bike.png");//new icon for pane
		String txtKmTraveled = (String) JOptionPane.showInputDialog(null,"<html><font color=blue><i>Remove by number of Km Traveled</i></html","",JOptionPane.INFORMATION_MESSAGE,icon,null,"");
		int kmTraveled = Integer.parseInt(txtKmTraveled);
		boolean found = false;
		int i = 0;

		while(!found && (i < VehicleList.size()) )
		{
			if(VehicleList.get(i).getKmTraveled() == kmTraveled)
			{
				VehicleList.remove(i);
				found = true;
			}
			i++;
		}
	}

	/**
	 * writes the vehicles to file
	 */
	public void writeToFile()
	{
		fileHandler.writeToFile(VehicleList);
	}
}