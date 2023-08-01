package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * This is a javadoc for Your Journal, a personalized journal available as a desktop application
 * @author Elayna Seguin, Gael Gil, Devansh Bhargava
 */

/**
 * This class is used to start the application, it directs the application to the first stage, which displays an option to either login for the first time or return to the application.
 */
public class Main extends Application {
	@Override
	/**
	 * This function accepts a @param Stage Class which is used to show the first page of the application.
	 */
	public void start(Stage primaryStage) {
		try {
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("MainPage.fxml"));
			Scene scene = new Scene(root,1200,800);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Stage 1");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
