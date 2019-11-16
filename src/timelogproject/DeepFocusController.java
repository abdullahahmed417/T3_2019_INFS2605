/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogproject;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import timelogproject.PageSwitchHelper;

/**
 * FXML Controller class
 *
 * @author Tarn
 */
public class DeepFocusController implements Initializable {

    /**
     * Initializes the controller class.
     */
    PageSwitchHelper psh = new PageSwitchHelper();
    //private final String URL = "jdbc:sqlite:timelog.db";
    //private Connection connection;
    final String URL = "jdbc:sqlite:timelog.db";
    Connection connection;

    @FXML
    private Label dateTime;

    @FXML
    private ComboBox comboBoxTasks;

    //Delete:
    @FXML
    private Label taskName;

    @FXML
    private Label taskDescription;
    @FXML
    private Button tlBtn, kbBtn, mlBtn, wbdBtn, dbdBtn;

//    handleTLBtnAction
    @FXML
    private void handleTLBtnAction(ActionEvent event) throws IOException {
        psh.switcher(event, "TimeLogView.fxml");
    }

    @FXML
    private void handleHelpButtonAction(ActionEvent event) throws IOException {
        psh.switcher(event, "AboutScreen.fxml");
    }

    @FXML
    private void handleKBBtnAction(ActionEvent event) throws IOException {
        psh.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleMLBtnAction(ActionEvent event) throws IOException {
        psh.switcher(event, "PieChartView.fxml");
    }

    @FXML
    private void handleWBdBtnAction(ActionEvent event) throws IOException {
        psh.switcher(event, "WeeklyGraphsView.fxml");
    }

    @FXML
    private void handleDBdBtnAction(ActionEvent event) throws IOException {
        psh.switcher(event, "TimeGraphsView.fxml");
    }

    //Set default values:
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //Make connection:

        //Combo Box: Make a list of items that have been uploaded into the database:
        //Clock:
        initClock();
        fillComboBox();

    }

    /*
private void initClock() {
    
Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {        
        LocalTime currentTime = LocalTime.now();
        dateTime.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
    }),
         new KeyFrame(Duration.seconds(1))
    );
    clock.setCycleCount(Animation.INDEFINITE);
    clock.play();
}
     */
    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd     HH:mm:ss");
            dateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    /**
     *
     * @param event
     */
    public void comboChanged(ActionEvent event) {
        taskName.setText(comboBoxTasks.getValue().toString());
        try {

            String tasks = "SELECT task_description FROM Tasks WHERE task_title ='" + (comboBoxTasks.getValue().toString()) + "'";
            connection = DriverManager.getConnection("jdbc:sqlite:timelog.db");
            PreparedStatement prep = connection.prepareStatement(tasks);
            ResultSet results = prep.executeQuery();
            String rs;
            while (results.next()) {
                rs = results.getString(1);
                taskDescription.setText(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillComboBox() {

        Connection connection = null;

        try {
            ObservableList<String> tasksCombo = FXCollections.observableArrayList();
            String tasks = "SELECT * FROM Tasks";
            connection = DriverManager.getConnection("jdbc:sqlite:timelog.db");
            PreparedStatement prep = connection.prepareStatement(tasks);
            ResultSet results = prep.executeQuery();

            while (results.next()) {
                tasksCombo.add(results.getString("task_title"));
                for (String i : tasksCombo) {
                    taskName.setText(results.getString("task_title").toString());
                    taskDescription.setText(results.getString("task_description".toString()));
                }
            }

            while (results.next()) {
                //Label taskName = new Label();
                taskName.setText(results.getString("task_title".toString()));
                //taskName.setText(results.getString("task_title"));
            }

            while (results.next()) {
                //Label taskDescription = new Label();
                taskDescription.setText(results.getString("task_description".toString()));
            }

            comboBoxTasks.setItems(tasksCombo);
            //taskName.setVisible(true);
            //taskDescription.setVisible(true);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
