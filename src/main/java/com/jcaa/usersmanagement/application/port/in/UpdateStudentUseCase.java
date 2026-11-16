package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateStudentCommand;
import com.jcaa.usersmanagement.domain.model.StudentModel;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UpdateStudentUseCase {
    StudentModel execute(@NotNull @Valid UpdateStudentCommand command);
}
