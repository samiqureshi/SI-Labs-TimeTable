/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import static timetable.utility.Constants.CLASSROOMS;

/**
 *
 * @author Qureshi
 */
public class DBReader {

    Connection conn;

    public DBReader() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        this.conn = DriverManager.getConnection("jdbc:h2:~/test", "samiqureshi", "sq3147");
    }

    public int getBatchNo(String batch) throws SQLException{
        Statement s = conn.createStatement();
        int batchNo = 0;
        ResultSet rs = s.executeQuery("SELECT BATCH_NO FROM BATCH WHERE BATCH_NAME = '" + batch + "';");
        
        if(rs.next()){
            batchNo = rs.getInt(1);
        }
        return batchNo;
    }
    
    public int getTeacherID(String teacher) throws SQLException{
        Statement s = conn.createStatement();
        int teacherID = 0;
        ResultSet rs = s.executeQuery("SELECT TEACHER_ID FROM TEACHER WHERE TEACHER_NAME = '" + teacher + "';");
        if(rs.next()){
            teacherID = rs.getInt(1);
        }
        return teacherID;
    }
    public int getStudentNo(String studentID) throws SQLException{
        Statement s = conn.createStatement();
        int studentNo = 0;
        ResultSet rs = s.executeQuery("SELECT STUDENT_NO FROM STUDENT WHERE STUDENT_ID = '" + studentID + "';");
        if(rs.next()){
            studentNo = rs.getInt(1);
        }
        return studentNo;
    }
    public int getCourseNo(String courseCode) throws SQLException{
        Statement s = conn.createStatement();
        int courseNo = 0;
        ResultSet rs = s.executeQuery("SELECT COURSE_NO FROM COURSE WHERE COURSE_CODE = '" + courseCode + "';");
        if(rs.next()){
            courseNo = rs.getInt(1);
        }
        return courseNo;
    }
    
    public String getCourseCode(int cno) throws SQLException{
        Statement s = conn.createStatement();
        String courseCode = new String();
        ResultSet rs = s.executeQuery("SELECT COURSE_CODE FROM COURSE WHERE COURSE_NO = " + cno + ";");
        if(rs.next()){
            courseCode = rs.getString(1);
        }
        return courseCode;
        
    }
    
    public int getRoomNo(String roomName) throws SQLException{
        Statement s = conn.createStatement();
        int roomNo = 0;
        ResultSet rs = s.executeQuery("SELECT ROOM_NO FROM ROOM WHERE ROOM_NAME = '" + roomName + "';");
        if(rs.next()){
            roomNo = rs.getInt(1);
        }
        return roomNo;
    }
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
    public ArrayList<ArrayList<String>> getAllClashes() throws SQLException {
        Statement s = conn.createStatement();
        
        ArrayList<ArrayList<String>> results = new ArrayList<>();

        for (int i = 1; i <= 40; i++) {
            ArrayList<String> tempResult = new ArrayList<>();
            ResultSet rs = s.executeQuery("SELECT C.COURSE_NO, C.COURSE_CODE, R.ROOM_NAME "
                    + "FROM COURSE_TIMESLOT CT "
                    + "JOIN COURSE C ON CT.COURSE_NO_FK2 = C.COURSE_NO "
                    + "JOIN ROOM R ON CT.ROOM_NO_FK = R.ROOM_NO "
                    + "WHERE CT.TIMESLOT_NO_FK = " + i);
            while (rs.next()) {
                
                String temp;
                temp = rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3);
                tempResult.add(temp);
            }

            results.add(tempResult);
        }
        return results;
    }
    
    //Return list of Student IDs enrolled in the given course
    public ArrayList<String> getCourseEnrolments(int cno) throws SQLException {
        Statement s = conn.createStatement();
        ArrayList<String> result = new ArrayList<>();
        ResultSet rs = s.executeQuery("SELECT S.STUDENT_NAME FROM STUDENT_COURSE SC JOIN STUDENT S ON SC.STUDENT_NO_FK = S.STUDENT_NO WHERE COURSE_NO_FK1 = " + cno + ";");
        while (rs.next()) {
            result.add(rs.getString(1));
        }
        return result;
    }
    
    //Return list of courses having timing clashes with the argument
    public ArrayList<Integer> getCourseClashes(int cno) throws SQLException {
        Statement s = conn.createStatement();
        ArrayList<Integer> result = new ArrayList<>();
        ResultSet rs = s.executeQuery("SELECT C.COURSE_NO "
                + "FROM COURSE_TIMESLOT CT "
                + "JOIN COURSE C ON CT.COURSE_NO_FK2 = C.COURSE_NO "
                + "WHERE CT.TIMESLOT_NO_FK IN (SELECT TIMESLOT_NO_FK FROM COURSE_TIMESLOT WHERE COURSE_NO_FK2 = " + cno + ") "
                + "AND CT.COURSE_NO_FK2 <> " + cno + ";");
        while (rs.next()) {
            result.add(rs.getInt(1));
        }
        return result;
    }
    
    public ArrayList<String> getCourseConflicts(int cno) throws SQLException{
        ArrayList<String> result = new ArrayList<>();
//        ArrayList<Integer> conflicts = new ArrayList<>();
        ArrayList<Integer> clashes = getCourseClashes(cno);
        for(int i = 0; i<clashes.size(); i++){
            int clash = clashes.get(i);
            float percentage = getCourseConflictPercentage(cno, clash);
            if(percentage>0){
//                conflicts.add(clash);
                result.add(getCourseCode(clash) + " -> " + String.format("%.2f", percentage) + "%");
            }
        }
        
        
        
        return result;
    }
    
    //Returns percentage of students enrolled in both given courses over total studnets in both (Intersection/Union)
    public float getCourseConflictPercentage(int cno1, int cno2) throws SQLException {
//        DataBaseReader dbReader = new DataBaseReader();
        ArrayList<String> enrolments1 = getCourseEnrolments(cno1);
        ArrayList<String> enrolments2 = getCourseEnrolments(cno2);
        HashSet<String> studentSet = new HashSet<>();
        studentSet.addAll(enrolments1);
        studentSet.addAll(enrolments2);
        float total = studentSet.size();
        enrolments1.retainAll(enrolments2);
        float common = enrolments1.size();
        return (common * 100) / total;
    }

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

    public ArrayList<String> getCourseConflicts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
