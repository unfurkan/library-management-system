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

public class TopGenreChartController implements Initializable
{
    @FXML
    private BarChart<?, ?> userChart;

    @FXML
    private CategoryAxis x;

    @FXML
    private NumberAxis y;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadData();
    }

    private void loadData() {
        XYChart.Series userBorrowedSeries =new XYChart.Series<>();

        String queryCountBorrowedBooks ="Select genre,Count(genre) as numberOfBooks from book_genre group by genre order by numberOfBooks desc";
        try {
            ResultSet rs = DbConnection.readFromDatabase(queryCountBorrowedBooks);
            int topNumber =10;
            while(topNumber > 0 && rs.next()){
                String genre =rs.getString("genre");
                int numberOfBooks =rs.getInt("numberOfBooks");
                userBorrowedSeries.getData().add(new XYChart.Data(genre,numberOfBooks));
                topNumber--;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    userChart.getData().addAll(userBorrowedSeries);

    }
}
