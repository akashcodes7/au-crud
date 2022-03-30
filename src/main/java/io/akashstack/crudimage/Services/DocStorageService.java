package io.akashstack.crudimage.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.akashstack.crudimage.Models.Employee;
import io.akashstack.crudimage.Repo.EmployeeRepo;
 
@Service
public class DocStorageService {
    @Autowired
    private EmployeeRepo employeeRepo;
    public Optional<Employee> getFile(Long id){
        return employeeRepo.findById(id);
    }
    
}
