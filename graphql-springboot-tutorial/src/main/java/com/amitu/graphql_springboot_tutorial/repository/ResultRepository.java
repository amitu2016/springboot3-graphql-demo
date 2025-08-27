package com.amitu.graphql_springboot_tutorial.repository;

import java.util.List;

import com.amitu.graphql_springboot_tutorial.entity.Result;
import com.amitu.graphql_springboot_tutorial.entity.ResultID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ResultRepository extends JpaRepository<Result, ResultID>{

	List<Result> findByStudentId(int studentId);
}
