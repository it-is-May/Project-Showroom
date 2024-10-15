/**Program name: Customer
 * Name: Giao Quynh Nguyen
 * Purpose: To create an abstract class that creates some blueprints for its child classes
 */
public abstract class Customer
{
    protected String name;              //customer's name
    protected String phoneNum;          //customer's phone number
    protected String planChoice;        //customer's plan choice
    protected double monthlyBill;       //monthly bill for customer

    public Customer(String inName, String inPhone, String inChoice)
    {
        name = inName;
        phoneNum = inPhone;
        planChoice = inChoice;
        monthlyBill = 0.0;
    }

    public abstract void computeBill();

    public abstract String toString();

}
