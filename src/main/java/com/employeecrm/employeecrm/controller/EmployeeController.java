package com.employeecrm.employeecrm.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeecrm.employeecrm.entity.Employee;
import com.employeecrm.employeecrm.service.EmployeeService;
import com.employeecrm.employeecrm.service.EmployeeService.EmployeeDetails;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
	@Autowired
	private EmployeeService service;

	@PostMapping
	public ResponseEntity<Employee> create(@RequestBody Employee e) {
		return ResponseEntity.ok(service.create(e));
	}

	@GetMapping
	public ResponseEntity<List<Employee>> getAll() {
		return ResponseEntity.ok(service.getAll());
	}

	@GetMapping("/{id}") //Employee details with 3 performance reviews
	public ResponseEntity<EmployeeDetails> getDetails(@PathVariable Long id) {
		return ResponseEntity.ofNullable(service.getEmployeeDetails(id));
	}

	@SuppressWarnings("unchecked")
	@PostMapping("/filter")  // list of employees with filters 
	public ResponseEntity<List<Employee>> filter(@RequestBody HashMap<String, Object> emp) {
		return ResponseEntity.ok( service.filter(
				(Integer)emp.get("score"),
				(String)emp.get("reviewDate"),
				(List<String>)emp.get("departments"),
				(List<String>)emp.get("projects")) );
	}
}

