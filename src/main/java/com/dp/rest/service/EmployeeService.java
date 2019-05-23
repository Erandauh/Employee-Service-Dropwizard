package com.dp.rest.service;

import com.dp.rest.dal.DB;
import com.dp.rest.exception.EmployeeAlreadyExistException;
import com.dp.rest.exception.EmployeeNotFoundException;
import com.dp.rest.exception.ValidationException;
import com.dp.rest.model.Employee;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class EmployeeService {

    private final Validator validator;

    public EmployeeService(Validator validator) {
        this.validator = validator;
    }

    public List<Employee> getEmployees(){
        return new ArrayList<>(DB.employees.values());
    }

    public Employee getEmployee(Integer id){
        return DB.employees.get(id);
    }

    public boolean createEmployee(Employee employee) throws EmployeeAlreadyExistException {

        if(DB.employees.get(employee.getId()) != null)
           throw new EmployeeAlreadyExistException("Existing employee found for id : " + employee.getId());

        try {
            DB.employees.put(employee.getId(), employee);
            return true;
        }
        catch (Exception e){
            throw e;
        }
    }

    public boolean updateEmployee(int employeeId, Employee employee) throws ValidationException, EmployeeNotFoundException{

        // validation
        Set<ConstraintViolation<Employee>> violations = validator.validate(employee);
        Employee e = this.getEmployee(employee.getId());
        if (violations.size() > 0) {
            StringBuilder validationMessages = new StringBuilder();
            violations.forEach(v-> validationMessages.append(v.getPropertyPath() + ": " + v.getMessage()));
            throw new ValidationException(validationMessages.toString());
        }

        if(e == null){
            throw new EmployeeNotFoundException("Existing employee found for id : " + employeeId);
        }

        employee.setId(employeeId);
        DB.employees.put(employeeId, employee);

        return true;
    }

    public boolean removeEmployee(int employeeId) throws EmployeeNotFoundException{

        if(DB.employees.get(employeeId) == null)
            throw new EmployeeNotFoundException("Existing employee found for id : " + employeeId);

        try {
            DB.employees.remove(employeeId);
            return true;
        }
        catch (Exception e){
            throw e;
        }
    }

}
