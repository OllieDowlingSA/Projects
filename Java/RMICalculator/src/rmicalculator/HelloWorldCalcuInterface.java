package rmicalculator;

import java.rmi.*;

/**
 * @author Ollie Dowling
 * @version 1.0
 */

public interface HelloWorldCalcuInterface extends Remote  
{         //Declaring the methods
          double addition(double c1,double c2) throws RemoteException;
          double subtraction(double c1,double c2) throws RemoteException;
          double multiplication(double c1,double c2) throws RemoteException;
          double division(double c1,double c2) throws RemoteException;
}

