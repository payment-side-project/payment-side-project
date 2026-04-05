package com.paymentsideproject.domain.customer.entity;


import com.paymentsideproject.domain.customer.enums.Rank;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 고객 이름
    @NotBlank(message = "이름은 필수 입력값입니다.")
    private String name;

    // 고객 이메일
    @NotBlank(message = "이메일은 필수 입력값입니다.")
    private String email;


    // 고객 비밀번호
    @NotBlank(message = "비밀번호는 필수 입력값입니다.")
    private String password;

    @Enumerated(EnumType.STRING)
    private Rank rank;

}
