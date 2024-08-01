package org.lg.websquare.domain.employee.service;

import org.lg.websquare.domain.employee.dto.Pagination;
import org.lg.websquare.domain.employee.dto.request.EmployeeRequest;
import org.lg.websquare.domain.employee.dto.request.ParamSearchRequest;
import org.lg.websquare.domain.employee.entity.Employee;

import java.text.ParseException;
import java.util.List;

public interface IEmployeeService {

    Pagination searchEmployees(ParamSearchRequest searchRequest);

    void delete(List<String> ids);

    List<Employee> exportDataToExcel(ParamSearchRequest searchRequest);

    Employee createOrUpdate(EmployeeRequest createRequest) throws ParseException;
}
