package stockdata;

import java.util.LinkedList;

public class CompanyDetails 
{
	// This data will be from the company specific page on LSE
	//Summary
	public String fullName;
	public double high;
	public double volume;
	public double bid;
	public String tradingStatus;
	public double low;
	public String lastClose;
	public double offer;
	public String specialConditions;
	
	//News Analysis
	public LinkedList<String> newsPageUrls;
	
}
