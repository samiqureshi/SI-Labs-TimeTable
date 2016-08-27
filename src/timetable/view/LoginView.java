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
    Button loginBtn, courseBtn;
    HBox hbLoginBtn, hbCourseBtn;
    Stage theStage;
    GridPane loginGrid, homeGrid;
    Text welcomeTitle;
    Label usrLabel, pwdLabel, cnoLabel, courseResultLabel;
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
        homeGrid.setPadding(new Insets(25, 25, 25, 25));
        cnoLabel = new Label("Select Course:");
        homeGrid.add(cnoLabel, 0, 1);
        list = queryHandler.getCourseList();
        courseList = FXCollections.observableArrayList(list);
        courseMenu = new ComboBox(courseList);
        homeGrid.add(courseMenu, 1, 1);
        courseBtn = new Button("Get Conflicts");
        hbCourseBtn = new HBox(10);
        hbCourseBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbCourseBtn.getChildren().add(cnoLabel);
        hbCourseBtn.getChildren().add(courseMenu);
        hbCourseBtn.getChildren().add(courseBtn);
        homeGrid.add(hbCourseBtn, 2, 1, 13, 1);
        courseBtn.setOnAction(e -> {
            try {
                ButtonClicked(e);
            } catch (SQLException | ClassNotFoundException | IOException ex) {
                Logger.getLogger(LoginView.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        //Top Separator
        final Separator sepTop = new Separator();
        sepTop.setValignment(VPos.CENTER);
        homeGrid.setConstraints(sepTop, 1, 6);
        homeGrid.setColumnSpan(sepTop, 17);
        homeGrid.getChildren().add(sepTop);
                       
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
        final Separator sepBottom = new Separator();
        sepBottom.setValignment(VPos.CENTER);
        homeGrid.setConstraints(sepBottom, 1, 16);
        homeGrid.setColumnSpan(sepBottom, 17);
        homeGrid.getChildren().add(sepBottom);
        
        //Left Separator
        final Separator sepLeft = new Separator();
        sepLeft.setOrientation(Orientation.VERTICAL);
        sepLeft.setHalignment(HPos.CENTER);
        homeGrid.setConstraints(sepLeft, 1, 6);
        homeGrid.setRowSpan(sepLeft, 11);
        homeGrid.getChildren().add(sepLeft);        
        
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
        final Separator sepRight = new Separator();
        sepRight.setOrientation(Orientation.VERTICAL);
        sepRight.setHalignment(HPos.CENTER);
        homeGrid.setConstraints(sepRight, 17, 6);
        homeGrid.setRowSpan(sepRight, 11);
        homeGrid.getChildren().add(sepRight);
        
        

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

        } else if (e.getSource() == courseBtn) {
            homeGrid.getChildren().remove(courseResultLabel);
            if (courseMenu.getValue() == null) {
                courseResultLabel = new Label("Please choose a course.");
            } else {
                courseResultLabel = new Label(courseMenu.getValue().toString());
            }
            homeGrid.add(courseResultLabel, 3, 4);

        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
