package com.example.tester.controllers;

import com.example.tester.TesterApp;
import com.example.tester.classes.Directions;
import com.example.tester.classes.Tests;
import com.example.tester.models.DirectionsDAO;
import com.example.tester.models.TestsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class TestsViewController {
    @FXML
    private VBox testsVbox;
    @FXML
    private Button exiBtn;
    @FXML
    private Button backBtn;
    private Directions direction;
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
    private static final String TEST_IDLE_STYLE = "-fx-background-color: #ffffff;" +
            "-fx-text-fill:  #21334c;" +
            " -fx-border-color:  #21334c;" +
            " -fx-background-radius:  5px;" +
            " -fx-border-radius: 5px;" +

            "-fx-border-insets: 5px; " +
            "-fx-background-insets: 5px;";
    private static final String TEST_HOVERED_STYLE = "-fx-background-color: #21334c;" +
            "-fx-text-fill:  #ffffff;" +
            " -fx-border-color:  #0a1118;" +
            " -fx-background-radius:  5px;" +
            " -fx-border-radius: 5px;" +

            "-fx-border-insets: 5px; " +
            "-fx-background-insets: 5px;";
    private static final String BACK_IDLE_STYLE = "-fx-background-color: transparent;" +
                                                  "-fx-text-fill: #21334c;";
    private static final String BACK_HOOVER_STYLE = "-fx-background-color: transparent;" +
                                                    "-fx-text-fill: #426698;";

    @FXML
    void ExitProgramm(MouseEvent event) {
        Stage stage = (Stage) exiBtn.getScene().getWindow();
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

    public void setDirection(Directions direction) throws SQLException {
        this.direction = direction;
        CreateTestsButtons();
    }

    private void OpenTestMenu(Tests test, ActionEvent event) throws SQLException {
        URL url = TesterApp.class.getResource("views/UserDataView.fxml");
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

        UserDataViewController controller = loader.getController();

        controller.setInfo(test, direction);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.setResizable(false);

        stage.show();
    }

    private void OpenFinalTestMenu(Directions direction, ActionEvent event) throws SQLException {
        URL url = TesterApp.class.getResource("views/UserDataView.fxml");
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

        UserDataViewController controller = loader.getController();
        controller.setInfo(null, direction);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        stage.setResizable(false);

        stage.show();
    }

    private void CreateTestsButtons() throws SQLException {
        TestsDAO testsDAO = new TestsDAO();
        List<Tests> tests = testsDAO.findByDirectionId(this.direction.getId());

        for(Tests test : tests) {
            HBox hbox = new HBox();
            Button testBtn = new Button(test.getName());
            Button resultBtn = new Button("?");

            testBtn.setWrapText(true);

            hbox.setAlignment(Pos.CENTER);

            testBtn.setMinHeight(40);
            testBtn.setMinWidth(400);
            testBtn.setMaxWidth(400);

            resultBtn.setMinHeight(40);
            resultBtn.setMaxHeight(40);
            resultBtn.setMinWidth(40);
            resultBtn.setMaxWidth(40);

            testBtn.setStyle(TEST_IDLE_STYLE);
            testBtn.setOnMouseEntered(event -> testBtn.setStyle(TEST_HOVERED_STYLE));
            testBtn.setOnMouseExited(event -> testBtn.setStyle(TEST_IDLE_STYLE));

            resultBtn.setStyle(TEST_IDLE_STYLE);
            resultBtn.setOnMouseEntered(event -> resultBtn.setStyle(TEST_HOVERED_STYLE));
            resultBtn.setOnMouseExited(event -> resultBtn.setStyle(TEST_IDLE_STYLE));

            testBtn.setOnAction(event -> {
                try {
                    OpenTestMenu(test, event);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            resultBtn.setOnAction(event -> {
                URL url = TesterApp.class.getResource("views/TestResultsView.fxml");
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

                TestsResultsViewController controller = loader.getController();
                controller.setInfo(test, direction);

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);

                stage.setResizable(false);

                stage.show();
            });


            hbox.getChildren().add(testBtn);
            hbox.getChildren().add(resultBtn);
            testsVbox.getChildren().add(hbox);
        }

        if(tests.size() != 0){
            HBox hbox = new HBox();
            Button testBtn = new Button("Итоговый тест");
            Button resultBtn = new Button("?");

            hbox.setAlignment(Pos.CENTER);

            testBtn.setMinHeight(40);
            testBtn.setMinWidth(400);
            testBtn.setMaxWidth(400);

            resultBtn.setMinHeight(40);
            resultBtn.setMaxHeight(40);
            resultBtn.setMinWidth(40);
            resultBtn.setMaxWidth(40);

            testBtn.setStyle(TEST_IDLE_STYLE);
            testBtn.setOnMouseEntered(event -> testBtn.setStyle(TEST_HOVERED_STYLE));
            testBtn.setOnMouseExited(event -> testBtn.setStyle(TEST_IDLE_STYLE));

            resultBtn.setStyle(TEST_IDLE_STYLE);
            resultBtn.setOnMouseEntered(event -> resultBtn.setStyle(TEST_HOVERED_STYLE));
            resultBtn.setOnMouseExited(event -> resultBtn.setStyle(TEST_IDLE_STYLE));

            testBtn.setOnAction(event -> {
                try {
                    OpenFinalTestMenu(this.direction, event);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });

            resultBtn.setOnAction(event -> {
                URL url = TesterApp.class.getResource("views/TestResultsView.fxml");
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

                TestsResultsViewController controller = loader.getController();
                controller.setInfo(null, direction);

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);

                stage.setResizable(false);

                stage.show();
            });

            hbox.getChildren().add(testBtn);
            hbox.getChildren().add(resultBtn);
            testsVbox.getChildren().add(hbox);
        }
    }

    @FXML
    void ReturnToDirections(ActionEvent event) {
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

    @FXML
    public void initialize() throws SQLException {
        exiBtn.setStyle(IDLE_BUTTON_STYLE);
        exiBtn.setOnMouseEntered(e -> exiBtn.setStyle(HOVERED_BUTTON_STYLE));
        exiBtn.setOnMouseExited(e -> exiBtn.setStyle(IDLE_BUTTON_STYLE));

        backBtn.setStyle(BACK_IDLE_STYLE);
        backBtn.setOnMouseEntered(event -> backBtn.setStyle(BACK_HOOVER_STYLE));
        backBtn.setOnMouseExited(event -> backBtn.setStyle(BACK_IDLE_STYLE));

    }
}
