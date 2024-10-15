/**Program name: Customer_Cable_Internet
 * Name: Giao Quynh Nguyen
 * Purpose: Extending from Customer, for cable and Internet customers
 */
public class Customer_Cable_Internet extends Customer
{
    protected String speed;     //Internet speed

    public Customer_Cable_Internet(String inName, String inPhone, String inChoice, String inSpeed)
    {
        super(inName, inPhone, inChoice);
        speed = inSpeed;
        computeBill();
    }

    @Override
    public void computeBill()
    {
        //Figuring out the plan
        if(planChoice.equalsIgnoreCase("Basic"))
            monthlyBill = 75.00;
        else if(planChoice.equalsIgnoreCase("Premium"))
            monthlyBill = 100.00;
        else if(planChoice.equalsIgnoreCase("Platinum"))
            monthlyBill = 125.00;

        //Add the speed cost after knowing the plan
        if(speed.equalsIgnoreCase("High"))
            monthlyBill += 60.00;
        else if(speed.equalsIgnoreCase("Regular"))
            monthlyBill += 40.00;
    }

    @Override
    public String toString()
    {
        String output = "This is a cable-and-Internet customer. \n"
                + String.format("Name: %s \nPhone Number: %s \nPlan Choice: %s \nMonthly Bill: $%,.2f \nInternet Speed: %s\n", name, phoneNum, planChoice, monthlyBill, speed);
        return output;
    }
}