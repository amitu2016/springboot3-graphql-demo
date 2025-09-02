package com.amitu.graphql_springboot_tutorial.repository;

import java.util.List;

import com.amitu.graphql_springboot_tutorial.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;



@Repository
public interface MemberRepository extends JpaRepository<Member, Integer>{
	
	List<Member> findByType(String type);

	List<Member> findByTypeIgnoreCase(String type);

    @Query(value = "SELECT m FROM Member m WHERE LOWER(m.firstName) like %:name%")
    List<Member> fetchMembersByName (@Param("name") String text);
}
