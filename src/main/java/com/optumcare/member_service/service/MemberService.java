package com.optumcare.member_service.service;

import com.optumcare.member_service.entity.Member;
import com.optumcare.member_service.exception.ResourceNotFoundException;
import com.optumcare.member_service.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;



    // Fetch all members
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    // Delete member by ID
    public void deleteMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        memberRepository.delete(member);
    }


    public Member registerMember(Member member) {

        member.setStatus("ACTIVE");
        String msg = "New Member Created Successfully,checking Advance Kafka" + member.getName();

        String message = "New Member Created Successfully" + member.getName();
        kafkaTemplate.send("member-events", message);
        kafkaTemplate.send("notifications", msg);


        return memberRepository.save(member);

    }

    public Member getMemberById(Long id) {
//        if(id==null || id<1){
//            throw new ResourceNotFoundException("Member is not present,enter id greater than 0");
//
//        }
        return memberRepository.findById(id).orElseThrow(() -> new RuntimeException("member not found"));
    }

}
