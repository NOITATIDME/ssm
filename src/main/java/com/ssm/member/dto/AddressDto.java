package com.ssm.member.dto;

import com.ssm.domain.member.entity.Address;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record AddressDto(
        @NotBlank
        String roadAddress,

        @NotBlank
        String addressDetail,

        @NotBlank
        String zipcode
) {

    public static AddressDto fromEntity(Address address) {
        return AddressDto.builder()
                .roadAddress(address.getRoadAddress())
                .addressDetail(address.getAddressDetail())
                .zipcode(address.getZipcode())
                .build();
    }

    public Address toEntity() {
        return Address.builder()
                .roadAddress(roadAddress)
                .addressDetail(addressDetail)
                .zipcode(zipcode)
                .build();
    }
}