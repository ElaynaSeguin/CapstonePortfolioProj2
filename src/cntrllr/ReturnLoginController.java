package cntrllr;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * This class displays returning login page. It provides the user options to reset their password or login to the main journal page.
 */
public class ReturnLoginController {

    @FXML
    private Button LoginButton;
    
    @FXML
    private Button ResetPass;
    
    Stage stage;
	BorderPane root;
    /**
     * This method allows user to reset their password if forgotten.
     * @param event occurs when reset password button is clicked
     * @throws Exception
     */
    @FXML
    void GoReset(MouseEvent event) throws Exception{
    	stage = (Stage) ResetPass.getScene().getWindow();
    	root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/ResetPassword.fxml"));
    	Text text = new Text();
    	String question = showText();
    	text.setText(question);
		stage.setTitle("Reset Password");
		stage.setScene(new Scene(root));
		stage.show();
    }
    /**
     * This method directs user to main journal page, where the user can view existing journals or create a new journal.
     * @param event occurs when user enters password and login button is clicked
     * @throws Exception
     */
    @FXML
    void handleLogin(MouseEvent event) throws Exception{
    	stage = (Stage) LoginButton.getScene().getWindow();
    	root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/LoginSuccessPage.fxml"));
		stage.setTitle("Main Page");
		stage.setScene(new Scene(root));
		stage.show();

    }
    
    public String showText() {
			
		Path path = Paths.get("src\\controller\\User_Password.txt");
    	String absPath = path.toAbsolutePath().toString();
    	absPath = absPath.replace("\\","\\\\");
    	//System.out.println(absPath);
	    File file = new File(absPath);
		
		//Scanner scanner;
		try {
			String question = null;
			Scanner scanner = new Scanner(file);
			while(scanner.hasNext()) {
				question = scanner.next();	
		    }
			scanner.close();
			return question;
			
			
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}