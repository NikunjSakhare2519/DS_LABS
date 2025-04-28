import ReverseApp.*;
import org.omg.CORBA.*;
import java.io.*;
import java.util.Scanner;

public class ReverseClient {
    public static void main(String args[]) {
    Scanner sc=new Scanner(System.in);
        try {
            ORB orb = ORB.init(args, null);

            // Read IOR from file
            BufferedReader br = new BufferedReader(new FileReader("ReverseIOR.txt"));
            String ior = br.readLine();
            br.close();

            org.omg.CORBA.Object objRef = orb.string_to_object(ior);
            Reverse reverseRef = ReverseHelper.narrow(objRef);

            String input = "CORBA is cool";
            System.out.println("Enter the String");
            String s1=sc.nextLine();
            String reversed = reverseRef.reverse_string(s1);
            String upperCase = reverseRef.uppercase(s1);

            System.out.println("Original: " + s1);
            System.out.println("Reversed: " + reversed);
            System.out.println("Uppercase: " + upperCase);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
