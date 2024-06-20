package org.lg.websquare.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CreateRequest {
    private String id;
    private String name;
    private String birthDate;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private String team;
    private String status;
}
