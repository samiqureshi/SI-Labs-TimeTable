/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.test;


import java.util.ArrayList;
import timetable.handler.Handler;
import timetable.handler.QueryHandler;

/**
 *
 * @author SamiQureshi
 */
public class QueryTest {
    public static void main(String[] args){
        Handler handler = new QueryHandler();
        ArrayList<Integer> actionCode = new ArrayList<>();
        actionCode.add(5);
        actionCode.add(34);
        handler.handleRequest(actionCode);
    }
}
