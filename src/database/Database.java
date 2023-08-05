package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Database {
    private static final String DB_URL = "jdbc:sqlite:journaldb.db"; // SQLite URL
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "password";


    /**
     * Establishes a database connection.
     *
     * @return The Connection object representing the database connection.
     * @throws SQLException If a database access error occurs.
     */

    private static Connection getConnection() throws SQLException {
//    	createTable();
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    /**
     * Creates a new journal entry in the database.
     *
     * @param title The title of the journal entry.
     * @param date  The date of the journal entry.
     */
    public static void createNewJournal(String title, String date, String jText) throws SQLException {
        try (Connection connection = getConnection()) {
            String insertValues = "INSERT INTO journal (title, date, jText) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(insertValues)) {
                statement.setString(1, title);
                statement.setString(2, date);
                statement.setString(3, jText);
                statement.executeUpdate();
            }
        }
    }


    /**
     * Updates the title of a journal entry in the database based on its ID.
     *
     * @param id       The ID of the journal entry to be updated.
     * @param newTitle The new title for the journal entry.
     * @param newDate  The new date for the journal
     * @param newText  The new text to be added to the journal.
     */
    public static void updateJournal(int id, String newTitle, String newDate, String newText) throws SQLException {
        try (Connection connection = getConnection()) {
            String updateDataSql = "UPDATE journal SET title = ?, date = ?, jText = ? WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(updateDataSql)) {
            statement.setString(1, newTitle);
            statement.setString(2, newDate);
            statement.setString(3, newText);
            statement.setInt(4, id);
                statement.executeUpdate();
            }
        }
    }


    /**
     * Deletes a journal entry from the database based on its ID.
     *
     * @param id The ID of the journal entry to be deleted.
     */
    public static void deleteJournal(int id) throws SQLException {
        try (Connection connection = getConnection()) {
            String deleteFrom = "DELETE FROM journal WHERE id = ?";
            try (PreparedStatement statement = connection.prepareStatement(deleteFrom)) {
                statement.setInt(1, id);
                statement.executeUpdate();
                System.out.println("Deleted Journal");
            }
        }
    }


    /**
     * Retrieves a list of journal objects from the database.
     *
     * @return An ObservableList of journal objects.
     * @throws SQLException If a database access error occurs.
     */
    public static ObservableList<Journal> getAllRecords() throws SQLException {
        String query = "SELECT id, title, date, jText FROM journal";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            // Call the getJournalObjs method and return the result directly.
            return getJournalObjs(resultSet);

        } catch (SQLException e) {
            System.out.println("Error: " + e);
            throw e;
        }
    }


    /**
     * Retrieves a single journal object from the database.
     *
     * @return A Journal object.
     * @throws SQLException If a database access error occurs.
     */
    public static Journal getOneRecord(int id) throws SQLException {
        String query = "SELECT id, title, date, jText FROM journal WHERE id = ?";
        try (Connection connection = getConnection();
                PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the id parameter for the query
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Journal journal = new Journal();
                    journal.setJournalId(resultSet.getInt("id"));
                    journal.setTitle(resultSet.getString("title"));
                    journal.setDate(resultSet.getString("date"));
                    journal.setText(resultSet.getString("jText"));

                    return journal;
                } else {
                    // No record found for the given id
                    return null;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e);
            throw e;
        }
    }



    /**
     * Searches for journal entries that match the given search query.
     *
     * @param searchQuery The search query used to match journal titles.
     * @return A list of journal titles that match the search query (each item in the list is a class).
     */
    public static ObservableList<Journal> searchJournals(String searchQuery) throws SQLException{
    	String query = "SELECT id, title, date, jText FROM journal WHERE title LIKE ?";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, searchQuery + "%");
            ResultSet resultSet = statement.executeQuery();
    		ObservableList<Journal> journalList = getJournalObjs(resultSet);
    		return journalList;
        }
        catch(SQLException e){
        	System.out.println("error" + e);
        	throw e;
        }


    }

    /**
     * Retrieves a list of journal objects from the database.
     *
     * @return An ObservableList of journal objects.
     * @throws SQLException If a database access error occurs.
     */
    private static ObservableList<Journal> getJournalObjs(ResultSet resultSet) throws SQLException {
        ObservableList<Journal> journalList = FXCollections.observableArrayList();

        while (resultSet.next()) {
            Journal journal = new Journal();
            journal.setJournalId(resultSet.getInt("id"));
            journal.setTitle(resultSet.getString("title"));
            journal.setDate(resultSet.getString("date"));
            journal.setText(resultSet.getString("jText"));
            journalList.add(journal);
        }

        return journalList;
    }



    /**
     * Creates the database if it does not exist.
     */
    public static void createDatabase() throws SQLException {
        try (Connection connection = getConnection()){
             Statement statement = connection.createStatement();
            String createDatabaseSql = "CREATE DATABASE IF NOT EXISTS journaldb";
            statement.executeUpdate(createDatabaseSql);
        }
    }


    /**
     * Creates the 'journal' table if it does not exist.
     */
    public static void createTable() throws SQLException {
        String createTableSql = "CREATE TABLE IF NOT EXISTS journal (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title VARCHAR(50) NOT NULL," +
                "date VARCHAR(50)," +
                "jText TEXT" +
                ")";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSql);
        }
    }



	public static void main(String[] args) throws SQLException {
	}

}

