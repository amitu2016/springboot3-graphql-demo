package com.amitu.graphql_springboot_tutorial.response;

import lombok.Data;

import java.util.List;

@Data
public class TeacherResponse {

    private int id;

    private String name;

    private String contact;

    private List<TeacherSubjectResponse> subjects;
}
