package cntrllr;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import database.Database;
import database.Journal;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class EditJournalController {

    @FXML
    private TextField titleField;

    @FXML
    private DatePicker journalDate;

    @FXML
    private TextArea jtextField;

    @FXML
    private Button saveButton;
    
    @FXML
    private Label journalIdLabel;
    
    private Journal selectedJournal;
	
//    Stage stage;
//
//    BorderPane root;

	
    
    

    // Method to set the selected journal for editing
    public void setJournal(Journal journal) {
        selectedJournal = journal;
        populateFields();
    }

    // Populate the fields with the journal data
    private void populateFields() {
        titleField.setText(selectedJournal.getTitle());
        journalIdLabel.setText(selectedJournal.getJournalId()+"");
        journalDate.setValue(LocalDate.parse(selectedJournal.getDate()));
        jtextField.setText(selectedJournal.getText());
    }

    // Method to handle the "Save Changes" button action
    @FXML
    private void saveChanges() {
        // Get the edited data from the fields
        String title = titleField.getText();
        String date = journalDate.getValue().toString();
        String text = jtextField.getText();
        int id = Integer.parseInt(journalIdLabel.getText());

        try {
			Database.updateJournal(id, title, date, text);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//
    	Stage stage = (Stage) saveButton.getScene().getWindow();
		BorderPane root = null;
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("/application/LoginSuccessPage.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	stage.setTitle("Change Password Page");
    	stage.setScene(new Scene(root));
		stage.show();

    }


}
