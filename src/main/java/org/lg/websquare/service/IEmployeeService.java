package org.lg.websquare.service;

import org.lg.websquare.dto.CreateRequest;
import org.lg.websquare.dto.Params;
import org.lg.websquare.dto.SearchResponse;
import org.lg.websquare.entity.Employee;
import org.springframework.data.domain.Pageable;

import java.text.ParseException;
import java.util.List;

public interface IEmployeeService {

    SearchResponse search(Params searchRequest, Pageable pageable);

    void delete(List<String> ids);

    List<Employee> exportDataToExcel(Params searchRequest);

    Employee createOrUpdate(CreateRequest createRequest) throws ParseException;
}
