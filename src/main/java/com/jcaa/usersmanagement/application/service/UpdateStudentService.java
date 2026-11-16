package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateStudentUseCase;
import com.jcaa.usersmanagement.application.port.out.GetStudentByEmailPort;
import com.jcaa.usersmanagement.application.port.out.GetStudentByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateStudentPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateStudentCommand;
import com.jcaa.usersmanagement.application.service.mapper.StudentApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.StudentAlreadyExistsException;
import com.jcaa.usersmanagement.domain.exception.StudentNotFoundException;
import com.jcaa.usersmanagement.domain.model.StudentModel;
import com.jcaa.usersmanagement.domain.valueobject.StudentEmail;
import com.jcaa.usersmanagement.domain.valueobject.StudentId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateStudentService implements UpdateStudentUseCase {

    private final UpdateStudentPort updateStudentPort;
    private final GetStudentByIdPort getStudentByIdPort;
    private final GetStudentByEmailPort getStudentByEmailPort;
    private final Validator validator;

    @Override
    public StudentModel execute(final UpdateStudentCommand command) {
        validateCommand(command);

        final StudentId studentId = new StudentId(command.id());
        final StudentModel current = findExistingStudentOrFail(studentId);
        final StudentEmail newEmail = new StudentEmail(command.email());

        ensureEmailIsNotTakenByAnotherStudent(newEmail, studentId);

        final StudentModel studentToUpdate =
                StudentApplicationMapper.fromUpdateCommandToModel(command, current);
        return updateStudentPort.update(studentToUpdate);
    }

    private void validateCommand(final UpdateStudentCommand command) {
        final Set<ConstraintViolation<UpdateStudentCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private StudentModel findExistingStudentOrFail(final StudentId studentId) {
        return getStudentByIdPort
                .getById(studentId)
                .orElseThrow(() -> StudentNotFoundException.becauseIdWasNotFound(studentId.value()));
    }

    private void ensureEmailIsNotTakenByAnotherStudent(
            final StudentEmail newEmail, final StudentId ownerId) {
        getStudentByEmailPort
                .getByEmail(newEmail)
                .ifPresent(
                        found -> {
                            if (!found.getId().equals(ownerId)) {
                                throw StudentAlreadyExistsException.becauseEmailAlreadyExists(newEmail.value());
                            }
                        });
    }
}
