package ie.ollie.imp;
/**
 * 
 * @author Ollie Dowling
 *
 */

public class MotorBikeApp
{
	/**
	 * @param args
	 */
	public static void main( String args[] )
	{
		//vehicle manager
		int option;
		//case statement for the main menu
		MotorBikeManager manager = new MotorBikeManager();
		do {
			option = manager.menuMain();
			switch(option)
			{
			case 1  : manager.menuAddVehicle();
			break;
			case 2  : manager.menuPlayVehicle();
			break;
			case 3  : manager.menuListVehicles();
			break;	
			case 4  : manager.menuRemoveAVehicle();
			break;
			case 5  : manager.writeToFile();
			break;		
			default : break;
			}
		} while(option != 5);
	} 			
}


