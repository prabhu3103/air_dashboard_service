package com.unisys.trans.cps.middleware.utilities;

import com.unisys.trans.cps.middleware.models.entity.ContactQuery;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/*  create new excel sheet using Xssf workbook.
   convert byteArrayStream to Byte Array
   return byte Array
*/
public class ExcelExporter {

    private final XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private final List<ContactQuery> listUsers;

    public ExcelExporter(List<ContactQuery> listUsers) {
        this.listUsers = listUsers;
        workbook = new XSSFWorkbook();
    }

    /*
     create new excel sheet with Header names
     */
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Customer_Inquiries");

        Row row = sheet.createRow(0);

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);

        createCell(row, 0, "Function Description", style);
        createCell(row, 1, "Problem Description", style);
        createCell(row, 2, "Name of User", style);
        createCell(row, 3, "Email", style);
        createCell(row, 4, "Creation Date", style);
        createCell(row, 5, "Contact Number", style);

    }

    /*
      Create particular row and column with data
     */
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }


    /*
      Data populates to respective header
     */
    private void writeDataLines() {
        int rowCount = 1;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

        for (ContactQuery contactQuery : listUsers) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;

            createCell(row, columnCount++, contactQuery.getFunctionDesc(), style);
            createCell(row, columnCount++, contactQuery.getProblemDesc(), style);
            createCell(row, columnCount++, contactQuery.getName(), style);
            createCell(row, columnCount++, contactQuery.getEmail(), style);
            createCell(row, columnCount++, contactQuery.getDate().format(formatter), style);
            createCell(row, columnCount++, contactQuery.getPhone(), style);

        }
    }


    /*
      Converting ByteArrayOutputStream to byte Array.
       return byte array.
     */
    public byte[] export() throws IOException {
        writeHeaderLine();
        writeDataLines();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        workbook.write(byteArrayOutputStream);
        workbook.close();
        byte[] file = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return file;

    }

}
