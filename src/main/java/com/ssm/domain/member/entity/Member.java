package com.ssm.domain.member.entity;


import com.ssm.domain.common.entity.BaseTimeEntity;
import com.ssm.member.dto.MemberEditRequest;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 20, unique = true)
    private String email;

    @Column(nullable = false)
    private String profile;

    @Column(nullable = false, unique = true)
    private String memberKey;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Embedded
    private Address address;

    @Builder
    public Member(String name, String email, String profile, String memberKey, Role role) {
        this.name = name;
        this.email = email;
        this.profile = profile;
        this.memberKey = memberKey;
        this.role = role;
    }

    public void addAddress(Address address) {
        this.address = address;
    }

    public void updateMember(MemberEditRequest request) {
        this.name = request.name();

        if (request.address() != null) {
            this.address = request.address().toEntity();
        }
    }
}