package controllers;

import database.DbConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RankController {
    @FXML
    private BorderPane rootPane;


    @FXML
    void handleAgeGroupForSpecialBook(ActionEvent event) {

    }

    @FXML
    void handleAvgBookKeepingTİme(ActionEvent event) {


    }

    @FXML
    void handleTop10Books(ActionEvent event) {
        Pane top10Books =getGraphic("../fxml/top10BooksChart.fxml");

        rootPane.setCenter(top10Books);


    }

    @FXML
    void handleTop10Genre(ActionEvent event) {
        Pane top10Genre =getGraphic("../fxml/top10GenreChart.fxml");

        rootPane.setCenter(top10Genre);



    }

    @FXML
    void handleTop10Users(ActionEvent event) {
        Pane top10User =getGraphic("../fxml/top10UsersChart.fxml");

        rootPane.setCenter(top10User);


    }

    private Pane getGraphic(String resource){
        Pane view = null;
        try {
            view = FXMLLoader.load(getClass().getResource(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return view;
}

}

