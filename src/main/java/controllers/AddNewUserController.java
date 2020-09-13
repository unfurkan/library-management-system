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


import java.sql.Connection;

import java.sql.SQLException;


public class AddNewUserController {

    private Connection connection;

    @FXML
    private JFXTextField txtfirstName;

    @FXML
    private JFXTextField txtlastName;

    @FXML
    private JFXTextField txtEmail;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private AnchorPane rootContainer;

    @FXML
    void addNewUser(ActionEvent event) {
        String firstName = txtfirstName.getText();
        String lastName = txtlastName.getText();
        String email = txtEmail.getText();

        String date ="";

        if(dateOfBirth.getValue() !=null){
            date =dateOfBirth.getValue().toString();
        }

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || dateOfBirth.getValue()==null || date.isEmpty()){
            Alert alert =new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("All fields are required !  ");
            alert.showAndWait();
            return;

        }

        String sql = "INSERT INTO user(fistName,lastName,email,dateOfBirth) VALUES(" +
                "'" + firstName + "','" +
                "" + lastName + "','" +
                "" + email + "','" +
                "" + date + "');";

        try {
                if(DbConnection.updateQuery(sql)){
                    Alert alert =new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("User successfully added.");
                    alert.showAndWait();

            }
            else{
                Alert alert =new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Failed ");
                alert.showAndWait();

            }
        } catch (SQLException e1) {

            e1.printStackTrace();
        }


    }


    @FXML
    public void closeWindow(ActionEvent event) {
        Stage stage =(Stage)rootContainer.getScene().getWindow();
        stage.close();
    }


}






