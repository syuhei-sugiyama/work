package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
