package edu.school21.app.models.hash;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "hashes")
@Data
public class HashEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hash;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at")
    private Long createdAt;
}
