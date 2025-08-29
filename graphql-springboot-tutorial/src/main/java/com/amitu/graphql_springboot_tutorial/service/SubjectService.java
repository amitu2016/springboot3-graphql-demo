package com.amitu.graphql_springboot_tutorial.service;

import com.amitu.graphql_springboot_tutorial.entity.Subject;
import com.amitu.graphql_springboot_tutorial.repository.SubjectRepository;
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

    public Map<TeacherResponse, List<TeacherSubjectResponse>> getSubjectsForAllTeachers(List<TeacherResponse> teachers) {
        List<Subject> subjects = repository.findAll();

        Map<TeacherResponse, List<TeacherSubjectResponse>> batchingMap = new HashMap<TeacherResponse, List<TeacherSubjectResponse>>();
        for (TeacherResponse teacher : teachers) {
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
}
