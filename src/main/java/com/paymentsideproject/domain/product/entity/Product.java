package com.paymentsideproject.domain.product.entity;

import com.paymentsideproject.domain.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String name; // 상품명

        @Column(nullable = false)
        private Long price; // 판매가 (계산의 정확성을 위해 Long 추천)

        @Column(nullable = false)
        private Integer stock; // 재고 수량

        private String description; // 상품 설명

        @Enumerated(EnumType.STRING)
        private ProductStatus status; // ON_SALE(판매중), SOLD_OUT(품절)

        private String category; // 카테고리

        public void removeStock(int quantity) {
            int restStock = this.stock - quantity;
            if (restStock < 0) {
                throw new RuntimeException("재고가 부족합니다. (현재 재고: " + this.stock + ")");
            }
            this.stock = restStock;
        }
    }



