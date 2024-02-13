package com.example.mccHomePage.Member.repository;

import com.example.mccHomePage.Member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member , Long> {
    public boolean existsByMemberNumber(String memberNumber);
}
