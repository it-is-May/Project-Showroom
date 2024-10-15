/**Program name: Customer_Cable
 * Name: Giao Quynh Nguyen
 * Purpose: Extending from Customer, for cable-only customers
 */
public class Customer_Cable extends Customer
{
    public Customer_Cable(String inName, String inPhone, String inChoice)
    {
        super(inName, inPhone, inChoice);
        computeBill();
    }

    @Override
    public void computeBill()
    {
        if(planChoice.equalsIgnoreCase("Basic"))
            monthlyBill = 75.00;
        else if(planChoice.equalsIgnoreCase("Premium"))
            monthlyBill = 100.00;
        else if(planChoice.equalsIgnoreCase("Platinum"))
            monthlyBill = 125.00;
    }

    @Override
    public String toString()
    {
        String output = "This is cable-only customer. \n"
                + String.format("Name: %s \nPhone Number: %s \nPlan Choice: %s \nMonthly Bill: $%,.2f\n", name, phoneNum, planChoice, monthlyBill);
        return output;
    }
}