package stockdata;

/**
 *  This class represents a simple stock.
 *  Not much point having setters and getter (too many variables, easier to access each variable
 *  directly - could be added for encapsulation ... it will be a hassle).
 */

public class Stock 
{
	// This data will be straight from the LSE FTSE100 table
	public String code;
	public String name;
	public String curr;
	public double price;
	public double diff;
	public double perDiff;
	public String LSEPageURL;
	public CompanyDetails company;
}
