package com.maveric.Student.controller;

import com.maveric.Student.model.Request;
import com.maveric.Student.model.Response;
import com.maveric.Student.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Response> createStudent(@RequestBody Request request){
        Response response=studentService.saveStudent(request);
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Response> getStudent(@PathVariable Long id){
        Response response=studentService.findStudent(id);
        return  ResponseEntity.ok().body(response);
    }
}
