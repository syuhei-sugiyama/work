package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
