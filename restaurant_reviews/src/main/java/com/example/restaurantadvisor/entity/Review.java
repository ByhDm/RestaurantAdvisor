package com.example.restaurantadvisor.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant_id;

    @Basic
    @Column(name = "review")
    private String review;

    @Basic
    @Column(name = "rating")
    private Integer rating;

    public Review(Restaurant restaurant_id, String review, Integer rating) {
        this.restaurant_id = restaurant_id;
        this.review = review;
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review1 = (Review) o;
        return Objects.equals(id, review1.id);
    }

    @Override
    public int hashCode() {
        return 100;
    }
}