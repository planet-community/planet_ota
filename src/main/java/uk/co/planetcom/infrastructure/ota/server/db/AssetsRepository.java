package uk.co.planetcom.infrastructure.ota.server.db;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.planetcom.infrastructure.ota.server.domain.Asset;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendor;
import uk.co.planetcom.infrastructure.ota.server.enums.UpdateChannel;

import java.util.List;
import java.util.UUID;

public interface AssetsRepository extends JpaRepository<Asset, UUID> {
    List<Asset> findAllByAssetType(AssetType assetType);

    List<Asset> findAllByAssetVendor(AssetVendor assetVendor);

    List<Asset> findAllByUpdateChannel(UpdateChannel channel);
}
