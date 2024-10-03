package edu.school21.app.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Entity for text")
public class PastEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String hash;
    private String url;

    @Column(name = "expiration_time")
    private Long expirationTimeSeconds;

    @Column(name = "created_at")
    private Long createdAt;
}
