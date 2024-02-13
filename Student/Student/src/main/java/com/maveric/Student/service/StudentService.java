package com.maveric.Student.service;

import com.maveric.Student.model.Request;
import com.maveric.Student.model.Response;

public interface StudentService {
    Response saveStudent(Request request);
    Response findStudent(Long id);
}
