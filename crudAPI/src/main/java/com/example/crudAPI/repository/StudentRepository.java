package com.example.crudAPI.repository;

import com.example.crudAPI.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}

