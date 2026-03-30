package domain.order.entity;


import domain.customer.entity.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "order")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JoinColumn(name = "customer_id", nullable = false)
//    private Customer customer;  <- QueryDSL 사용 예정

    private Integer totalAmount;

    private String status;  // enum 추가 예정

    private LocalDateTime createdAt;

}
