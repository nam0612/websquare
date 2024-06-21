package org.lg.websquare.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.lg.websquare.entity.Employee;
import org.lg.websquare.entity.dto.CreateRequest;
import org.lg.websquare.entity.dto.Params;
import org.lg.websquare.entity.dto.SearchRequest;
import org.lg.websquare.service.EmployeeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/search")
    public Page<Employee> search(
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

    @GetMapping("/downloadsExcel")
    public void downloadsExcel(HttpServletResponse response) throws IOException {
        employeeService.exportDataToExcel(response);
    }
}
