/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.test;

import java.sql.SQLException;
import timetable.dal.DBReader;

/**
 *
 * @author SamiQureshi
 */
public class GetBatchNoTest {
    public static void main(String[] args) throws SQLException, ClassNotFoundException{
        DBReader dbReader = new DBReader();
        System.out.println("Batch No: " + dbReader.getBatchNo("CS6"));        
    }
}
