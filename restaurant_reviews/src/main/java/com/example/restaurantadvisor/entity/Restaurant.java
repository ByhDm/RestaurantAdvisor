package com.example.restaurantadvisor.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurants")
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "phone_number")
    private String phoneNumber;

    @Basic
    @Column(name = "email")
    private String email;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "creation_date")
    private LocalDate date;

    @OneToMany(mappedBy = "restaurant_id"
            , cascade = CascadeType.PERSIST
            , fetch = FetchType.LAZY)
    private List<Review> reviews;
}