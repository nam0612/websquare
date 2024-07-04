package org.lg.websquare.controller;

import lombok.RequiredArgsConstructor;
import org.lg.websquare.dto.CreateRequest;
import org.lg.websquare.dto.ExportResponse;
import org.lg.websquare.dto.SearchRequest;
import org.lg.websquare.dto.SearchResponse;
import org.lg.websquare.entity.Employee;
import org.lg.websquare.service.IEmployeeService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final IEmployeeService employeeService;

    @PostMapping("/search")
    public SearchResponse search(@RequestBody SearchRequest searchRequest) {
        Pageable pageable = Pageable.ofSize(searchRequest.getParams().getPsize()).withPage(searchRequest.getParams().getPpage());
        return employeeService.search(searchRequest.getParams(), pageable);
    }

    @DeleteMapping()
    public void delete(@RequestBody List<String> ids) {
        employeeService.delete(ids);
    }

    @PostMapping()
    public Employee addOrUpdate(@RequestBody CreateRequest employee) throws ParseException {
        return employeeService.createOrUpdate(employee);
    }

    @PostMapping("/downloadsExcel")
    public ExportResponse downloadsExcel(@RequestBody SearchRequest searchRequest) {
        return ExportResponse.builder()
                .employees(employeeService.exportDataToExcel(searchRequest.getParams()))
                .build();
    }
}
