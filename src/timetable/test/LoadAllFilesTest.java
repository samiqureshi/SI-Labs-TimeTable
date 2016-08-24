/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.test;

import java.util.ArrayList;
import timetable.handler.Handler;
import timetable.handler.LoadHandler;

/**
 *
 * @author SamiQureshi
 */
public class LoadAllFilesTest {
    public static void main(String[] args){
        Handler handler = new LoadHandler();
        ArrayList<Integer> actionCode = new ArrayList<>();
        actionCode.add(0);
        handler.handleRequest(actionCode);
    }
    
    
}
