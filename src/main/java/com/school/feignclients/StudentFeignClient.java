package com.school.feignclients;

import com.school.students.dtos.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@FeignClient("zuul")
public interface StudentFeignClient
{
    @GetMapping("/student/students/")
    List<Student> getAllStudents();

    @GetMapping("/student/students/{studentNumber}/")
    Optional<Student> findByStudentNumber(@PathVariable String studentNumber);

    @GetMapping("/student/students/yearOfBirthBetween/")
    List<Student> getStudentBetweenBirthYears(@RequestParam(required=true) int yearLower, @RequestParam(required=true) int yearUpper);

    @PostMapping("/student/students/")
    ResponseEntity createStudent(@RequestBody Student newStudent);

    //@Transactional
    @DeleteMapping("/student/students/{studentNumber}/")
    void deleteStudentByStudentNumber(@PathVariable String studentNumber);

    @PutMapping("/student/students/")
    public ResponseEntity updateStudent(@RequestBody Student newStudent);

    }

