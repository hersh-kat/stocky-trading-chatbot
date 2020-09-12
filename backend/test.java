import newsdata.News;
import newsdata.NewsPageParser;
import stockdata.FTSE100;

public class test
{
	
	public static void main(String[] args)
	{
	    FTSE100 ftse100 = new FTSE100();
	    
	    ftse100.updateFTSE100();
	    
	    System.out.println("-----------FTSE 100 TEST---------------");
	    System.out.println("");
	    
	    String stock = "ADM";
	    
	    System.out.println("NAME: " + ftse100.getStock(stock).name);
	    System.out.println("CURRENCY: " + ftse100.getStock(stock).curr);
	    System.out.println("PRICE: " + ftse100.getStock(stock).price);
	    System.out.println("DIFFERNCE: " + ftse100.getStock(stock).diff);
	    System.out.println("PERCENTAGE DIFFERENCE: " + ftse100.getStock(stock).perDiff);
	    
	    System.out.println("");
	    System.out.println("-----------COMPANY DETAILS TEST---------------");
	    System.out.println("");
	    
	    String company = "ABF";
	    
	    ftse100.updateCompany(company);
	    System.out.println("FULL NAME: " + ftse100.getStock(company).company.fullName);
	    System.out.println("HIGH: " + ftse100.getStock(company).company.high);
	    System.out.println("LOW: " + ftse100.getStock(company).company.low);
	    System.out.println("VOLUME: " + ftse100.getStock(company).company.volume);
	    System.out.println("LAST CLOSE: " + ftse100.getStock(company).company.lastClose);
	    System.out.println("BID: " + ftse100.getStock(company).company.bid);
	    System.out.println("OFFER " + ftse100.getStock(company).company.offer);
	    System.out.println("TRADING STATUS: " + ftse100.getStock(company).company.tradingStatus);
	    System.out.println("SPECIAL CONDITIONS: " + ftse100.getStock(company).company.specialConditions);
	    
	    System.out.println("");
	    System.out.println("-----------NEWS TEST---------------");
	    System.out.println("");
	    
	    News news = NewsPageParser.getNews("http://www.bbc.co.uk/news/uk-politics-42819405");
	    
	    System.out.println("TITLE: " + news.title);
	    System.out.println("");
	    System.out.println("TYPE: " + news.type);
	    System.out.println("");
	    System.out.println("DESCRIPTION: " + news.description);
	    System.out.println("");
	    
	    System.out.println("Image URLs");
	    for(String URL: news.imgURLs)
	    {
	    	System.out.println(URL);
	    }
	    System.out.println("");
	}
	
}
