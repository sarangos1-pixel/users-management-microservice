package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record CreateStudentRestRequest(
        @NotBlank(message = "id must not be blank") String id,
        @NotBlank(message = "name must not be blank") String name,
        @NotBlank(message = "lastName must not be blank") String lastName,
        @NotNull(message = "birthDate must not be null") LocalDate birthDate,
        @NotNull(message = "semester must not be null")
        @Min(value = 1, message = "semester must be at least 1")
        @Max(value = 12, message = "semester must be at most 12")
        Integer semester,
        @NotBlank(message = "email must not be blank")
        @Email(message = "email must be a valid email address")
        String email,
        @NotBlank(message = "gender must not be blank") String gender,
        @NotBlank(message = "phone must not be blank") String phone,
        @NotBlank(message = "program must not be blank") String program,
        @NotBlank(message = "university must not be blank") String university) {}
