package com.revature.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.Repository.EmployeeRepo;
import com.revature.model.Employee;

@RestController
@RequestMapping("/api")
public class EmployeeController {
	
	@Autowired
	EmployeeRepo employeeRepo;

	@GetMapping("/employees")
	public List<Employee> getAllEmployees() {
		return employeeRepo.findAll();
	}
	
	@PostMapping("/employee/add")
	public Employee addEmployee(@RequestBody Employee employee) {
		return employeeRepo.save(employee);
	}
	
	@GetMapping("/employee/{id}")
	public Employee getEmployeeById(@PathVariable(value ="id")String id) {
		return employeeRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee","id",id));
	}
	
	@PutMapping("/employee/{id}")
	public Employee putEmployee(@PathVariable(value="id") String id,@RequestBody Employee employee) {
		Employee _employee = employeeRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("Employee","id",id));
		
		_employee.setEmail(employee.getEmail());
		_employee.setFirstName(employee.getFirstName());
		_employee.setId(employee.getId());
		_employee.setLastName(employee.getLastName());
		_employee.setPassword(employee.getPassword());
		_employee.setUsername(employee.getUsername());
		_employee.setUserRole(employee.getUserRole());
		
		Employee updatedEmployee = employeeRepo.save(_employee);
		return updatedEmployee;
	}
}
