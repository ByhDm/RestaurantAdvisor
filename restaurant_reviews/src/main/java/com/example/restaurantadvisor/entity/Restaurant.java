package com.example.restaurantadvisor.entity;

import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "restaurants")
@Builder
@SQLDelete(sql = "UPDATE restaurants SET is_deleted = true WHERE id=?")
@Where(clause = "is_deleted=false")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "id_Boss")
    private Long idBoss;

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

    @Column(name = "update_datetime")
    @UpdateTimestamp
    private LocalDateTime updatedDateTime;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @OneToMany(mappedBy = "restaurant_id"
            , cascade = CascadeType.PERSIST
            , fetch = FetchType.LAZY)
    private List<Review> reviews;
}