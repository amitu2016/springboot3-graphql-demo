package com.amitu.graphql_springboot_tutorial.service;

import com.amitu.graphql_springboot_tutorial.entity.Subject;
import com.amitu.graphql_springboot_tutorial.repository.SubjectRepository;
import com.amitu.graphql_springboot_tutorial.response.MemberResponse;
import com.amitu.graphql_springboot_tutorial.response.MemberSearchResult;
import com.amitu.graphql_springboot_tutorial.response.TeacherResponse;
import com.amitu.graphql_springboot_tutorial.response.TeacherSubjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SubjectService {

    @Autowired
    private SubjectRepository repository;

    public List<TeacherSubjectResponse> getSubjectsForTeacher(int id) {
        System.out.println(" :: in SubjectService, fetching subjects for teacher : " + id);
        List<Subject> subjects = repository.findByTeacherId(id);
        List<TeacherSubjectResponse> responses = new ArrayList<TeacherSubjectResponse>();
        for (Subject subject : subjects) {
            TeacherSubjectResponse res = new TeacherSubjectResponse();
            res.setYearsOfExperience(subject.getExperience());
            res.setSubjectName(subject.getSubjectName());
            responses.add(res);
    }
        return responses;
    }

    public Map<MemberResponse, List<?>> getSubjectsForAllTeachers(List<MemberResponse> teachers) {
        List<Subject> subjects = repository.findAll();

        Map<MemberResponse, List<?>> batchingMap = new HashMap<>();
        for (MemberResponse teacher : teachers) {
            List<TeacherSubjectResponse> responses = new ArrayList<TeacherSubjectResponse>();
            for (Subject subject : subjects) {
                if (teacher.getId() == subject.getTeacher().getId()) {
                    TeacherSubjectResponse res = new TeacherSubjectResponse();
                    res.setYearsOfExperience(subject.getExperience());
                    res.setSubjectName(subject.getSubjectName());
                    responses.add(res);
                }
            }
            batchingMap.put(teacher, responses);
        }
        return batchingMap;
    }

    public Map<? extends MemberSearchResult,? extends List<?>> getSubjectSearchResults(List<MemberSearchResult> teachers) {
        List<Subject> subjects = repository.findAll();

        Map<MemberSearchResult, List<?>> batchingMap = new HashMap<>();
        for (MemberSearchResult response: teachers) {
            List<TeacherSubjectResponse> ssResponse = new ArrayList<>();
            for(Subject sub : subjects) {
                if(response.getId() == sub.getTeacher().getId()) {
                    TeacherSubjectResponse res = new TeacherSubjectResponse();
                    res.setSubjectName(sub.getSubjectName());
                    res.setYearsOfExperience(sub.getExperience());
                    ssResponse.add(res);
                }
            }
            batchingMap.put(response, ssResponse);
        }
        return batchingMap;
    }
}
