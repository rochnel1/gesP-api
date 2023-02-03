package com.gis.gesP.repository;

import com.gis.gesP.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    //All CRUD database methods
}
