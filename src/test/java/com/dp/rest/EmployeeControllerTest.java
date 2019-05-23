package com.dp.rest;

import com.dp.rest.controller.EmployeeController;
import com.dp.rest.model.Employee;
import com.dp.rest.service.EmployeeService;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.junit.ClassRule;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


import javax.validation.Validator;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

public class EmployeeControllerTest {

    private  static Validator validator = mock(Validator.class);
    private static EmployeeService employeeService = mock(EmployeeService.class);

    @ClassRule
    public static final ResourceTestRule controller = ResourceTestRule.builder()
            .addResource(new EmployeeController(employeeService))
            .build();

    @Test
    public void canGetAllEmployees(){

        Employee employee = new Employee(1,"","","");
        List<Employee> employees = Arrays.asList(employee);

        when(employeeService.getEmployees()).thenReturn(employees);

        Response response = controller.target("/employees").request().get();
        String expected = "[{\"id\":1,\"firstName\":\"\",\"lastName\":\"\",\"email\":\"\"}]";

        assertThat(response).isNotNull();
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.readEntity(String.class)).isEqualTo(expected);
    }

    @Test
    public void canGetEmployee(){

        Employee employee = new Employee(1,"","","");

        when(employeeService.getEmployee(any())).thenReturn(employee);

        Employee result = controller.target("/employees/1").request().get(Employee.class);

        assertThat(result).isEqualToComparingFieldByField(employee);
    }
}
