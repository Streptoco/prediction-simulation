package handler.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Map;

public class HistogramController {
    private Stage stage;
    @FXML
    private AnchorPane mainAnchor;
    @FXML
    private BarChart<String, Number> histogram;

    public void setStage(Stage stage, Map<String, Integer> dataMap) {
        this.stage = stage;
        CategoryAxis xAxisValue = new CategoryAxis();
        xAxisValue.setLabel("Value");
        xAxisValue.setLayoutX(5);
        NumberAxis yAxisAmount = new NumberAxis();
        yAxisAmount.setLabel("Amount");
        histogram = new BarChart<>(xAxisValue, yAxisAmount);
        histogram.setTitle("Test");
        XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
        for(Map.Entry<String, Integer> entry : dataMap.entrySet()) {
            dataSeries.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
        }
        histogram.getData().add(dataSeries);
        mainAnchor.getChildren().add(histogram);
    }
    private void closeHistogram(ActionEvent event) {
        stage.close();
    }


}
