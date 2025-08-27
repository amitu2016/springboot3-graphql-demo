package com.amitu.graphql_springboot_tutorial.repository;

import com.amitu.graphql_springboot_tutorial.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer>{

}
