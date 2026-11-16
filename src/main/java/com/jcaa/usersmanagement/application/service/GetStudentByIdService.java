package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetStudentByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetStudentByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetStudentByIdQuery;
import com.jcaa.usersmanagement.application.service.mapper.StudentApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.StudentNotFoundException;
import com.jcaa.usersmanagement.domain.model.StudentModel;
import com.jcaa.usersmanagement.domain.valueobject.StudentId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetStudentByIdService implements GetStudentByIdUseCase {

    private final GetStudentByIdPort getStudentByIdPort;
    private final Validator validator;

    @Override
    public StudentModel execute(final GetStudentByIdQuery query) {
        validateQuery(query);

        final StudentId studentId = StudentApplicationMapper.fromGetStudentByIdQueryToStudentId(query);
        return getStudentByIdPort
                .getById(studentId)
                .orElseThrow(() -> StudentNotFoundException.becauseIdWasNotFound(studentId.value()));
    }

    private void validateQuery(final GetStudentByIdQuery query) {
        final Set<ConstraintViolation<GetStudentByIdQuery>> violations = validator.validate(query);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
