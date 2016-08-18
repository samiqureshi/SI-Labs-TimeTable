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
                    ArrayList<String> batchInsertStatements = 
                            dbTranslator.translateBatchInsertStatements(batchWorkbook);
                    dbWriter.clearAllTables();
                    dbWriter.runInsertStatements(batchInsertStatements);
                    
                    
                    XSSFWorkbook courseWorkbook = fileReader.readCourseFile();
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
        }
    return true;
    }
}
