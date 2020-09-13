package controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import database.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.SQLException;

public class AddNewBookController {

    @FXML
    private AnchorPane rootContainer;
    @FXML
    private JFXTextField txtBookISBN;
    @FXML
    private JFXTextField txtBookTitle;

    @FXML
    private JFXTextField txtBookAuthor;

    @FXML
    private JFXTextField txtBookGenre;

    @FXML
    private DatePicker bookReleaseDate;

    @FXML
    private JFXButton saveButton;

    @FXML
    private JFXButton cancelButton;


    @FXML
    void addNewBook(ActionEvent event) {

        String isbn =txtBookISBN.getText();
        String title =txtBookTitle.getText();
        String author =txtBookAuthor.getText();
        String genre =txtBookGenre.getText();
        String releaseDate =null;

        if(bookReleaseDate.getValue() !=null){
            releaseDate =bookReleaseDate.getValue().toString();
        }

        if(isbn.isEmpty() || title.isEmpty() || author.isEmpty() || genre.isEmpty() || bookReleaseDate.getValue()==null || releaseDate.isEmpty()){
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Fields cannot be empty !  ");
            alert.showAndWait();
            return;
        }


        String sql = "INSERT INTO book(ISBN,title,author,releaseDate) VALUES(" +
                "'" + isbn + "','" +
                "" + title + "','" +
                "" + author + "','" +
                "" + releaseDate + "');";

        String sql2 = "INSERT INTO book_genre VALUES(" +
                "'" + genre + "','" +

                "" + isbn + "');";
        try {
            if(DbConnection.updateQuery(sql) && DbConnection.updateQuery(sql2)){
                Alert alert =new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Book has been added.");
                alert.showAndWait();

            }
            else{
                Alert alert =new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Failed ");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage =(Stage)rootContainer.getScene().getWindow();
        stage.close();

    }
}
