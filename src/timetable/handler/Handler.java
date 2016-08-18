/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.handler;

import java.util.ArrayList;
import timetable.dal.DBWriter;
import timetable.dal.FileReader;
import timetable.translate.DBTranslator;

/**
 *
 * @author SamiQureshi
 */
public abstract class Handler {
    private ArrayList<Integer> actionCode;
    public FileReader fileReader;
//    private FileTranslator fileTranslator;
    public DBTranslator dbTranslator;
    public DBWriter dbWriter;
    
    
    public abstract boolean handleRequest(ArrayList<Integer> actionCode);
}
