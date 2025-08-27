package com.amitu.graphql_springboot_tutorial.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class Query {

    @QueryMapping
    public String firstQuery() {
        return "First Query Successful!";
    }
    @QueryMapping
    public String secondQuery(@Argument String firstName, @Argument String lastName) {
        return "Hello, " + firstName + " " + lastName + "!";
    }
}
