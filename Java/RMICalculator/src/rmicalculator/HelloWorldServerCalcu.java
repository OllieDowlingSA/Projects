package rmicalculator;

import java.rmi.Naming;

/**
 * @author Ollie Dowling
 * @version 1.0
 */

public class HelloWorldServerCalcu
{
	public static void main(String args[])
	{
		try
		{    
			HelloWorldCalImpl cal = new HelloWorldCalImpl();
			Naming.rebind("server",cal);
		}
		catch(Exception e){}
	}
}
