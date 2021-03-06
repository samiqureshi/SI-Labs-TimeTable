/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import timetable.handler.Handler;
import timetable.handler.LoadHandler;
import timetable.handler.QueryHandler;



/**
 *
 * @author Qureshi
 */
public class Controller {
//    Handler handler;
    
    public Controller(){
//        handler = new Handler();
    }
    
    public boolean handleRequest(ArrayList<Integer> actionCode) throws SQLException, ClassNotFoundException, IOException{
        int code = actionCode.get(0);
        int arg1 = actionCode.get(1);
        int arg2 = actionCode.get(2);
        LoadHandler loadHandler;
        QueryHandler queryHandler;
        
        switch(code){
            case 0:     //Load all files
                loadHandler = new LoadHandler();
                loadHandler.loadAllFiles();
                return true;
                
            case 1:     //Get All Clashes
                queryHandler = new QueryHandler();
                System.out.println(queryHandler.getAllClashes());
                break;
                
            case 2:     //Get all conflicting courses with arg1
                queryHandler = new QueryHandler();
                System.out.println(queryHandler.getCourseConflicts(arg1));
                break;
                
            case 3:     //Get all timeslots which have conflicts
                queryHandler = new QueryHandler();
                System.out.println(queryHandler.getConflictedTimeslots());
                break;
        }
        
        
        return true;
    }
//
//    //Admin
//    public boolean clearDataBase() throws SQLException {
//        DataBaseWriter dbWriter = new DataBaseWriter();
//        return dbWriter.clearAllTables();
//    }
//
//    //Admin
//    public boolean loadDataBase() throws SQLException, IOException {
//        DataBaseTranslator dbTranslator = new DataBaseTranslator();
//        CourseInfoReader ciReader = new CourseInfoReader();
//        CourseInfoTranslator ciTranslator = new CourseInfoTranslator();
//        ScheduleReader schReader = new ScheduleReader();
//        DataBaseWriter dbWriter = new DataBaseWriter();
//        ScheduleTranslator schTranslator = new ScheduleTranslator();
//        StudentInfoReader stReader = new StudentInfoReader();
//        StudentInfoTranslator stTranslator = new StudentInfoTranslator();
//
//        classRoomInsertStatements = dbTranslator.convertToClassRoomInsertStatements(
//                Constants.CLASSROOMS);
//        dbWriter.runInsertStatements(classRoomInsertStatements);
//
//        workbook = ciReader.read();
//        ciTranslator.convertToCourseStruct(workbook, coursesInfo);
//        teacherInsertStatements = dbTranslator.convertToTeacherInsertStatements(
//                coursesInfo, teacherList);
//        dbWriter.runInsertStatements(teacherInsertStatements);
//        courseInsertStatements = dbTranslator.convertToCourseInsertStatements(
//                coursesInfo, courseList, teacherList);
//        dbWriter.runInsertStatements(courseInsertStatements);
//
//        workbook = schReader.read();
//        schTranslator.convertToTableStruct(workbook, semesterTables);
//        scheduleInfo = schTranslator.parseSchedule(semesterTables, courseList);
//        scheduleInsertStatements = dbTranslator.convertToScheduleInsertStatements(
//                scheduleInfo);
//        dbWriter.runInsertStatements(scheduleInsertStatements);
//
//        workbook = stReader.read();
//        stTranslator.convertToStudentMap(workbook, studentsInfo, coursesInfo, courseList);
//        studentInsertStatements = dbTranslator.convertToStudentInsertStatements(studentsInfo, studentList);
//        
//        enrolmentInsertStatements = dbTranslator.convertToEnrolmentInsertStatements(coursesInfo, courseList);
//        dbWriter.runInsertStatements(studentInsertStatements);
//        dbWriter.runInsertStatements(enrolmentInsertStatements);
//
//        return true;
//    }
//    
//    //Admin
//    public boolean testQuery() throws SQLException{
//        DataBaseReader dbReader = new DataBaseReader();
//        dbReader.queryTest();
//        return true;
//        
//    }
//    
//    //Returns List of 40 Lists, one for each timeslot of the week
//    public ArrayList<ArrayList<String>> getAllClashes() throws SQLException{
//        DataBaseReader dbReader = new DataBaseReader();
//        return dbReader.queryAllClashes();
//    }
//
//    //Returns List of scheduled courses for a particular TimeSlot Number.
//    public ArrayList<String> getTimeSlotClashes(int tsno) throws SQLException{
//        DataBaseReader dbReader = new DataBaseReader();
//        return dbReader.queryTimeSlotClashes(tsno);
//    }
//    
//    //Returns list of conflicting Timeslots for a student (sid) trying to register for a new course (ccode)
//    public ArrayList<Integer> getStudentCourseClashes(String sid, String ccode) throws SQLException{
//        DataBaseReader dbReader = new DataBaseReader();
//        HashSet<Integer> studentClashSet = new HashSet<>();
//        int cno = dbReader.queryCourseNo(ccode);
//        ArrayList<Integer> courseTimeSlots = dbReader.queryCourseTimeSlots(cno);
//        ArrayList<Integer> result = new ArrayList<>();
//        ArrayList<Integer> studentCourses = dbReader.queryStudentCourses(sid);
//        ArrayList<ClashStruct> studentClashes = new ArrayList<>();
//        //Iterate through all student's courses and get all timeslots
//        for(int tempCNo : studentCourses){
//            if(tempCNo != cno){
//                ArrayList<Integer> courseClashes = dbReader.queryCourseTimeSlots(tempCNo);
//                studentClashSet.addAll(courseClashes);
//            }            
//        }    
//        
//        //Check if any of the student's timeslots clash with any of the course timeslots
//        for(int tempCourseTimeSlot : courseTimeSlots){
//            if(studentClashSet.contains(tempCourseTimeSlot)){
//                result.add(tempCourseTimeSlot);
//            }
//            
//        }
//        return result;
//       
//    }    
//
//    //Return list of clashes with Course Code and Conflict Percentage for rescheduling a course (ccode) to a new time (tsno)
//    public ArrayList<String> getCourseTimeSlotClashes(String ccode, int tsno) throws SQLException{
//        DataBaseReader dbReader = new DataBaseReader();
//        ArrayList<String> clashedCourses = dbReader.queryTimeSlotClashes(tsno);
//        ArrayList<String> clashedCourseCodes = new ArrayList<>();
//        ArrayList<String> clashedCourseCodesWithPc = new ArrayList<>();
//        for(String course : clashedCourses){
//            String temp = course.split(" ")[1].split(" ")[0];
//            clashedCourseCodes.add(temp);
//        }
//        for(String temp : clashedCourseCodes){
//            int i = clashedCourseCodes.indexOf(temp);
//            float pc = getCourseConflictPercentage(ccode, temp);
//            String tempPc = temp + " " + pc;
//            clashedCourseCodesWithPc.add(tempPc);
//        }
//        
//        return clashedCourseCodesWithPc;
//        
//    }
//    
//    //Returns percentage of students enrolled in both given courses over total studnets in both (Intersection/Union)
//    public float getCourseConflictPercentage(String ccode1, String ccode2) throws SQLException {
//        DataBaseReader dbReader = new DataBaseReader();
//        ArrayList<String> enrolments1 = dbReader.queryCourseEnrolments(dbReader.queryCourseNo(ccode1));
//        ArrayList<String> enrolments2 = dbReader.queryCourseEnrolments(dbReader.queryCourseNo(ccode2));
//        HashSet<String> studentSet = new HashSet<>();
//        studentSet.addAll(enrolments1);
//        studentSet.addAll(enrolments2);
//        float total = studentSet.size();
//        enrolments1.retainAll(enrolments2);
//        float common = enrolments1.size();
//        return (common*100)/total;
//    }    

}
