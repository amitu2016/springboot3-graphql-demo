package com.amitu.graphql_springboot_tutorial.repository;

import com.amitu.graphql_springboot_tutorial.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

;import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>{

    List<Subject> findByTeacherId(int id);
}
