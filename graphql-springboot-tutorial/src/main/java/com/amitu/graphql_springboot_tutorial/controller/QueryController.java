package com.amitu.graphql_springboot_tutorial.controller;

import java.util.List;
import java.util.Map;

import com.amitu.graphql_springboot_tutorial.response.StudentResponse;
import com.amitu.graphql_springboot_tutorial.response.StudentSubjectResponse;
import com.amitu.graphql_springboot_tutorial.response.TeacherResponse;
import com.amitu.graphql_springboot_tutorial.response.TeacherSubjectResponse;
import com.amitu.graphql_springboot_tutorial.service.MemberService;
import com.amitu.graphql_springboot_tutorial.service.ResultService;
import com.amitu.graphql_springboot_tutorial.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;



@Controller
public class QueryController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ResultService resultService;

    @Autowired
    private SubjectService subjectService;
	
	@QueryMapping
	public String firstQuery() {
		return "First GQL Query successful";
	}
	
	@QueryMapping
	public String secondQuery(@Argument String firstName, @Argument String lastName) {
		return firstName + " " + lastName;
	}
	
	@QueryMapping(name = "getAllStudents")
	public List<StudentResponse> getStudents() {
		// fetch all students records
		return memberService.getStudents();
	}
	
//	// implement GraphQL Resolver or DataFetcher
//	@SchemaMapping(typeName = "StudentResponse", field = "result")
//	public List<StudentSubjectResponse> getResultsForStudents(StudentResponse student) {
//		System.out.println(":: In Graphql resolver ::");
//		return resultService.getResultForStudent(student.getId());
//	}

    // implement batching
    // graphql dataloader
    @BatchMapping(typeName = "StudentResponse", field = "result", maxBatchSize = 10)
    public Map<StudentResponse, List<StudentSubjectResponse>> getResultsForAllStudents(List<StudentResponse> students) {

        // fetch all students records in single query
        System.out.println(":: In Graphql batching ::");
        System.out.println(":: Fetching all students results ::");
        return resultService.
                getResultsForAllStudents(students);
    }

    @QueryMapping(name = "getAllTeachers")
    public List<TeacherResponse> getTeachers() {
        // fetch all teachers records
        return memberService.getTeachers();
    }

//    @SchemaMapping(typeName = "TeacherResponse", field = "subject")
//    public List<TeacherSubjectResponse> getSubjectsForTeacher(TeacherResponse teacher) {
//        System.out.println(":: In Graphql resolver ::");
//        return subjectService.getSubjectsForTeacher(teacher.getId());
//    }

    // implement batching
    // graphql dataloader
    @BatchMapping(typeName = "TeacherResponse", field = "subject", maxBatchSize = 10)
    public Map<TeacherResponse, List<TeacherSubjectResponse>> getResultsForAllTeachers(List<TeacherResponse> teachers) {
        // fetch all teachers records in single query
        System.out.println(":: In Graphql batching ::");
        System.out.println(":: Fetching all teachers subjects ::");
        return subjectService.getSubjectsForAllTeachers(teachers);
    }

}
