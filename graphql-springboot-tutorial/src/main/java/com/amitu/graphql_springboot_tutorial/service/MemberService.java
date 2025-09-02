package com.amitu.graphql_springboot_tutorial.service;

import java.util.ArrayList;
import java.util.List;

import com.amitu.graphql_springboot_tutorial.entity.Member;
import com.amitu.graphql_springboot_tutorial.entity.MemberType;
import com.amitu.graphql_springboot_tutorial.repository.MemberRepository;
import com.amitu.graphql_springboot_tutorial.response.MemberResponse;
import com.amitu.graphql_springboot_tutorial.response.MemberSearchResult;
import com.amitu.graphql_springboot_tutorial.response.StudentResponse;
import com.amitu.graphql_springboot_tutorial.response.TeacherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class MemberService {

	@Autowired
	private MemberRepository repository;
	
	@Autowired
	private ResultService resultService;

    @Autowired
    private SubjectService subjectService;

//	public List<StudentResponse> getStudents() {
//		System.out.println(":: in MemberService, fetching all students ::");
//		List<Member> students = repository.findByTypeIgnoreCase(MemberType.STUDENT.toString());
//		List<StudentResponse> responses = new ArrayList<StudentResponse>();
//		for(Member student : students) {
//			StudentResponse studentRes = new StudentResponse();
//			studentRes.setId(student.getId());
//			studentRes.setName(student.getFirstName()+" "+student.getLastName());
//			studentRes.setContact(student.getContact());
//
//			// get results for each student
//			//studentRes.setResult(resultService.getResultForStudent(student.getId()));
//
//			responses.add(studentRes);
//		}
//		return responses;
//	}
//
//    public List<TeacherResponse> getTeachers() {
//        System.out.println(":: in MemberService, fetching all teachers ::");
//        List<Member> teachers = repository.findByTypeIgnoreCase(MemberType.TEACHER.toString());
//        List<TeacherResponse> responses = new ArrayList<TeacherResponse>();
//        for(Member teacher : teachers) {
//            TeacherResponse teacherRes = new TeacherResponse();
//            teacherRes.setId(teacher.getId());
//            teacherRes.setName(teacher.getFirstName()+" "+teacher.getLastName());
//            teacherRes.setContact(teacher.getContact());
//
//            // get subjects for each teacher
//            //teacherRes.setSubjects(subjectService.getSubjectsForTeacher(teacher.getId()));
//
//            responses.add(teacherRes);
//        }
//        return responses;


    public List<MemberResponse> getMembers(MemberType memberType) {
        System.out.println(":: in MemberService, fetching all members of type : "+memberType);
        List<Member> members = new ArrayList<Member>();
        if(memberType == null) {
        	members = repository.findAll();
        }else {
            members = repository.findByTypeIgnoreCase(memberType.toString());
        }
        List<MemberResponse> responses = getMemberResponses(members);
        return responses;
    }

    private List<MemberResponse> getMemberResponses(List<Member> members) {
        List<MemberResponse> responses = new ArrayList<MemberResponse>();
        for(Member member : members) {
            MemberResponse memberRes = new MemberResponse();
            memberRes.setId(member.getId());
            memberRes.setName(member.getFirstName()+" "+member.getLastName());
            memberRes.setContact(member.getContact());
            memberRes.setType(MemberType.valueOf(member.getType()));
            // get results for each student
            //memberRes.setResult(resultService.getResultForStudent(member.getId()));

            responses.add(memberRes);
        }
        return responses;
    }

    public List<MemberSearchResult> getSearchResults(String name) {
        List<Member> members = repository.fetchMembersByName(name);
        if(members.isEmpty()) {
            return new ArrayList<MemberSearchResult>();
        }

        List<MemberSearchResult> responses = new ArrayList<>();
        for(Member mem : members) {
            MemberSearchResult res = new MemberSearchResult();
            res.setId(mem.getId());
            res.setName(mem.getFirstName()+" "+mem.getLastName());
            res.setContact(mem.getContact());
            res.setType(MemberType.valueOf(mem.getType()));
            responses.add(res);
        }
        return responses;
    }
}
