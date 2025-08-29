package com.amitu.graphql_springboot_tutorial.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amitu.graphql_springboot_tutorial.entity.Result;
import com.amitu.graphql_springboot_tutorial.repository.ResultRepository;
import com.amitu.graphql_springboot_tutorial.response.StudentResponse;
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

    public Map<StudentResponse, List<StudentSubjectResponse>> getResultsForAllStudents(List<StudentResponse> students) {
        List<Result> results = repository.findAll();

        Map<StudentResponse, List<StudentSubjectResponse>> batchingMap = new HashMap<StudentResponse, List<StudentSubjectResponse>>();
        for (StudentResponse student: students) {
            List<StudentSubjectResponse> responses = new ArrayList<StudentSubjectResponse>();
            for (Result result: results) {
                if (student.getId() == result.getStudent().getId()) {
                    StudentSubjectResponse res = new StudentSubjectResponse();
                    res.setMarks(result.getMarks());
                    res.setSubjectName(result.getSubject().getSubjectName());
                    responses.add(res);
                }
            }
            batchingMap.put(student, responses);
        }
        return batchingMap;
    }
}
