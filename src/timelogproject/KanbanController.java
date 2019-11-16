/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timelogproject;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.paint.Color;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import java.io.*;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.event.ChangeListener;
import timelogproject.TimeLogApplication;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import java.io.*;
import java.util.Optional;
import java.util.Scanner;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import java.io.*;
import java.util.Optional;
import java.util.Scanner;
import timelogproject.PageSwitchHelper;

public class KanbanController implements Initializable {

    private final String URL = "jdbc:sqlite:timelog.db";
    private Connection connection;
    private final DataFormat buttonFormat = new DataFormat(" ");
    PageSwitchHelper psh = new PageSwitchHelper();

    @FXML
    private FlowPane fpane, spane, tpane, fopane;
    @FXML
    private Button dragBtn;
    @FXML
    private Label dateToday;
    @FXML
    private Separator fSep;
    @FXML
    private Separator sSep;
    @FXML
    private Separator tSep;
    @FXML
    private Separator hSep;
    @FXML
    private Button timelog, dfBtn, mlBtn, wbdBtn, dBdBtn;

    public ArrayList<String> getCategories(String query) throws SQLException {

        // create new ArrayList of Strings
        ArrayList<String> catList = new ArrayList<>();

        Connection connection = DriverManager.getConnection(URL);

        //create statement
        Statement st = connection.createStatement();

        System.out.println("** get categories from database **");
        ResultSet rs = st.executeQuery(query);

        // Iterate over result set from query
        while (rs.next()) {

            // Get individual attributes from each row
            String category = rs.getString("label");

            // Populate new category with attributes and add to event array
            catList.add(category);
        }

        //close connection 
        st.close();
        connection.close();

        // return array of events
        return catList;
    }

//    putting away w/ TextArea as it is not consistent with the thing that we need
//    public TextArea initTA(String categories, String date) {
//        String details = categories + "\n" + date;
//        TextArea ta = new TextArea(details);
//        return ta;
//    }
    public Button initBtn(String categories, String date) {

        String details = categories + "\n" + date;
        Button newButton = new Button(details);
        newButton.setMinWidth(120);
        newButton.setOnDragDetected(e -> {
            Dragboard db = newButton.startDragAndDrop(TransferMode.MOVE);
            db.setDragView(newButton.snapshot(null, null));
            ClipboardContent cc = new ClipboardContent();
            cc.put(buttonFormat, "button");
            db.setContent(cc);
            dragBtn = newButton;
        });
        newButton.setOnDragDone(e -> dragBtn = null);

        //Drag work starts here:
//        dragBtn.setOnDragDetected(new EventHandler<MouseEvent>() {
//            public void handle(MouseEvent event) {
//                System.out.println("onDragDetected");
//                Dragboard db = dragBtn.startDragAndDrop(TransferMode.ANY);
//                ClipboardContent content = new ClipboardContent();
//                content.putString(dragBtn.getText());
//                db.setContent(content);
//
//                event.consume();
//            }
//        }
//        );
//
//        fpane.addEventHandler(DragEvent.DRAG_OVER, (DragEvent event) -> {
//            if (event.getGestureSource() != fpane
//                    && event.getDragboard().hasString()) {
//                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//            }
//            event.consume();
//        });
//
//        spane.addEventHandler(DragEvent.DRAG_OVER, (DragEvent event) -> {
//            if (event.getGestureSource() != spane
//                    && event.getDragboard().hasString()) {
//                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//            }
//            event.consume();
//        });
//        tpane.addEventHandler(DragEvent.DRAG_OVER, (DragEvent event) -> {
//            if (event.getGestureSource() != tpane
//                    && event.getDragboard().hasString()) {
//                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//            }
//            event.consume();
//        });
//        fopane.addEventHandler(DragEvent.DRAG_OVER, (DragEvent event) -> {
//            if (event.getGestureSource() != fopane
//                    && event.getDragboard().hasString()) {
//                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
//            }
//            event.consume();
//        });
//        fpane.addEventHandler(DragEvent.DRAG_DROPPED, (DragEvent event) -> {
//            //Get the dragboard back
//            Dragboard db = event.getDragboard();
//            boolean success = false;
//            //Could have some more thorough checks of course.
//            if (db.hasString()) {
//                //Get the textarea and place it into flowPane2 instead
//                fpane.getChildren().add(dragBtn);
//                success = true;
//            }
//            //Complete and consume the event.
//            event.setDropCompleted(success);
//            event.consume();
//        });
//        spane.addEventHandler(DragEvent.DRAG_DROPPED, (DragEvent event) -> {
//            //Get the dragboard back
//            Dragboard db = event.getDragboard();
//            boolean success = false;
//            //Could have some more thorough checks of course.
//            if (db.hasString()) {
//                //Get the textarea and place it into flowPane2 instead
//                spane.getChildren().add(dragBtn);
//                success = true;
//            }
//            //Complete and consume the event.
//            event.setDropCompleted(success);
//            event.consume();
//        });
//        tpane.addEventHandler(DragEvent.DRAG_DROPPED, (DragEvent event) -> {
//            //Get the dragboard back
//            Dragboard db = event.getDragboard();
//            boolean success = false;
//            //Could have some more thorough checks of course.
//            if (db.hasString()) {
//                //Get the textarea and place it into flowPane2 instead
//                tpane.getChildren().add(dragBtn);
//                success = true;
//            }
//            //Complete and consume the event.
//            event.setDropCompleted(success);
//            event.consume();
//        });
//        fopane.addEventHandler(DragEvent.DRAG_DROPPED, (DragEvent event) -> {
//            //Get the dragboard back
//            Dragboard db = event.getDragboard();
//            boolean success = false;
//            //Could have some more thorough checks of course.
//            if (db.hasString()) {
//                //Get the textarea and place it into flowPane2 instead
//                fopane.getChildren().add(dragBtn);
//                success = true;
//            }
//            //Complete and consume the event.
//            event.setDropCompleted(success);
//            event.consume();
//        });
        //Drag work ends here
        return newButton;

    }

    @FXML
    private void handleTLBtnAction(ActionEvent event) throws IOException {
        psh.switcher(event, "TimeLogView.fxml");
    }

    @FXML
    private void handleDFBtnAction(ActionEvent event) throws IOException {
        psh.switcher(event, "DeepFocus.fxml");
    }

    @FXML
    private void handleMyLifetnAction(ActionEvent event) throws IOException {
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
        @FXML
    private void handleHelpButtonAction(ActionEvent event) throws IOException {
        psh.switcher(event, "AboutScreen.fxml");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Initialize all panes to handle dragging
        paneDragging(fpane);
        paneDragging(spane);
        paneDragging(tpane);
        paneDragging(fopane);

        // TODO
        //Sample: to instantiate an object of the button
//        fopane.getChildren().add(initBtn("hj", "56"));
//        Sample: testing initBtn's date comparison:
//        initBtn("date from youtube video", "2020-02-03");
//        initBtn("date yesterday", "2019-11-12");
//        initBtn("date tomorrow", "2019-11-14");
//        initBtn("day following tomorrow", "2019-11-15");
        //to connect to the DB
        try {
            String query = "SELECT task_title, dueDate, doDate FROM Tasks ORDER BY dueDate ASC";
//            Testing:
//            String query = "SELECT task_title, dueDate FROM Tasks WHERE dueDate ='" + "2019-11-16" + "'";
            connection = DriverManager.getConnection("jdbc:sqlite:timelog.db");
            PreparedStatement prep = connection.prepareStatement(query);
            ResultSet results = prep.executeQuery();
            String rs;
            while (results.next()) {
                System.out.println(results.getString(1) + results.getString(2));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate convdDate = LocalDate.parse(results.getString(2), formatter);
                LocalDate convdTodayDate = LocalDate.parse(date(), formatter);
                int comparison = convdDate.compareTo(convdTodayDate);

                switch (comparison) {
                    case 0:
                        System.out.println("today" + ": " + comparison + " " + convdDate + " " + results.getString(1));
                        spane.getChildren().add(initBtn(results.getString(1), results.getString(2)));

                        break;
                    case 1:
                        System.out.println("tomorrow" + ": " + comparison + " " + convdDate + " " + results.getString(1));
                        tpane.getChildren().add(initBtn(results.getString(1), results.getString(2)));

                        break;
                    case 2:
                        System.out.println("day after tomorrow" + ": " + comparison + " " + convdDate + " " + results.getString(1));
                        fopane.getChildren().add(initBtn(results.getString(1), results.getString(2)));

                        break;
                    case 3:
                        System.out.println("day following tomorrow" + ": " + comparison + " " + convdDate + " " + results.getString(1));
                        fopane.getChildren().add(initBtn(results.getString(1), results.getString(2)));

                        break;
                    case 4:
                        System.out.println("day following tomorrow" + ": " + comparison + " " + convdDate + " " + results.getString(1));
                        fopane.getChildren().add(initBtn(results.getString(1), results.getString(2)));

                        break;
                    case 5:
                        System.out.println("day following tomorrow" + ": " + comparison + " " + convdDate + " " + results.getString(1));
                        fopane.getChildren().add(initBtn(results.getString(1), results.getString(2)));

                        break;
                    case 6:
                        System.out.println("day following tomorrow" + ": " + comparison + " " + convdDate + " " + results.getString(1));
                        fopane.getChildren().add(initBtn(results.getString(1), results.getString(2)));

                        break;
                    case 7:
                        System.out.println("day following tomorrow" + ": " + comparison + " " + convdDate + " " + results.getString(1));
                        fopane.getChildren().add(initBtn(results.getString(1), results.getString(2)));

                        break;
                    case 8:
                        System.out.println("day following tomorrow" + ": " + comparison + " " + convdDate + " " + results.getString(1));
                        fopane.getChildren().add(initBtn(results.getString(1), results.getString(2)));

                        break;

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String date() {
        LocalDateTime today;
        today = LocalDateTime.now();
        String fToday = today.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        //System.out.println(fToday + " from the method");
//        Today's date displayed on the label top-left
        dateToday.setText(today.format(DateTimeFormatter.ofPattern("dd MMM yyyy")));
        return fToday;
    }

    private void paneDragging(Pane pane) {
        pane.setOnDragOver(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(buttonFormat) && dragBtn != null && dragBtn.getParent() != pane) {
                e.acceptTransferModes(TransferMode.MOVE);
            }
        });

        // event handler for when items dragged to pane
        pane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasContent(buttonFormat)) {
                ((Pane) dragBtn.getParent()).getChildren().remove(dragBtn);
                pane.getChildren().add(dragBtn);
                e.setDropCompleted(true);

            }
        });
    }

}
