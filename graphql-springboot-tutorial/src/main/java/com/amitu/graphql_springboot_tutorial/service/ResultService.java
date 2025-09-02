package com.amitu.graphql_springboot_tutorial.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amitu.graphql_springboot_tutorial.entity.Result;
import com.amitu.graphql_springboot_tutorial.repository.ResultRepository;
import com.amitu.graphql_springboot_tutorial.response.MemberResponse;
import com.amitu.graphql_springboot_tutorial.response.MemberSearchResult;
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


    public Map<MemberSearchResult, List<?>> getResultaForSearch(List<MemberSearchResult> students) {
        List<Result> results = repository.findAll();

        Map<MemberSearchResult, List<?>> batchingMap = new HashMap<>();
        for (MemberSearchResult response: students) {
            List<StudentSubjectResponse> ssResponse = new ArrayList<StudentSubjectResponse>();
            for(Result result: results) {
                if(response.getId() == result.getStudent().getId()) {
                    StudentSubjectResponse res = new StudentSubjectResponse();
                    res.setMarks(result.getMarks());
                    res.setSubjectName(result.getSubject().getSubjectName());
                    ssResponse.add(res);
                }
            }
            batchingMap.put(response, ssResponse);
        }
        return batchingMap;
    }

    public Map<MemberResponse, List<?>> getResultsForAllStudents(List<MemberResponse> studemts) {
        List<Result> results = repository.findAll();

        Map<MemberResponse, List<?>> batchingMap = new HashMap<>();
        for (MemberResponse response: studemts) {
            List<StudentSubjectResponse> ssResponse = new ArrayList<StudentSubjectResponse>();
            for(Result result: results) {
                if(response.getId() == result.getStudent().getId()) {
                    StudentSubjectResponse res = new StudentSubjectResponse();
                    res.setMarks(result.getMarks());
                    res.setSubjectName(result.getSubject().getSubjectName());
                    ssResponse.add(res);
                }
            }
            batchingMap.put(response, ssResponse);
        }
        return batchingMap;
    }
}
