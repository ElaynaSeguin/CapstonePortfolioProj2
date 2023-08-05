package application;

import java.io.File;
import java.io.FileNotFoundException;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
			BorderPane root;
			//BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
			if (verifyLogin()) {
				root = (BorderPane)FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
				System.out.println("True");
			}
			else {
				root = (BorderPane)FXMLLoader.load(getClass().getResource("ReturnLoginPage.fxml"));
				System.out.println("false");
			}
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

	public boolean verifyLogin() {
//		Path path = Paths.get("src\\cntrllr\\LoginCheck.txt");
//    	String absPath = path.toAbsolutePath().toString();
//    	absPath = absPath.replace("\\","\\\\");
//    	File file = new File(absPath);
//
//
        String workingDir = System.getProperty("user.dir");
        String pwFile = workingDir + "/src/cntrllr/LoginCheck.txt";
	    File file = new File(pwFile);

	    System.out.println("here");

    	try {
			String checkFirstTime = null;
			Scanner scanner = new Scanner(file);
			while(scanner.hasNext()) {
				checkFirstTime = scanner.next();
		    }
			scanner.close();
			System.out.println(checkFirstTime + " value");
			if (checkFirstTime.equals("true")) {
				return true;
			}

		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}