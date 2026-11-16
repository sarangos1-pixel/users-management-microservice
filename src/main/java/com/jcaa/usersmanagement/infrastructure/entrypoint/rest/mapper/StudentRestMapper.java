package com.jcaa.usersmanagement.infrastructure.entrypoint.rest.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateStudentCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteStudentCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateStudentCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetStudentByIdQuery;
import com.jcaa.usersmanagement.domain.model.StudentModel;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.CreateStudentRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.request.UpdateStudentRestRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.rest.dto.response.StudentRestResponse;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StudentRestMapper {

    public CreateStudentCommand toCreateCommand(final CreateStudentRestRequest request) {
        return new CreateStudentCommand(
                request.id(),
                request.name(),
                request.lastName(),
                request.birthDate(),
                request.semester(),
                request.email(),
                request.gender(),
                request.phone(),
                request.program(),
                request.university());
    }

    public UpdateStudentCommand toUpdateCommand(
            final String id, final UpdateStudentRestRequest request) {
        return new UpdateStudentCommand(
                id,
                request.name(),
                request.lastName(),
                request.birthDate(),
                request.semester(),
                request.email(),
                request.gender(),
                request.phone(),
                request.program(),
                request.university());
    }

    public GetStudentByIdQuery toGetByIdQuery(final String id) {
        return new GetStudentByIdQuery(id);
    }

    public DeleteStudentCommand toDeleteCommand(final String id) {
        return new DeleteStudentCommand(id);
    }

    public StudentRestResponse toResponse(final StudentModel student) {
        return new StudentRestResponse(
                student.getId().value(),
                student.getName().value(),
                student.getLastName().value(),
                student.getBirthDate().value(),
                student.getSemester().value(),
                student.getEmail().value(),
                student.getGender().name(),
                student.getPhone().value(),
                student.getProgram().value(),
                student.getUniversity().value());
    }

    public List<StudentRestResponse> toResponseList(final List<StudentModel> students) {
        return students.stream().map(StudentRestMapper::toResponse).toList();
    }
}
