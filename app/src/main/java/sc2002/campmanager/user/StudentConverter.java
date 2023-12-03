package sc2002.campmanager.user;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

import static sc2002.campmanager.user.UserManager.studentMap;

public class StudentConverter {

    /**
     * Writes student data back to the excel file
     * @param outputStream
     */
     static void studentToFile(OutputStream outputStream) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            Row row;
            int i = 0;
            String s;
            for (Student student :
                    studentMap.values()) {
                row = sheet.createRow(i++);
                row.createCell(0).setCellValue(student.getUserId());
                row.createCell(1).setCellValue(student.getFaculty());
                row.createCell(2).setCellValue(student.getUserName());
                row.createCell(3).setCellValue(student.getEmail());
                row.createCell(4).setCellValue(student.getPassword());
                row.createCell(5).setCellValue(student.getCampCommittee());
                row.createCell(8).setCellValue(student.getPoints());
                if(!student.getOwnCamps().isEmpty()) {
//                    if(student.getOwnCamps().size()==1){
//                        s = student.getOwnCamps().toString() + ", ";
//                    }
//                    else {
                        s = student.getOwnCamps().toString();

                    row.createCell(6).setCellValue(s);
                }
                if(!student.getWithdrawnFrom().isEmpty()) {
//                    if(student.getWithdrawnFrom().size()==1){
//                        s = student.getWithdrawnFrom().toString() + ", ";
//                    }
//                    else {
                        s = student.getWithdrawnFrom().toString();
//                        s = s.substring(1, s.length() - 1);
//                    }
                    row.createCell(7).setCellValue(s);
                }
                if(!student.getOwnEnquiry().isEmpty()) {
//                    if(student.getOwnEnquiry().size()==1){
//                        s = student.getOwnEnquiry().toString() + ", ";
//                    }
//                    else {
                        s = student.getOwnEnquiry().toString();
//                        s = s.substring(1, s.length() - 1);
//                    }
                    row.createCell(9).setCellValue(s);
                }
                if(!student.getOwnSuggestion().isEmpty()) {
//                    if(student.getOwnSuggestion().size()==1){
//                        s = student.getOwnSuggestion().toString() + ", ";
//                    }
//                    else {
                        s = student.getOwnSuggestion().toString();
//                        s = s.substring(1, s.length() - 1);
//                    }
                    row.createCell(10).setCellValue(s);
                }
            }
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    /**
     * Reads student data from the excel file to the studentMap
     * @param inputStream
     */
     static void fileToStudentMap(InputStream inputStream) {
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)) {
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIte = sheet.iterator();
            Row row ;
            while (rowIte.hasNext()) {
                row = rowIte.next();
                ArrayList<Integer> l1 = new ArrayList<>();
                ArrayList<Integer> l2 = new ArrayList<>();
                ArrayList<Integer> l3 = new ArrayList<>();
                ArrayList<Integer> l4 = new ArrayList<>();
                if (row.getCell(6) != null) {
                    for (String s : row.getCell(6).getStringCellValue().replace("[", "").replace("]", "").split(", ")) {
                        l1.add(Integer.parseInt(s));
                    }
                } //else l1 = null;
                if (row.getCell(7) != null) {
                    for (String s : row.getCell(7).getStringCellValue().replace("[", "").replace("]", "").split(", ")) {
                        l2.add(Integer.parseInt(s));
                    }
                } //else l2 = null;
                if (row.getCell(9) != null) {
                    for (String s : row.getCell(9).getStringCellValue().replace("[", "").replace("]", "").split(", ")) {
                        l3.add(Integer.parseInt(s));
                    }
                } //else l3 = null;
                if (row.getCell(10) != null) {
                    for (String s : row.getCell(10).getStringCellValue().replace("[", "").replace("]", "").split(", ")) {
                        l4.add(Integer.parseInt(s));
                    }
                } //else l4 = null;
                Student student = new Student(row.getCell(0).getStringCellValue(),
                        row.getCell(1).getStringCellValue(),
                        row.getCell(2).getStringCellValue(),
                        row.getCell(3).getStringCellValue(),
                        row.getCell(4).getStringCellValue(),
                        (int) row.getCell(5).getNumericCellValue(),
                        l1, l2,
                        (int) row.getCell(8).getNumericCellValue(),
                        l3, l4
                );
                studentMap.put(row.getCell(0).getStringCellValue(), student);
            }
            workbook.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    }

