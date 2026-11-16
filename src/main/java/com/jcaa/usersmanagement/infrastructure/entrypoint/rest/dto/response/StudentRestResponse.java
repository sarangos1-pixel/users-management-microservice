package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response;

import java.time.LocalDate;

public record StudentRestResponse(
        String id,
        String name,
        String lastName,
        LocalDate birthDate,
        int semester,
        String email,
        String gender,
        String phone,
        String program,
        String university) {}
