/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.handler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import timetable.dal.DBReader;

/**
 *
 * @author SamiQureshi
 */
public class QueryHandler extends Handler{
    public boolean handleRequest(ArrayList<Integer> actionCode) {
        try {
            dbReader = new DBReader();
        } catch (SQLException ex) {
            Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        switch (actionCode.get(0)) {
            case 1: {
                ArrayList<ArrayList<String>> temp;
                try {
                    //Get List of clashing courses for each of 40 timeslots i.e. List of Lists
                    temp = dbReader.getAllClashes();
                } catch (SQLException ex) {
                    Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case 2: {  //Get List of Students enrolled in a course
                ArrayList<String> temp;

                try {
                    temp = dbReader.getCourseEnrolments(actionCode.get(1));
                } catch (SQLException ex) {
                    Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;

            case 3:
                break;
          
                
            default:
                return false;
        }
        return true;
    }
}
