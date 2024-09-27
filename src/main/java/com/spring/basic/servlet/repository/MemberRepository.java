package com.spring.basic.servlet.repository;

import com.spring.basic.servlet.domain.Member;

import java.util.List;

public interface MemberRepository {
    void save(Member member);

    List<Member> getList();

    void delete(String id);

}
