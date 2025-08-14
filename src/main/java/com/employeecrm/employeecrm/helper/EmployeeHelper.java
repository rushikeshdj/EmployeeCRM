package com.employeecrm.employeecrm.helper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.employeecrm.employeecrm.entity.Employee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Component
public class EmployeeHelper {

	@PersistenceContext
	private EntityManager entityManager;
	
	public List<Employee> findEmployeesWithFilters(String reviewDate, Integer performanceScore,
			List<String> departments, List<String> projects) {
		// TODO Auto-generated method stub

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Employee> cq = cb.createQuery(Employee.class);
		Root<Employee> employee = cq.from(Employee.class);

		// joins
		Join<Object, Object> pr = employee.join("performanceReviews", JoinType.LEFT);
		Join<Object, Object> dept = employee.join("department", JoinType.LEFT);
		Join<Object, Object> proj = employee.join("projects", JoinType.LEFT);

		List<Predicate> predicates = new ArrayList<>();

		// Filter by review date & performance score
		if (reviewDate != null) {
			Predicate dateMatch = cb.equal(pr.get("reviewDate"), reviewDate);
			if (performanceScore != null) {
				predicates.add(cb.and(dateMatch, cb.greaterThanOrEqualTo(pr.get("score"), performanceScore)));
			} else {
				predicates.add(dateMatch);
			}
		}

		// Department contains (multiple)
		if (departments != null && !departments.isEmpty()) {
			List<Predicate> deptPreds = new ArrayList<>();
			for (String deptName : departments) {
				deptPreds.add(cb.like(cb.lower(dept.get("name")), "%" + deptName.toLowerCase() + "%"));
			}
			predicates.add(cb.or(deptPreds.toArray(new Predicate[0])));
		}

		// Projects contains (multiple)
		if (projects != null && !projects.isEmpty()) {
			List<Predicate> projPreds = new ArrayList<>();
			for (String projName : projects) {
				projPreds.add(cb.like(cb.lower(proj.get("name")), "%" + projName.toLowerCase() + "%"));
			}
			predicates.add(cb.or(projPreds.toArray(new Predicate[0])));
		}

		cq.select(employee).distinct(true).where(cb.and(predicates.toArray(new Predicate[0])));

		return entityManager.createQuery(cq).getResultList();
	}
	
}
