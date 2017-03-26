package ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;

import com.ibm.watson.developer_cloud.personality_insights.v2.PersonalityInsights;
import com.ibm.watson.developer_cloud.personality_insights.v2.model.Profile;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;

import application.DataPersonalInsights;
import application.DataToneAnalyser;
import application.utilities;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

/*
 * <h2>The Controller Class </h2>
 * This class is used to bridge the gap between the view and the modal (MVC).
 * This class has all the button handlers and events.
 * 
 * @author Ieuan Walker
 */

public class controller implements Initializable{

	@FXML private TextArea theText;
	@FXML private Label errorLb;
	@FXML private ScrollPane scrollPane;
	@FXML private PieChart personalityPieChart;
	@FXML private BarChart<String, Double> needsBarChart;
	@FXML private LineChart<String, Double> valuesLineChart;
	@FXML private PieChart tonePieChart;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		System.out.println("View Loaded :D");
	}
	
	/*
	 * This is the event handler for the resetBtn().
	 * This method resets the application to the original load state. 
	 */
	public void resetBtn(){
		//Delete text in text area
		theText.setText("");
		
		//Remove any error messages
		utilities.clearError(errorLb);
		
		//Reset Pie chart
		ObservableList<PieChart.Data> datapiechart = FXCollections.observableArrayList();
		personalityPieChart.setTitle(null);
		personalityPieChart.setData(datapiechart);
		
		//Reset Bar graph
		ObservableList<XYChart.Series<String, Double>> answer = FXCollections.observableArrayList();
		needsBarChart.setData(answer);
		
		//Reset Line graph
		valuesLineChart.getData().clear();
		
		//Hide scroll pane
		scrollPane.setVisible(false);
	}
	
	/*
	 * This is the event handler for the analyzeBtnClick().
	 * This method does a number of things.
	 * 		1. It checks that the text area contains something
	 * 		2. It checks if there is less than 100 words
	 * 		3. If it passes all the checks it will then get the user  and password from the properties file
	 *		4. If it successfully gets the username and passwords it will then send the string to IBM Watson
	 * 		4. The returned profile then gets parsed
	 * 		5. The different graphs get created 
	 */
	public void analyseBtnClick(){	
		System.out.println("Analyse button clicked.");
		//Clears any error messages
		utilities.clearError(errorLb);
		
		if(theText.getText().trim().length() == 0){
			utilities.errorMessage(errorLb, "You must enter some text");
		} else if(utilities.wordCount(theText.getText()) < 100){
			utilities.errorMessage(errorLb, "Word count = " + utilities.wordCount(theText.getText()) + ". Must include at least 100 words.");
		} else {
			String ToneUsername = null;
			String TonePassword = null;
			String PersonalityUsername = null;
			String PersonalityPassword = null;
			
			//In case reading the properties file has a problem.  
			boolean usernamePass = false;
			
			
			Properties props = new Properties();
			FileInputStream fis = null;
			
			try {
				fis = new FileInputStream("IbmWatsonCredentials.properties");
				props.load(fis);
				
				ToneUsername = props.getProperty("ToneAnalyser_USERNAME");
				TonePassword = props.getProperty("ToneAnalyser_PASSWORD");
				
				PersonalityUsername = props.getProperty("PersonalityInsights_USERNAME"); 
				PersonalityPassword = props.getProperty("PersonalityInsights_PASSWORD"); 
			} catch (IOException e) {
				usernamePass = true;
			} catch (Exception e){
				usernamePass = true;
			}
				
			if(ToneUsername == null || TonePassword == null || PersonalityUsername == null || PersonalityPassword == null || usernamePass){
				utilities.errorMessage(errorLb, "Sorry their was a problem getting the relevant Credentials to access IBM watson");
			} else {
				System.out.println("Connecting to IBM Watson.");
				
				//Text to be analysed
				String text = theText.getText();
				
				
				//Tone Analyser
				ToneAnalyzer serviceToneAnalyser = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_05_19);
				serviceToneAnalyser.setUsernameAndPassword(ToneUsername, TonePassword);
				
				//Send the text to IBM Watson
				ToneAnalysis tone = serviceToneAnalyser.getTone(text, null).execute();

				//Create a data object
				DataToneAnalyser objToneAnalyser = new DataToneAnalyser();
				objToneAnalyser = utilities.parserJsonToneAnalyser(tone);

				
				
				//Personality Insights
				PersonalityInsights servicePersonalityInsights = new PersonalityInsights();
			    servicePersonalityInsights.setUsernameAndPassword(PersonalityUsername, PersonalityPassword);
			    
			    //Send the text to IBM Watson
			    Profile profilePersonalityInsights = servicePersonalityInsights.getProfile(text).execute();
			    
			    
			    //Create a data object
			    DataPersonalInsights objPersonalInsights = new DataPersonalInsights();
				objPersonalInsights = utilities.parserJsonPersonalInsights(profilePersonalityInsights);

				System.out.println("Got data back from IBM Watson");
				
				//Display Scroll pane
				scrollPane.setVisible(true);
				
				//Clearing the line chart
				valuesLineChart.getData().clear();

				//Create the Charts and diagrams
				System.out.println("Creating charts.");
				createCharts(objPersonalInsights, objToneAnalyser);
				System.out.println("Charts created.");
			}
		}
	}
	
	/*
	 * This is the event handler for the fileOpenBtnClick().
	 * This method calls getFile() method that opens a file explorer and returns a file.
	 * If the file doesn't equal null then the content of the file gets written to the text area. 
	 */
	public void fileOpenBtnClick(){
		//Show the dialog
		File result = utilities.getFile(errorLb);
		
		if(result != null){
			utilities.writeToArea(result, theText);
		}
	}

	/*
	 * This method creates all the charts for the application.
	 * It does this be gathering all the necessary data and sending it to the utilities.createGraph() method.
	 * 
	 * @param objPersonalInsights. This is the instance for the personal insights data modal.
	 * @param objToneAnalyser. This is the instance for the tone analyser data modal
	 */
	public void createCharts(DataPersonalInsights objPersonalInsights, DataToneAnalyser objToneAnalyser){
		//Pie chart
		ArrayList<String> personalityId = new ArrayList<String>();
		ArrayList<Double> personalityPercentage = new ArrayList<Double>();
		
		for(int i = 0; i < objPersonalInsights.getPersonalitySize(); i++){
			personalityId.add(objPersonalInsights.getPersonalityDetails(i, "name").replace("\"", ""));
			personalityPercentage.add(Double.parseDouble(objPersonalInsights.getPersonalityDetails(i, "percentage")));
		}    
		
		utilities.createPieChart(personalityPieChart, personalityId, personalityPercentage, "Personality");
		
	
		//Bar chart
		ArrayList<String> needsId = new ArrayList<String>();
		ArrayList<Double> needsPercentage = new ArrayList<Double>();
		
		for(int i = 0; i < objPersonalInsights.getNeedValueSize(1); i++){
			needsId.add(objPersonalInsights.getNeedValueDetails(1, i, "name").replace("\"", ""));
			needsPercentage.add(Double.parseDouble(objPersonalInsights.getNeedValueDetails(1, i, "percentage")));
		}
		
		utilities.createBarChart(needsBarChart, needsId, needsPercentage, "Needs");
		
		
		//Line graph
		ArrayList<String> valueId = new ArrayList<String>();
		ArrayList<Double> valuePercentage = new ArrayList<Double>();
		
		for(int i = 0; i < objPersonalInsights.getNeedValueSize(2); i++){
			valueId.add(objPersonalInsights.getNeedValueDetails(2, i, "name").replace("\"", ""));
			valuePercentage.add(Double.parseDouble(objPersonalInsights.getNeedValueDetails(2, i, "percentage")));
		}
		
		utilities.createLineChart(valuesLineChart, valueId, valuePercentage, "Values");	
		
		
		//Pie chart for tone analyser
		ArrayList<String> toneName = new ArrayList<String>();
		ArrayList<Double> toneScore = new ArrayList<Double>();
		
		for(int i = 0; i < objToneAnalyser.getToneSize(); i++){
			toneName.add(objToneAnalyser.getToneDetails(i, "tone_name").replace("\"", ""));
			toneScore.add(Double.parseDouble(objToneAnalyser.getToneDetails(i, "score")));
		}    
		
		utilities.createPieChart(tonePieChart, toneName, toneScore, "Tone");
	}
}