package ie.ollie.imp;

import ie.ollie.ab1.Vehicle;
import javax.swing.*;
/**
 * 
 * @author Ollie Dowling
 *
 */
public class MotorBike extends Vehicle {

	private static final long serialVersionUID = 1L;
	protected String country;

	public MotorBike() 
	{
		super();
		this.country = "";
	}
	/**
	 * @param year
	 * @param typeOfVehicle
	 * @param price
	 * @param owners
	 * @param kmTraveled
	 * @param previousOwner
	 * @param extraInfo
	 * @param country
	 */
	public MotorBike(int year, String typeOfVehicle, double price,int owners, 
			int kmTraveled, String previousOwner,  String extraInfo,String country) 

	{
		super(year,typeOfVehicle,price, owners,kmTraveled,previousOwner, extraInfo);
		this.country = country;
	}
	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * @return a string representation of the vehicle information
	 */
	public String toString() {
		return "Vechicle Information [ year=" + year + ", typeOfVehicle=" + typeOfVehicle
				+ ", price=" + price + ", owners=" + owners +", kmTraveled=" + kmTraveled + "," +
				" previousOwner=" + previousOwner + ", country=" + country + ", extraInfo=" + extraInfo +"]\n";
	}
	/**
	 * displays details of a specific vehicle
	 */
	public void playDetails() 
	{
		JOptionPane.showMessageDialog(null," This is a " + this.typeOfVehicle + " From " + this.country + " and it costs " + this.price + " " + this.extraInfo );	
	}
	/**
	 * 
	 * @return
	 */
	public String decoder()
	{
		return ".AVI";
	}

	/**
	 * @return toString
	 */
	public String getInfo() { return this.toString(); }

}

