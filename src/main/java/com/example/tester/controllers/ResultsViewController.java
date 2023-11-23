package com.example.tester.controllers;

import com.example.tester.TesterApp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class ResultsViewController {

    @FXML
    private Button backBtn;

    @FXML
    private Button extBtn;

    @FXML
    private Label gradeLabel;

    @FXML
    private Label scoreLabel;

    Stage stage;
    Scene scene;
    private double xOffset;
    private double yOffset;

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: #ffffff;" +
            "-fx-text-fill:  #21334c;" +
            "-fx-background-radius: 0px;" +
            "-fx-border-radius: 0px;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #21334c;" +
            "-fx-text-fill:  #ffffff;" +
            "-fx-background-radius: 0px;" +
            "-fx-border-radius: 0px;";

    private static final String BACK_IDLE_STYLE = "-fx-background-color: #ffffff;" +
            "-fx-text-fill:  #21334c;" +
            " -fx-border-color:  #21334c;" +
            " -fx-background-radius:  5px;" +
            " -fx-border-radius: 5px;" +

            "-fx-border-insets: 5px; " +
            "-fx-background-insets: 5px;";
    private static final String BACK_HOVERED_STYLE = "-fx-background-color: #21334c;" +
            "-fx-text-fill:  #ffffff;" +
            " -fx-border-color:  #0a1118;" +
            " -fx-background-radius:  5px;" +
            " -fx-border-radius: 5px;" +

            "-fx-border-insets: 5px; " +
            "-fx-background-insets: 5px;";

    @FXML
    void ExitProgramm(MouseEvent event) {
        Stage stage = (Stage) extBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void DragWindow(MouseEvent event) {

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        double x = event.getScreenX() - xOffset;
        double y = event.getScreenY() - yOffset;

        stage.setX(x);
        stage.setY(y);

    }

    @FXML
    void BackToDirections(MouseEvent event) {
        URL url = TesterApp.class.getResource("views/DirectionsView.fxml");
        FXMLLoader loader = new FXMLLoader(url);
        if (loader == null) {
            throw new RuntimeException("Could not find " + url.toString());
        }
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DirectionViewController controller = loader.getController();
        controller.CheckDBChecked();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.setResizable(false);

        stage.show();
    }

    public void onMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    public void SetResultsData(int grade, double score){
        gradeLabel.setText(Integer.toString(grade));
        scoreLabel.setText(String.format("%.2f", score) + "%");
    }

    @FXML
    public void initialize() throws SQLException {
        extBtn.setStyle(IDLE_BUTTON_STYLE);
        extBtn.setOnMouseEntered(e -> extBtn.setStyle(HOVERED_BUTTON_STYLE));
        extBtn.setOnMouseExited(e -> extBtn.setStyle(IDLE_BUTTON_STYLE));

        backBtn.setStyle(BACK_IDLE_STYLE);
        backBtn.setOnMouseEntered(event -> backBtn.setStyle(BACK_HOVERED_STYLE));
        backBtn.setOnMouseExited(event -> backBtn.setStyle(BACK_IDLE_STYLE));

    }
}
