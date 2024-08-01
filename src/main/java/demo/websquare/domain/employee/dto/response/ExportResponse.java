package demo.websquare.domain.employee.dto.response;

import demo.websquare.domain.employee.entity.Employee;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@Builder
public class ExportResponse {
    private List<Employee> employees;
}


