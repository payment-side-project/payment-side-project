package com.paymentsideproject.domain.customer.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Rank {

    NORMAL("일반"),
    ADMIN("관리자"),
    PENDING("미정");

    private final String role;

}
