import java.rmi.*;

public interface AddServerIntf extends Remote{
    double divide (double d1, double d2) throws RemoteException;
    double add(double d1, double d2) throws RemoteException;
    double multiply(double d1, double d2) throws RemoteException;
    double subtract(double d1, double d2) throws RemoteException;
}