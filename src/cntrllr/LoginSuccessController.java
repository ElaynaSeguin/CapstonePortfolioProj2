package cntrllr;

import java.io.IOException;
import java.sql.SQLException;
//import java.time.LocalDate;
//import java.time.LocalDate;

import database.Database;
import database.Journal;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;




/**
 * This class handles the main journal entry page for users. It has the option to direct users to logout (returning user login page) or have users start a new journal entry.
 */

public class LoginSuccessController {

	@FXML private TextField securityQuestionField;

	@FXML private ComboBox<String> questionOptionsBox;

    @FXML
    private Button Logout;

    @FXML
    private Button NewEntry;

    @FXML
    private Button ChangePW;

    @FXML
    private TextField titleField;

    @FXML
    private DatePicker journalDate;

    @FXML
    private TextArea jtextField;

    @FXML
    private TextField searchField;

    @FXML
    private Button searchButton;

    @FXML
    private Button Save;

    @FXML
    private Button Cancel;

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




//
//


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


    /**
     * This method sets the journals that came up during the search into our table
     * @param event occurs when user clicks "Search" Button
     * @throws Exception
     */
    @FXML
    void searchJournals(MouseEvent event) throws Exception{
        String searchQuery = searchField.getText().trim();
        ObservableList<Journal> filteredJournals = Database.searchJournals(searchQuery);
        // Set the journals that we got from the search
        journalTable.setItems(filteredJournals);

    }



    @FXML
    private void initialize() {
        // Set cell value factories to extract values from the Journal object
        idColumn.setCellValueFactory(new PropertyValueFactory<>("journalId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        textColumn.setCellValueFactory(new PropertyValueFactory<>("text"));



        // Add Edit button column to the TableView
        TableColumn<Journal, Void> editColumn = new TableColumn<>("Edit");
        editColumn.setPrefWidth(100);
        editColumn.setCellFactory(getEditButtonCells());
        journalTable.getColumns().add(editColumn);

        // Add Delete button column to the TableView
        TableColumn<Journal, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setPrefWidth(100);
        deleteColumn.setCellFactory(getDeleteButtonCells());
        journalTable.getColumns().add(deleteColumn);

        try {
            Database.createTable();
            // get all journal records from the database
            ObservableList<Journal> journalList = Database.getAllRecords();

            // set the journals to the TableView
            journalTable.setItems(journalList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method handles the delete button and action
     * @param event occurs when user clicks "Search" Button
     * @throws Exception
     */
    @FXML
    protected Callback<TableColumn<Journal, Void>, TableCell<Journal, Void>> getDeleteButtonCells() {
        return new Callback<>() {
            @Override
            public TableCell<Journal, Void> call(final TableColumn<Journal, Void> param) {
                return new TableCell<>() {
                    private final Button deleteButton = new Button("Delete");

                    {
                        deleteButton.setOnAction((ActionEvent event) -> { // handle button click
                            Journal journal = getTableView().getItems().get(getIndex()); // get the journal that was clicked

                            try {
								Database.deleteJournal(journal.getJournalId()); // delete the journal by passing in the id
					            ObservableList<Journal> journalList = Database.getAllRecords(); // get all the records

					            // Set the journals into the table view
					            journalTable.setItems(journalList);

							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(deleteButton);
                        }
                    }
                };
            }
        };
    }




    protected Callback<TableColumn<Journal, Void>, TableCell<Journal, Void>> getEditButtonCells() {
        return new Callback<>() {
            @Override
            public TableCell<Journal, Void> call(final TableColumn<Journal, Void> param) {
                return new TableCell<>() {
                    private final Button editButton = new Button("Edit");

                    {
                        editButton.setOnAction((ActionEvent event) -> {
                            Journal journal = getTableView().getItems().get(getIndex());


                            try {
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/EditJournal.fxml"));
                                BorderPane root = loader.load();
                                EditJournalController editJournalController = loader.getController();
                                editJournalController.setJournal(journal); // Pass the selected journal to the edit view controller

                                Stage stage = new Stage();
                                stage.setTitle("Edit Journal");
                                stage.setScene(new Scene(root));
                                stage.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(editButton);
                        }
                    }
                };
            }
        };
    }






}
