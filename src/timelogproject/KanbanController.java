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

public class KanbanController implements Initializable {

    private final String URL = "jdbc:sqlite:timelog.db";
    private Connection connection;
    private final DataFormat buttonFormat = new DataFormat(" ");

    @FXML
    private FlowPane fpane, spane, tpane, fopane;
    @FXML
    private Button dragRace;
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
        dragRace = newButton;
        //Drag work starts here:
        dragRace.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("onDragDetected");
                Dragboard db = dragRace.startDragAndDrop(TransferMode.ANY);
                ClipboardContent content = new ClipboardContent();
                content.putString(dragRace.getText());
                db.setContent(content);

                event.consume();
            }
        }
        );

        fpane.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragOver-firstPane");
                if (event.getGestureSource() != fpane
                        && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        }
        );
        spane.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragOver-secondPane");
                if (event.getGestureSource() != spane
                        && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        }
        );
        tpane.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragOver-thirdPane");
                if (event.getGestureSource() != tpane
                        && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });
        fopane.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragOver-fourthPane");
                if (event.getGestureSource() != fopane
                        && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        fpane.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragEntered-firstPane");
                if (event.getGestureSource() != fpane
                        && event.getDragboard().hasString()) {
                    fpane.setStyle("Color.GREEN");
                }

                event.consume();
            }
        }
        );
        spane.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragEntered-secondPane");
                if (event.getGestureSource() != spane
                        && event.getDragboard().hasString()) {
                    spane.setStyle("Color.GREEN");
                }

                event.consume();
            }
        }
        );
        tpane.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragEntered-thirdPane");
                if (event.getGestureSource() != tpane
                        && event.getDragboard().hasString()) {
                    tpane.setStyle("Color.GREEN");
                }

                event.consume();
            }
        }
        );
        fopane.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragEntered-fourthPane");
                if (event.getGestureSource() != fopane
                        && event.getDragboard().hasString()) {
                    fopane.setStyle("Color.GREEN");
                }

                event.consume();
            }
        }
        );
        fpane.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragDropped-firstPane");
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    //fpane.setText(db.getString());
                    success = true;
                }
                event.setDropCompleted(success);
                fpane.getChildren().add(dragRace);
                event.consume();

            }
        }
        );
        spane.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragDropped-secondPane");
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    //fpane.setText(db.getString());
                    success = true;
                }
                event.setDropCompleted(success);
                spane.getChildren().add(dragRace);
                event.consume();

            }
        }
        );
        tpane.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragDropped-thirdPane");
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    //fpane.setText(db.getString());
                    success = true;
                }
                event.setDropCompleted(success);
                tpane.getChildren().add(dragRace);
                event.consume();

            }
        }
        );
        fopane.setOnDragDropped(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragDropped-fourthPane");
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    //fpane.setText(db.getString());
                    success = true;
                }
                event.setDropCompleted(success);
                fopane.getChildren().add(dragRace);
                event.consume();

            }
        }
        );
        dragRace.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                System.out.println("onDragDone");
                if (event.getTransferMode() == TransferMode.MOVE) {
                    //newButton.setText("");
                }

                event.consume();
            }

        });

        //Drag work ends here
        return newButton;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
            String query = "SELECT task_title, dueDate FROM Tasks ORDER BY dueDate ASC";
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

                //fpane.getChildren().add(initBtn(results.getString(1), results.getString(2)));
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

}
