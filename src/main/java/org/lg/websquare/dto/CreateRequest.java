package org.lg.websquare.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class CreateRequest {
    private String id;

    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    private String birthDate;

    @NotNull(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d+$", message = "Phone number must contain only digits")
    private String phone;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    private String address;

    private String team;
    private String status;
}

// phone not exist
// email not exist