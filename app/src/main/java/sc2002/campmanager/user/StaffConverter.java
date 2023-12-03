package sc2002.campmanager.user;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;

import static sc2002.campmanager.user.UserManager.staffMap;

public class StaffConverter {

    /**
     * Writes staff data back to the excel file
     * @param outputStream
     */
     static void staffToFile(OutputStream outputStream) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            Row row;
            int i = 0;
            String s;
            for (Staff staff :
                    staffMap.values()) {
                row = sheet.createRow(i++);
                row.createCell(0).setCellValue(staff.getUserId());
                row.createCell(1).setCellValue(staff.getFaculty());
                row.createCell(2).setCellValue(staff.getUserName());
                row.createCell(3).setCellValue(staff.getEmail());
                row.createCell(4).setCellValue(staff.getPassword());
                if(!staff.getOwnCamps().isEmpty()) {
//                    if(staff.getOwnCamps().size()==1){
//                        s = staff.getOwnCamps().toString() + ", ";
//                    }
//                    else {
                        s = staff.getOwnCamps().toString();
                     //   s = s.substring(1, s.length() - 1);
                    //}
                    row.createCell(5).setCellValue(s);
                }
            }
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Reads staff data from the excel file to the staffMap
     * @param inputStream
     */
     static void fileToStaffMap(InputStream inputStream)  {
        try (XSSFWorkbook workbook = new XSSFWorkbook(inputStream)){
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIte = sheet.iterator();
            Row row;
            while (rowIte.hasNext()) {
                row = rowIte.next();
                ArrayList<Integer> l1 = new ArrayList<>();
                if (row.getCell(5) != null) {
                    for (String s : row.getCell(5).getStringCellValue().replace("[", "").replace("]", "").split(", ")) {
                        l1.add(Integer.parseInt(s));
                    }
                } //else l1 = null;
                Staff staff = new Staff(row.getCell(0).getStringCellValue(),
                        row.getCell(1).getStringCellValue(),
                        row.getCell(2).getStringCellValue(),
                        row.getCell(3).getStringCellValue(),
                        row.getCell(4).getStringCellValue(),
                        l1
                );
                staffMap.put(row.getCell(0).getStringCellValue(), staff);
            }
            workbook.close();
        }catch (Exception e){
            System.out.println(e.getMessage());}
        finally {
            //inputStream.close();
            //return staffMap;
        }
    }
}
