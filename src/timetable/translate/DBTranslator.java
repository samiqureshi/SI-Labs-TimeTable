/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.translate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import timetable.bo.CourseTimeSlotStruct;
import timetable.bo.TableStruct;
import timetable.dal.DBReader;
import static timetable.utility.Constants.DAYS_PER_WEEK;
import static timetable.utility.Constants.SLOTS_PER_DAY;

/**
 *
 * @author Qureshi
 */
public class DBTranslator {

    public DBReader dbReader;

    public ArrayList<String> translateBatchInsertStatements(XSSFWorkbook batchWorkbook) {
        ArrayList<String> batchInsertStatements = new ArrayList<>();
        XSSFSheet batchSheet = batchWorkbook.getSheetAt(0);

        for (Row row : batchSheet) {
            String stmt = "INSERT INTO BATCH VALUES(" + row.getCell(0).toString() + ", '" + row.getCell(1).getStringCellValue() + "');";
            batchInsertStatements.add(stmt);

        }
//        semesterSheets[iSheet] = workbook.getSheetAt(iSheet);

        return batchInsertStatements;
    }

    public ArrayList<String> translateTeacherInsertStatements(XSSFWorkbook courseWorkbook) {
        HashSet<String> teacherSet = new HashSet<>();
        ArrayList<String> teacherInsertStatements = new ArrayList<>();
        XSSFSheet courseSheet = courseWorkbook.getSheetAt(0);

        for (Row row : courseSheet) {
            if (row.getCell(4) != null) {
                teacherSet.add(row.getCell(4).getStringCellValue());
            }
        }
        int i = 1;
        for (String teacher : teacherSet) {

            String stmt = "INSERT INTO TEACHER VALUES (" + i + ", '" + teacher + "');";
            teacherInsertStatements.add(stmt);
            i++;
        }

        return teacherInsertStatements;
    }

    public ArrayList<String> translateCourseInsertStatements(XSSFWorkbook courseWorkbook) throws SQLException, ClassNotFoundException {
        dbReader = new DBReader();
        ArrayList<String> courseInsertStatements = new ArrayList<>();
        XSSFSheet courseSheet = courseWorkbook.getSheetAt(0);
        for (Row row : courseSheet) {
            String stmt = "";
            if (row.getCell(4) != null) {
                stmt = "INSERT INTO COURSE VALUES("
                        + row.getCell(0).toString()
                        + ", '" + row.getCell(1).getStringCellValue()
                        + "', '" + row.getCell(2).getStringCellValue()
                        + "', " + dbReader.getTeacherID(row.getCell(4).getStringCellValue())
                        + ", "
                        + dbReader.getBatchNo(row.getCell(3).getStringCellValue())
                        + "); ";
            } else {
                stmt = "INSERT INTO COURSE VALUES("
                        + row.getCell(0).toString()
                        + ", '" + row.getCell(1).getStringCellValue()
                        + "', '" + row.getCell(2).getStringCellValue() + "', NULL, "
                        + dbReader.getBatchNo(row.getCell(3).getStringCellValue())
                        + "); ";
            }
            courseInsertStatements.add(stmt);

        }
        return courseInsertStatements;
    }

    public ArrayList<String> translateStudentInsertStatements(XSSFWorkbook enrolmentWorkbook) {
        ArrayList<String> studentInsertStatements = new ArrayList<>();
        HashMap<String, String> studentSet = new HashMap<>();
        for (int i = 0; i < enrolmentWorkbook.getNumberOfSheets(); i++) {
            for (Row row : enrolmentWorkbook.getSheetAt(i)) {
                studentSet.put(row.getCell(0).toString(), row.getCell(1).toString());
            }
        }
        int i = 1;
        for (HashMap.Entry<String, String> entry : studentSet.entrySet()) {
            String stmt = "";
            stmt = "INSERT INTO STUDENT VALUES("
                    + i
                    + ", '"
                    + entry.getKey()
                    + "', '"
                    + entry.getValue()
                    + "');";
            studentInsertStatements.add(stmt);
            i++;
        }

        return studentInsertStatements;
    }

    public ArrayList<String> translateEnrolmentInsertStatements(XSSFWorkbook enrolmentWorkbook) throws SQLException, ClassNotFoundException {
        dbReader = new DBReader();
        ArrayList<String> enrolmentInsertStatements = new ArrayList<>();
        int j = 1;
        for (int i = 0; i < enrolmentWorkbook.getNumberOfSheets(); i++) {
            String course = enrolmentWorkbook.getSheetName(i);

            for (Row row : enrolmentWorkbook.getSheetAt(i)) {
                String stmt = "INSERT INTO STUDENT_COURSE VALUES("
                        + j
                        + ", "
                        + dbReader.getStudentNo(row.getCell(0).getStringCellValue())
                        + ", "
                        + dbReader.getCourseNo(course)
                        + ", NULL, NULL);";
                enrolmentInsertStatements.add(stmt);
                j++;
            }
        }

        return enrolmentInsertStatements;
    }

    public ArrayList<String> translateRoomInsertStatements(XSSFWorkbook scheduleWorkbook) {
        ArrayList<String> roomInsertStatements = new ArrayList<>();
        HashSet<String> classroomSet = new HashSet<>();

        for (Sheet semesterSheet : scheduleWorkbook) {
            String temp = semesterSheet.getRow(3).getCell(1).getStringCellValue();
            classroomSet.add(temp.substring(temp.indexOf("[") + 1,
                    temp.indexOf("]")));
            // Decide which rows to process
            int rowStart = Math.min(6, semesterSheet.getFirstRowNum());
            int rowEnd = Math.max(10, semesterSheet.getLastRowNum());

            for (int rowNum = rowStart; rowNum <= rowEnd; rowNum++) {
                Row r = semesterSheet.getRow(rowNum);
                if (r == null) {
                    // This whole row is empty
                    // Handle it as needed
                    continue;
                }

                int lastColumn = Math.max(r.getLastCellNum(), 9);

                for (int cn = 0; cn < lastColumn; cn++) {
                    Cell c = r.getCell(cn, Row.RETURN_BLANK_AS_NULL);
                    if (c == null) {
                        // The spreadsheet is empty in this cell
                    } else {
                        // Do something useful with the cell's contents
                        String tempCell = c.getStringCellValue();
                        String tempStr = "";
                        if ((tempCell.contains("FF-"))) {
                            tempStr = "FF-" + tempCell.split("FF-")[1].substring(0, 3);
                            classroomSet.add(tempStr);
                        }
                        if ((tempCell.contains("GF-"))) {
                            tempStr = "GF-" + tempCell.split("GF-")[1].substring(0, 3);
                            classroomSet.add(tempStr);
                        }
                        if ((tempCell.contains("SF-"))) {
                            tempStr = "SF-" + tempCell.split("SF-")[1].substring(0, 3);
                            classroomSet.add(tempStr);
                        }
                        
                    }
                }
            }

        }
        int i = 1;
        for (String room : classroomSet) {
            String stmt = "INSERT INTO ROOM VALUES (" + i + ", '" + room + "');"; 
            roomInsertStatements.add(stmt);
            i++;
        }
        return roomInsertStatements;
    }

    public ArrayList<String> translateScheduleInsertStatements(XSSFWorkbook scheduleWorkbook) throws SQLException, ClassNotFoundException {
        ArrayList<String> scheduleInsertStatements = new ArrayList<>();
        TableStruct semesterTables[] = new TableStruct[scheduleWorkbook.getNumberOfSheets()];
        for(int i=0; i<scheduleWorkbook.getNumberOfSheets(); i++){
            semesterTables[i] = new TableStruct(DAYS_PER_WEEK, SLOTS_PER_DAY);
        }
        ArrayList<CourseTimeSlotStruct> scheduleInfo = new ArrayList<>();
        ScheduleTranslator schTranslator = new ScheduleTranslator();
        
//        workbook = schReader.read();
        schTranslator.convertToTableStruct(scheduleWorkbook, semesterTables);
        scheduleInfo = schTranslator.parseSchedule(semesterTables);
        scheduleInsertStatements = schTranslator.convertToScheduleInsertStatements(
                scheduleInfo);
//        dbWriter.runInsertStatements(scheduleInsertStatements);
 
        return scheduleInsertStatements;
    }
//    public ArrayList<String> convertToClassRoomInsertStatements(String[] classrooms){
//        ArrayList<String> instructions = new ArrayList<>();
//        for(int i=1; i<classrooms.length; i++){
//            String temp = "INSERT into ROOM (RoomN"
//                    + "o, RoomName) VALUES ("
//                    + i + ", '"
//                    + classrooms[i] + "')";
//            instructions.add(temp);
//        }
//        return instructions;
//    }
//
//    public ArrayList<String> convertToCourseInsertStatements(ArrayList<CourseStruct> coursesInfo, ArrayList<String> courseList, ArrayList<String> teacherList) {
//        ArrayList<String> instructions = new ArrayList<>();
//        ArrayList<String> batches = new ArrayList<>(Arrays.asList(Constants.BATCHES));
//        for(int i=0; i<coursesInfo.size(); i++){
//            String temp = "INSERT into COURSE (CNo, CCode, CName, TID_FK, BatchNo_FK) VALUES ("
//                    + (i+1) + ", '"
//                    + coursesInfo.get(i).courseCode + "', '"
//                    + coursesInfo.get(i).courseTitle + "', "
//                    + (teacherList.indexOf(coursesInfo.get(i).teacher)+1) + ", "
//                    + batches.indexOf(coursesInfo.get(i).batch) + ")" ;
//            instructions.add(temp);
//            courseList.add(coursesInfo.get(i).courseCode);
//        }    
//        return instructions;
//    }
//    
//    public ArrayList<String> convertToScheduleInsertStatements(ArrayList<CourseTimeSlotStruct> coursesTSInfo){ 
//        ArrayList<String> instructions = new ArrayList<>();
//        String tempCCode;
//        int i=0;
//        int index = 1;
//        while (i < coursesTSInfo.size()) {
//            if (!coursesTSInfo.get(i).courseCode.equals("")) {
//                String temp = "INSERT into COURSE_TIMESLOT (CTSNo, CNo_FK, TSNo_FK, RoomNo_FK) VALUES ("
//                        + index + ", "
//                        + coursesTSInfo.get(i).courseNo + ", "
//                        + coursesTSInfo.get(i).timeSlotNo + ", "
//                        + coursesTSInfo.get(i).roomNo + ")";
//                instructions.add(temp);
//                index++;
//                
//                if (!coursesTSInfo.get(i).altCourseCode.equals("")) {
//                    temp = "INSERT into COURSE_TIMESLOT (CTSNo, CNo_FK, TSNo_FK, RoomNo_FK) VALUES ("
//                            + index + ", "
//                            + coursesTSInfo.get(i).altCourseNo + ", "
//                            + coursesTSInfo.get(i).timeSlotNo + ", "
//                            + coursesTSInfo.get(i).altRoomNo + ")";
//                    instructions.add(temp);
//                    index++;
//                    i++;
//                }
//                
//            } 
//            i++;
//        }
////        for (int i = 0; i < coursesTSInfo.size(); i++) {
////            
////
////            
////            
////            
////        }        
//        
//        
//        return instructions;
//    }
//    
//    public ArrayList<String> convertToTeacherInsertStatements(ArrayList<CourseStruct> coursesInfo, ArrayList<String> teacherList){
//        
//        ArrayList<String> instructions = new ArrayList<>();
//        HashSet<String> teacherSet = new HashSet<>();
//        for(int i=1; i<coursesInfo.size(); i++){
//            if(!"".equals(coursesInfo.get(i).teacher)){
//                teacherSet.add(coursesInfo.get(i).teacher);
//            }
//        }
//        teacherList.addAll(teacherSet);
//        for(int i=0; i<teacherList.size(); i++){
//            if(!"".equals(teacherList.get(i))){
//                String temp = "INSERT into TEACHER (TID, TName) VALUES ("
//                    + (i+1) + ", '"
//                    + teacherList.get(i) + "')";
//                instructions.add(temp);
//            }
//            
//        }
//        return instructions;
//    }
//
//    public ArrayList<String> convertToStudentInsertStatements(HashMap<String, String> studentsInfo, ArrayList<String> studentList) {
//
//        ArrayList<String> instructions = new ArrayList<>();
//        studentList.addAll(studentsInfo.keySet());
//        for(String temp : studentList){
//            temp = temp.substring(0, 8);
//        }
//        for (int i = 0; i < studentList.size(); i++) {
//            String temp = "INSERT into STUDENT (SID, SName) VALUES ('"
//                    + studentList.get(i) + "', '"
//                    + studentsInfo.get(studentList.get(i)) + "')";
//            instructions.add(temp);
//
//        }
//
//        return instructions;
//    }
//    
//    public ArrayList<String> convertToEnrolmentInsertStatements(ArrayList<CourseStruct> coursesInfo, ArrayList<String> courseList){
//        ArrayList<String> instructions = new ArrayList<>();
//        int eIndex = 1;
//        for(CourseStruct tempCourse : coursesInfo){
//            for(StudentStruct tempStudent : tempCourse.enrolledStudents){
//                String temp = "INSERT into STUDENT_COURSE (SCNo, SID_FK, CNo_FK) VALUES ("
//                    + eIndex + ", '"
//                    + tempStudent.studentID + "', "
//                    + (courseList.indexOf(tempCourse.courseCode)+1) + ")";
//                instructions.add(temp);                
//                eIndex++;
//                
//            }
//        }
//        
//        return instructions;
//    }
}
