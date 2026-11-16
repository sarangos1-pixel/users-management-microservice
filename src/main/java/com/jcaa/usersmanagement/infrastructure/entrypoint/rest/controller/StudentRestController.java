package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.controller;

import com.jcaa.usersmanagement.application.port.in.CreateStudentUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteStudentUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllStudentsUseCase;
import com.jcaa.usersmanagement.application.port.in.GetStudentByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateStudentUseCase;
import com.jcaa.usersmanagement.domain.model.StudentModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateStudentRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateStudentRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.StudentRestResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper.StudentRestMapper;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentRestController implements StudentRestControllerDocs {

    private final CreateStudentUseCase createStudentUseCase;
    private final UpdateStudentUseCase updateStudentUseCase;
    private final DeleteStudentUseCase deleteStudentUseCase;
    private final GetStudentByIdUseCase getStudentByIdUseCase;
    private final GetAllStudentsUseCase getAllStudentsUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentRestResponse create(@Valid @RequestBody final CreateStudentRestRequest request) {
        final StudentModel student =
                createStudentUseCase.execute(StudentRestMapper.toCreateCommand(request));
        return StudentRestMapper.toResponse(student);
    }

    @GetMapping
    public List<StudentRestResponse> getAll() {
        return StudentRestMapper.toResponseList(getAllStudentsUseCase.execute());
    }

    @GetMapping("/{id}")
    public StudentRestResponse getById(@PathVariable final String id) {
        final StudentModel student =
                getStudentByIdUseCase.execute(StudentRestMapper.toGetByIdQuery(id));
        return StudentRestMapper.toResponse(student);
    }

    @PutMapping("/{id}")
    public StudentRestResponse update(
            @PathVariable final String id,
            @Valid @RequestBody final UpdateStudentRestRequest request) {
        final StudentModel student =
                updateStudentUseCase.execute(StudentRestMapper.toUpdateCommand(id, request));
        return StudentRestMapper.toResponse(student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable final String id) {
        deleteStudentUseCase.execute(StudentRestMapper.toDeleteCommand(id));
    }
}
