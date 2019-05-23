package com.dp.rest.dal;
 
import java.util.HashMap;

import com.dp.rest.model.Employee;

public class DB {
     
    public static HashMap<Integer, Employee> employees = new HashMap<>();
    static{
        employees.put(1, new Employee(1, "Era", "Hora", "era@hh.com"));
        employees.put(2, new Employee(2, "Neil", "Armstrong", "neil@aa.com"));
        employees.put(3, new Employee(3, "Diana", "Choco", "dd@dd.com"));
    }
}