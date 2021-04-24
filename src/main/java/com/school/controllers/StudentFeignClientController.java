package com.school.controllers;

import com.school.feignclients.StudentFeignClient;
import com.school.students.dtos.Student;
import com.school.students.enums.MaritalStatus;

import com.school.students.exceptions.StudentGenericError;
import com.school.students.exceptions.StudentNotFoundException;
import feign.FeignException;
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
        try{
            return studentFeignClient.getAllStudents();
        } catch(FeignException e) {
            throw new StudentGenericError("Feign Error - Error getting students: ");
        }
    }

    @GetMapping("students/{studentNumber}/")
    Optional<Student> getStudentByStudentNumber(@PathVariable String studentNumber)
    {
        try{
            return studentFeignClient.findByStudentNumber(studentNumber);
        } catch(FeignException e) {
            throw new StudentNotFoundException("Feign Error - Student not found: " + studentNumber);
        }

    }

    @GetMapping("students/yearOfBirthBetween/")
    public List<Student> getStudentBetweenBirthYears(@RequestParam(required=true) int yearLower, @RequestParam(required=true) int yearUpper) {

        try{
            return studentFeignClient.getStudentBetweenBirthYears(yearLower, yearUpper);
        } catch(FeignException e) {
            throw new StudentNotFoundException("Feign Error - Student not found between year range");
        }

    }

    @GetMapping("students/maritalStatus/")
    public List<Student> getStudentsByMaritalStatus(@RequestParam(required=true) MaritalStatus status) {

        try{
            return studentFeignClient.getStudentsByMaritalStatus(status);
        } catch(FeignException e) {
            throw new StudentNotFoundException("Feign Error - Students not found: ");
        }

    }

    @PostMapping("students/")
    public ResponseEntity<String> createStudent(@RequestBody Student newStudent) {
        try{
            ResponseEntity<String> result = studentFeignClient.createStudent(newStudent);
            return new ResponseEntity<String>("", result.getHeaders(), result.getStatusCode());
        } catch(FeignException e) {
            e.printStackTrace();
            throw new StudentGenericError("Feign Error - Error Creating Student. See Log: ");
        }
    }


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
