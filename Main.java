package application;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class Main extends Application {
	
	Stage window;
	Scene scene1, scene2;
	final int W = 900;
	final int H = 200;
	
	static String output;
	
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
		
		output = "The 20 most common words in the text file, with their frequency (word=# in the text), are:\n" + freqMap;
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try
		{
			window = primaryStage;
			window.setTitle("TheRavenApp");
			
			//Start window
			
			Button button1 = new Button();
			button1.setText("Let's do this!");
			Label t = new Label("This is the GUI for TheRavenProject.java.");
			Label prompt = new Label("Click the button below to get the words in TheRaven.txt.");
			
			VBox layout1 = new VBox(20);
			layout1.getChildren().addAll(t, prompt, button1);
			
			scene1 = new Scene(layout1, W, H);
			
			
			//Run window
			
			System.out.println(output);
			Label result = new Label(output);
			
			VBox layout2 = new VBox(20);
			layout2.getChildren().addAll(result);
			
			scene2 = new Scene(layout2, W, H);
			
			//Implement
			
			primaryStage.setScene(scene1);
			primaryStage.show();
			
			
			//// Button logic ////
			
			button1.setOnAction(e ->
	        {
	            window.setScene(scene2);
	        });
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
