package com.amitu.graphql_springboot_tutorial.response;

import com.amitu.graphql_springboot_tutorial.entity.MemberType;
import lombok.Data;

@Data
public class MemberSearchResult {

    private int id;

    private String name;

    private String contact;

    private MemberType type;
}
