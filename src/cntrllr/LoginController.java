package cntrllr;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 * This class handles the default password page that logs a user into the system for the first time. This class points the user to the change password page before going to the main journal page.
 */
public class LoginController {
	
	ObservableList<String> questionsList = FXCollections.observableArrayList("Who is your favorite author?","What is your favorite book?","What is your first pet's name?");
	
	@FXML private TextField securityQuestionField;
	
	@FXML private ComboBox<String> questionOptionsBox;
	
    @FXML
    private Button LoginButton;
    
    @FXML
    private TextField passEntry;
    
    @FXML
    private Text invalidResult;
    
    
    //invalidResult.setDisable(true);

    /**
     * TODO: Move variable to PreLoginController, which is the updated main page when application is launched.
     * @param event occurs when user enters default password and clicks login button
     * @throws Exception
     */
    @FXML
    void LoginDirect(MouseEvent event) throws Exception{
    	invalidResult.setVisible(false);
    	boolean res = verifydefault();
    	if (res == true) {
	    	Stage stage = (Stage) LoginButton.getScene().getWindow();
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/ChangePassword.fxml"));
	    	stage.setTitle("Change Password Page");
	    	stage.setScene(new Scene(root));
			stage.show();
    	}
    	else {
    		invalidResult.setOpacity(1);
    		invalidResult.setVisible(true);
    		
    	}

    }
    
    /**
     * This method adds authentication of user default password, found in default password text file
     * @return true if user entry matches default password on file
     */
    public boolean verifydefault(){
    	//enter path to default password file to authenticate
//        String workingDir = System.getProperty("user.dir");
//        String pwFile = workingDir + "/src/cntrllr/Default_Password.txt";
//	    File file = new File(pwFile);
//	    invalidResult.setOpacity(0);
	    
        String workingDir = System.getProperty("user.dir");
        String pwFile = workingDir + "/src/cntrllr/Default_Password.txt";
	    File file = new File(pwFile);
	    
		//Scanner scanner;
		try {
			String entry = passEntry.getText();
			String password = null;
			Scanner scanner = new Scanner(file);
			while(scanner.hasNext()) {
				password = scanner.next();	
		    }
			scanner.close();
			System.out.println(password);
			System.out.println(entry);
			if (password.equals(entry)) {
				System.out.println(password);
				System.out.println(entry);
				return true;
			}
			
		} 
		catch (FileNotFoundException e) {
			System.out.println(file);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			System.out.println("failed");
			return false;
    }
	

	
	
}
