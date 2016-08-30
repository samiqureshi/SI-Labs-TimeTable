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
public class QueryHandler extends Handler {

    //Get List of 40 Lists showing overall scheduled courses of the week
    public ArrayList<ArrayList<String>> getAllClashes() throws SQLException, ClassNotFoundException {
        dbReader = new DBReader();
        return dbReader.getAllClashes();
    }

    //Get List of all courses having enrolment conflict AND timing clash with given course
    public ArrayList<String> getCourseConflicts(int cno) throws SQLException, ClassNotFoundException {
        dbReader = new DBReader();
        return dbReader.getCourseConflicts(cno);
    }
    //Get Courses List
    public ArrayList<String> getCourseList() throws SQLException, ClassNotFoundException {
        dbReader = new DBReader();
        return dbReader.getCourseList();
    }

    //Get list of all timeslots that have courses with enrolment conflict
    public ArrayList<String> getConflictedTimeslots() throws SQLException, ClassNotFoundException {
        dbReader = new DBReader();
        ArrayList<Integer> conflicts = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();
        for (int i = 1; i <= 40; i++) {
            try {
                if (dbReader.isTimeslotConflicted(i)) {
                    conflicts.add(i);
                }
            } catch (SQLException ex) {
                Logger.getLogger(QueryHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        for(Integer i : conflicts){
            result.add(dbReader.getTimeslot(i));
        }

        return result;
    }

    public String getRoom(String courseCode) throws SQLException, ClassNotFoundException{
        dbReader = new DBReader();
        int cno = dbReader.getCourseNo(courseCode);
        int roomNo = dbReader.getCourseRoomNo(cno);
        return dbReader.getRoom(roomNo);
    }
}
