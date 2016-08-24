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
    public ViewHandler viewHandler = new ViewHandler();
    @Override
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

            case 2: {  //Get Course Clashes i.e. list of courses having timing clashes with given course
                ArrayList<Integer> temp = new ArrayList<>();
                
                try {
                    temp = dbReader.getCourseClashes(actionCode.get(1));
                } catch (SQLException ex) {
                    Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            break;

            case 3: {   //Get List of courses having enrolment conflicts (also timing clash) with the given course
                ArrayList<String> temp = new ArrayList<>();
                
                try {
                    temp = dbReader.getCourseConflicts(actionCode.get(1));
                } catch (SQLException ex) {
                    Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
                break;
                
            case 4: {   //Get list of timeslots that have conflict
                ArrayList<Integer> temp = new ArrayList<>();
                for(int i=1; i<=40; i++){
                    try {
                        if(dbReader.isTimeslotConflicted(i)){
                            temp.add(i);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
            }

            break;

            case 5:  {  //Is timeslot conflicted?
                boolean temp = false;
                try {
                    temp = dbReader.isTimeslotConflicted(actionCode.get(1));
                } catch (SQLException ex) {
                    Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;


            default:
                return false;
        }
        return true;
    }
}
