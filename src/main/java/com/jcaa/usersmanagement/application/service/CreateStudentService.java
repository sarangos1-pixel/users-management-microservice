package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateStudentUseCase;
import com.jcaa.usersmanagement.application.port.out.GetStudentByEmailPort;
import com.jcaa.usersmanagement.application.port.out.SaveStudentPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateStudentCommand;
import com.jcaa.usersmanagement.application.service.mapper.StudentApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.StudentAlreadyExistsException;
import com.jcaa.usersmanagement.domain.model.StudentModel;
import com.jcaa.usersmanagement.domain.valueobject.StudentEmail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateStudentService implements CreateStudentUseCase {

    private final SaveStudentPort saveStudentPort;
    private final GetStudentByEmailPort getStudentByEmailPort;
    private final Validator validator;

    @Override
    public StudentModel execute(final CreateStudentCommand command) {
        validateCommand(command);

        final StudentEmail email = new StudentEmail(command.email());
        ensureEmailIsNotTaken(email);

        final StudentModel studentToSave = StudentApplicationMapper.fromCreateCommandToModel(command);
        return saveStudentPort.save(studentToSave);
    }

    private void validateCommand(final CreateStudentCommand command) {
        final Set<ConstraintViolation<CreateStudentCommand>> violations = validator.validate(command);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }

    private void ensureEmailIsNotTaken(final StudentEmail email) {
        getStudentByEmailPort
                .getByEmail(email)
                .ifPresent(
                        ignored -> {
                            throw StudentAlreadyExistsException.becauseEmailAlreadyExists(email.value());
                        });
    }
}
