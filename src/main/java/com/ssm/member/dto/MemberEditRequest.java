package com.ssm.member.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberEditRequest(
        @NotBlank String name,
        AddressDto address
) {
}