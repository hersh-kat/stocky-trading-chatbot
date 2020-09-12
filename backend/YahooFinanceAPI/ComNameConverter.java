package Data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

public class ComNameConverter {

	static String convert(String comName) throws IOException {
		
		String urlString = "http://d.yimg.com/autoc.finance.yahoo.com/autoc?query=" + comName
					+ "&region=1&lang=en&callback=YAHOO.Finance.SymbolSuggest.ssCallback";
		URL url = new URL(urlString);
		try {
		    URLConnection conn = url.openConnection();
		    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String inputLine = "";
		    String symbol = "";
		    while ((inputLine = in.readLine()) != null) {
		    		if((inputLine.indexOf("]") - inputLine.indexOf("[")) != 1) {
			    		inputLine = inputLine.substring(inputLine.indexOf("symbol")+9);
			    		symbol = inputLine.substring(0,inputLine.indexOf("\""));
			    		System.out.println(symbol);
		    		}
		    		else
		    			System.out.println("No such company!");
		    }
		    	in.close();
		    	return symbol;
		} catch(UnknownHostException e) {
			System.out.println("the converter server link is down!");
			return "";
		}
	}
}
