package controllers;
import database.DbConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class TopBooksChartController implements Initializable {
    @FXML
    private BarChart<?, ?> booksChart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    private void loadData() {
        XYChart.Series userBorrowedSeries =new XYChart.Series<>();

        String queryCountBorrowedBooks ="Select title,count(Book_ISBN) as times from book as bookTable\n" +
                "INNER JOIN borrowed as borrowedTable\n" +
                "ON bookTable.ISBN = borrowedTable.Book_ISBN\n" +
                "group by Book_ISBN order by times desc";
        try {
            ResultSet rs = DbConnection.readFromDatabase(queryCountBorrowedBooks);
            int topNumber =10;
            while(topNumber > 0 && rs.next()){
                String title =rs.getString("title");
                int numberOfTimes =rs.getInt("times");
                userBorrowedSeries.getData().add(new XYChart.Data(title,numberOfTimes));
                topNumber--;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        booksChart.getData().addAll(userBorrowedSeries);

    }
}
