package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record UpdateStudentCommand(
        @NotBlank(message = "id must not be blank") String id,
        @NotBlank(message = "name must not be blank") String name,
        @NotBlank(message = "lastName must not be blank") String lastName,
        @NotNull(message = "birthDate must not be null") LocalDate birthDate,
        @NotNull(message = "semester must not be null") Integer semester,
        @NotBlank(message = "email must not be blank")
        @Email(message = "email must be a valid email address")
        String email,
        @NotBlank(message = "gender must not be blank") String gender,
        @NotBlank(message = "phone must not be blank") String phone,
        @NotBlank(message = "program must not be blank") String program,
        @NotBlank(message = "university must not be blank") String university) {}
