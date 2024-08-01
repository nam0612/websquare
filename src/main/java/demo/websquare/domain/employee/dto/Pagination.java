package demo.websquare.domain.employee.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
