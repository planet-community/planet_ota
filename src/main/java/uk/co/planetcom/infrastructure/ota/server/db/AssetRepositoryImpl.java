package uk.co.planetcom.infrastructure.ota.server.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;
import uk.co.planetcom.infrastructure.ota.server.db.entities.Asset;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetProductQuirkEnum;

import java.util.List;

@Component
public class AssetRepositoryImpl {
    @PersistenceContext
    private EntityManager entityManager;

    public List<Asset> findAllByAssetProductQuirkEnum(final List<AssetProductQuirkEnum> quirkEnumList) {

        return List.of();
    }
}
