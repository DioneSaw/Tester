package com.example.tester.controllers;

import com.example.tester.TesterApp;
import com.example.tester.classes.Directions;
import com.example.tester.classes.Results;
import com.example.tester.classes.Tests;
import com.example.tester.models.ResultsDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;

public class UserDataViewController {
    @FXML
    private Button backBtn;

    @FXML
    private Button extBtn;

    @FXML
    private TextField fioField;

    @FXML
    private TextField groupField;

    @FXML
    private Button startTestBtn;
    Stage stage;
    Scene scene;
    private double xOffset;
    private double yOffset;

    private Tests test;
    private Directions direction;

    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: #ffffff;" +
            "-fx-text-fill:  #21334c;" +
            "-fx-background-radius: 0px;" +
            "-fx-border-radius: 0px;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #21334c;" +
            "-fx-text-fill:  #ffffff;" +
            "-fx-background-radius: 0px;" +
            "-fx-border-radius: 0px;";

    private static final String BACK_IDLE_STYLE = "-fx-background-color: transparent;" +
            "-fx-text-fill: #21334c;";
    private static final String BACK_HOOVER_STYLE = "-fx-background-color: transparent;" +
            "-fx-text-fill: #426698;";

    private static final String START_IDLE_STYLE = "-fx-background-color: #ffffff;" +
            "-fx-text-fill:  #21334c;" +
            " -fx-border-color:  #21334c;" +
            " -fx-background-radius:  5px;" +
            " -fx-border-radius: 5px;" +

            "-fx-border-insets: 5px; " +
            "-fx-background-insets: 5px;";
    private static final String START_HOVERED_STYLE = "-fx-background-color: #21334c;" +
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

    public void onMousePressed(MouseEvent event) {
        xOffset = event.getSceneX();
        yOffset = event.getSceneY();
    }

    @FXML
    void BackToTests(MouseEvent event) throws SQLException {
        URL url = TesterApp.class.getResource("views/TestsView.fxml");
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

        TestsViewController controller = loader.getController();
        controller.setDirection(direction);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.setResizable(false);

        stage.show();
    }

    private boolean CheckFields(){
        if((!fioField.getText().isEmpty()) && (!groupField.getText().isEmpty())){return true;}
        return false;
    }

    @FXML
    void StartTesting(MouseEvent event) throws SQLException {
        if(!CheckFields()){return;}

        URL url = TesterApp.class.getResource("views/TestView.fxml");
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

        Tests test = new Tests();
        Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        TestViewController controller = loader.getController();

        ResultsDAO resultsDAO = new ResultsDAO();

        if(this.test != null){
            Results result = new Results(fioField.getText(), groupField.getText(), sqlDate, 0, 0, 0.0, this.test.getId(), -1, null);
            resultsDAO.save(result);
            controller.setTest(this.test);
        }
        else{
            Results result = new Results(fioField.getText(), groupField.getText(), sqlDate, 0, 0, 0.0, -1, direction.getId(), null);
            resultsDAO.save(result);
            controller.setFinalTest(direction);
        }

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.setFullScreen(true);
        stage.setResizable(false);

        stage.show();
    }

    @FXML
    public void initialize() throws SQLException {
        extBtn.setStyle(IDLE_BUTTON_STYLE);
        extBtn.setOnMouseEntered(e -> extBtn.setStyle(HOVERED_BUTTON_STYLE));
        extBtn.setOnMouseExited(e -> extBtn.setStyle(IDLE_BUTTON_STYLE));

        backBtn.setStyle(BACK_IDLE_STYLE);
        backBtn.setOnMouseEntered(event -> backBtn.setStyle(BACK_HOOVER_STYLE));
        backBtn.setOnMouseExited(event -> backBtn.setStyle(BACK_IDLE_STYLE));

        startTestBtn.setStyle(START_IDLE_STYLE);
        startTestBtn.setOnMouseEntered(event -> startTestBtn.setStyle(START_HOVERED_STYLE));
        startTestBtn.setOnMouseExited(event -> startTestBtn.setStyle(START_IDLE_STYLE));
    }

    void setInfo(Tests test, Directions direction){
        this.test = test;
        this.direction = direction;
    }
}
