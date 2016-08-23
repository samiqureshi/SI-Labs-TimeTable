/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.handler;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import timetable.dal.DBWriter;
import timetable.dal.FileReader;
import timetable.translate.DBTranslator;

/**
 *
 * @author SamiQureshi
 */
public class LoadHandler extends Handler{
    @Override
    public boolean handleRequest(ArrayList<Integer> actionCode){
        fileReader = new FileReader();
        dbTranslator = new DBTranslator();
        try {
            dbWriter = new DBWriter();
        } catch (SQLException ex) {
            Logger.getLogger(LoadHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoadHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            switch(actionCode.get(0)){
                case 0:     //Load All Files
                    XSSFWorkbook batchWorkbook = fileReader.readBatchFile();                    
                    XSSFWorkbook courseWorkbook = fileReader.readCourseFile();
                    XSSFWorkbook enrolmentWorkbook = fileReader.readEnrolmentFile();
                    XSSFWorkbook scheduleWorkbook = fileReader.readScheduleFile();
                    
                    ArrayList<String> batchInsertStatements = 
                            dbTranslator.translateBatchInsertStatements(batchWorkbook);
                    ArrayList<String> teacherInsertStatements = 
                            dbTranslator.translateTeacherInsertStatements(courseWorkbook);
                    ArrayList<String> courseInsertStatements = 
                            dbTranslator.translateCourseInsertStatements(courseWorkbook);
                    ArrayList<String> studentInsertStatements = 
                            dbTranslator.translateStudentInsertStatements(enrolmentWorkbook);
                    ArrayList<String> enrolmentInsertStatements = 
                            dbTranslator.translateEnrolmentInsertStatements(enrolmentWorkbook);
                    ArrayList<String> roomInsertStatements = 
                            dbTranslator.translateRoomInsertStatements(scheduleWorkbook);
                    ArrayList<String> scheduleInsertStatements = 
                            dbTranslator.translateScheduleInsertStatements(scheduleWorkbook);
                    dbWriter.clearAllTables();
                    dbWriter.runInsertStatements(batchInsertStatements);
                    dbWriter.runInsertStatements(teacherInsertStatements);
                    dbWriter.runInsertStatements(courseInsertStatements);
                    dbWriter.runInsertStatements(studentInsertStatements);
                    dbWriter.runInsertStatements(enrolmentInsertStatements);
                    dbWriter.runInsertStatements(roomInsertStatements);
                    dbWriter.runInsertStatements(scheduleInsertStatements);
                    
                    
                    
                    break;
                    
                case 1:
                    
                    break;
                    
                case 2:
                    break;
                    
                case 3:
                    break;
                    
                default:
                    return false;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(LoadHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoadHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoadHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    return true;
    }
}
