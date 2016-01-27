package ie.ollie.imp;

import ie.ollie.ab1.Vehicle;
import javax.swing.JOptionPane;
/**
 * 
 * @author Ollie Dowling
 *
 */

public class Car extends Vehicle
{

	private static final long serialVersionUID = 1L;
	protected String model;

	public Car() {
		super();
		this.model = "";
	}
	/**
	 * 
	 * @param year
	 * @param typeOfVehicle
	 * @param price
	 * @param owners
	 * @param kmTraveled
	 * @param previousOwner
	 * @param model
	 * @param extraInfo
	 */

	public Car(int year, String typeOfVehicle, double price,int owners, int kmTraveled, String previousOwner, String model, String extraInfo) {
		super(year,typeOfVehicle,price, owners, kmTraveled, previousOwner, extraInfo);
		this.model = model;
	}
	/**
	 * @return the model to return
	 */
	public String getmodel() {
		return model;
	}
	/** 
	 * @param model the model to set
	 */
	public void setmodel(String model) {
		this.model = model;
	}
	/**
	 * returns the vehicle information
	 */
	@Override
	public String toString() {
		return "Vehicle Information [ year=" + year + ", typeOfVehicle=" + typeOfVehicle
				+ ", price=" + price + ", owners=" + owners +", kmTraveled=" + kmTraveled + ", previousOwner=" + previousOwner + ",  model=" + model + ", extraInfo=" + extraInfo +  "]\n";
	}

	/**
	 * returns the details of a specific vehicle
	 */
	public void playDetails() 
	{
		JOptionPane.showMessageDialog(null,"This is a " + this.typeOfVehicle + " and it is an " + this.model + " Model that costs " + this.price + " " + this.extraInfo);	
	}

}
