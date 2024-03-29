/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogproject;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import timelogproject.PageSwitchHelper;
import java.io.IOException;
import java.lang.Math;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import timelogproject.PageSwitchHelper;

/**
 *
 * @author priyal
 */
public class PieChartController implements Initializable {

    @FXML
    private Label label;

    private final String URL = "jdbc:sqlite:timelog.db";
    private Connection connection;

    @FXML
    private Text PieChartTitle;

    @FXML
    private PieChart PieChartofMyLife;
    @FXML
    private Button tlBtn, kbBtn, dpBtn, wbBtn, dbBtn;
        
    PageSwitchHelper psh = new PageSwitchHelper();

    @FXML
    private void handleTLBtnAction(ActionEvent event) throws IOException {
        psh.switcher(event, "TimeLogView.fxml");
    }
    @FXML
    private void handleAboutBtnAction(ActionEvent event) throws IOException {
        psh.switcher(event, "AboutScreen.fxml");
    }
    @FXML
    private void handleKBBtnAction(ActionEvent event) throws IOException {
        psh.switcher(event, "Kanban.fxml");
    }
    @FXML
    private void handleDFBtnAction(ActionEvent event) throws IOException {
        psh.switcher(event, "DeepFocus.fxml");
    }
    @FXML
    private void handleWBBtnAction(ActionEvent event) throws IOException {
        psh.switcher(event, "WeeklyGraphsView.fxml");
    }
    @FXML
    private void handleDBBtnAction(ActionEvent event) throws IOException {
        psh.switcher(event, "TimeGraphsView.fxml");
    }


    public class Event {

        public String eventCategory = "";
        public double eventDur = 0.0;

        // constructor
        public Event(String name, double duration) {
            this.eventCategory = name;
            this.eventDur = duration;
        }

        public String getEventCategory() {
            return eventCategory;
        }

        public void setEventCategory(String eventCat) {
            this.eventCategory = eventCat;
        }

        public double getEventDur() {
            return eventDur;
        }

        public void setEventDur(int eventDur) {
            this.eventDur = eventDur;
        }
    }

    public ArrayList<Event> getEvents(String query) throws SQLException {

        // create new EventArray
        ArrayList<Event> eventsList = new ArrayList<>();

        //create connection
        Connection conn = DriverManager.getConnection(URL);

        //create statement
        Statement st = conn.createStatement();

        //write the SQL query to retrieve all pets that are of the species specified in the parameter of this method
        System.out.println("** get events from database **");

        ResultSet rs = st.executeQuery(query);

        // Iterate over result set from query
        int i = 0;
        while (rs.next()) {

            // Get individual attributes from each row
            String currentCategory = rs.getString("category");
            double currentDuration = rs.getDouble("sum(total_duration)");

            // Populate new event with attributes and add to event array
            Event currentEvent = new Event(currentCategory, currentDuration);
            eventsList.add(currentEvent);
            i++;
        }

        //close connection 
        st.close();
        conn.close();

        // return array of events
        return eventsList;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

   
        // Show weekly breakdown graph
        String myLife = "select   category, sum(total_duration) "
                + "from     entries "
                + "group by category "
                + "order by sum(total_duration) desc";

        try {

            // get array list of events for selected date
            ArrayList<Event> eventsList = getEvents(myLife);

            // for each event in list, add to the bar chart 
            for (Event curr : eventsList) {

                String currCategory = curr.eventCategory;
                double currDuration = curr.eventDur;

                System.out.println("Added: " + currCategory + ", " + currDuration);

                PieChart.Data slice = new PieChart.Data(currCategory, currDuration);
                PieChartofMyLife.getData().add(slice);

            }

            System.out.println("Pie (chart) is now ready!");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
