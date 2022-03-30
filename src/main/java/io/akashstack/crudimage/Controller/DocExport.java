package io.akashstack.crudimage.Controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
 
import javax.servlet.http.HttpServletResponse;

import com.lowagie.text.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.akashstack.crudimage.Models.Employee;
import io.akashstack.crudimage.Repo.EmployeeRepo;
import io.akashstack.crudimage.Services.ExportToExcel;
import io.akashstack.crudimage.Services.ExportToPDF;

@RestController
@RequestMapping("employee/export")
public class DocExport {
    @Autowired
    private EmployeeRepo employeeRepo;

    @GetMapping("/to-excel")
    public void exportToExcel(HttpServletResponse response) throws IOException{
        response.setContentType("application/octet-stream");
        String headerKey="Content-Disposition";
        String headerValue="attachment; filename=Employee_info.xlsx";

        response.setHeader(headerKey, headerValue);
        List<Employee> listEmployee = employeeRepo.findAll();
        ExportToExcel exp = new ExportToExcel(listEmployee);
        exp.export(response);
    }

    @GetMapping("/to-pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Allemployees_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        List<Employee> listEmployee = employeeRepo.findAll();
        ExportToPDF exporter = new ExportToPDF(listEmployee);
        exporter.export(response);
    }
}