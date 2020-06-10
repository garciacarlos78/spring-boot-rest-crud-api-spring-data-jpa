package com.cgrdev.springbootrestcrudapispringdatajpa.service;

import com.cgrdev.springbootrestcrudapispringdatajpa.dao.EmployeeDAO;
import com.cgrdev.springbootrestcrudapispringdatajpa.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeDAO employeeDAO;

    @Autowired
    public EmployeeServiceImpl(@Qualifier("employeeDAOJpaImpl") EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    @Override
    public List<Employee> getAll() {
        return employeeDAO.findAll();
    }

    @Override
    public Employee getOne(int id) {
        return employeeDAO.findById(id);
    }

    @Override
    public Employee create(Employee employee) {

        // check correct id, must be 0
        if (employee.getId()!=0)
            throw new RuntimeException("You cannot assign an id on creation, must be 0 or not be.");

        return employeeDAO.save(employee);
    }

    @Override
    public Employee update(Employee employee) {

        // check existing employee
        Employee dbEmployee = employeeDAO.findById(employee.getId());
        if (dbEmployee==null)
            throw new RuntimeException("Employee with id not found - " + employee.getId());

        // update db employee with new data
        updateEmployee(dbEmployee, employee);

        // update database
        employeeDAO.save(dbEmployee);

        return dbEmployee;
    }

    private void updateEmployee(Employee dbEmployee, Employee newEmployee) {
        String field = newEmployee.getFirstName();
        if (field!=null) dbEmployee.setFirstName(field);
        field = newEmployee.getLastName();
        if (field!=null) dbEmployee.setLastName(field);
        field = newEmployee.getEmail();
        if (field!=null) dbEmployee.setEmail(field);
    }

    @Override
    public boolean delete(int id) {

        // check for existing employee
        Employee employee = employeeDAO.findById(id);
        if (employee==null) return false;

        // delete employee
        employeeDAO.delete(employee);

        return true;
    }
}
