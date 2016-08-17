/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.handler;

import java.util.ArrayList;

/**
 *
 * @author SamiQureshi
 */
public class QueryHandler extends Handler{
    public boolean handleRequest(ArrayList<Integer> actionCode){
        switch(actionCode.get(0)){
            case 4:
                break;
                
            case 5:
                break;
                
            case 6:
                break;
                
            case 7:
                break;
                
            case 8:
                break;
                
            case 9:
                break;
                
            default:
                return false;
        }
        return true;
    }
}
