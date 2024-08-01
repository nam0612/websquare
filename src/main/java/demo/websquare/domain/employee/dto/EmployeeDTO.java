package demo.websquare.domain.employee.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class EmployeeDTO {
    private String id;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private String team;
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String birthDate;
}
