/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
//import java.util.List;
import java.util.Scanner;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import timetable.bo.ClashStruct;
import timetable.bo.CourseStruct;
import timetable.bo.CourseTimeSlotStruct;
import timetable.bo.TableStruct;
import timetable.dal.*;
import timetable.translate.CourseInfoTranslator;
import timetable.translate.DataBaseTranslator;
import timetable.translate.ScheduleTranslator;
import timetable.translate.StudentInfoTranslator;
import timetable.utility.Constants;
import static timetable.utility.Constants.TIMESLOTS;

/**
 *
 * @author Qureshi
 */
public class Controller {

    private TableStruct[] semesterTables;
    private ArrayList<CourseStruct> coursesInfo;
    private HashMap<String, String> studentsInfo;
    private ArrayList<CourseTimeSlotStruct> scheduleInfo;
    private XSSFWorkbook workbook;
    private ArrayList<String> courseInsertStatements;
    private ArrayList<String> teacherInsertStatements;
    private ArrayList<String> studentInsertStatements;
    private ArrayList<String> scheduleInsertStatements;
    private ArrayList<String> classRoomInsertStatements;
    private ArrayList<String> enrolmentInsertStatements;
    private ArrayList<String> teacherList;
    private ArrayList<String> courseList;
    private ArrayList<String> studentList;

    public Controller() throws SQLException {
        semesterTables = new TableStruct[15];
        for (int i = 0; i < 15; i++) {
            semesterTables[i] = new TableStruct(5, 8);
        }
        coursesInfo = new ArrayList<>();
        studentsInfo = new HashMap<>();
        scheduleInfo = new ArrayList<>();
        workbook = new XSSFWorkbook();
        teacherList = new ArrayList<>();
        courseList = new ArrayList<>();
        studentList = new ArrayList<>();
    }

    public boolean clearDataBase() throws SQLException {
        DataBaseWriter dbWriter = new DataBaseWriter();
        return dbWriter.clearAllTables();
    }

    public boolean loadDataBase() throws SQLException, IOException {
        DataBaseTranslator dbTranslator = new DataBaseTranslator();
        CourseInfoReader ciReader = new CourseInfoReader();
        CourseInfoTranslator ciTranslator = new CourseInfoTranslator();
        ScheduleReader schReader = new ScheduleReader();
        DataBaseWriter dbWriter = new DataBaseWriter();
        ScheduleTranslator schTranslator = new ScheduleTranslator();
        StudentInfoReader stReader = new StudentInfoReader();
        StudentInfoTranslator stTranslator = new StudentInfoTranslator();

        classRoomInsertStatements = dbTranslator.convertToClassRoomInsertStatements(
                Constants.CLASSROOMS);
        dbWriter.runInsertStatements(classRoomInsertStatements);

        workbook = ciReader.read();
        ciTranslator.convertToCourseStruct(workbook, coursesInfo);
        teacherInsertStatements = dbTranslator.convertToTeacherInsertStatements(
                coursesInfo, teacherList);
        dbWriter.runInsertStatements(teacherInsertStatements);
        courseInsertStatements = dbTranslator.convertToCourseInsertStatements(
                coursesInfo, courseList, teacherList);
        dbWriter.runInsertStatements(courseInsertStatements);

        workbook = schReader.read();
        schTranslator.convertToTableStruct(workbook, semesterTables);
        scheduleInfo = schTranslator.parseSchedule(semesterTables, courseList);
        scheduleInsertStatements = dbTranslator.convertToScheduleInsertStatements(
                scheduleInfo);
        dbWriter.runInsertStatements(scheduleInsertStatements);

        workbook = stReader.read();
        stTranslator.convertToStudentMap(workbook, studentsInfo, coursesInfo, courseList);
        studentInsertStatements = dbTranslator.convertToStudentInsertStatements(studentsInfo, studentList);
        
        enrolmentInsertStatements = dbTranslator.convertToEnrolmentInsertStatements(coursesInfo, courseList);
        dbWriter.runInsertStatements(studentInsertStatements);
        dbWriter.runInsertStatements(enrolmentInsertStatements);

        return true;
    }
    
    public boolean testQuery() throws SQLException{
        DataBaseReader dbReader = new DataBaseReader();
        dbReader.queryTest();
        return true;
        
    }
    
    //Returns List of 40 Lists, one for each timeslot of the week
    public ArrayList<ArrayList<String>> getAllClashes() throws SQLException{
        DataBaseReader dbReader = new DataBaseReader();
        return dbReader.queryAllClashes();
    }

    //Returns List of scheduled courses for a particular TimeSlot Number.
    public ArrayList<String> getTimeSlotClashes(int tsno) throws SQLException{
        DataBaseReader dbReader = new DataBaseReader();
        return dbReader.queryTimeSlotClashes(tsno);
    }
    
    //Returns list of conflicting Timeslots for a student (sid) trying to register for a new course (ccode)
    public ArrayList<Integer> getStudentCourseClashes(String sid, String ccode) throws SQLException{
        DataBaseReader dbReader = new DataBaseReader();
        HashSet<Integer> studentClashSet = new HashSet<>();
        int cno = dbReader.queryCourseNo(ccode);
        ArrayList<Integer> courseTimeSlots = dbReader.queryCourseTimeSlots(cno);
        ArrayList<Integer> result = new ArrayList<>();
        ArrayList<Integer> studentCourses = dbReader.queryStudentCourses(sid);
        ArrayList<ClashStruct> studentClashes = new ArrayList<>();
        //Iterate through all student's courses and get all timeslots
        for(int tempCNo : studentCourses){
            if(tempCNo != cno){
                ArrayList<Integer> courseClashes = dbReader.queryCourseTimeSlots(tempCNo);
                studentClashSet.addAll(courseClashes);
            }            
        }    
        
        for(int tempCourseTimeSlot : courseTimeSlots){
            if(studentClashSet.contains(tempCourseTimeSlot)){
                result.add(tempCourseTimeSlot);
            }
            
        }
        return result;
       
    }
    

//    public ArrayList<> getCourseTimeSlotClashes(String ccode, int tsno) throws SQLException{
//        
//    }
    
    public float getCourseConflictPercentage(String ccode1, String ccode2) throws SQLException {
        DataBaseReader dbReader = new DataBaseReader();
        ArrayList<String> enrolments1 = dbReader.queryCourseEnrolments(dbReader.queryCourseNo(ccode1));
        ArrayList<String> enrolments2 = dbReader.queryCourseEnrolments(dbReader.queryCourseNo(ccode2));
        HashSet<String> studentSet = new HashSet<>();
        studentSet.addAll(enrolments1);
        studentSet.addAll(enrolments2);
        float total = studentSet.size();
        enrolments1.retainAll(enrolments2);
        float common = enrolments1.size();
        return (common*100)/total;
    }
    
    

}
