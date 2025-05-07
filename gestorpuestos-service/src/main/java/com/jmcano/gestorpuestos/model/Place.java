package com.jmcano.gestorpuestos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "places")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private int x;

    @Column(nullable = false)
    private int y;

    @OneToOne
    @JoinColumn(name = "preferred_user_id", nullable = true)
    private User preferredUser;
}
