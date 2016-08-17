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
public abstract class Handler {
    public ArrayList<Integer> actionCode;
    public abstract boolean handleRequest(ArrayList<Integer> actionCode);
}
