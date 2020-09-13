package controllers;

import com.jfoenix.controls.JFXTextField;
import database.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class ManageBooksController {
    @FXML
    private JFXTextField txtBookBorrowISBN;

    @FXML
    private Text txtbookTitle;

    @FXML
    private Text txtbookAuthor;

    @FXML
    private Text txtbookStatus;

    @FXML
    private JFXTextField txtuserID;


    @FXML
    private Text txtuserName;

    @FXML
    private Text txtuserContact;

    @FXML
    private JFXTextField txtBookReturnISBN;

    @FXML
    private ListView<String> borrowedBookList;

    @FXML
    void handleBorrowBook(ActionEvent event) {
        String bookISBN =txtBookBorrowISBN.getText();
        String userID =txtuserID.getText();

        String query ="SELECT * FROM book WHERE ISBN ='" + bookISBN + "'" ;
        try {
            ResultSet rs =DbConnection.readFromDatabase(query);
            if(rs.next()){

                Boolean isAvaliable =rs.getBoolean("status");

                if(isAvaliable){

                    String query1 ="INSERT INTO borrowed(user_userID,Book_ISBN) VALUES("
                            + "'" + userID + "',"
                            + "'" +bookISBN+ "')";

                    String query2 ="UPDATE book SET status=false WHERE ISBN =" + "'" +bookISBN + "'";

                    try {
                        if(DbConnection.updateQuery(query1) && DbConnection.updateQuery(query2)){
                            Alert alertSucces =new Alert(Alert.AlertType.INFORMATION);
                            alertSucces.setHeaderText(null);
                            alertSucces.setTitle("Success");
                            alertSucces.setContentText("Member " + txtuserName.getText() +" borrowed "+txtbookTitle.getText() + ".");

                            alertSucces.show();

                        }else{
                            alertError();

                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
                else{ // OFFER RESERVATION
                    Alert reservationAlert =new Alert(Alert.AlertType.ERROR);
                    reservationAlert.setTitle("Book Already Borrowed.");
                    reservationAlert.setContentText("Book is not currently avaliable.Do you want to make a reservation for future dates?");
                    Optional<ButtonType> response =reservationAlert.showAndWait();
                    if(response.get() == ButtonType.OK){

                        String reservationQuery ="INSERT INTO reserves(user_userID,Book_ISBN) VALUES("
                                + "'" + userID + "',"
                                + "'" +bookISBN+ "')";

                        if(DbConnection.updateQuery(reservationQuery)){
                            Alert succes =new Alert(Alert.AlertType.INFORMATION);
                            succes.setContentText("Reservation created successfully ");
                            succes.show();
                        }

                    }

                }





            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        }





    @FXML
    void loadBookInfo(ActionEvent event) {

        String bookISBN =txtBookBorrowISBN.getText();
        String query ="SELECT * FROM book WHERE ISBN ='" + bookISBN + "'" ;

        try {

            ResultSet rs = DbConnection.readFromDatabase(query);
            if(rs.next()) {
                String title = rs.getString("title");
                String author = rs.getString("author");
                Boolean status = rs.getBoolean("status");

                txtbookTitle.setText(title);
                txtbookAuthor.setText(author);

                String bookStatus = status ? "Avaliable" : "NOT AVALIABLE";
                txtbookStatus.setText(bookStatus);

            }
            else{
                txtbookTitle.setText("BOOK DOESNT EXIST");
                txtbookAuthor.setText("");
                txtbookStatus.setText("");

            }

        }

         catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loadBookInfoListView(ActionEvent event) {
        ObservableList<String> borrowInfos = FXCollections.observableArrayList();
        String bookISBN =txtBookReturnISBN.getText();

        String query ="SELECT * FROM borrowed WHERE Book_ISBN ='" + bookISBN + "'" ;

        try {
            ResultSet rs =DbConnection.readFromDatabase(query);

            while(rs.next()){
                Timestamp dueDate =rs.getTimestamp("borrow_Date");
                String borrowedUserId =rs.getString("user_userID");
                int renewTimes =rs.getInt("renew");

                borrowInfos.add("Borrowed Since : " + dueDate.toGMTString() );
                borrowInfos.add("Renewed : " + renewTimes);

                String query2 ="SELECT * FROM book WHERE ISBN ='" + bookISBN + "'" ;

                borrowInfos.add("Book Infos: ");

                ResultSet rs2 =DbConnection.readFromDatabase(query2);
                while (rs2.next()){
                    String title = rs2.getString("title");
                    String author = rs2.getString("author");

                    borrowInfos.add("Book ISBN: " + bookISBN);
                    borrowInfos.add("Author: " + author);
                    borrowInfos.add("Title: " + title);


                }

                borrowInfos.add("Member Infos: ");
                String query3="SELECT * FROM user WHERE userID ='" + borrowedUserId + "'" ;

                rs2 =DbConnection.readFromDatabase(query3);
                while(rs2.next()){
                    String firstName = rs2.getString("fistName");
                    String lastName = rs2.getString("lastName");
                    String email = rs2.getString("email");

                    borrowInfos.add("Full Name : " + firstName + " " + lastName);
                    borrowInfos.add("Contact : " + email);

                }


            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        borrowedBookList.getItems().setAll(borrowInfos);

    }

    @FXML
    void loadUserInfo(ActionEvent event) {

        String userID =txtuserID.getText();

        String query ="SELECT * FROM user WHERE userID ='" + userID + "'" ;


        try {

            ResultSet rs = DbConnection.readFromDatabase(query);
            if(rs.next()) {
                String firstName = rs.getString("fistName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");


                txtuserName.setText(firstName + lastName);
                txtuserContact.setText(email);

            }
            else{
                txtuserName.setText("USER DOESN'T EXIST");
                txtuserContact.setText("");

            }

        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handleRenew(ActionEvent event) {
        String bookISBN =txtBookReturnISBN.getText();

        String query1 ="SELECT * FROM borrowed WHERE Book_ISBN ='" + bookISBN + "'" ;
        boolean isBookBorrowed =false;

        try {

            ResultSet rs=DbConnection.readFromDatabase(query1);
            if(rs.next()){
                isBookBorrowed =true;
            }

            //RENEW TIME AND COUNT
            String query2 ="UPDATE borrowed SET borrow_Date =CURRENT_TIMESTAMP,renew =renew+1 WHERE Book_ISBN ='" +bookISBN + "'";

            if(isBookBorrowed && DbConnection.updateQuery(query2)) {
                Alert alertSucces =new Alert(Alert.AlertType.INFORMATION);
                alertSucces.setHeaderText(null);
                alertSucces.setTitle("Success");
                alertSucces.setContentText("Borrow Time Extended.");
                alertSucces.show();
            }
            else{
                alertError();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }




        String query2 ="UPDATE borrowed SET borrowed_Date =CURRENT_TIMESTAMP renew=renew+1 WHERE ISBN =" + "'" +bookISBN + "'";





    }

    private void alertError() {
        Alert alertSucces =new Alert(Alert.AlertType.ERROR);
        alertSucces.setHeaderText(null);
        alertSucces.setTitle("Failled");
        alertSucces.setContentText("Operation Failled.");

        alertSucces.show();
    }


    @FXML
    void handleReturnBook(ActionEvent event) {

        String bookISBN =txtBookReturnISBN.getText();

        String query1 ="SELECT * FROM borrowed WHERE Book_ISBN ='" + bookISBN + "'" ;

        String borrowedUserId ;
        try {
            ResultSet rs =DbConnection.readFromDatabase(query1);

            if(rs.next()){
                borrowedUserId =rs.getString("user_userID");

                //delete from borrowed
                String query2 ="DELETE FROM borrowed WHERE Book_ISBN ='" + bookISBN + "'" ;

                //set book status avaliable
                String query3 ="UPDATE book SET status=true WHERE ISBN =" + "'" +bookISBN + "'";

                //add returned table new record
                String query4 ="INSERT INTO returned(user_userID,Book_ISBN) VALUES("
                        + "'" + borrowedUserId + "',"
                        + "'" +bookISBN+ "')";

                if(DbConnection.updateQuery(query2) && DbConnection.updateQuery(query3)
                        && DbConnection.updateQuery(query4)){
                    Alert alertSucces =new Alert(Alert.AlertType.INFORMATION);
                    alertSucces.setHeaderText(null);
                    alertSucces.setTitle("Success");
                    alertSucces.setContentText("Member " + borrowedUserId +" returned "+ bookISBN + ".");
                    alertSucces.show();
                }

            }
            else{
                alertError();

            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
