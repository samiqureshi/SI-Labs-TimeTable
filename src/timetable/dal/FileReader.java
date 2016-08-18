/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.dal;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import static timetable.utility.Constants.BATCH_FILE_PATH;
import static timetable.utility.Constants.COURSE_FILE_PATH;
import static timetable.utility.Constants.ENROLMENT_FILE_PATH;
import static timetable.utility.Constants.SCHEDULE_FILE_PATH;

/**
 *
 * @author SamiQureshi
 */
public class FileReader {
    
    public XSSFWorkbook readBatchFile() throws IOException{
        File file = new File(BATCH_FILE_PATH);
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        return workbook;
    }
    public XSSFWorkbook readCourseFile() throws IOException{
        File file = new File(COURSE_FILE_PATH);
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        return workbook;
    }
    
    public XSSFWorkbook readScheduleFile() throws IOException{
        File file = new File(SCHEDULE_FILE_PATH);
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        return workbook;
    }
    
    public XSSFWorkbook readEnrolmentFile(int cno) throws IOException{
        File file = new File(ENROLMENT_FILE_PATH);
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        return workbook;
    }
}
