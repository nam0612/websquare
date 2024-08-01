package demo.websquare.domain.employee.service;

import demo.websquare.domain.employee.dto.Pagination;
import demo.websquare.domain.employee.dto.request.EmployeeRequest;
import demo.websquare.domain.employee.dto.request.ParamSearchRequest;
import demo.websquare.domain.employee.entity.Employee;

import java.text.ParseException;
import java.util.List;

public interface IEmployeeService {

    Pagination searchEmployees(ParamSearchRequest searchRequest);

    void delete(List<String> ids);

    List<Employee> exportDataToExcel(ParamSearchRequest searchRequest);

    Employee createOrUpdate(EmployeeRequest createRequest) throws ParseException;
}
