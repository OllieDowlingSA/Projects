package ie.ollie.ab1;

import ie.ollie.imp.InvalidPriceException;
import java.io.Serializable;
/**
 * 
 * @author Ollie Dowling
 *
 */
public abstract class Vehicle implements Serializable
{
	/**
	 * instance variables
	 */
	private static final long serialVersionUID = 1L;
	protected int year;
	protected String typeOfVehicle;
	protected double price;
	protected int owners;
	protected int kmTraveled;
	protected String previousOwner;
	protected String extraInfo;

	public Vehicle()
	{
		this.year = 0;
		this.typeOfVehicle = "";
		this.price = 0.0;
		this.owners = 0;
		this.kmTraveled = 0;
		this.previousOwner = "";
		this.extraInfo = "";
	}
	/**
	 * @param year
	 * @param typeOfVehicle
	 * @param price
	 * @param owners
	 * @param kmTraveled
	 * @param previousOwner
	 * @param extraInfo
	 */
	public Vehicle(int year, String typeOfVehicle, double price,int owners,int kmTraveled, String previousOwner, String extraInfo) {
		this.year = year;
		this.typeOfVehicle = typeOfVehicle;
		this.price = price;
		this.owners = owners;
		this.kmTraveled = kmTraveled;
		this.previousOwner = previousOwner;
		this.extraInfo = extraInfo;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year set the year
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return type of vehicle
	 */
	public String getTypeOfVehicle() {
		return typeOfVehicle;
	}
	/**
	 * @param typeOfVehicle set type of vehicle
	 */
	public void setTypeOfVehicle(String typeOfVehicle) {
		this.typeOfVehicle = typeOfVehicle;
	}
	/** 
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * @param price set the price
	 * @throws InvalidPriceException of price
	 */
	public void setPrice(double price) throws InvalidPriceException 
	{
		if(price <= 0.0) throw new InvalidPriceException();
		this.price = price;
	}
	/**
	 * @return the owners
	 */
	public int getOwners() {
		return owners;
	}
	/**
	 * @param owners set the owners
	 */
	public void setOwners(int owners) {
		this.owners = owners;
	}
	/**
	 * @return the km traveled
	 */
	public int getKmTraveled() {
		return kmTraveled;
	}
	/**
	 * @param kmTraveled set the km traveled
	 */
	public void setKmTraveled(int kmTraveled) {
		this.kmTraveled = kmTraveled;
	}
	/**
	 * @return the previous owner
	 */
	public String getPreviousOwner() {
		return previousOwner;
	}
	/**
	 * @param previousOwner set the previous owner
	 */
	public void setPreviousOwner(String previousOwner) {
		this.previousOwner = previousOwner;
	}
	/**
	 * @return the extra information
	 */
	public String getExtraInfo() {
		return extraInfo;
	}
	/**
	 * @param extraInfo set the extra information
	 */
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	/**
	 * abstract method playDetails
	 */
	public abstract void playDetails();
	/**
	 * @return toString
	 */
	public String getInfo() { return this.toString(); }

}
