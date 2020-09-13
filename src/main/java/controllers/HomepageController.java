package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HomepageController {

    @FXML
    void loadAddUser(ActionEvent event) {
        loadWindow("../fxml/addNewUser.fxml","Add New User");

    }

    @FXML
    void loadManageBooks(ActionEvent event) {
        loadWindow("../fxml/manageBooks.fxml","Manage Books");


    }

    @FXML
    void loadAddNewBook(ActionEvent event) {
        loadWindow("../fxml/addNewBook.fxml","Add New Book");

    }

    @FXML
    void loadTopRank(ActionEvent event) {
        loadWindow("../fxml/viewRank.fxml","Add New Book");


    }

    @FXML
    void loadViewBooks(ActionEvent event) {
        loadWindow("../fxml/viewBooks.fxml","Book Table");


    }
    @FXML
    void loadViewUsers(ActionEvent event) {

    }

    @FXML
    private void loadWindow(String resource,String title){
        Parent parent = null;
        try {
            parent = FXMLLoader.load(getClass().getResource(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Stage stage =new Stage(StageStyle.DECORATED);
        stage.setTitle(title);
        stage.setScene(new Scene(parent));
        stage.show();

    }
}
