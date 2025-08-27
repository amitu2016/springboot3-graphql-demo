package com.amitu.graphql_springboot_tutorial.service;

import java.util.ArrayList;
import java.util.List;

import com.amitu.graphql_springboot_tutorial.entity.Result;
import com.amitu.graphql_springboot_tutorial.repository.ResultRepository;
import com.amitu.graphql_springboot_tutorial.response.StudentSubjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class ResultService {
	
	@Autowired
	private ResultRepository repository;

	public List<StudentSubjectResponse> getResultForStudent(int studentId) {
		System.out.println(":: in ResultService, fetching result for student : "+studentId);
		List<Result> results = repository.findByStudentId(studentId);
		List<StudentSubjectResponse> responses = new ArrayList<StudentSubjectResponse>();
		for (Result result: results) {
			StudentSubjectResponse res = new StudentSubjectResponse();
			res.setMarks(result.getMarks());
			res.setSubjectName(result.getSubject().getSubjectName());
			responses.add(res);
		}
		return responses;
	}
	
}
