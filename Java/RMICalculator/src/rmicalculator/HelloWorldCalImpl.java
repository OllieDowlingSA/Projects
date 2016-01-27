package rmicalculator;

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Ollie Dowling
 * @version 1.0
 */

public class HelloWorldCalImpl extends UnicastRemoteObject implements HelloWorldCalcuInterface
{                                    
	private static final long serialVersionUID = 1L;
	//interface methods
	HelloWorldCalImpl() throws RemoteException
	{
	}
	public double addition(double c1, double c2)throws RemoteException
	{
		return(c1+c2);
	}
	public double subtraction(double c1,double c2)throws RemoteException
	{
		return(c1-c2);
	}
	public double multiplication(double c1,double c2)throws RemoteException
	{
		return(c1*c2);
	}    
	public double division(double c1,double c2)throws RemoteException
	{
		return(c1/c2);
	} 
}