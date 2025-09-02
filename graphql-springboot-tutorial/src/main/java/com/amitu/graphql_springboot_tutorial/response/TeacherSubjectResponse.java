package com.amitu.graphql_springboot_tutorial.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherSubjectResponse extends SubjectResponse implements SearchResult {
    private int yearsOfExperience;
}
