package io.akashstack.crudimage.Repo;



// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import io.akashstack.crudimage.Models.Employee;


public interface EmployeeRepo extends JpaRepository<Employee, Long> {
	// Optional<Employee> findById(Integer id);
}
