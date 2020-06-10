package com.cgrdev.springbootrestcrudapispringdatajpa.service;

import com.cgrdev.springbootrestcrudapispringdatajpa.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> getAll();

    Employee getOne(int id);

    Employee create(Employee employee);

    Employee update(Employee employee);

    void delete(int id);
}
