package com.employeecrm.employeecrm.model;

import java.util.List;

import com.employeecrm.employeecrm.entity.Employee;
import com.employeecrm.employeecrm.entity.PerformanceReview;

public class EmployeeDetails {
		public Employee employee;
		public List<PerformanceReview> last3Reviews;

		public EmployeeDetails(Employee employee, List<PerformanceReview> last3Reviews) {
			this.employee = employee;
			this.last3Reviews = last3Reviews;
		}
	}
