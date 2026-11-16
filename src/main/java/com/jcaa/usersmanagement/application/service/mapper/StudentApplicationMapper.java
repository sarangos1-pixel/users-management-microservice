package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateStudentCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteStudentCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateStudentCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetStudentByIdQuery;
import com.jcaa.usersmanagement.domain.enums.StudentGender;
import com.jcaa.usersmanagement.domain.model.StudentModel;
import com.jcaa.usersmanagement.domain.valueobject.StudentBirthDate;
import com.jcaa.usersmanagement.domain.valueobject.StudentEmail;
import com.jcaa.usersmanagement.domain.valueobject.StudentId;
import com.jcaa.usersmanagement.domain.valueobject.StudentLastName;
import com.jcaa.usersmanagement.domain.valueobject.StudentName;
import com.jcaa.usersmanagement.domain.valueobject.StudentPhone;
import com.jcaa.usersmanagement.domain.valueobject.StudentProgram;
import com.jcaa.usersmanagement.domain.valueobject.StudentSemester;
import com.jcaa.usersmanagement.domain.valueobject.StudentUniversity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StudentApplicationMapper {

    public StudentModel fromCreateCommandToModel(final CreateStudentCommand command) {
        return StudentModel.create(
                new StudentId(command.id()),
                new StudentName(command.name()),
                new StudentLastName(command.lastName()),
                new StudentBirthDate(command.birthDate()),
                new StudentSemester(command.semester()),
                new StudentEmail(command.email()),
                StudentGender.fromString(command.gender()),
                new StudentPhone(command.phone()),
                new StudentProgram(command.program()),
                new StudentUniversity(command.university()));
    }

    public StudentModel fromUpdateCommandToModel(
            final UpdateStudentCommand command, final StudentModel current) {
        return current.update(
                new StudentName(command.name()),
                new StudentLastName(command.lastName()),
                new StudentBirthDate(command.birthDate()),
                new StudentSemester(command.semester()),
                new StudentEmail(command.email()),
                StudentGender.fromString(command.gender()),
                new StudentPhone(command.phone()),
                new StudentProgram(command.program()),
                new StudentUniversity(command.university()));
    }

    public StudentId fromGetStudentByIdQueryToStudentId(final GetStudentByIdQuery query) {
        return new StudentId(query.id());
    }

    public StudentId fromDeleteCommandToStudentId(final DeleteStudentCommand command) {
        return new StudentId(command.id());
    }
}
