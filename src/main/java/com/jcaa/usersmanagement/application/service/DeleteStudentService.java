package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteStudentUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteStudentPort;
import com.jcaa.usersmanagement.application.port.out.GetStudentByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteStudentCommand;
import com.jcaa.usersmanagement.application.service.mapper.StudentApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.StudentNotFoundException;
import com.jcaa.usersmanagement.domain.valueobject.StudentId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteStudentService implements DeleteStudentUseCase {

    private final DeleteStudentPort deleteStudentPort;
    private final GetStudentByIdPort getStudentByIdPort;
    private final Validator validator;

    @Override
    public void execute(final DeleteStudentCommand command) {
        validateCommand(command);

        final StudentId studentId = StudentApplicationMapper.fromDeleteCommandToStudentId(command);
        ensureStudentExists(studentId);
        deleteStudentPort.delete(studentId);
    }

    private void validateCommand(final DeleteStudentCommand command) {
        final Set<ConstraintViolation<DeleteStudentCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void ensureStudentExists(final StudentId studentId) {
        getStudentByIdPort
                .getById(studentId)
                .orElseThrow(() -> StudentNotFoundException.becauseIdWasNotFound(studentId.value()));
    }
}
