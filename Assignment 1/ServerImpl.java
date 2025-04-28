import java.rmi.*;
import java.rmi.server.*;

//eligible to be exported to receive incoming RMI calls.
public class ServerImpl extends UnicastRemoteObject implements ServerIntf {

    public ServerImpl() throws RemoteException {
    }

    public int addition(int a, int b) throws RemoteException {
        return a + b;
    }

    public int substraction(int a, int b) throws RemoteException {
        return a - b;
    }

    public int division(int a, int b) throws RemoteException {
        return a / b;
    }

    public int multiplication(int a, int b) throws RemoteException {
        return a * b;
    }

    public int square(int a) throws RemoteException {
        return a * a;
    }

    public int squareroot(int a) throws RemoteException {
        return (int) (Math.sqrt(a));
    }

    public void palindrome(String str) throws RemoteException {
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();

        if (str.equals(sb.toString()))
            System.out.println("String is Palindrome!");
        else
            System.out.println("String is Not Palindrome!");

    }

    public void isequalstring(String str1, String str2) throws RemoteException {
        if (str1.equals(str2))
            System.out.println("String is equal!");
        else
            System.out.println("String is not equal!");
    }

    public long calculatePower(int exponent) throws RemoteException {
        return (long) Math.pow(2, exponent);
    }

    public double convertCelsiusToFahrenheit(double celsius) throws RemoteException {
        return (celsius * 9.0 / 5.0) + 32.0;
    }

    public double convertMilesToKilometers(double miles) throws RemoteException {
        return miles * 1.60934;
    }

    public String getLargestString(String str1, String str2) throws RemoteException {
        if (str1.compareTo(str2) > 0)
            return str1;
        else
            return str2;
    }
    
    public String hel(String c) throws RemoteException {
      return "Hello "+c; 
      }
      
      public int cvwl(String c) throws RemoteException {
        int vc = 0;
        for (int i = 0; i < c.length(); i++) {
            char ch = c.charAt(i);
            if (ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u') {
                vc++;
            }
        }
        
        return vc;
        }
        
        public int fct(int c) throws RemoteException {
         	int fact=1;
         	for(int i=1;i<=c;i++)
         	{
         	fact=fact*i;
         	}
	        return fact;
 }
    
    

}
