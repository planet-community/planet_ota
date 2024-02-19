package uk.co.planetcom.infrastructure.ota.server.db;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.planetcom.infrastructure.ota.server.db.entities.Asset;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendorEnum;

import java.util.List;
import java.util.UUID;

public interface AssetRepository extends JpaRepository<Asset, UUID> {
    List<Asset> findAllByAssetVendor(final AssetVendorEnum assetVendorEnum);
}
