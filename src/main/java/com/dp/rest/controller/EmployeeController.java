package com.dp.rest.controller;

import com.dp.rest.exception.EmployeeAlreadyExistException;
import com.dp.rest.exception.EmployeeNotFoundException;
import com.dp.rest.model.Employee;
import com.dp.rest.service.EmployeeService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URI;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeController {
 

    private EmployeeService employeeService;
 
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
 
    @GET
    public Response getEmployees() {
        return Response.ok(employeeService.getEmployees()).build();
    }
 
    @GET
    @Path("/{id}")
    public Response getEmployeeById(@PathParam("id") Integer id) {

        Employee employee = employeeService.getEmployee(id);
        if (employee != null)
            return Response.ok(employee).build();
        else
            return Response.status(Status.NOT_FOUND).build();
    }
 
    @POST
    public Response createEmployee(Employee employee) {

        try {
            employeeService.createEmployee(employee);
            return Response.created(new URI("/employees/" + employee.getId()))
                    .build();
        }
        catch (EmployeeAlreadyExistException ex){
            throw new BadRequestException(ex.getMessage());
        }
        catch (Exception e){
            throw new InternalServerErrorException();
        }
    }
 
    @PUT
    @Path("/{id}")
    public Response updateEmployeeById(@PathParam("id") Integer id, Employee employee) {

        try {
            employeeService.updateEmployee(id, employee);
            return Response.ok(employee).build();
        }
        catch (EmployeeNotFoundException ex){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        catch (Exception e){
            throw new InternalServerErrorException();
        }
    }
 
    @DELETE
    @Path("/{id}")
    public Response removeEmployeeById(@PathParam("id") Integer id) {

       try {
           employeeService.removeEmployee(id);
           return Response.ok().build();
       }
       catch (EmployeeNotFoundException ex){
           return Response.status(Status.NOT_FOUND).build();
       }
       catch (Exception e){
           throw new InternalServerErrorException();
       }

    }
}