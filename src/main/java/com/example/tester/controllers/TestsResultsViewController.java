package com.example.tester.controllers;

import com.example.tester.TesterApp;
import com.example.tester.classes.Directions;
import com.example.tester.classes.Results;
import com.example.tester.classes.Tests;
import com.example.tester.models.ResultsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class TestsResultsViewController {
    private double xOffset;
    private double yOffset;
    private Directions direction;
    private Tests test;
    Stage stage;
    Scene scene;

    @FXML
    private TableColumn<Results, Date> dateCol;

    @FXML
    private TableColumn<Results, Integer> gradeCol;

    @FXML
    private TableColumn<Results, String> groupCol;

    @FXML
    private TableColumn<Results, String> nameCol;

    @FXML
    private TableColumn<Results, Double> scoreCol;

    @FXML
    private Button backBtn;

    @FXML
    private Button extBtn;

    @FXML
    private TableView<Results> rsultTableView;

    @FXML
    private Label testNameLabel;

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

    @FXML
    void DragWindow(MouseEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        double x = event.getScreenX() - xOffset;
        double y = event.getScreenY() - yOffset;

        stage.setX(x);
        stage.setY(y);
    }

    @FXML
    void ExitProgramm(MouseEvent event) {
        Stage stage = (Stage) extBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onMousePressed(MouseEvent event) {
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

    void setInfo(Tests test, Directions direction){
        this.test = test;
        this.direction = direction;

        if(this.test == null){
            testNameLabel.setText("Итоговый тест");
            showFinalTestResults(this.direction.getId());
        }
        else{
            testNameLabel.setText(this.test.getName());
            showTestResults(this.test.getId());
        }
    }

    public void showTestResults(int testId) {
        try{
            ResultsDAO dao = new ResultsDAO();
            List<Results> results = dao.findByTestId(testId);

            ObservableList<Results> data = FXCollections.observableArrayList(results);
            rsultTableView.setItems(data);

            nameCol.setCellValueFactory(new PropertyValueFactory<>("student_name"));
            groupCol.setCellValueFactory(new PropertyValueFactory<>("student_group"));
            scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
            gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    public void showFinalTestResults(int directionId) {
        try{
            ResultsDAO dao = new ResultsDAO();
            List<Results> results = dao.findByDirectionId(directionId);

            ObservableList<Results> data = FXCollections.observableArrayList(results);
            rsultTableView.setItems(data);

            nameCol.setCellValueFactory(new PropertyValueFactory<>("student_name"));
            groupCol.setCellValueFactory(new PropertyValueFactory<>("student_group"));
            scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
            gradeCol.setCellValueFactory(new PropertyValueFactory<>("grade"));
        }catch (SQLException e){
            System.out.println(e);
        }
    }

    @FXML
    public void initialize() {
        extBtn.setStyle(IDLE_BUTTON_STYLE);
        extBtn.setOnMouseEntered(e -> extBtn.setStyle(HOVERED_BUTTON_STYLE));
        extBtn.setOnMouseExited(e -> extBtn.setStyle(IDLE_BUTTON_STYLE));

        backBtn.setStyle(BACK_IDLE_STYLE);
        backBtn.setOnMouseEntered(event -> backBtn.setStyle(BACK_HOOVER_STYLE));
        backBtn.setOnMouseExited(event -> backBtn.setStyle(BACK_IDLE_STYLE));
    }
}
