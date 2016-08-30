/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.view;

//import java.awt.Insets;
import java.io.IOException;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;

import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import timetable.handler.QueryHandler;
import timetable.controller.Controller;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author SamiQureshi
 */
public class LoginView extends Application {

    Controller controller;
    QueryHandler queryHandler;
    ArrayList<String> list;
    String courseCode, courseRoom;
    ArrayList<ArrayList<String>> lists;
    ArrayList<Label> scheduledTimeslots;
    Button loginBtn, getConflictsBtn;
    HBox hbLoginBtn, hbCourseBtn, hbResultTitle;
    Stage theStage;
    GridPane loginGrid, homeGrid;
    Text welcomeTitle;
    Label usrLabel, pwdLabel, cnoLabel, courseInfoTitle, courseInfoRoom, courseInfoTimings;
    Label timeslot1, timeslot2, timeslot3, timeslot4, timeslot5, timeslot6, timeslot7, timeslot8;
    Label monday, tuesday, wednesday, thursday, friday;
    TextField userTextField, courseTextField;
    ObservableList<String> courseList;
    ComboBox courseMenu;

    PasswordField pwBox;
    Scene loginScene, homeScene;

    @Override
    public void start(Stage primaryStage) throws SQLException, ClassNotFoundException {
        //Primary Stage
        theStage = primaryStage;
        primaryStage.setTitle("Academic Schedule Manager");
        queryHandler = new QueryHandler();

        //Login Screen Setup        
        loginGrid = new GridPane();
        loginGrid.setAlignment(Pos.CENTER);
        loginGrid.setHgap(10);
        loginGrid.setVgap(10);
        loginGrid.setPadding(new Insets(25, 25, 25, 25));
        welcomeTitle = new Text("Welcome to ASM");
        welcomeTitle.setId("welcome-text");
        loginGrid.add(welcomeTitle, 0, 0, 2, 1);
        usrLabel = new Label("User Name:");
        loginGrid.add(usrLabel, 0, 1);
        userTextField = new TextField();
        loginGrid.add(userTextField, 1, 1);
        pwdLabel = new Label("Password:");
        loginGrid.add(pwdLabel, 0, 2);
        pwBox = new PasswordField();
        loginGrid.add(pwBox, 1, 2);
        loginBtn = new Button("Sign in");
        hbLoginBtn = new HBox(10);
        hbLoginBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbLoginBtn.getChildren().add(loginBtn);
        loginGrid.add(hbLoginBtn, 1, 4);
        loginBtn.setOnAction(e -> {
            try {
                ButtonClicked(e);
            } catch (SQLException | ClassNotFoundException | IOException ex) {
                Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        loginScene = new Scene(loginGrid, 600, 500);

        //Home Screen Setup
        homeGrid = new GridPane();
//        homeGrid.gridLinesVisibleProperty().set(true);
        homeGrid.setHgap(10);
        homeGrid.setVgap(10);
        homeGrid.setPadding(new Insets(10, 10, 10, 10));
        cnoLabel = new Label("Select Course:");
        homeGrid.add(cnoLabel, 0, 1);
        list = queryHandler.getCourseList();
        courseList = FXCollections.observableArrayList(list);
        courseMenu = new ComboBox(courseList);
        homeGrid.add(courseMenu, 1, 1);
        getConflictsBtn = new Button("Get Conflicts");
        hbCourseBtn = new HBox(10);
        hbCourseBtn.setAlignment(Pos.BASELINE_LEFT);
        hbCourseBtn.getChildren().add(cnoLabel);
        hbCourseBtn.getChildren().add(courseMenu);
        hbCourseBtn.getChildren().add(getConflictsBtn);
        homeGrid.add(hbCourseBtn, 2, 1, 16, 1);
        getConflictsBtn.setOnAction(e -> {
            try {
                ButtonClicked(e);
            } catch (SQLException | ClassNotFoundException | IOException ex) {
                Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //Top Separator
        final Separator topSep = new Separator();
        topSep.setValignment(VPos.CENTER);
        homeGrid.setConstraints(topSep, 1, 6);
        homeGrid.setColumnSpan(topSep, 17);
        homeGrid.getChildren().add(topSep);

        //Horizontal Separator 1
        final Separator hSep1 = new Separator();
        hSep1.setValignment(VPos.CENTER);
        homeGrid.setConstraints(hSep1, 1, 8);
        homeGrid.setColumnSpan(hSep1, 17);
        homeGrid.getChildren().add(hSep1);
        //Horizontal Separator 2
        final Separator hSep2 = new Separator();
        hSep2.setValignment(VPos.CENTER);
        homeGrid.setConstraints(hSep2, 1, 10);
        homeGrid.setColumnSpan(hSep2, 17);
        homeGrid.getChildren().add(hSep2);
        //Horizontal Separator 3
        final Separator hSep3 = new Separator();
        hSep3.setValignment(VPos.CENTER);
        homeGrid.setConstraints(hSep3, 1, 12);
        homeGrid.setColumnSpan(hSep3, 17);
        homeGrid.getChildren().add(hSep3);
        //Horizontal Separator 4
        final Separator hSep4 = new Separator();
        hSep4.setValignment(VPos.CENTER);
        homeGrid.setConstraints(hSep4, 1, 14);
        homeGrid.setColumnSpan(hSep4, 17);
        homeGrid.getChildren().add(hSep4);

        //Bottom Separator
        final Separator bottomSep = new Separator();
        bottomSep.setValignment(VPos.CENTER);
        homeGrid.setConstraints(bottomSep, 1, 16);
        homeGrid.setColumnSpan(bottomSep, 17);
        homeGrid.getChildren().add(bottomSep);

        //Left Separator
        final Separator LeftSep = new Separator();
        LeftSep.setOrientation(Orientation.VERTICAL);
        LeftSep.setHalignment(HPos.CENTER);
        homeGrid.setConstraints(LeftSep, 1, 6);
        homeGrid.setRowSpan(LeftSep, 11);
        homeGrid.getChildren().add(LeftSep);

        //Vertical Seperator 1
        final Separator vSep1 = new Separator();
        vSep1.setOrientation(Orientation.VERTICAL);
        vSep1.setHalignment(HPos.CENTER);
        homeGrid.setConstraints(vSep1, 3, 6);
        homeGrid.setRowSpan(vSep1, 11);
        homeGrid.getChildren().add(vSep1);
        //Vertical Seperator 2
        final Separator vSep2 = new Separator();
        vSep2.setOrientation(Orientation.VERTICAL);
        vSep2.setHalignment(HPos.CENTER);
        homeGrid.setConstraints(vSep2, 5, 6);
        homeGrid.setRowSpan(vSep2, 11);
        homeGrid.getChildren().add(vSep2);
        //Vertical Seperator 3
        final Separator vSep3 = new Separator();
        vSep3.setOrientation(Orientation.VERTICAL);
        vSep3.setHalignment(HPos.CENTER);
        homeGrid.setConstraints(vSep3, 7, 6);
        homeGrid.setRowSpan(vSep3, 11);
        homeGrid.getChildren().add(vSep3);
        //Vertical Seperator 4
        final Separator vSep4 = new Separator();
        vSep4.setOrientation(Orientation.VERTICAL);
        vSep4.setHalignment(HPos.CENTER);
        homeGrid.setConstraints(vSep4, 9, 6);
        homeGrid.setRowSpan(vSep4, 11);
        homeGrid.getChildren().add(vSep4);
        //Vertical Seperator 5
        final Separator vSep5 = new Separator();
        vSep5.setOrientation(Orientation.VERTICAL);
        vSep5.setHalignment(HPos.CENTER);
        homeGrid.setConstraints(vSep5, 11, 6);
        homeGrid.setRowSpan(vSep5, 11);
        homeGrid.getChildren().add(vSep5);
        //Vertical Seperator 6
        final Separator vSep6 = new Separator();
        vSep6.setOrientation(Orientation.VERTICAL);
        vSep6.setHalignment(HPos.CENTER);
        homeGrid.setConstraints(vSep6, 13, 6);
        homeGrid.setRowSpan(vSep6, 11);
        homeGrid.getChildren().add(vSep6);
        //Vertical Seperator 7
        final Separator vSep7 = new Separator();
        vSep7.setOrientation(Orientation.VERTICAL);
        vSep7.setHalignment(HPos.CENTER);
        homeGrid.setConstraints(vSep7, 15, 6);
        homeGrid.setRowSpan(vSep7, 11);
        homeGrid.getChildren().add(vSep7);
        //Right Separator
        final Separator rightSep = new Separator();
        rightSep.setOrientation(Orientation.VERTICAL);
        rightSep.setHalignment(HPos.CENTER);
        homeGrid.setConstraints(rightSep, 17, 6);
        homeGrid.setRowSpan(rightSep, 11);
        homeGrid.getChildren().add(rightSep);

        timeslot1 = new Label("9:00 - 10:00");
        timeslot2 = new Label("10:00 - 11:00");
        timeslot3 = new Label("11:00 - 12:00");
        timeslot4 = new Label("12:00 - 1:00");
        timeslot5 = new Label("1:00 - 2:00");
        timeslot6 = new Label("2:00 - 3:00");
        timeslot7 = new Label("3:00 - 4:00");
        timeslot8 = new Label("4:00 - 5:00");
        monday = new Label("Monday");
        tuesday = new Label("Tuesday");
        wednesday = new Label("Wednesday");
        thursday = new Label("Thursday");
        friday = new Label("Friday");
        homeGrid.add(timeslot1, 2, 5);
        homeGrid.add(timeslot2, 4, 5);
        homeGrid.add(timeslot3, 6, 5);
        homeGrid.add(timeslot4, 8, 5);
        homeGrid.add(timeslot5, 10, 5);
        homeGrid.add(timeslot6, 12, 5);
        homeGrid.add(timeslot7, 14, 5);
        homeGrid.add(timeslot8, 16, 5);
        homeGrid.add(monday, 0, 7);
        homeGrid.add(tuesday, 0, 9);
        homeGrid.add(wednesday, 0, 11);
        homeGrid.add(thursday, 0, 13);
        homeGrid.add(friday, 0, 15);

        //Filling up the table
        scheduledTimeslots = new ArrayList<>();
        

        String temp = new String();
        lists = queryHandler.getAllClashes();
        for (int i = 0; i < 40; i++) {
            temp = "";
            list = lists.get(i);
            for (String course : list) {
                temp += course + "\n";

            }
            scheduledTimeslots.add(new Label(temp));
        }
        for (Label label : scheduledTimeslots) {
            label.setId("cell-text");
        }

        homeGrid.add(scheduledTimeslots.get(0), 2, 7);
        homeGrid.add(scheduledTimeslots.get(1), 4, 7);
        homeGrid.add(scheduledTimeslots.get(2), 6, 7);
        homeGrid.add(scheduledTimeslots.get(3), 8, 7);
        homeGrid.add(scheduledTimeslots.get(4), 10, 7);
        homeGrid.add(scheduledTimeslots.get(5), 12, 7);
        homeGrid.add(scheduledTimeslots.get(6), 14, 7);
        homeGrid.add(scheduledTimeslots.get(7), 16, 7);

        homeGrid.add(scheduledTimeslots.get(8), 2, 9);
        homeGrid.add(scheduledTimeslots.get(9), 4, 9);
        homeGrid.add(scheduledTimeslots.get(10), 6, 9);
        homeGrid.add(scheduledTimeslots.get(11), 8, 9);
        homeGrid.add(scheduledTimeslots.get(12), 10, 9);
        homeGrid.add(scheduledTimeslots.get(13), 12, 9);
        homeGrid.add(scheduledTimeslots.get(14), 14, 9);
        homeGrid.add(scheduledTimeslots.get(15), 16, 9);

        homeGrid.add(scheduledTimeslots.get(16), 2, 11);
        homeGrid.add(scheduledTimeslots.get(17), 4, 11);
        homeGrid.add(scheduledTimeslots.get(18), 6, 11);
        homeGrid.add(scheduledTimeslots.get(19), 8, 11);
        homeGrid.add(scheduledTimeslots.get(20), 10, 11);
        homeGrid.add(scheduledTimeslots.get(21), 12, 11);
        homeGrid.add(scheduledTimeslots.get(22), 14, 11);
        homeGrid.add(scheduledTimeslots.get(23), 16, 11);

        homeGrid.add(scheduledTimeslots.get(24), 2, 13);
        homeGrid.add(scheduledTimeslots.get(25), 4, 13);
        homeGrid.add(scheduledTimeslots.get(26), 6, 13);
        homeGrid.add(scheduledTimeslots.get(27), 8, 13);
        homeGrid.add(scheduledTimeslots.get(28), 10, 13);
        homeGrid.add(scheduledTimeslots.get(29), 12, 13);
        homeGrid.add(scheduledTimeslots.get(30), 14, 13);
        homeGrid.add(scheduledTimeslots.get(31), 16, 13);

        homeGrid.add(scheduledTimeslots.get(32), 2, 15);
        homeGrid.add(scheduledTimeslots.get(33), 4, 15);
        homeGrid.add(scheduledTimeslots.get(34), 6, 15);
        homeGrid.add(scheduledTimeslots.get(35), 8, 15);
        homeGrid.add(scheduledTimeslots.get(36), 10, 15);
        homeGrid.add(scheduledTimeslots.get(37), 12, 15);
        homeGrid.add(scheduledTimeslots.get(38), 14, 15);
        homeGrid.add(scheduledTimeslots.get(39), 16, 15);

        homeScene = new Scene(homeGrid, 1000, 600);

        primaryStage.setScene(loginScene);
        loginScene.getStylesheets().add(LoginView.class.getResource("LoginView.css").toExternalForm());
        homeScene.getStylesheets().add(LoginView.class.getResource("LoginView.css").toExternalForm());
        primaryStage.show();

    }

    public void ButtonClicked(ActionEvent e) throws SQLException, ClassNotFoundException, IOException {
        if (e.getSource() == loginBtn) {
            controller = new Controller();
            ArrayList<Integer> actionCode = new ArrayList();
            actionCode.add(0);
            actionCode.add(0);
            actionCode.add(0);
            if (controller.handleRequest(actionCode)) {
                theStage.setScene(homeScene);
            }

        } else if (e.getSource() == getConflictsBtn) {
            homeGrid.getChildren().remove(courseInfoTitle);
            homeGrid.getChildren().remove(courseInfoRoom);
//            hbResultTitle = new HBox();
            if (courseMenu.getValue() == null) {
                courseInfoTitle = new Label("Please choose a course.");
            } else {
                courseInfoTitle = new Label(courseMenu.getValue().toString());
                courseCode = courseMenu.getValue().toString().split(" ")[0];
                courseRoom = queryHandler.getRoom(courseCode);
                courseInfoRoom = new Label("Room: " + courseRoom);
                
                
                courseInfoTitle.setId("course-info-title");
                courseInfoRoom.setId("course-info-room");
//            hbResultTitle.setAlignment(Pos.CENTER);
//            hbResultTitle.getChildren().add(courseInfoTitle);
                homeGrid.add(courseInfoTitle, 18, 7);
                homeGrid.add(courseInfoRoom, 18, 8);

            }

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
