package com.employeecrm.employeecrm.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employeecrm.employeecrm.entity.Employee;
import com.employeecrm.employeecrm.entity.PerformanceReview;
import com.employeecrm.employeecrm.helper.EmployeeHelper;
import com.employeecrm.employeecrm.repo.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private EmployeeHelper employeeHelper;



	public Employee create(Employee e) {
		return employeeRepository.save(e);
	}

	public List<Employee> getAll() {
		return employeeRepository.findAll();
	}

	public Employee getById(Long id) {
		return employeeRepository.findById(id).orElse(null);
	}

	public Employee update(Long id, Employee e) {
		return employeeRepository.findById(id).map(existing -> {
			existing.setName(e.getName());
			existing.setEmail(e.getEmail());
			existing.setDepartment(e.getDepartment());
			existing.setDateOfJoining(e.getDateOfJoining());
			existing.setSalary(e.getSalary());
			existing.setManager(e.getManager());
			return employeeRepository.save(existing);
		}).orElse(null);
	}

	public void delete(Long id) {
		employeeRepository.deleteById(id);
	}

	public List<Employee> filter(Integer score, String reviewDate, List<String> departments, List<String> projects) {
		return employeeHelper.findEmployeesWithFilters(reviewDate, score, departments, projects);
	}

	@Transactional(readOnly = true)
	public EmployeeDetails getEmployeeDetails(Long id) {
		Employee e = getById(id);
		if (e == null)
			return null;
		List<PerformanceReview> last3 = e.getPerformanceReviews() == null ? List.of()
				: e.getPerformanceReviews().stream()
						.sorted(Comparator.comparing(PerformanceReview::getReviewDate).reversed()).limit(3)
						.collect(Collectors.toList());
		return new EmployeeDetails(e, last3);
	}

	public static class EmployeeDetails {
		public Employee employee;
		public List<PerformanceReview> last3Reviews;

		public EmployeeDetails(Employee employee, List<PerformanceReview> last3Reviews) {
			this.employee = employee;
			this.last3Reviews = last3Reviews;
		}
	}
}

