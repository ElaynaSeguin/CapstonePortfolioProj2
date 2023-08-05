package cntrllr;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class is first launches in main.java. This class displays the user options to login with a default password or login as a returning user.
 * TODO: Verify that default login is only available if it is the user's first time logging into the system. This option should be greyed out or not clickable otherwise.
 */

public class PreLoginController {

    @FXML
    private Button FirstTime;

    @FXML
    private Button Return;


    Stage stage;
	BorderPane root;
    @FXML

    /**
     * This method directs user to Default Password Page. At this time it is clickable whether first time or returning. In future release, will not be clickable if returning.
     * @param event occurs when FirstTime Button is clicked
     * @throws Exception
     */
    void DefaultLogin(MouseEvent event) throws Exception{
    	stage = (Stage) FirstTime.getScene().getWindow();
    	root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/LoginPage.fxml"));
    	stage.setTitle("Initial Login");
    	stage.setScene(new Scene(root));
		stage.show();
    }

   /**
    * This method directs user to Return Login page. This assumes that user has changed default passwor
    * @param event occurs when returning user button is clicked
    * @throws Exception
    */

    @FXML
    void ReturnLogin(MouseEvent event) throws Exception{
    	stage = (Stage) Return.getScene().getWindow();
    	root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/ReturnLoginPage.fxml"));
    	stage.setTitle("Returning Login");
    	stage.setScene(new Scene(root));
		stage.show();

    }

}