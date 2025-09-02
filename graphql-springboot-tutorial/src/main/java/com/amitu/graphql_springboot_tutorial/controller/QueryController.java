package com.amitu.graphql_springboot_tutorial.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.amitu.graphql_springboot_tutorial.entity.MemberType;
import com.amitu.graphql_springboot_tutorial.response.*;
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
	
//	@QueryMapping(name = "getAllStudents")
//	public List<StudentResponse> getStudents() {
//		// fetch all students records
//		return memberService.getStudents();
//	}
//
////	// implement GraphQL Resolver or DataFetcher
////	@SchemaMapping(typeName = "StudentResponse", field = "result")
////	public List<StudentSubjectResponse> getResultsForStudents(StudentResponse student) {
////		System.out.println(":: In Graphql resolver ::");
////		return resultService.getResultForStudent(student.getId());
////	}
//
//    // implement batching
//    // graphql dataloader
//    @BatchMapping(typeName = "StudentResponse", field = "result", maxBatchSize = 10)
//    public Map<StudentResponse, List<StudentSubjectResponse>> getResultsForAllStudents(List<StudentResponse> students) {
//
//        // fetch all students records in single query
//        System.out.println(":: In Graphql batching ::");
//        System.out.println(":: Fetching all students results ::");
//        return resultService.
//                getResultsForAllStudents(students);
//    }
//
//    @QueryMapping(name = "getAllTeachers")
//    public List<TeacherResponse> getTeachers() {
//        // fetch all teachers records
//        return memberService.getTeachers();
//    }
//
////    @SchemaMapping(typeName = "TeacherResponse", field = "subject")
////    public List<TeacherSubjectResponse> getSubjectsForTeacher(TeacherResponse teacher) {
////        System.out.println(":: In Graphql resolver ::");
////        return subjectService.getSubjectsForTeacher(teacher.getId());
////    }
//
//    // implement batching
//    // graphql dataloader
//    @BatchMapping(typeName = "TeacherResponse", field = "subject", maxBatchSize = 10)
//    public Map<TeacherResponse, List<TeacherSubjectResponse>> getResultsForAllTeachers(List<TeacherResponse> teachers) {
//        // fetch all teachers records in single query
//        System.out.println(":: In Graphql batching ::");
//        System.out.println(":: Fetching all teachers subjects ::");
//        return subjectService.getSubjectsForAllTeachers(teachers);
//    }


    @QueryMapping(name = "getAllMembers")
	public List<MemberResponse> getStudents(@Argument("filter") MemberType memberType) {
            // fetch all students records
            System.out.println(" :: Filter: " + memberType);
            System.out.println(" :: Fetching all members ::");
            return memberService.getMembers(memberType);
    }

    @BatchMapping(typeName = "MemberResponse", field = "subjectData", maxBatchSize = 20)
    public Map<MemberResponse, List<?>> getRelatedDataForAllMembers(List<MemberResponse> members) {
        // fetch all members related data in single query
        System.out.println(":: In Graphql batching ::");
        System.out.println(":: Fetching all members related data ::");

        List<MemberResponse> studemts = new ArrayList<>();
        List<MemberResponse> teachers = new ArrayList<>();
        Map<MemberResponse, List<?>> outputMap = new HashMap<>();

        members.forEach(member -> {
            if(member.getType() == MemberType.STUDENT) {
                studemts.add(member);
            }else if(member.getType() == MemberType.TEACHER) {
                teachers.add(member);
            }
        });

        if(!teachers.isEmpty()) {
            Map<MemberResponse, List<?>> teacherMap = subjectService.getSubjectsForAllTeachers(teachers);
            outputMap.putAll(teacherMap);
        }

        if(!studemts.isEmpty()) {
            Map<MemberResponse, List<?>> studentMap = resultService.getResultsForAllStudents(studemts);
            outputMap.putAll(studentMap);
        }

        return outputMap;
    }

    @QueryMapping("searchByName")
    public List<MemberSearchResult> getSearchResults(@Argument String name) {
        return memberService.getSearchResults(name);
    }

    @BatchMapping(typeName = "MemberSearchResult", field = "subjectData", maxBatchSize = 20)
    public Map<MemberSearchResult, List<?>> getSearchData(List<MemberSearchResult> members) {
        List<MemberSearchResult> students = new ArrayList<>();
        List<MemberSearchResult> teachers = new ArrayList<>();
        Map<MemberSearchResult, List<?>> outputMap = new HashMap<>();

        members.forEach(member -> {
            if(member.getType() == MemberType.STUDENT) {
                students.add(member);
            }else if(member.getType() == MemberType.TEACHER) {
                teachers.add(member);
            }
        });

        if(!teachers.isEmpty()) {
           outputMap.putAll(subjectService.getSubjectSearchResults(teachers));
        }

        if(!students.isEmpty()) {
            outputMap.putAll(resultService.getResultaForSearch(students));
        }
        return outputMap;
    }

}
