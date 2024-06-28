package org.lg.websquare.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name= "employee")
public class Employee {

    @Id
    @UuidGenerator
    private String id;
    private String name;
    private Date birthDate;
    private String gender;
    private String phone;
    private String email;
    private String address;
    private String team;
    private String status;

    private Date createdDate;
}
