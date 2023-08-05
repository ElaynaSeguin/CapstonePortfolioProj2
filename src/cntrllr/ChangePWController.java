package cntrllr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class is used to control the change password page for an initial login from the user. The user will automatically be redirected to this page after entering the default password.
 */
public class ChangePWController {
	
	//ObservableList<String> questionsList = FXCollections.observableArrayList("Who is your favorite author?","What is your favorite book?","What is your first pet's name?");
	
	@FXML
    private TextField NewPass;

    @FXML
    private TextField ConfirmPass;

    @FXML
    private TextField SecQ;

    @FXML
    private TextField SecQAnswer;
	
	@FXML
    private TextField passEntry;
	
    @FXML
    private Button ChangePWButton;
    
    @FXML
    private Button back;
    
    
    Stage stage;
    BorderPane root;
    
    /**
     * This method is used to redirect the application after successfully filling out the required fields to change the password. 
     * The required fields include a custom security question, security question answer, default password, new password, confirm new password.
     * @param event is a mouse click on the change password button confirming changes
     * @throws Exception
     */

  //need more rigorous way to verify that fields are not blank, null does not work
   
    /**
     * This method takes user to main page after successfully changing password
     * @param event occurs on button mouse click
     * @throws Exception
     */
    @FXML
    void goNext(MouseEvent event) throws Exception{
    	if(setSecQ() && verifyDefault() && confirmPass() == true) {
	    	Stage stage = (Stage) ChangePWButton.getScene().getWindow();
	    	BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/LoginSuccessPage.fxml"));
	    	stage.setTitle("Stage 2");
	    	stage.setScene(new Scene(root));
			stage.show();
    	}
		else {
			System.out.println("Please double check fields.");
			System.out.println("Sec Q: " + setSecQ());
			System.out.println("Verify Default: " + verifyDefault());
			System.out.println("Confirm Pass: " + confirmPass());
			//invalidResult.setVisible(true);
		}
    }
    
    /**
     * This method takes user to main page after successfully reseting password
     * @param event occurs on button mouse click
     * @throws Exception
     */
    
	@FXML
    void goBack(MouseEvent event) throws Exception{
    	stage = (Stage) back.getScene().getWindow();
    	root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/LogInSuccesPage.fxml"));
    	stage.setTitle("Logout");
		stage.setScene(new Scene(root));
		stage.show();
    }
	
    
    
    /**
     * Verifies user input to password in text file
     * @return true if default password matches user entry, else false
     */
    public boolean verifyDefault(){
    	//enter path to default password file to authenticate
	    //File file = new File("C:\\Users\\Owner\\eclipse-workspace\\Term_Project_07_22_23\\src\\controller\\Default_Password.txt");
//    	Path path = Paths.get("/src/cntrllr/Default_Password.txt");
//    	String absPath = path.toAbsolutePath().toString();
//    	absPath = absPath.replace("\\","\\\\");
//    	//System.out.println(absPath);
//	    File file = new File(absPath);
	    

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
			if (password.equals(entry)) {
				return true;
			}
			
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			return false;
    }
    
    /**
     * Verifies user input for new password matches, saves in text file "user password"
     * @return true if user input for password and confirmation match, else false
     */
    public boolean confirmPass() {
    	String pass = NewPass.getText();
    	String cpass = ConfirmPass.getText();
//    	Path path = Paths.get("src\\cntrllr\\User_Password.txt");
//    	String absPath = path.toAbsolutePath().toString();
//    	absPath = absPath.replace("\\","\\\\");
    	
        String workingDir = System.getProperty("user.dir");
        String pwFile = workingDir + "/src/cntrllr/Default_Password.txt";
	    File file = new File(pwFile);
    	
    	
    	if(pass.equals(cpass)) {
    		try {
    			PrintStream toFile = new PrintStream(new FileOutputStream(file));
        		toFile.print(pass);
        		toFile.close();
				
				if(pass != null && cpass != null && pass.equals(cpass) == true) {
					return true;
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}	
    	
		return false;
    }
	
    /**
     * Set's user security question that will verify identity on password reset
     * @return true if both fields (question/answer) filled, else false
     */
	public boolean setSecQ() {
		String question = SecQ.getText();
		String answer = SecQAnswer.getText();
		
        String workingDir = System.getProperty("user.dir");
        String qFile = workingDir + "/src/cntrllr/SecQuestion.txt";
	    File absPathQ = new File(qFile);
	    
        String aFile = workingDir + "/src/cntrllr/SeAnswer.txt";
	    File absPathA = new File(aFile);
	    
    	try {
    		PrintStream toFileQ = new PrintStream(new FileOutputStream(absPathQ));
    		toFileQ.print(question);
    		toFileQ.close();
    		PrintStream toFileA = new PrintStream(new FileOutputStream(absPathA));
			toFileA.print(answer);
			toFileA.close();
			if (question != null && answer != null) {
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
