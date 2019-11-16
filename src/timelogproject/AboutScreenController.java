/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogproject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import timelogproject.PageSwitchHelper;

public class AboutScreenController implements Initializable {
    PageSwitchHelper psh = new PageSwitchHelper();

    @FXML
    private Label title;
    @FXML
    private Label UUID;
    @FXML
    private Label copyright;
//    handleTimeLogButtonAction
        @FXML
    private void handleTimeLogButtonAction(ActionEvent event) throws IOException {
        psh.switcher(event, "TimeLogView.fxml");
    }
        @FXML
    private void handleKanbanButtonAction(ActionEvent event) throws IOException {
        psh.switcher(event, "Kanban.fxml");
    }

    @FXML
    private void handleDeepFocusButtonAction(ActionEvent event) throws IOException {
        psh.switcher(event, "DeepFocus.fxml");
    }

    @FXML
    private void handleMyLifeButtonAction(ActionEvent event) throws IOException {
        psh.switcher(event, "PieChartView.fxml");
    }

    @FXML
    private void handleWeeklyTrendsButtonAction(ActionEvent event) throws IOException {
        psh.switcher(event, "WeeklyGraphsView.fxml");
    }
    @FXML
    private void handleWeeklyBreakdownButtonAction(ActionEvent event) throws IOException {
        psh.switcher(event, "WeeklyGraphsView.fxml");
    }
//    handleWeeklyBreakdownButtonAction

    @FXML
    private void handleDailyBreakdownButtonAction(ActionEvent event) throws IOException {
        psh.switcher(event, "TimeGraphsView.fxml");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
//        Label copyright = new Label();
        copyright.setText("Copyright © 2019. We, the developers of this software application, \ndeclare that it is our work towards an assessment item \nsubmitted to fulfil the requirements of INFS2605 \n(UNSW Sydney). We declare that it is our own work, except \nwhere acknowledged, and has not been submitted for \nacademic credit elsewhere. We acknowledge that the \nassessor of this item may, for the purpose of assessing this item: \nReproduce this assessment item and provide a copy to another member of the \nUniversity; and/or, Communicate a copy of this assessment item to a \nplagiarism checking service (which may then retain a copy of \nthe assessment item on its database for the purpose of future \nplagiarism checking). We certify that we have read and understood \nthe UNSW University Rules in respect of Student \nAcademic Misconduct. \n We fully accredit the UNSW INFS2605 faculty for the PageSwitchHelper class from the Indiefy Music application.\n The SQL JAR file is a 3rd party library essential to the operation of this application");
        
//        Label title = new Label();
        title.setText("About");
        
//        Label UUID = new Label();
        UUID.setText("Submission UUID: 3E89080F-0107-4FA3-9B90-B30F990356D1");
        
    }    
    
}
