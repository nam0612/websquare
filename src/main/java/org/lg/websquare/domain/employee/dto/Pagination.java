package org.lg.websquare.domain.employee.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.formula.functions.T;
import org.lg.websquare.domain.employee.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@Builder
public class Pagination {
    private Long totalElement;
    private Integer totalPage;
    private Integer pageSize;
    private Integer pageIndex;
    private List<EmployeeDTO> data;
}
