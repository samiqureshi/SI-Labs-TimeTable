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
import java.util.List;

/**
 *
 * @author Qureshi
 */


        

public class DBWriter {
    
    Connection conn;

    public DBWriter() throws SQLException, ClassNotFoundException {
        Class.forName("org.h2.Driver");
        this.conn = DriverManager.getConnection("jdbc:h2:~/test", "samiqureshi", "sq3147");
        
    }
    
    public boolean clearAllTables() throws SQLException{
        Statement s = conn.createStatement();
        
        s.execute("DELETE FROM COURSE;");
        s.execute("DELETE FROM BATCH;");
        s.execute("DELETE FROM TEACHER;");
        
//        s.execute("DELETE FROM [COURSE_TIMESLOT]");
//        s.execute("DELETE FROM [TEACHER]");
//        s.execute("DELETE FROM [ROOM]");
//        s.execute("DELETE FROM [STUDENT]");
//        s.execute("DELETE * FROM [STUDENT_COURSE]");

        return true;
    }
    
//    public boolean clearTeacherTable() throws SQLException{
//        Statement s = conn.createStatement();
//        s.execute("DELETE * FROM [TEACHER]");        
//        return true;
//    }
//    public boolean clearCourseTable() throws SQLException{
//        Statement s = conn.createStatement();
//        s.execute("DELETE * FROM [COURSE]");        
//        return true;
//    }
//    public boolean clearScheduleTable() throws SQLException{
//        Statement s = conn.createStatement();
//        s.execute("DELETE * FROM [COURSE_TIMESLOT]");        
//        return true;
//    }
//    public boolean clearRoomTable() throws SQLException{
//        Statement s = conn.createStatement();
//        s.execute("DELETE * FROM [ROOM]");        
//        return true;
//    }
    
    public boolean runInsertStatements(List<String> instructions) throws SQLException{
        Statement s = conn.createStatement();
        for(String sql : instructions){
            s.execute(sql);
        }
        return true;
    }
    
//    public boolean queryTest() throws SQLException{
//        Statement s = conn.createStatement();
//        ResultSet rs = s.executeQuery("SELECT [TimeSlot] FROM [TIMESLOT] WHERE [Day] like 'Wednesday'");
//        while (rs.next()) {
//            System.out.println(rs.getString(1));
//        }
//        return true;
//    }
}
