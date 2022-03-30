package io.akashstack.crudimage.Services;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;
 
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import io.akashstack.crudimage.Models.Employee;
 
 
public class ExportToPDF {
    private List<Employee> listEmployee;


    public ExportToPDF(List<Employee> listEmployee) {
        this.listEmployee = listEmployee;
    }
 
    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.GRAY);
        cell.setPadding(5);
        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
        cell.setPhrase(new Phrase("Emp ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("E-mail", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("DP", font));
        table.addCell(cell);       
    }

    private void writeTableData(PdfPTable table) {
        for (Employee employee : listEmployee) {
            table.addCell(String.valueOf(employee.getId()));
            table.addCell(employee.getName());
            table.addCell(employee.getemail());
            table.addCell(employee.getPicByte().toString());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        
        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.RED);
         
        Paragraph p = new Paragraph("List of Employees", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
         
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);
        document.add(table);
        document.close();
    }
}