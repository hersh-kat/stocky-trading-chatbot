package newsdata;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NewsPageParser 
{
	private static String IMAGE_DESTINATION_FOLDER = "PATH/TO/FOLDER/";
	
	// Needed as sometimes you get junk files which in in the <img> tag in HTML
	private static String[] imgTypes = {"png", "jpg", "jpeg", "gif"};
	
	public static News getNews(String URL)
	{
		Document doc = null;
		News news = null;
		
		try
		{
			doc = Jsoup.connect(URL).timeout(6000).get();
		} catch(IOException e)
		{
			e.printStackTrace();
			return news;
		}
		
		news = new News();
		
		
		// Title
	    Elements metaOg = doc.select("meta[property=og:title]");
	    if (metaOg!=null) 
	    {
	    	news.title = metaOg.attr("content");
	    }
	    else 
	    {
	    	news.title = doc.title();
	    }
	    
	    // Type
	    metaOg = doc.select("meta[property=og:type]");
		if (metaOg!=null) 
		{
	    	news.type = metaOg.attr("content");
	    }
	    else 
	    {
	    	news.type = null;
	    }
	    
	    // Description
	    metaOg = doc.select("meta[property=og:description]");
		if (metaOg!=null) 
		{
	    	news.description = metaOg.attr("content");
	    }
	    else 
	    {
	    	news.description = null;
	    }
		
		// Image URLs
		news.imgURLs = new LinkedList<String>();
		
		metaOg = doc.select("img");
	
		//iterate over each image
		for(Element imgElement : metaOg)
		{
			//make sure to get the absolute URL using abs: prefix
			String strImageURL = imgElement.attr("abs:src");
			
			// Checking if the URL is actually an image (sometimes you get junk files)
			if(Arrays.asList(imgTypes).contains(strImageURL.substring(strImageURL.lastIndexOf('.') + 1)))
			{
				// Storing the image URL.
				news.imgURLs.add(strImageURL);
			}
			
			
		}
		
		return news;
	}
	
	public static void downloadImage(String strImageURL)
	{
		//get file name from image path
		String strImageName = strImageURL.substring( strImageURL.lastIndexOf("/") + 1 );
		
		System.out.println("Saving: " + strImageName + ", from: " + strImageURL);
		
		try 
		{
			
			//open the stream from URL
			URL urlImage = new URL(strImageURL);
			InputStream in = urlImage.openStream();
			
			byte[] buffer = new byte[4096];
			int n = -1;
			
			OutputStream os = new FileOutputStream( IMAGE_DESTINATION_FOLDER + "/" + strImageName );
			
			//write bytes to the output stream
			while ( (n = in.read(buffer)) != -1 )
			{
				os.write(buffer, 0, n);
			}
			
			//close the stream
			os.close();
			
			System.out.println("Image saved");
			
		} catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
}
