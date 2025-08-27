package com.amitu.graphql_springboot_tutorial.controller;

import java.util.List;

import com.amitu.graphql_springboot_tutorial.response.StudentResponse;
import com.amitu.graphql_springboot_tutorial.response.StudentSubjectResponse;
import com.amitu.graphql_springboot_tutorial.service.MemberService;
import com.amitu.graphql_springboot_tutorial.service.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;



@Controller
public class QueryController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ResultService resultService;
	
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
	
	// implement GraphQL Resolver or DataFetcher
	@SchemaMapping(typeName = "StudentResponse", field = "result")
	public List<StudentSubjectResponse> getResultsForStudents(StudentResponse student) {
		System.out.println(":: In Graphql resolver ::");
		return resultService.getResultForStudent(student.getId());
	}

}
