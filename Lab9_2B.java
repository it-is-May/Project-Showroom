/**Program name: Lab9_2B
 * Name: Giao Quynh Nguyen
 * Purpose: To test the functionality of Customer, Customer_Cable, and Customer_Cable_Internet
 */
import java.util.*;
import java.io.*;
public class Lab9_2B
{
    public static void main(String[] args) throws FileNotFoundException
    {
        String name;        //customer's name
        String phone;       //customer's phone number
        String plan;        //customer's plan choice
        String speed;       //Internet speed chosen


        File textfile = new File("Lab9_2B.txt");
        Scanner infile = new Scanner(textfile);

        Customer_Cable[] list = new Customer_Cable[2];
        Customer_Cable_Internet[] list2 = new Customer_Cable_Internet[2];

        for(int i = 0; i < 4; i++)
        {
            if(i < 2)
            {
                name = infile.next();
                phone = infile.next();
                plan = infile.next();
                list[i] = new Customer_Cable(name, phone, plan);
            }
            else
            {
                name = infile.next();
                phone = infile.next();
                plan = infile.next();
                speed = infile.next();
                list2[i-2] = new Customer_Cable_Internet(name, phone, plan, speed);
            }
        }

        System.out.println(list[0]);
        System.out.println(list[1]);
        System.out.println(list2[0]);
        System.out.println(list2[1]);
    }

}