package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetStudentByIdQuery;
import com.jcaa.usersmanagement.domain.model.StudentModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetStudentByIdUseCase {
    StudentModel execute(@NotNull @Valid GetStudentByIdQuery query);
}
