///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package timetable.dal;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.sql.Statement;
//import java.util.ArrayList;
//import java.util.List;
//import static timetable.utility.Constants.CLASSROOMS;
//
///**
// *
// * @author Qureshi
// */
//public class DataBaseReader {
//
//    Connection conn;
//
//    public DataBaseReader() throws SQLException {
//        this.conn = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/Qureshi/Documents/SI Labs/SI-Labs-TimeTable/SILABS_DB.accdb");
//    }
//
//    public boolean queryTest() throws SQLException {
//        Statement s = conn.createStatement();
//        ResultSet rs = s.executeQuery("SELECT [Time] FROM [TIMESLOT] WHERE Day like 'Wednesday'");
//        while (rs.next()) {
//            System.out.println(rs.getString(1));
//
//        }
//        return true;
//    }
//
//    public ArrayList<ArrayList<String>> queryAllClashes() throws SQLException {
//        Statement s = conn.createStatement();
//        
//        ArrayList<ArrayList<String>> results = new ArrayList<>();
//
//        for (int i = 1; i <= 40; i++) {
//            ArrayList<String> tempResult = new ArrayList<>();
//            ResultSet rs = s.executeQuery("SELECT c.CCode, c.CName, r.RoomName "
//                    + "FROM COURSE_TIMESLOT ct "
//                    + "JOIN COURSE c ON ct.CNo_FK = c.CNo "
//                    + "JOIN ROOM r ON ct.RoomNo_FK = r.RoomNo "
//                    + "WHERE ct.TSNo_FK = " + i);
//            while (rs.next()) {
//                
//                String temp;
//                temp = rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3);
//                tempResult.add(temp);
//            }
//
//            results.add(tempResult);
//        }
//        return results;
//    }
//
//
//    //Return list of courses scheduled at the given timeslot
//    public ArrayList<String> queryTimeSlotClashes(int tsno) throws SQLException {
//        Statement s = conn.createStatement();
//        ArrayList<String> result = new ArrayList<>();
//
//        ResultSet rs = s.executeQuery("SELECT c.CNo, c.CCode, c.CName, r.RoomName "
//                + "FROM COURSE_TIMESLOT ct "
//                + "JOIN COURSE c ON ct.CNo_FK = c.CNo "
//                + "JOIN ROOM r ON ct.RoomNo_FK = r.RoomNo "
//                + "WHERE ct.TSNo_FK = " + tsno);
//        while (rs.next()) {
//            String temp;
//            temp = rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3);
//            result.add(temp);
//        }
//        return result;
//    }
//
//    //Return list of 
////    public ArrayList<Integer> queryStudentCourseClashes(String sid, int cno) throws SQLException {
////        Statement s = conn.createStatement();
////        ArrayList<Integer> results = new ArrayList<>();
////
////        String tempQuery = "";
//////        String tempQuery = "SELECT CTS.TSNo_FK FROM COURSE_TIMESLOT CTS WHERE CTS.CNo_FK = "+ cno +" AND CTS.CNo_FK IN (SELECT SC.CNo_FK  "
//////                + "FROM STUDENT_COURSE SC  "
//////                + "WHERE SID_FK = ' " + sid + "')";
////        ResultSet rs = s.executeQuery(tempQuery);
////        while (rs.next()) {
////            results.add(Integer.parseInt(rs.getString(1)));
////        }
////        return results;
////    }
//    
//    //Return list of Course Numbers the given student is enrolled in
//    public ArrayList<Integer> queryStudentCourses(String sid) throws SQLException {
//        Statement s = conn.createStatement();
//
//        ArrayList<Integer> result = new ArrayList<>();
//        String tempQuery = "SELECT SC.CNo_FK FROM STUDENT_COURSE SC WHERE SC.SID_FK = '" + sid + "' ";
////        String tempQuery = "SELECT CTS.TSNo_FK FROM COURSE_TIMESLOT CTS WHERE CTS.CNo_FK = "+ cno +" AND CTS.CNo_FK IN (SELECT SC.CNo_FK  "
////                + "FROM STUDENT_COURSE SC  "
////                + "WHERE SID_FK = ' " + sid + "')";
//        ResultSet rs = s.executeQuery(tempQuery);
//        while (rs.next()) {
//            result.add(Integer.parseInt(rs.getString(1)));
//        }
//        return result;
//    }
//    
//    //Return list of Time Slots the given course is scheduled at
//    public ArrayList<Integer> queryCourseTimeSlots(int cno) throws SQLException {
//        Statement s = conn.createStatement();
//        ArrayList<Integer> result = new ArrayList<>();
//        String tempQuery = "SELECT CTS.TSNo_FK FROM COURSE_TIMESLOT CTS WHERE CTS.CNo_FK = " + cno + ";";
//        ResultSet rs = s.executeQuery(tempQuery);
//        while (rs.next()) {
//            result.add(Integer.parseInt(rs.getString(1)));
//        }
//        return result;
//    }
//    
//    //Convert Course Code (CS206) into Course Number (45) entered in the database
//    public int queryCourseNo(String ccode) throws SQLException {
//        int cno = 0;
//        Statement s = conn.createStatement();
//        ResultSet rs = s.executeQuery("Select CNo from COURSE WHERE CCode = '" + ccode + "'");
//        if(rs.next()){
//            cno = Integer.parseInt(rs.getString(1));
//        }
//        return cno;
//    }
//    
//    //Convert Course Number into Course Code
//    public String queryCourseCode(int cno) throws SQLException {
//        String ccode = null;
//        Statement s = conn.createStatement();
//        ResultSet rs = s.executeQuery("Select CCode from COURSE WHERE CNo = " + cno + ";");
//        if(rs.next()){
//            ccode = (rs.getString(1));
//        }
//        return ccode;
//    }
//    
//    //Return list of Student IDs enrolled in the given course
//    public ArrayList<String> queryCourseEnrolments(int cno) throws SQLException {
//        Statement s = conn.createStatement();
//        ArrayList<String> result = new ArrayList<>();
//        ResultSet rs = s.executeQuery("SELECT SID_FK FROM STUDENT_COURSE WHERE CNo_FK = " + cno + ";");
//        while(rs.next()){
//            result.add(rs.getString(1));
//        }
//        return result;
//    }
//
//}
