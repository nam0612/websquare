package org.lg.websquare.controller;

import lombok.RequiredArgsConstructor;
import org.lg.websquare.entity.dto.*;
import org.lg.websquare.service.EmployeeService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/search")
    public SearchResponse search(
            @RequestBody SearchRequest searchRequest
    ) {
        Pageable pageable = Pageable.ofSize(searchRequest.getParams().getPsize()).withPage(searchRequest.getParams().getPpage());
        return employeeService.search(searchRequest.getParams(), pageable);
    }

    @DeleteMapping()
    public String delete(@RequestBody List<String> ids) {
        return employeeService.delete(ids);
    }

    @PostMapping()
    public String addOrCreate(@RequestBody CreateRequest employee) {
        return employeeService.create(employee);
    }

    @PostMapping("/downloadsExcel")
    public ExportResponse downloadsExcel(@RequestBody SearchRequest searchRequest) {
        return ExportResponse.builder()
                .employees(employeeService.exportDataToExcel(searchRequest.getParams()))
                .build();
    }
}
