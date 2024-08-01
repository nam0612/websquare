package org.lg.websquare.domain.employee.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.lg.websquare.domain.employee.dto.EmployeeDTO;
import org.lg.websquare.domain.employee.dto.Pagination;

import java.util.List;
@Getter
@Setter
@Builder
public class SearchResponse {
    private List<EmployeeDTO> employees;
    private Pagination paginations;
}
