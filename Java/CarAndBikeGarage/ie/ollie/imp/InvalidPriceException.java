package ie.ollie.imp;

public class InvalidPriceException extends ArithmeticException {

	/**
	 * @author Ollie Dowling
	 */
	private static final long serialVersionUID = 1L;
    
	public InvalidPriceException() 
	{
		// TODO Auto-generated constructor stub
		super("Sorry, No Can Do");
	}

	public InvalidPriceException(String message) 
	{
		super(message);
		// TODO Auto-generated constructor stub
	}

}
