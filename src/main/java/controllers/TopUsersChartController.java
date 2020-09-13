package controllers;

import database.DbConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TopUsersChartController  implements Initializable{


    @FXML
    private BarChart<?, ?> usersChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    private void loadData() {
        XYChart.Series userBorrowedSeries =new XYChart.Series<>();
        String queryBorrowedCount ="Select fistName,lastName,user_userID,count(user_userID) as times from user as userTable\n" +
                "INNER JOIN borrowed as borrowedTable\n" +
                "ON userTable.userID = borrowedTable.user_userID\n" +
                "group by user_userID order by times desc";

        try {
            ResultSet rs = DbConnection.readFromDatabase(queryBorrowedCount);
            int topNumber =10;
            while(topNumber > 0 && rs.next()){
                String firstName =rs.getString("fistName");
                String lastName =rs.getString("fistName");
                int numberOfBooks =rs.getInt("times");
                userBorrowedSeries.getData().add(new XYChart.Data(firstName +" " +  lastName,numberOfBooks));
                topNumber--;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        usersChart.getData().addAll(userBorrowedSeries);

    }
}

