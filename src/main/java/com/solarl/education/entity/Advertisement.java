package com.solarl.education.entity;

import com.solarl.education.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * Сущность "Объявление"
 */
@Entity
@Table(name = "advertisement")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "category", nullable = false, length = 100)
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;

    @Column(name = "subcategory", length = 100)
    private String subcategory;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "seller")
    private String seller;

    @Column(name = "address")
    private String address;

    @Column(name = "create_date_time", nullable = false)
    @CreationTimestamp
    private LocalDateTime createDateTime;

}
