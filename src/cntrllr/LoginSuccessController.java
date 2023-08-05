package cntrllr;

import java.sql.SQLException;

import database.Database;
import database.Journal;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * This class handles the main journal entry page for users. It has the option to direct users to logout (returning user login page) or have users start a new journal entry.
 */

public class LoginSuccessController {
		
	@FXML private TextField securityQuestionField;
	
	@FXML private ComboBox<String> questionOptionsBox;
	
	/*@FXML private void initialize() {
		questionOptionsBox.setValue("Who is your favorite author?");
		questionOptionsBox.setItems(questionsList);
	}*/
	
    @FXML
    private Button Logout;
    
    @FXML
    private Button NewEntry;
    
    @FXML
    private Button ChangePW;
    
    
    @FXML
    private TableView<Journal> journalTable;
    
    @FXML
    private TableColumn<Journal, Integer> idColumn;
    
    @FXML
    private TableColumn<Journal, String> titleColumn;
    
    @FXML
    private TableColumn<Journal, String> dateColumn;
    
    @FXML
    private TableColumn<Journal, String> textColumn;
    
    
    Stage stage;
    BorderPane root;
    /**
     * This method directs user to a new journal creation page.
     * @param event occurs when user clicks button "New Entry" to create a new journal. Redirects user to journal creation page.
     * @throws Exception
     */
    
    @FXML
    void newJournal(MouseEvent event) throws Exception{
    	stage = (Stage) NewEntry.getScene().getWindow();
    	root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/CreateJournal.fxml"));
    	stage.setTitle("Create New Journal");
    	stage.setScene(new Scene(root));
		stage.show();
    }
    
    
    /**
     * This method returns user to returning user login page.
     * @param event occurs when user clicks "Logout" button
     * @throws Exception
     */
    @FXML
    void goNext(MouseEvent event) throws Exception{
    	stage = (Stage) Logout.getScene().getWindow();
    	root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/ReturnLoginPage.fxml"));
    	stage.setTitle("Logout");
		stage.setScene(new Scene(root));
		stage.show();
    	
    }
    
    /**
     * This method returns user to returning user login page.
     * @param event occurs when user clicks "Logout" button
     * @throws Exception
     */
    @FXML
    void changePW(MouseEvent event) throws Exception{
    	stage = (Stage) ChangePW.getScene().getWindow();
    	root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/ResetPassword.fxml"));
    	stage.setTitle("Logout");
		stage.setScene(new Scene(root));
		stage.show();
    	
    }    
    
    
    @FXML
    private void initialize() {
        // Set cell value factories to extract values from the Journal object
        idColumn.setCellValueFactory(new PropertyValueFactory<>("journalId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        textColumn.setCellValueFactory(new PropertyValueFactory<>("text"));

        try {
        	Database.createTable();
            // Retrieve all journal records from the database
            ObservableList<Journal> journalList = Database.getAllRecords();

            // Set the retrieved journals to the TableView
            journalTable.setItems(journalList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


  
	

	
	
}
