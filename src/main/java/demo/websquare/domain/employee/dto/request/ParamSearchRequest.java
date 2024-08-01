package demo.websquare.domain.employee.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ParamSearchRequest {
    private String name;
    private String team;
    private String phone;
    private String gender;
    private String dateFrom;
    private String dateTo;
    private int page;
    private int pageSize;
}
