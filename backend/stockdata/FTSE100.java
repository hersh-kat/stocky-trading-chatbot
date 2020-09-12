package stockdata;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * The following class is designed to store the most recent FTSE100 data.
 * The data is stored in a HashMap - array could be used but each index would have to correspond with a specific company,
 * this might become a hassle, if the FTSE changes.
 *
 * Uses JSoup to connect to the LSE website: http://www.londonstockexchange.com/exchange/prices-and-markets/stocks/indices/summary/summary-indices-constituents.html?index=UKX&page=1
 * Iterates through all 6 pages, and gets the data in the table.
 * The data in the table is then stored in the Stock class, then put into HashMap.
 */
public class FTSE100
{
	private HashMap<String, Stock> ftse100;
	
	/**
	 * The Constructor, simple initiating the HashMap.
	 */
	public FTSE100() 
	{
		ftse100 = new HashMap<String, Stock>();
	}
	
	/**
	 * Definitely the most important function in this class.
	 * Uses JSoup to connect to the FTSE100 page in LSE.
	 * Throws the data into the HashMap.
	 * This function needs to be called to called before attempting to access 
	 * anything in the table (the HashMap will be empty otherwise).
	 * 
	 * @return true on success, false otherwise
	 */
	public boolean updateFTSE100()
	{
		Document doc = null;
		
		// Iterating through all 6 pages.
		for(int i = 1; i <= 6; i++)
		{
			try
			{
				doc = Jsoup.connect("http://www.londonstockexchange.com/exchange/prices-and-markets/stocks/indices/summary/summary-indices-constituents.html?index=UKX&page=" + Integer.toString(i)).timeout(6000).get();
			} catch(IOException e)
			{
				e.printStackTrace();
				return false;
			}
			
			// Accessing the table in that page
			Element table = doc.selectFirst("table.table_dati");
			
			// Going through each row in the table
	        for (Element row : table.select("tr")) 
	        {
	        	// Going through each cell in the row.
	            Elements tds = row.select("td");
	            if (tds.size() > 9) 
	            {
	            	
	            	Stock stock = new Stock();
	            	
	            	// Putting all the data into the stock class
	            	stock.code = tds.get(0).text();
	            	stock.name = tds.get(1).text();
	            	stock.LSEPageURL = "http://www.londonstockexchange.com" + tds.get(1).html().split("\"", 3)[1];
	            	stock.curr = tds.get(2).text();
	            	stock.price = parseVal(tds.get(3).text());
	            	stock.diff = parseVal(tds.get(4).text());
	            	stock.perDiff = parseVal(tds.get(5).text());
	            							
	            	ftse100.put(tds.get(0).text(), stock);
	            }
	        }
		}
		
		return true;
	}
	
	public boolean updateCompany(String LSETicker)
	{
		// Parsing the SUMMARY page
		Stock stock = getStock(LSETicker);
		if(stock == null)
		{
			System.err.println("Company not in FTSE100 table");
			return false;
		}
		
		if(stock.LSEPageURL == null)
		{
			System.err.println("Stock does not have the LSE company page URL");
			return false;
		}
		
		Document doc = null;
		
		try
		{
			doc = Jsoup.connect(stock.LSEPageURL).timeout(6000).get();
		} catch(IOException e)
		{
			e.printStackTrace();
			return false;
		}
		
		// First time getting company details on stock.
		if(stock.company == null)
		{	
			stock.company = new CompanyDetails();
		}
		
		stock.company.fullName = doc.selectFirst("h1").text();
		
		Element table = doc.selectFirst("table");
		
		Element row = table.select("tr").get(1);	// Row 1 (0 index rows, so this is the 2nd row)
		Elements tds = row.select("td");
		stock.company.high = parseVal(tds.get(1).text());
		stock.company.low = parseVal(tds.get(3).text());
		
		row = table.select("tr").get(2);
		tds = row.select("td");
		stock.company.volume = parseVal(tds.get(1).text());
		stock.company.lastClose = tds.get(3).text();
		
		row = table.select("tr").get(3);
		tds = row.select("td");
		stock.company.bid = parseVal(tds.get(1).text());
		stock.company.offer = parseVal(tds.get(3).text());
		
		row = table.select("tr").get(4);
		tds = row.select("td");
		stock.company.tradingStatus = tds.get(1).text();
		stock.company.specialConditions = tds.get(3).text();
		
		// Parsing the NEWS page
		
		return true;
	}
	
	/**
	 *  Access a stock from the LSE ticker code.
	 * @param LSETicker the LSE symbol of the company you want the stock of
	 * @return the corresponding stock of the given LSE symbol
	 */
	public Stock getStock(String LSETicker)
	{
		return ftse100.get(LSETicker);
	}
	
	/**
	 * Just a simple helper function, the number values in the tables are in the following format:
	 * Price -> 1,234.00
	 * +/- and %+/- -> +124.00
	 * This parser simple accounts for both formats, and converts the string to the corresponding 
	 * double value.
	 * @param value the string of the number from the LSE table that needs to be parsed
	 * @return the corresponding double value of the given string
	 */
	private double parseVal(String value)
	{
		String noSignString = value;
		
		// Need to remove the '+' or '-' char for number format to work.
		if(value.charAt(0) == '+' || value.charAt(0) == '-')
		{
			noSignString = value.substring(1);
		}
			
		// Allows you to take string of a number with commas, and converts it to a decimal.
		NumberFormat format = NumberFormat.getInstance(Locale.US);
    	Number number = 0;
    	
    	try {
    		number = format.parse(noSignString);
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	
    	double returnVal = number.doubleValue();
    	
    	// If the first char was '-', then double needs to be negative.
    	if(value.charAt(0) == '-')
    	{
    		return returnVal * -1;
    	}
    	
    	return returnVal;
    	
	}
}


