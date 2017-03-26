package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.personality_insights.v2.model.Profile;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * This class is an abstract class and it has different 
 * methods that can be used.
 * 
 * @author Ieuan Walker
 */

public abstract class utilities {
	
	/*
	 * This method is used parse the JSON data from IBM Watson and create an object 
	 * that included the parsed JSON and the data class. 
	 * 
	 * @param tone. This is what is returned from IBM Watson. 
	 * 
	 * @return DataToneAnalyser(). An instance of DataToneAnalyser with the JSON file attached to it
	 */
	public static DataToneAnalyser parserJsonToneAnalyser(ToneAnalysis tone){
		Gson gson = new Gson();	
		
		DataToneAnalyser dataObj = new DataToneAnalyser();
		
    	//read json file
		//BufferedReader br = new BufferedReader(new FileReader(profile));  
		String json = gson.toJson(tone);
		//parse and pass json file to pojo class
		dataObj = gson.fromJson(json, DataToneAnalyser.class);
    	return dataObj;
	}
	
	/*
	 * This method is used parse the JSON data from IBM Watson and create an object 
	 * that included the parsed JSON and the data class. 
	 * 
	 * @param profile. This is what is returned from IBM Watson. 
	 * 
	 * @return DataPersonalInsights(). An instance of DataPersonalInsights with the JSON file attached to it
	 */
	public static DataPersonalInsights parserJsonPersonalInsights(Profile profile){
		Gson gson = new Gson();	
		
		DataPersonalInsights dataObj = new DataPersonalInsights();
		
    	//read json file
		//BufferedReader br = new BufferedReader(new FileReader(profile));  
		String json = gson.toJson(profile);
		//parse and pass json file to pojo class
		dataObj = gson.fromJson(json, DataPersonalInsights.class);
    	return dataObj;
	}
	
	/*
	 * This method is used to take a selected file and write its content to a text area. 
	 * 
	 * @param file. The selected file.
	 * @param theText. The text area you want the file written too.
	 */
	public static void writeToArea(File file, TextArea theText){
		theText.setText("");
		try
        {
			@SuppressWarnings("resource")
			//read text file
			BufferedReader in = new BufferedReader(new FileReader(file));
			String line = in.readLine();
			while(line != null){
				theText.appendText(line + "\n");
				line = in.readLine();
			}
        }
        catch(Exception e2) {}
	}
	
	/*
	 * This method is used to allow the user to select a text file.
	 * It opens a file explorer and filter the file extensions with text file, txt and text. 
	 * 
	 * It then checks if the file is a text file and return the file or null. 
	 * 
	 * @param errorLb. This is the label the error will be written too.
	 * @return File. Returns the selected file path or null if no file is selected. 
	 */
	public static File getFile(Label errorLb){				
		//Create a new instance of JFileChooser class:
		JFileChooser fileChooser = new JFileChooser();
		//Set current directory:
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		fileChooser.setFileFilter(filter);
		
		int result = fileChooser.showOpenDialog(fileChooser);
		
		File selectedFile = null;
		if (result == JFileChooser.APPROVE_OPTION) {
			//Get the selected file path
			String filePath = fileChooser.getSelectedFile().getPath();

			if(fileType(filePath).equals("text/plain") || fileType(filePath).equals("application/json")){
				selectedFile = fileChooser.getSelectedFile();
			} else {
				errorMessage(errorLb, "Sorry you must choose a text file");
			}
		}
		//Show the dialog
		return selectedFile;
	}
	
	
	/** 
	 * Identify file type of file with provided path and name 
	 * using JDK 7's Files.probeContentType(Path). 
	 * http://marxsoftware.blogspot.co.uk/2015/02/determining-file-types-in-java.html
	 * 
	 * @param selectedFile Name of file whose type is desired. 
	 * @return String representing identified type of file with provided name. 
	 */  
	public static String fileType(final String selectedFile){  
	   String fileType = "Undetermined";  
	   final File file = new File(selectedFile);  
	   try{  
	      fileType = Files.probeContentType(file.toPath());  
	   }  
	   catch (IOException ioException){  
	      System.out.println("ERROR: Unable to determine file type for " + selectedFile + " due to exception " + ioException);  
	   }
	   if(fileType == null){
		   fileType = "null";
	   }
	   return fileType;  
	} 
	/*
	 * Used to set the text of a label. 
	 * More importantly this method is primarily used to set the error message text. 
	 * 
	 * @param errorLb. The label that the text is set on.
	 * @param theError. The text for the label.
	 */
	public static void errorMessage(Label errorLb, String theError){
		errorLb.setText("Error: " + theError);
	}
	
	/*
	 * This method removes text from a label.
	 * 
	 * @param errorLb. The label you want cleared
	 */
	public static void clearError(Label errorLb){
		errorLb.setText("");
	}
	
	/*
	 * This method takes a string and counts how many words are in it.
	 * 
	 * @param text. The string that will be counted. 
	 * 
	 * @return int. The number of words in the string. 
	 */
	public static int wordCount(String text){
		int wordCount = 0;

		String[] wordArray = text.split("\\s+");
		wordCount = wordArray.length;
		
	    return wordCount;
	}
	
	/*
	 * This method is used to create a pie chart.
	 * 
	 * @param piechart. This is the pie chart that the data will be added to.
	 * @param array1. This is a array list of variable names.
	 * @param array2. This is an array this of Double.
	 * @param string. This is the title for the pie chart. 
	 */
	public static void createPieChart(PieChart piechart, ArrayList<String> array1, ArrayList<Double> array2, String string){
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        
		for(int i = 0; i < array1.size(); i++){
			pieChartData.add(new PieChart.Data(array1.get(i), array2.get(i)));
		}   
		
		piechart.setTitle(string);
		piechart.setData(pieChartData);
	}
	
	/*
	 * This method is used to create a bar chart.
	 * 
	 * @param barchart. This is the bar chart that the data will be added to.
	 * @param array1. This is a array list of variable names.
	 * @param array2. This is an array this of Double.
	 * @param string. This is the title for the bar chart. 
	 */
	@SuppressWarnings("unchecked")
	public static void createBarChart(BarChart<String, Double> barchart, ArrayList<String> array1, ArrayList<Double> array2, String string) {
		ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
		
		Series<String, Double> barChartSeries = new Series<String, Double>();
		
		for (int i = 0; i < array1.size(); i++) {
			barChartSeries.getData().add(new Data<String, Double>(array1.get(i), array2.get(i)));
        }
		answer.addAll(barChartSeries);
		
		barchart.setTitle(string);
		barchart.setData(answer);
	}
	
	/*
	 * This method is used to create a line chart.
	 * 
	 * @param linechart. This is the line chart that the data will be added to.
	 * @param array1. This is a array list of variable names.
	 * @param array2. This is an array this of Double.
	 * @param string. This is the title for the pie chart. 
	 */
	public static void createLineChart(LineChart<String, Double> linechart, ArrayList<String> array1, ArrayList<Double> array2, String string) {
		XYChart.Series<String, Double> lineChartSeries = new XYChart.Series<String, Double>();
		
		for(int i = 0; i < array1.size(); i++){
			lineChartSeries.getData().add(new XYChart.Data<String, Double>(array1.get(i), (double) array2.get(i)));
		}   
		
		linechart.setTitle(string);
		linechart.getData().add(lineChartSeries);
	}
}
