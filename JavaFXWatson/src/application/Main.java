package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
* <h1>IBM Watson - Personality insights</h1>
* The IBM Watson application utilises the personality insights API 
* that scan text and returns a JSON fill with different details within it. 
* The application displays the information in a user friendly way.
*
* @author  Ieuan Walker
* @version 1.0
* @since   14-11-2016
*/

public class Main extends Application {

	/**
	 * This is the main method that launches the GUI for the application. 
	 * The launch method is inside the application class.
	 * @param args
	 */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * This method is inside the application class (javafx.application.Application) and it is begin overridden.
     * The start method is the main entry point for JavaFX application.
     * This method is responsible for setting the stage. 
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
    	
    	Parent root = FXMLLoader.load(getClass().getResource("/ui/mainLayout.fxml"));

    	//Add css
        String cssURL = this.getClass().getResource("/ui/style.css").toExternalForm();
        root.getStylesheets().add(cssURL);

        //Create and set the scene
        primaryStage.setScene(new Scene(root));
        
        //Set the title of the page
        primaryStage.setTitle("IBM Watson");

        //Display the stage
        primaryStage.show();
    }
}