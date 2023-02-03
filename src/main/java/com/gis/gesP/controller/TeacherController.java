package com.gis.gesP.controller;

import com.gis.gesP.exception.RessourceNotFoundExeption;
import com.gis.gesP.model.Teacher;
import com.gis.gesP.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    private TeacherRepository teacherRepository;

    //Read all teachers Rest API
    @GetMapping
    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    //Get teacher by id
    @GetMapping("{id}")
    public ResponseEntity<Teacher> getTeacherById(@PathVariable long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(
                () -> new RessourceNotFoundExeption("Teacher not exist with id: " + id));
        return ResponseEntity.ok(teacher);
    }

    //Create teacher Rest API
    @PostMapping
    public Teacher createTeacher(@RequestBody Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    //Update teacher Rest API
    @PutMapping("{id}")
    public ResponseEntity<Teacher> updateTeacher(@PathVariable long id, @RequestBody Teacher teacherDetails) {
        Teacher updateTeacher = teacherRepository.findById(id).orElseThrow(
                () -> new RessourceNotFoundExeption("Teacher not exist with id: " + id)
        );
        updateTeacher.setName(teacherDetails.getName());
        updateTeacher.setMatricule(teacherDetails.getMatricule());
        updateTeacher.setGrade(teacherDetails.getGrade());
        updateTeacher.setSubject(teacherDetails.getSubject());

        teacherRepository.save(updateTeacher);

        return ResponseEntity.ok(updateTeacher);
    }

    //Delete teacher Rest API
    @DeleteMapping("{id}")
    public ResponseEntity<Teacher> deleteTeacherById(@PathVariable long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(
                () -> new RessourceNotFoundExeption("Teacher not exist with id: " + id));
        teacherRepository.delete(teacher);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
