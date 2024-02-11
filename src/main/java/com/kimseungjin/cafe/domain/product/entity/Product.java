package com.kimseungjin.cafe.domain.product.entity;

import com.github.f4b6a3.ulid.UlidCreator;
import com.kimseungjin.cafe.global.audit.AuditListener;
import com.kimseungjin.cafe.global.audit.Auditable;
import com.kimseungjin.cafe.global.audit.BaseTime;

import jakarta.persistence.*;

import lombok.*;

import org.hibernate.annotations.SoftDelete;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@SoftDelete
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditListener.class)
@Table(indexes = @Index(columnList = "ownerId"))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product implements Auditable {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id = UlidCreator.getMonotonicUlid().toUuid();

    @Column(nullable = false, columnDefinition = "BINARY(16)")
    private UUID ownerId;

    private String category;

    @Column(nullable = false)
    private Integer price;

    private Integer cost;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String barcode;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Enumerated(EnumType.STRING)
    private ProduceSize productSize;

    @Setter @Embedded private BaseTime baseTime;

    @Builder
    public Product(
            final UUID ownerId,
            final String category,
            final Integer price,
            final Integer cost,
            final String name,
            final String description,
            final String barcode,
            final LocalDate expirationDate,
            final ProduceSize productSize) {
        this.ownerId = ownerId;
        this.category = category;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.productSize = productSize;
    }

    public void update(
            final String category,
            final Integer price,
            final Integer cost,
            final String name,
            final String description,
            final String barcode,
            final LocalDate expirationDate,
            final ProduceSize productSize) {
        this.category = category;
        this.price = price;
        this.cost = cost;
        this.name = name;
        this.description = description;
        this.barcode = barcode;
        this.expirationDate = expirationDate;
        this.productSize = productSize;
    }
}
