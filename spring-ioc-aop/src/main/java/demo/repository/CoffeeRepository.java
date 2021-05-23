package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.model.Coffee;

public interface CoffeeRepository extends JpaRepository<Coffee, Long> {

}
