package com.optumcare.member_service.controller;

import com.optumcare.member_service.entity.Member;
import com.optumcare.member_service.repository.MemberRepository;
import com.optumcare.member_service.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import org.apache.hc.client5.http.psl.PublicSuffixList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name="Member Controller", description = "Operations related to members")
@RestController
@RequestMapping("/api/members")
public class MemberController {

@Autowired
private JdbcTemplate jdbcTemplate;


    @Autowired
    private MemberService service;


    @Operation(summary = "check the summary for optum", description = "Create the members")
    @PostConstruct
    public void testDBConnection(){
        Integer count = jdbcTemplate.queryForObject("select count(*) from member",Integer.class);
        System.out.println("DB Connection Test : "+count);
    }

    @Operation(summary = "fetch the members for optum", description = "get the members")
    @GetMapping("/{id}")
    public ResponseEntity<Member> findById(@PathVariable Long id) {
        return new ResponseEntity<>(service.getMemberById(id), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Member> register(@RequestBody Member member){
        return new ResponseEntity<>(service.registerMember(member),HttpStatus.CREATED);
        }

    // Get all members
    @Operation(summary = "Get all members", description = "Fetch all members from the database")
    @GetMapping
    public ResponseEntity<List<Member>> getAllMembers() {
        return new ResponseEntity<>(service.getAllMembers(), HttpStatus.OK);
    }

    // Delete a member by ID
    @Operation(summary = "Delete member", description = "Delete a member by their ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        service.deleteMemberById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    }


