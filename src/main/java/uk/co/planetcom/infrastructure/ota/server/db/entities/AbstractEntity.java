package uk.co.planetcom.infrastructure.ota.server.db.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.UUID;

@MappedSuperclass
public abstract  class AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
}
