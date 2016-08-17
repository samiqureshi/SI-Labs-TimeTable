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
public class LoadHandler extends Handler{
    @Override
    public boolean handleRequest(ArrayList<Integer> actionCode){
        switch(actionCode.get(0)){
            case 1:
                break;
                
            case 2:
                break;
                
            case 3:
                break;
                
            default:
                return false;
        }
        return true;
    }
}
