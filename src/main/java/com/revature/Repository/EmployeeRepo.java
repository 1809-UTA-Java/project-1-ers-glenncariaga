package com.revature.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.revature.model.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {

}
