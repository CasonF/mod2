import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileReader {

	public static void main(String[] args) 
	{	
		Map<String, Integer> wordMap = new HashMap<>();
		
		try {
			String user = System.getProperty("user.home");
			//Simply using user + \\Desktop\\TheRaven.txt didn't work for me,
			//but if you have a Desktop folder in your main user folder,
			//feel free to remove the \\OneDrive part of this code
			File raven = new File(user + "\\OneDrive\\Desktop\\TheRaven.txt");
			Scanner reader = new Scanner(raven);
		
			while (reader.hasNextLine())
			{
				//This separates words that are separated by spaces. Also accounts for additional symbols between words.
				String words[] = reader.next().toLowerCase().split("[\\s*,.!?\"\'-+\\–\\—\\;]");
				for (String w : words)
				{
					//Here I use the conditional operator to check if a word has
					//already been counted, then I add 1 to the count (number of
					//that word present in the text file).
					Integer q = wordMap.get(w);
					q = (q == null) ? 1 : ++q; //if q doesn't exist for this var, q = 1, otherwise ++q
					wordMap.put(w, q);
				}
			}
			reader.close();
		}
		catch(FileNotFoundException e)
		{
			//If you have TheRaven.txt in another directory, you can ignore this if you have edited the
			//file searcher to locate your .txt
			System.out.println("An error occurred. Please make sure TheRaven.txt is on your desktop.");
			e.printStackTrace();
		}
		
		//LinkedHashMap is used to maintain the order of the variables
		LinkedHashMap<String, Integer> freqMap = new LinkedHashMap<String, Integer>();
		//entryset().stream() takes the wordMap's entry set and streams its values
		//sorted(...reverseOrder()) sorts the streamed values from highest value to lowest
		//limit(20) will produce only 20 results from the data set/stream (the top 20 words)
		//forEachOrdered(...) puts the ordered elements into freqMap in order from highest value to lowest
		wordMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(20).forEachOrdered(x -> freqMap.put(x.getKey(), x.getValue()));
		
		System.out.println("The 20 most common words in the text file, with their frequency (word=# in the text), are:\n" + freqMap);
	}
}

