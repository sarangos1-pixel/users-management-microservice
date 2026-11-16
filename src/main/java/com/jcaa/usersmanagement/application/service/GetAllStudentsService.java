package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllStudentsUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllStudentsPort;
import com.jcaa.usersmanagement.domain.model.StudentModel;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAllStudentsService implements GetAllStudentsUseCase {

    private final GetAllStudentsPort getAllStudentsPort;

    @Override
    public List<StudentModel> execute() {
        return getAllStudentsPort.getAll();
    }
}
