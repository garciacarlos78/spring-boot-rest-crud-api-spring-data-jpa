package com.cgrdev.springbootrestcrudapispringdatajpa.service;

import com.cgrdev.springbootrestcrudapispringdatajpa.dao.EmployeeRepository;
import com.cgrdev.springbootrestcrudapispringdatajpa.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee getOne(int id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Employee with id not found - " + id)
        );
    }

    @Override
    public Employee create(Employee employee) {
        // check correct id, must be 0
        if (employee.getId()!=0)
            throw new RuntimeException("You cannot assign an id on creation, must be 0 or not be.");
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {

        // check existing employee
        Employee dbEmployee = employeeRepository.findById(employee.getId()).
                orElseThrow(() -> new RuntimeException("Employee with id not found - " + employee.getId()));

        // update db employee with new data
        updateEmployee(dbEmployee, employee);

        // update database
        employeeRepository.save(dbEmployee);

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
    public void delete(int id) {

        // check for existing employee
        Employee employee = employeeRepository.findById(id).
                orElseThrow(()-> new RuntimeException("Employee with id not found - " + id));

        // delete employee
        employeeRepository.delete(employee);
    }
}
