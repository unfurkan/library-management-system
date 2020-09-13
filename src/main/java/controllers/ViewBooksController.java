package controllers;

import database.DbConnection;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ViewBooksController implements Initializable {

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book,String> ISBNColumn;

    @FXML
    private TableColumn<Book,String> titleColumn;

    @FXML
    private TableColumn<Book, String> authorColumn;

    @FXML
    private TableColumn<Book, String> releaseDateColumn;

    @FXML
    private TableColumn<Book, Boolean> statusColumn;

    private ObservableList<Book> bookData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initColumns();
        loadData();
    }

    private void initColumns() {
        ISBNColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        releaseDateColumn.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

    }
    private void loadData(){
        String query ="SELECT * FROM book" ;

        try {
            ResultSet rs = DbConnection.readFromDatabase(query);
            while(rs.next()){
                String ISBN =rs.getString("ISBN");
                String title =rs.getString("title");
                String author =rs.getString("author");
                String releaseDate =rs.getDate("releaseDate").toString();
                Boolean isAvaliable =rs.getBoolean("status");

                bookData.add(new Book(ISBN,title,author,releaseDate,isAvaliable));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        tableView.getItems().setAll(bookData);

    }

    public static class Book {

        private final SimpleStringProperty ISBN;
        private final SimpleStringProperty title;
        private final SimpleStringProperty author;
        private final SimpleStringProperty releaseDate;
        private final SimpleBooleanProperty status;

        Book(String ISBN, String title, String author, String releaseDate,boolean status){
            this.ISBN =new SimpleStringProperty(ISBN);
            this.title =new SimpleStringProperty(title);
            this.author =new SimpleStringProperty(author);;
            this.releaseDate =new SimpleStringProperty(releaseDate);;
            this.status =new SimpleBooleanProperty(status);;

        }

        public String getISBN() {
            return ISBN.get();
        }

        public String getTitle() {
            return title.get();
        }

        public String getAuthor() {
            return author.get();
        }

        public String getReleaseDate() {
            return releaseDate.get();
        }

        public boolean isStatus() {
            return status.get();
        }
    }


}
