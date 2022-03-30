package io.akashstack.crudimage.Services;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import io.akashstack.crudimage.Models.Employee;

public class ExportToExcel {
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    private List<Employee> listEmployee;

    public ExportToExcel(List<Employee> listEmployee){
        this.listEmployee = listEmployee;
        workbook = new XSSFWorkbook();
    }

    private void createCell(Row row, int columnCount, Object value,  CellStyle style){
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if(value instanceof Long){
            cell.setCellValue((Long) value);
        }else if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        }else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else if (value instanceof String) {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

    private void writeHeaderLine(){
        sheet=workbook.createSheet("Employee");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(20);
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        createCell(row, 0, "Employee Informations", style);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,3));
        font.setFontHeight((short)(10)); 

        row=sheet.createRow(1);
        font.setFontHeight(16);
        font.setBold(true);
        style.setFont(font);
        createCell(row, 0, "Employee ID", style);
        createCell(row, 1, "Employee Name", style);
        createCell(row, 2, "Employee Email", style);
        createCell(row, 3, "Employee Photo", style);
    }
    private void writeDataLines(){
        int rowCount=2;

        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);

        for(Employee emp:listEmployee){
            Row row = sheet.createRow(rowCount++);
            int columnCount=0;
            createCell(row, columnCount++, emp.getId() , style);
            createCell(row, columnCount++, emp.getName() , style);
            createCell(row, columnCount++, emp.getemail() , style);
            createCell(row, columnCount++, emp.getPicByte() , style);
        }
    }
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
 