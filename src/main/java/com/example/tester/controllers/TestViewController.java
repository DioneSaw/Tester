package com.example.tester.controllers;

import com.example.tester.TesterApp;
import com.example.tester.classes.*;
import com.example.tester.models.AnswersDAO;
import com.example.tester.models.QuestionsDAO;

import com.example.tester.models.ResultsDAO;
import com.example.tester.models.TestsDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class TestViewController {

    @FXML
    private VBox questionsVbox;

    @FXML
    private Label testNameLabel;

    @FXML
    private Button submitAnswersBtn;

    @FXML
    private Button extBtn;
    private Tests test;
    private Directions direction;
    private boolean isFinal = false;
    private int pass_3 = 70;
    private int pass_4 = 80;
    private int pass_5 = 90;
    private int grade = 2;
    private double xOffset;
    private double yOffset;
    private double score;
    private Results result;
    private List<Questions> questions;
    Stage stage;
    Scene scene;
    List<HBox> answersBox = new ArrayList<>();
    List<VBox> questionBoxes = new ArrayList<>();
    private QuestionsDAO questionsDAO = new QuestionsDAO();
    private AnswersDAO answersDAO = new AnswersDAO();

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

    private static final String SUBMIT_IDLE_STYLE = "-fx-background-color: #ffffff;" +
            "-fx-text-fill:  #21334c;" +
            " -fx-border-color:  #21334c;" +
            " -fx-background-radius:  5px;" +
            " -fx-border-radius: 5px;" +

            "-fx-border-insets: 5px; " +
            "-fx-background-insets: 5px;";
    private static final String SUBMIT_HOVERED_STYLE = "-fx-background-color: #21334c;" +
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

    private void LoadFirstType(List<Answers> answers, VBox questionBox){
        ToggleGroup group = new ToggleGroup();

        String labelStyle = "-fx-padding: 5px 0px 5px 5px;" +
                        "-fx-font: 14px verdana;";

        String buttonStyle = "-fx-padding: 5px 0px 5px 0px;" +
                             "-fx-font: 14px verdana;";

        for(Answers answer : answers) {
            HBox answerBox = new HBox();

            Label answerLabel = new Label(answer.getText());

            answerLabel.setStyle(labelStyle);
            answerLabel.setMaxWidth(700);
            answerLabel.setWrapText(true);

            RadioButton rb = new RadioButton();
            rb.setToggleGroup(group);
            rb.setUserData(answer);

            rb.setStyle(buttonStyle);

            answerBox.getChildren().addAll(rb, answerLabel);
            this.answersBox.add(answerBox);

            questionBox.getChildren().add(answerBox);
        }
    }

    private void LoadSecondType(List<Answers> answers, VBox questionBox){
        for(Answers answer : answers) {
            HBox answerBox = new HBox();

            String labelStyle = "-fx-padding: 5px 0px 5px 5px;" +
                    "-fx-font: 14px verdana;";

            String buttonStyle = "-fx-border-width: 1px;" +
                                 "-fx-border-color: -fx-darkest-grey-color;" +
                                 "-fx-background-color: white;" +
                                 "-fx-background-image: null;" +
                                 "-fx-border-radius: 15px;" +
                                 "-fx-padding: 3px;";

            Label answerLabel = new Label(answer.getText());

            answerLabel.setStyle(labelStyle);
            answerLabel.setMaxWidth(700);
            answerLabel.setWrapText(true);

            CheckBox cb = new CheckBox();
            cb.setUserData(answer);

            cb.setStyle(buttonStyle);

            answerBox.getChildren().addAll(cb, answerLabel);
            this.answersBox.add(answerBox);

            questionBox.getChildren().add(answerBox);
        }
    }

    private void LoadThirdType(List<Answers> answers, VBox questionBox){
        for(Answers answer : answers) {
            HBox answerBox = new HBox();

            TextField tf = new TextField();
            tf.setUserData(answer);

            answerBox.getChildren().addAll(tf);
            this.answersBox.add(answerBox);

            questionBox.getChildren().add(answerBox);
        }
    }

    private void LoadQuestions(List<Questions> questions) throws SQLException {

        for(Questions question : questions) {
            VBox questionBox = new VBox();

            String boxStyle =   "-fx-background-color: #ffffff; " +

                                "-fx-background-radius: 0px; " +
                                "-fx-border-radius: 0px; " +

                                "-fx-padding: 15px 10px 15px 10px; " +
                                "-fx-border-insets: 5px; " +
                                "-fx-background-insets: 5px;" ;

            String qText =  "-fx-font: 20px verdana;" +
                            "-fx-padding: 5px 0px 5px 0px;";

            Label questionLabel = new Label(question.getText());
            questionBox.getChildren().add(questionLabel);

            questionLabel.setWrapText(true);
            questionLabel.setMaxWidth(900);

            if(question.getImage() != null){
                byte[] imageData = question.getImage();
                Image image = new Image(new ByteArrayInputStream(imageData));
                ImageView imageView = new ImageView(image);

                imageView.maxHeight(360);
                imageView.maxWidth(360);

                questionBox.getChildren().add(imageView);
            }

            List<Answers> answers = answersDAO.findByQuestionId(question.getId());

            if(question.getType() == 1){LoadFirstType(answers, questionBox);}
            if(question.getType() == 2){LoadSecondType(answers, questionBox);}
            if(question.getType() == 3){LoadThirdType(answers, questionBox);}

            questionBox.setStyle(boxStyle);
            questionLabel.setStyle(qText);

            questionsVbox.getChildren().add(questionBox);
        }
    }

    public int checkMultipleChoiceAnswer(Questions question, List<Answers> userAnswers) throws SQLException {
        int score = 0;

        List<Answers> correctAnswers = answersDAO.findCorrectByQuestionId(question.getId());

        Set<Integer> correctIds = new HashSet<>();
        for(Answers ans : correctAnswers) {
            correctIds.add(ans.getId());
        }

        Set<Integer> userAnswerIds = new HashSet<>();

        for(Answers ans : userAnswers) {
            userAnswerIds.add(ans.getId());
        }

        if(correctIds.equals(userAnswerIds)) {
            score = 1;
        }

        return score;

    }

    public int checkTextAnswer(Questions question, String userAnswer) throws SQLException {
        int score = 0;

        Answers correctAnswer = answersDAO.findByQuestionId(question.getId()).get(0);

        if(correctAnswer != null) {

            if(userAnswer.equalsIgnoreCase(correctAnswer.getText())) {
                score = 1;
            } else {
                score = 0;
            }

        } else {
            score = 0;
        }

        return score;
    }

    public int checkSingleAnswer(Questions question, Object userAnswer) throws SQLException {
        int score = 0;

        Answers correctAnswer = answersDAO.findCorrectByQuestionId(question.getId()).get(0);

        if(correctAnswer != null) {
            if(((Answers)userAnswer).getId() == correctAnswer.getId()) {
                score = 1;
            } else {
                score = 0;
            }

        } else {
            score = 0;
        }

        return score;
    }

    List<Questions> getQuestions() throws SQLException {
        return this.questions;
    }

    ArrayList<Object> getUserAnswers(int size) {

        ArrayList<Object> answers = new ArrayList<Object>();
        List<Answers> qAns = new ArrayList<>();

        int qId = 0;
        boolean submited = false;

        for(int i = 0; i < this.answersBox.size(); i++){
            for(Node node : this.answersBox.get(i).getChildren()) {

                if(node instanceof RadioButton) {
                    if(qAns.size() != 0) {
                        List<Answers> ans = new ArrayList<Answers>(qAns);
                        answers.add(ans);
                        qAns.clear();
                        qId = ((Answers)((RadioButton)node).getUserData()).getQuestion_id();
                    }

                    if(qId == 0){ qId = ((Answers)((RadioButton)node).getUserData()).getQuestion_id(); }

                    if((qId != ((Answers)((RadioButton)node).getUserData()).getQuestion_id()) && (!submited)){
                        answers.add(null);
                        qId = ((Answers)((RadioButton)node).getUserData()).getQuestion_id();
                    }

                    if((qId != ((Answers)((RadioButton)node).getUserData()).getQuestion_id()) && (submited)){
                        qId = ((Answers)((RadioButton)node).getUserData()).getQuestion_id();
                        submited = false;
                    }

                    if(((RadioButton)node).isSelected()) {
                        submited = true;
                        answers.add((Answers)((RadioButton)node).getUserData());
                    }

                }

                if(node instanceof CheckBox){
                    if(qId == 0){ qId = ((Answers)((CheckBox)node).getUserData()).getQuestion_id(); }

                    if((qId != ((Answers)((CheckBox)node).getUserData()).getQuestion_id()) && (!submited)){
                        if(qAns.size() == 0) { answers.add(null); }
                        else{
                            List<Answers> ans = new ArrayList<Answers>(qAns);
                            answers.add(ans);
                            qAns.clear();
                        }

                        qId = ((Answers)((CheckBox)node).getUserData()).getQuestion_id();
                    }

                    if((qId != ((Answers)((CheckBox)node).getUserData()).getQuestion_id()) && (submited)){
                        qId = ((Answers)((CheckBox)node).getUserData()).getQuestion_id();
                        submited = false;
                    }

                    if(((CheckBox)node).isSelected()){
                        if(!(qAns.size() == 0)){
                            if((qAns.get(0).getQuestion_id() == ((Answers)((CheckBox)node).getUserData()).getQuestion_id())){
                                qAns.add((Answers)((CheckBox)node).getUserData());
                            }else {
                                List<Answers> ans = new ArrayList<Answers>(qAns);
                                answers.add(ans);
                                qAns.clear();
                                qAns.add((Answers)((CheckBox)node).getUserData());
                                submited = true;
                            }
                        }else{
                            qAns.add((Answers)((CheckBox)node).getUserData());
                        }
                    }
                }

                if(node instanceof TextField){
                    if(qAns.size() != 0) {
                        List<Answers> ans = new ArrayList<Answers>(qAns);
                        answers.add(ans);
                        qAns.clear();
                        qId = ((Answers)((TextField)node).getUserData()).getQuestion_id();
                    }

                    if(qId == 0){ qId = ((Answers)((TextField)node).getUserData()).getQuestion_id(); }

                    if((qId != ((Answers)((TextField)node).getUserData()).getQuestion_id()) && (!submited)){
                        answers.add(null);
                        qId = ((Answers)((TextField)node).getUserData()).getQuestion_id();
                    }

                    if((qId != ((Answers)((TextField)node).getUserData()).getQuestion_id()) && (submited)){
                        qId = ((Answers)((TextField)node).getUserData()).getQuestion_id();
                        submited = false;
                    }

                    if(!((TextField)node).getText().isEmpty()){
                        answers.add((Answers)((TextField)node).getUserData());
                        submited = true;
                    }else { answers.add(null); submited = true;}
                }
            }
        }
        if(qAns.size() != 0) {
            List<Answers> ans = new ArrayList<Answers>(qAns);
            answers.add(ans);
            qAns.clear();
            submited = true;
        }
        if(!submited){answers.add(null);}
        return answers;
    }

    void CheckAnswers() throws SQLException {
        List<Questions> questions = getQuestions();
        List<Object> userAnswers = getUserAnswers(questions.size());

        double totalScore = 0;

        for(int i = 0; i < questions.size(); i++) {

            Questions question = questions.get(i);
            Object userAnswer = userAnswers.get(i);

            if(userAnswer == null){
                continue;
            }

            switch(question.getType()) {

                case 1:
                    totalScore += checkSingleAnswer(question, userAnswer);
                    break;

                case 2:
                    totalScore += checkMultipleChoiceAnswer(question, ((List<Answers>)userAnswer));
                    break;

                case 3:
                    totalScore += checkTextAnswer(question, ((Answers)userAnswer).getText());
                    break;

            }
        }

        this.score = totalScore * 100 / questions.size();

        if(score >= pass_3){this.grade = 3;}
        if(score >= pass_4){this.grade = 4;}
        if(score >= pass_5){this.grade = 5;}

        ResultsDAO resultsDAO = new ResultsDAO();

        int resId = resultsDAO.findLastRecord().getId();

        resultsDAO.updateGrades(resId, score, grade);
    }

    @FXML
    void SubmitAnswers(MouseEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Вы хотите завершить прохождение теста и сохранить ответы?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try {
                CheckAnswers();

                URL url = TesterApp.class.getResource("views/ResultsView.fxml");
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

                ResultsViewController controller = loader.getController();
                controller.SetResultsData(this.grade, this.score);

                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);

                stage.setResizable(false);

                stage.show();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setTest(Tests test) throws SQLException {
        this.test = test;
        this.questions = questionsDAO.findByTestId(this.test.getId());

        pass_3 = test.getPass_score_3();
        pass_4 = test.getPass_score_4();
        pass_5 = test.getPass_score_5();

        LoadQuestions(questions);
    }

    public void setResult(Results result){
        this.result = result;
    }

    public void setFinalTest(Directions direction) throws SQLException {
        this.direction = direction;
        isFinal = true;
        this.questions = questionsDAO.findQuestionsForFinal(this.direction.getId());

        LoadQuestions(questions);
    }

    @FXML
    public void initialize() {
        extBtn.setStyle(IDLE_BUTTON_STYLE);
        extBtn.setOnMouseEntered(e -> extBtn.setStyle(HOVERED_BUTTON_STYLE));
        extBtn.setOnMouseExited(e -> extBtn.setStyle(IDLE_BUTTON_STYLE));

        submitAnswersBtn.setStyle(SUBMIT_IDLE_STYLE);
        submitAnswersBtn.setOnMouseEntered(event -> submitAnswersBtn.setStyle(SUBMIT_HOVERED_STYLE));
        submitAnswersBtn.setOnMouseExited(event -> submitAnswersBtn.setStyle(SUBMIT_IDLE_STYLE));
    }

}
