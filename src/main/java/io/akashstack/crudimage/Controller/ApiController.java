package io.akashstack.crudimage.Controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.akashstack.crudimage.Models.Employee;
import io.akashstack.crudimage.Repo.EmployeeRepo;
import io.akashstack.crudimage.Services.DocStorageService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/employee")
public class ApiController {
    @Autowired
    private EmployeeRepo employeeRepo;

	
	@Autowired
	private DocStorageService docStorageService;

	//create employee
    @PostMapping(value="/create")
    public ResponseEntity<?> createEmp(@RequestParam("dp") MultipartFile file, @RequestParam("name") String name, 
    @RequestParam("email") String email ) throws IOException {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Employee emp = new Employee(name, email, fileName, file.getContentType(), file.getBytes());
        employeeRepo.save(emp);
		// System.out.println(file.getBytes());
        return new ResponseEntity<String>("Employee created!", HttpStatus.CREATED);
    }

	//Getting all employees
	@GetMapping(path = { "/getEmployees" })
     Collection <Employee> getEmployees() {
        return employeeRepo.findAll();
    }
 
	// Download the image of employee
	@RequestMapping(value= "/files/{id}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> deleteEmployee(@PathVariable Long id) {
		Employee employee = docStorageService.getFile(id).get();
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + employee.getId() + ".jpeg\"")
			.body(employee.getPicByte());
	}

	// Delete the employee from database
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<?>  deleteEmployee(@PathVariable	long id) {
		employeeRepo.deleteById(id);
        return new ResponseEntity<String>("Employee deleted!", HttpStatus.OK);
    }

	// UPDATE the DETAILS OF employee from database
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> editEmployee(@RequestParam("dp") MultipartFile file, @RequestParam("name") String name, 
    @RequestParam("email") String email, @PathVariable("id") long id) throws IOException {
		// String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Employee existData = employeeRepo.findById(id).get();
		existData.setName(name);
		existData.setemail(email);
		existData.setPicByte(file.getBytes());
		employeeRepo.save(existData);
        return new ResponseEntity<String>("Employee updated!", HttpStatus.ACCEPTED);
    }






    // compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		return outputStream.toByteArray();
	}

    // uncompress the image bytes before returning it to the angular application
	public static byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}

}
