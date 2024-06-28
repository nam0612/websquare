package org.lg.websquare.entity.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class EmployeeDTO {

    private String id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String birthDate;

    private String gender;
    private String phone;
    private String email;
    private String address;
    private String team;
    private String status;
}
