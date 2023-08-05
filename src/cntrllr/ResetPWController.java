package cntrllr;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
//import java.nio.file.Path;
//import java.nio.file.Paths;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ResetPWController {
//TODO:
/*
 * 1. Print secq (Optional: can remove as not explicity in requirements to print question)
 * open file stream, get text, print?
 * 
 * 
 * 3. Verify reset pw matches (use function in change PW Controller)
 * 
 * 4.Verify security question matches text file
 * 
 * 5. get boolean returns for requirements and if true, execute gonext function to main page on change pw button click
 */
	@FXML
    private Button backOut;
	
	@FXML
    private TextField NewPass;

    @FXML
    private TextField ConfirmPass;
    
    @FXML
    private TextField SecQ;
    
    @FXML
    private TextArea showSecQuestion;
    
    @FXML
    private TextField SecQAnswer;
	
    @FXML
    private Button ChangePWButton;
    
    @FXML
    private Button back;
    
    @FXML
    private Text text;
    
    @FXML
    private Button securityButton;
    
    @FXML
    private Text invalidResult;
    
    Stage stage;
    BorderPane root;
    
    
	
    //@FXML
    //private Button showText;
    
    /**
     * This method takes user to main page after successfully reseting password
     * @param event occurs on button mouse click
     * @throws Exception
     */
    
    @FXML
    void goBack1(MouseEvent event) throws Exception{
    	stage = (Stage) backOut.getScene().getWindow();
    	root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/ReturnLoginPage.fxml"));
    	stage.setTitle("Main Page");
		stage.setScene(new Scene(root));
		stage.show();
    	
    }
    
	@FXML
    void goNext(MouseEvent event) throws Exception{
		invalidResult.setVisible(false);
    	if(confirmSecQ() && confirmPass() == true) {
	    	Stage stage = (Stage) ChangePWButton.getScene().getWindow();
	    	BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/LoginSuccessPage.fxml"));
	    	stage.setTitle("Stage 2");
	    	stage.setScene(new Scene(root));
			stage.show();
    	}
		else {
			invalidResult.setOpacity(1);
    		invalidResult.setVisible(true);
			System.out.println("Please double check fields.");
			//System.out.println("Sec Q: " + setSecQ());
			//System.out.println("Verify Default: " + verifyDefault());
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
    	root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/LogInSuccessPage.fxml"));
    	stage.setTitle("Logout");
		stage.setScene(new Scene(root));
		stage.show();
    }
	
	
	/**
	 * Confirms that user input in password and confirm password fields match
	 * @return true if user password matches in both fields, else false
	 */
	public boolean confirmPass() {
    	String pass = NewPass.getText();
    	String cpass = ConfirmPass.getText();
//    	Path path = Paths.get("src\\cntrllr\\User_Password.txt");
//    	String absPath = path.toAbsolutePath().toString();
//    	absPath = absPath.replace("\\","\\\\");
    	
        String workingDir = System.getProperty("user.dir");
        String pwFile = workingDir + "/src/cntrllr/User_Password.txt";
	    File absPath = new File(pwFile);
    	
    	if(pass.equals(cpass)) {
    		try {
    			PrintStream toFile = new PrintStream(new FileOutputStream(absPath));
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
	 * Confirms security answers what is on file
	 * @return true if security question matches (case-sensitive), else false
	 */
	public boolean confirmSecQ() {
		
	
		//String answer = SecQAnswer.getText();
//		Path pathA = Paths.get("src\\cntrllr\\SecAnswer.txt");
//    	String absPathA = pathA.toAbsolutePath().toString();
//    	absPathA = absPathA.replace("\\","\\\\");
    	
        String workingDir = System.getProperty("user.dir");
        String absPathA = workingDir + "/src/cntrllr/SecAnswer.txt";
//	    File file = new File(pwFile);
    	
    	File file = new File(absPathA);
    	try {
			String entry = SecQAnswer.getText();
			String answer = null;
			Scanner scanner = new Scanner(file);
			while(scanner.hasNext()) {
				answer = scanner.next();	
		    }
			scanner.close();
			if (answer.equals(entry)) {
				return true;
			}
			
		} 
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
		return false;
	}
	
	@FXML
    void appear(MouseEvent event) throws Exception{
		
		text = new Text();
		//text.setOpacity(0);
		String workingDir = System.getProperty("user.dir");
        String absPath = workingDir + "/src/cntrllr/SecQuestion.txt";
//	    File file = new File(pwFile);
    	
    	File file = new File(absPath);
		
		//Scanner scanner;
		try {
			String question = null;
			Scanner scanner = new Scanner(file);
			while(scanner.hasNext()) {
				question = scanner.next();	
				showSecQuestion.appendText(question + " ");
		    }
			scanner.close();
			text.setText(question);
			//text.setVisible(true);
			text.setOpacity(1);
    		text.setVisible(true);
    		securityButton.setDisable(true);
    		securityButton.setVisible(false);

    		
			//showSecQuestion.appendText(question);
			
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    }

	/**
	 * To display user's custom security question
	 * Only displays currently if security answer is improperly entered or goNext button does not work
	 * TODO: Need to find a way to properly display user input. 
	 */
	/*void showText() {
		
		text = new Text();
		text.setOpacity(0);
		
//		Path path = Paths.get("src\\cntrllr\\SecQuestion.txt");
//    	String absPath = path.toAbsolutePath().toString();
//    	absPath = absPath.replace("\\","\\\\");
//    	//System.out.println(absPath);
//	    File file = new File(absPath);
	    
	    
    	
        String workingDir = System.getProperty("user.dir");
        String absPath = workingDir + "/src/cntrllr/SecQuestion.txt";
//	    File file = new File(pwFile);
    	
    	File file = new File(absPath);
		
		//Scanner scanner;
		try {
			String question = null;
			Scanner scanner = new Scanner(file);
			while(scanner.hasNext()) {
				question = scanner.next();	
				showSecQuestion.appendText(question + " ");
		    }
			scanner.close();
			text.setText(question);
			//text.setVisible(true);
			text.setOpacity(1);
    		text.setVisible(true);
			//showSecQuestion.appendText(question);
			
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	

}