package com.example.tester.controllers;

import com.example.tester.TesterApp;
import com.example.tester.classes.*;
import com.example.tester.models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;

import java.util.List;

public class DirectionViewController {
    DBHandler connection = new DBHandler();
    DirectionsDAO directionsDAO = new DirectionsDAO();
    TestsDAO testsDAO = new TestsDAO();
    QuestionsDAO questionsDAO = new QuestionsDAO();
    AnswersDAO answersDAO = new AnswersDAO();
    ResultsDAO resultsDAO = new ResultsDAO();
    Stage stage;
    Scene scene;
    private double xOffset;
    private double yOffset;
    private boolean isDBChecked = false;
    ExecuteSQLScript sqlExecutor = new ExecuteSQLScript();

    @FXML
    private VBox directionsVbox;

    @FXML
    private Button exitBtn;

    @FXML
    void ExitProgramm(MouseEvent event) {
        Stage stage = (Stage) exitBtn.getScene().getWindow();
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


    private static final String IDLE_BUTTON_STYLE = "-fx-background-color: #ffffff;" +
                                                    "-fx-text-fill:  #21334c;" +
                                                    "-fx-background-radius: 0px;" +
                                                    "-fx-border-radius: 0px;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-background-color: #21334c;" +
                                                        "-fx-text-fill:  #ffffff;" +
                                                        "-fx-background-radius: 0px;" +
                                                        "-fx-border-radius: 0px;";
    private static final String DIRECTION_IDLE_STYLE = "-fx-background-color: #ffffff;" +
                                                        "-fx-text-fill:  #21334c;" +
                                                        " -fx-border-color:  #21334c;" +
                                                        " -fx-background-radius:  5px;" +
                                                        " -fx-border-radius: 5px;" +

                                                        "-fx-border-insets: 5px; " +
                                                        "-fx-background-insets: 5px;";
    private static final String DIRECTION_HOVERED_STYLE = "-fx-background-color: #21334c;" +
                                                            "-fx-text-fill:  #ffffff;" +
                                                            " -fx-border-color:  #0a1118;" +
                                                            " -fx-background-radius:  5px;" +
                                                            " -fx-border-radius: 5px;" +

                                                            "-fx-border-insets: 5px; " +
                                                            "-fx-background-insets: 5px;";

    private void DBCheck() throws SQLException{
        sqlExecutor.execute("init.sql");
        sqlExecutor.execute("directions.sql");
    }

    private void OpenTestsMenu(Directions direction, ActionEvent event) throws SQLException, IOException {
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

    private void CreateDirectionsButtons() throws SQLException {
        DirectionsDAO directionDao = new DirectionsDAO();
        List<Directions> directions = directionDao.findAll();

        for(Directions direction : directions) {
            Button button = new Button(direction.getName());

            button.setMinHeight(40);
            button.setMaxHeight(40);
            button.setMinWidth(325);
            button.setMaxWidth(325);

            button.setStyle(DIRECTION_IDLE_STYLE);
            button.setOnMouseEntered(event -> button.setStyle(DIRECTION_HOVERED_STYLE));
            button.setOnMouseExited(event -> button.setStyle(DIRECTION_IDLE_STYLE));

            button.setOnAction(event -> {
                try {
                    OpenTestsMenu(direction, event);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            directionsVbox.getChildren().add(button);
        }
    }

    void CheckDBChecked(){
        this.isDBChecked = true;
    }

    @FXML
    public void initialize() {
        exitBtn.setStyle(IDLE_BUTTON_STYLE);
        exitBtn.setOnMouseEntered(e -> exitBtn.setStyle(HOVERED_BUTTON_STYLE));
        exitBtn.setOnMouseExited(e -> exitBtn.setStyle(IDLE_BUTTON_STYLE));

        try {
            DBCheck();

            if(testsDAO.findAll().size() == 0){
                sqlExecutor.execute("rhbz_tests.sql");
                sqlExecutor.execute("sp_tests.sql");
            }
            else{System.out.println("No tests required");}

            CreateDirectionsButtons();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}