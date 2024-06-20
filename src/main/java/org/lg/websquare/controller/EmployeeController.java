package org.lg.websquare.controller;


import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.lg.websquare.entity.Employee;
import org.lg.websquare.entity.dto.CreateRequest;
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

    @PostMapping("/search/{page}/{size}")
    public Page<Employee> search(
                                 @PathVariable int page,
                                 @PathVariable int size,
                                 @RequestBody SearchRequest searchRequest
                                 ) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return employeeService.search(searchRequest, pageable);
    }

    @DeleteMapping()
    public String delete(@RequestBody List<String> ids) {
        return employeeService.delete(ids);
    }

    @PostMapping()
    public String add(@RequestBody CreateRequest employee) {
        return employeeService.create(employee);
    }

    @PutMapping
    public String update(@RequestBody CreateRequest employee) {
        return employeeService.create(employee);
    }

    @GetMapping("/downloadsExcel")
    public void downloadsExcel(HttpServletResponse response) throws IOException {
        employeeService.exportDataToExcel(response);
    }
}
