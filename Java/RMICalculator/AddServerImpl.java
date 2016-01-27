import java.rmi.*;
import java.rmi.server.*;
public class AddServerImpl extends UnicastRemoteObject implements AddServerIntf {
    public AddServerImpl() throws RemoteException {
    }
    public double add(double d1, double d2) throws RemoteException {
        return d1 + d2;
    }
    public double divide (double d1, double d2) throws RemoteException {
        return d1/d2;
    }
    public double multiply (double d1, double d2) throws RemoteException {
        return d1*d2;
    }
    public double subtract (double d1, double d2) throws RemoteException {
        return d1-d2;
    }
    
}