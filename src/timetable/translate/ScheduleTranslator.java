/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable.translate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import timetable.bo.CellStruct;
import timetable.bo.CourseTimeSlotStruct;
import timetable.bo.TableStruct;
import timetable.dal.DBReader;
import timetable.utility.Constants;
import static timetable.utility.Constants.DAYS_PER_WEEK;
import static timetable.utility.Constants.SLOTS_PER_DAY;

/**
 *
 * @author Qureshi
 */
public class ScheduleTranslator {

    public boolean convertToTableStruct(XSSFWorkbook workbook, TableStruct[] semesterTables) {
        XSSFSheet[] semesterSheets = new XSSFSheet[workbook.getNumberOfSheets()];
        XSSFRow[] row = new XSSFRow[workbook.getNumberOfSheets()];
        XSSFCell[] cell = new XSSFCell[workbook.getNumberOfSheets()];
        int firstCell = 1;
        int lastCell = 8;
        int firstRow = 6;
        int lastRow = 10;

        for (int iSheet = 0; iSheet < workbook.getNumberOfSheets(); iSheet++) {
            semesterSheets[iSheet] = workbook.getSheetAt(iSheet);
            if (semesterSheets[iSheet] != null) {

                //Setting the headers
                semesterTables[iSheet].university
                        = semesterSheets[iSheet].getRow(0).getCell(1).
                        getStringCellValue();
                semesterTables[iSheet].department
                        = semesterSheets[iSheet].getRow(1).getCell(1).
                        getStringCellValue();
                semesterTables[iSheet].semester
                        = semesterSheets[iSheet].getRow(2).getCell(1).
                        getStringCellValue();
                semesterTables[iSheet].section
                        = semesterSheets[iSheet].getRow(3).getCell(1).
                        getStringCellValue();
                semesterTables[iSheet].classRoom
                        = semesterSheets[iSheet].getRow(3).getCell(1).
                        getStringCellValue();

                ArrayList<CellRangeAddress> allMergedRegions = (ArrayList<CellRangeAddress>) semesterSheets[iSheet].getMergedRegions();
                ArrayList<CellStruct> mergedRegions = new ArrayList<>();
                Iterator<CellRangeAddress> iter = allMergedRegions.iterator();
                while (iter.hasNext()) {
                    CellRangeAddress region = iter.next();
                    if ((region.getFirstRow() >= firstRow)
                            && (region.getLastRow() <= lastRow)
                            && (region.getFirstColumn() >= firstCell - 1)
                            && (region.getLastColumn() <= lastCell)) {

                        mergedRegions.add(new CellStruct(region.getFirstRow(), region.getFirstColumn()));
                    }
                }

                int iRow = firstRow;
                for (int physRow = 0; physRow < 5; physRow++) {
                    row[iSheet] = semesterSheets[iSheet].getRow(iRow);
                    CellStruct currentDayCell = new CellStruct(iRow, firstCell - 1);
                    if (mergedRegions.contains(currentDayCell)) {     //This is a merged row.
                        int iCell = firstCell;
                        for (int physCol = 0; physCol < 8; physCol++) {
                            CellStruct currentCell = new CellStruct(iRow, iCell);
                            if (mergedRegions.contains(currentCell)) {        //This is a merged cell.
                                cell[iSheet] = row[iSheet].getCell(iCell);
                                String currentStr = cell[iSheet].getStringCellValue();
                                semesterTables[iSheet].table[physRow][physCol] = currentStr;
                                physCol++;
                                semesterTables[iSheet].table[physRow][physCol] = currentStr;
                                iCell++;
                            } else {
                                cell[iSheet] = row[iSheet].getCell(iCell);
                                String currentStr = cell[iSheet].getStringCellValue();
                                semesterTables[iSheet].table[physRow][physCol] = currentStr;
                            }
                            iCell++;
                        }
                        iRow++;
                        iCell = firstCell;
                        row[iSheet] = semesterSheets[iSheet].getRow(iRow);
                        for (int physCol = 0; physCol < 8; physCol++) {
                            CellStruct currentCell = new CellStruct(iRow, iCell);
                            if (mergedRegions.contains(currentCell)) {        //This is a merged cell.
                                cell[iSheet] = row[iSheet].getCell(iCell);
                                String currentStr = cell[iSheet].getStringCellValue();
                                semesterTables[iSheet].altTable[physRow][physCol] = currentStr;
                                physCol++;
                                semesterTables[iSheet].altTable[physRow][physCol] = currentStr;
                                iCell++;
                            } else {
                                cell[iSheet] = row[iSheet].getCell(iCell);
                                String currentStr = cell[iSheet].getStringCellValue();
                                semesterTables[iSheet].altTable[physRow][physCol] = currentStr;
                            }
                            iCell++;
                        }
                    } else {      //Not a merged row.
                        int iCell = firstCell;
                        for (int physCol = 0; physCol < 8; physCol++) {
                            CellStruct currentCell = new CellStruct(iRow, iCell);
                            if (mergedRegions.contains(currentCell)) {        //This is a merged cell.
                                cell[iSheet] = row[iSheet].getCell(iCell);
                                String currentStr = cell[iSheet].getStringCellValue();
                                semesterTables[iSheet].table[physRow][physCol] = currentStr;
                                physCol++;
                                semesterTables[iSheet].table[physRow][physCol] = currentStr;
                                iCell++;
                            } else {
                                cell[iSheet] = row[iSheet].getCell(iCell);
                                String currentStr = cell[iSheet].getStringCellValue();
                                semesterTables[iSheet].table[physRow][physCol] = currentStr;
                            }
                            iCell++;
                        }

                    }
                    iRow++;

                }

            }
        }
        return true;
    }

    public ArrayList<CourseTimeSlotStruct> parseSchedule(TableStruct[] semesterTables) throws SQLException, ClassNotFoundException {
//        TableStruct[] temp = new TableStruct[semesterTables.length];
        ArrayList<CourseTimeSlotStruct> courseTimesList = new ArrayList<>();
        CourseTimeSlotStruct tempCTS;
        DBReader dbReader = new DBReader();
//        int temp = Constants.NUMBER_OF_SHEETS;
        for (TableStruct semesterTable : semesterTables) {
            for (int j = 0; j < DAYS_PER_WEEK; j++) {
                for (int k = 0; k < SLOTS_PER_DAY; k++) {
                    tempCTS = new CourseTimeSlotStruct();
                    //Generating CourseCode
                    if ((semesterTable.section.split(" ")[0].length()) == 4) {
                        //No sections in batch name i.e. CS-3
                        if (!semesterTable.table[j][k].equals("")) {
                            //if not empty
                            tempCTS.courseCode = semesterTable.table[j][k].split(" ")[0];
                            if (semesterTable.table[j][k].contains(" L")) {
                                //if LAB ourse
                                tempCTS.courseCode += "-L";
                            }
                        }
                        if (!semesterTable.altTable[j][k].equals("")) {
                            tempCTS.altCourseCode = semesterTable.altTable[j][k].split(" ")[0];
                            if (semesterTable.altTable[j][k].contains(" L")) {
                                tempCTS.altCourseCode += "-L";
                            }
                        }
                    } else {
                        //Batch with sections i.e. CS-2A
                        if (!semesterTable.table[j][k].equals("")) {
                            //if not empty
                            tempCTS.courseCode = semesterTable.table[j][k].split(" ")[0] + semesterTable.section.charAt(4);
                            if (semesterTable.table[j][k].contains(" L")) {
                                //if LAB course
                                tempCTS.courseCode += "-L";
                                if (semesterTable.table[j][k].contains("SEC")) {
                                    tempCTS.courseCode += semesterTable.table[j][k].split("SEC ")[1].charAt(1);
                                }
                            }
                        }
                        if (!semesterTable.altTable[j][k].equals("")) {
                            tempCTS.altCourseCode = semesterTable.altTable[j][k].split(" ")[0] + semesterTable.section.charAt(4);
                            if (semesterTable.altTable[j][k].contains(" L")) {
                                tempCTS.altCourseCode += "-L";
                                if (semesterTable.altTable[j][k].contains("SEC")) {
                                    tempCTS.altCourseCode += semesterTable.altTable[j][k].split("SEC ")[1].charAt(1);
                                }
                            }
                        }
                    }
                    //CourseCode to CourseNo
                    if (!tempCTS.courseCode.equals("") || !tempCTS.altCourseCode.equals("")) {
                        if (!tempCTS.courseCode.equals("")) {
//                            tempCTS.courseNo = courseList.indexOf(tempCTS.courseCode) + 1;
                            tempCTS.courseNo = dbReader.getCourseNo(tempCTS.courseCode);
                        }
                        if (!tempCTS.altCourseCode.equals("")) {
                            tempCTS.altCourseNo = dbReader.getCourseNo(tempCTS.altCourseCode);
                        }

                    }
                    //Room Number(String) Extraction
                    if ((semesterTable.table[j][k].contains("FF-")) || (semesterTable.table[j][k].contains("GF-")) || (semesterTable.table[j][k].contains("SF-"))) {
                        if (semesterTable.table[j][k].contains("FF-")) {
                            tempCTS.roomNoStr = "FF-" + semesterTable.table[j][k].split("FF-")[1].substring(0, 3);
                        }
                        if (semesterTable.table[j][k].contains("GF-")) {
                            tempCTS.roomNoStr = "GF-" + semesterTable.table[j][k].split("GF-")[1].substring(0, 3);
                        }
                        if (semesterTable.table[j][k].contains("SF-")) {
                            tempCTS.roomNoStr = "SF-" + semesterTable.table[j][k].split("SF-")[1].substring(0, 3);
                        }
                        if (semesterTable.altTable[j][k].contains("FF-")) {
                            tempCTS.altRoomNoStr = "FF-" + semesterTable.altTable[j][k].split("FF-")[1].substring(0, 3);
                        }
                        if (semesterTable.altTable[j][k].contains("GF-")) {
                            tempCTS.altRoomNoStr = "GF-" + semesterTable.altTable[j][k].split("GF-")[1].substring(0, 3);
                        }
                        if (semesterTable.altTable[j][k].contains("SF-")) {
                            tempCTS.altRoomNoStr = "SF-" + semesterTable.altTable[j][k].split("SF-")[1].substring(0, 3);
                        }
                    } else {
                        tempCTS.roomNoStr = semesterTable.section.substring(semesterTable.section.indexOf("[") + 1, semesterTable.section.indexOf("]"));
//                        tempCTS.altRoomNoStr = semesterTables[i].section.substring(
//                                semesterTables[i].section.indexOf("[")+1, 
//                                semesterTables[i].section.indexOf("]"));
                    }
                    //Convert RoomNoStr to RoomNo int.
                    List<String> roomsList;
                    roomsList = Arrays.asList(Constants.CLASSROOMS);
                    tempCTS.roomNo = dbReader.getRoomNo(tempCTS.roomNoStr);
                    if (!tempCTS.altCourseCode.equals("")) {
                        tempCTS.altRoomNo = dbReader.getRoomNo(tempCTS.altRoomNoStr);
                    }
                    //Extract TimeSlot
                    int tempTimeSlot = (j) * 8 + k;
                    tempCTS.timeSlotNo = tempTimeSlot + 1;  //coz db starts at 1
                    courseTimesList.add(tempCTS);
                }
            }
        }

        return courseTimesList;
    }
    
    public ArrayList<String> convertToScheduleInsertStatements(ArrayList<CourseTimeSlotStruct> coursesTSInfo){ 
        ArrayList<String> instructions = new ArrayList<>();
        String tempCCode;
        int i=0;
        int index = 1;
        while (i < coursesTSInfo.size()) {
            if (!coursesTSInfo.get(i).courseCode.equals("")) {
                String temp = "INSERT into COURSE_TIMESLOT VALUES ("
                        + index + ", "
                        + coursesTSInfo.get(i).courseNo + ", "
                        + coursesTSInfo.get(i).timeSlotNo + ", "
                        + coursesTSInfo.get(i).roomNo + ")";
                instructions.add(temp);
                index++;
                
                if (!coursesTSInfo.get(i).altCourseCode.equals("")) {
                    temp = "INSERT into COURSE_TIMESLOT VALUES ("
                            + index + ", "
                            + coursesTSInfo.get(i).altCourseNo + ", "
                            + coursesTSInfo.get(i).timeSlotNo + ", "
                            + coursesTSInfo.get(i).altRoomNo + ")";
                    instructions.add(temp);
                    index++;
                    i++;
                }
                
            } 
            i++;
        }
//        for (int i = 0; i < coursesTSInfo.size(); i++) {
//            
//
//            
//            
//            
//        }        
        
        
        return instructions;
    }

}
