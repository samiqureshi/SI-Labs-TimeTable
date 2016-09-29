/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.test;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import timetable.controller.Controller;
import timetable.handler.Handler;
import timetable.handler.QueryHandler;

/**
 *
 * @author SamiQureshi
 */
public class QueryTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException{
        Controller controller = new Controller();

        ArrayList<Integer> actionCode = new ArrayList<>();
        actionCode.add(3);
        actionCode.add(43);
        actionCode.add(0);
        controller.handleRequest(actionCode);
    }
}
