package uk.co.planetcom.infrastructure.ota.server.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.planetcom.infrastructure.ota.server.db.entity.Asset;

import java.util.UUID;

public interface AssetsRepository extends JpaRepository<Asset, UUID> {}
