import java.rmi.*;
import java.io.*;

public class AddClient {
    public static void main(String args[]) {
        try {
            String addServerURL = "rmi://" + args[0] + "/AddServer";
            AddServerIntf addServerIntf =
            (AddServerIntf)Naming.lookup(addServerURL);
            
            int choice = 0;
            double d1, d2;
            double result = 0.0;
            BufferedReader console = new BufferedReader (new InputStreamReader (System.in));  
            
            while (true) {
                System.out.println ("Menu");
                System.out.println ("(1) Addition");
                System.out.println ("(2) Subtraction");
                System.out.println ("(3) Multiplication");
                System.out.println ("(4) Division");
                System.out.println ("(0) Quit");

                choice = Integer.parseInt (console.readLine());
                
                if (choice == 0) {
                    System.exit (0);
                }
                
                System.out.println("The first number: ");
                d1 = Double.parseDouble(console.readLine());
                System.out.println("The second number: ");
                d2 = Double.parseDouble(console.readLine());  
                switch (choice) {
                    case 1: result = addServerIntf.add (d1, d2);
                            break;
                    case 2: result = addServerIntf.subtract (d1, d2);
                            break;
                    case 3: result = addServerIntf.multiply (d1, d2);
                            break;
                    case 4: result = addServerIntf.divide (d1, d2);
                            break;
                    default: System.out.println ("Unknown Option Chosen");
                            break;
                }

                System.out.println("The result is: " + result);
            }
        }
        catch(Exception e) {
            System.out.println("Exception: " + e);
        }
    }
}