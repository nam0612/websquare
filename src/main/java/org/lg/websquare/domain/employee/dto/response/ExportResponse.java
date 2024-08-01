package org.lg.websquare.domain.employee.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.lg.websquare.domain.employee.entity.Employee;

import java.util.List;


@Getter
@Setter
@Builder
public class ExportResponse {
    private List<Employee> employees;
}


