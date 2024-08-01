package org.lg.websquare.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lg.websquare.app.dto.response.ResponseDto;
import org.lg.websquare.app.service.ResponseFactory;
import org.lg.websquare.domain.employee.dto.Pagination;
import org.lg.websquare.domain.employee.dto.request.EmployeeRequest;
import org.lg.websquare.domain.employee.dto.request.SearchRequest;
import org.lg.websquare.domain.employee.dto.response.ExportResponse;
import org.lg.websquare.domain.employee.dto.response.SearchResponse;
import org.lg.websquare.domain.employee.entity.Employee;
import org.lg.websquare.domain.employee.service.IEmployeeService;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employees")
@Slf4j
public class EmployeeController {

    private final IEmployeeService employeeService;
    private final ResponseFactory responseFactory;

    @PostMapping("/search")
    public ResponseDto searchEmployee(@RequestBody SearchRequest searchRequest) {
        Pagination response = employeeService.searchEmployees(searchRequest.getParams());

        return responseFactory.response(response);
    }

    @PostMapping()
    public Employee addOrUpdate(@Valid @RequestBody EmployeeRequest employee) throws ParseException {
        return employeeService.createOrUpdate(employee);
    }

    @DeleteMapping()
    public void delete(@RequestBody List<String> ids) {
        employeeService.delete(ids);
    }

    @PostMapping("/export")
    public ExportResponse exportEmployees(@RequestBody SearchRequest searchRequest) {
        return ExportResponse.builder()
                .employees(employeeService.exportDataToExcel(searchRequest.getParams()))
                .build();
    }

}
