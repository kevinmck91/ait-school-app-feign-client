package com.school.controllers;

import com.school.feignclients.StudentFeignClient;
import com.school.students.dtos.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class StudentFeignClientController
{

    @Autowired
    StudentFeignClient studentFeignClient;

    @GetMapping("students/")
    public List<Student> getAllStudents()
    {
        return studentFeignClient.getAllStudents();
    }

    @GetMapping("students/{studentNumber}/")
    Optional<Student> getStudentByStudentNumber(@PathVariable String studentNumber)
    {
        return studentFeignClient.findByStudentNumber(studentNumber);
    }

    @GetMapping("students/yearOfBirthBetween/")
    public List<Student> getStudentBetweenBirthYears(@RequestParam(required=true) int yearLower, @RequestParam(required=true) int yearUpper) {
        return studentFeignClient.getStudentBetweenBirthYears(yearLower, yearUpper);
    }

    @PostMapping("students/")
    public ResponseEntity<String> createStudent(@RequestBody Student newStudent) {
        ResponseEntity<String> result = studentFeignClient.createStudent(newStudent);
        return new ResponseEntity<String>("", result.getHeaders(), result.getStatusCode());

    }

    //@Transactional
    @DeleteMapping("students/{studentNumber}/")
    public void deleteStudentByStudentNumber(@PathVariable String studentNumber) {
        studentFeignClient.deleteStudentByStudentNumber(studentNumber);
    }

    @PutMapping("students/")
    public ResponseEntity<String> updateStudent(@RequestBody Student newStudent) {
        ResponseEntity<String> result = studentFeignClient.updateStudent(newStudent);
        return new ResponseEntity<String>("", result.getHeaders(), result.getStatusCode());
    }


}
