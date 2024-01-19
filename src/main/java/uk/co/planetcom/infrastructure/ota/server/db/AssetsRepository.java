package uk.co.planetcom.infrastructure.ota.server.db;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.co.planetcom.infrastructure.ota.server.domain.Asset;
import uk.co.planetcom.infrastructure.ota.server.enums.*;

import java.util.List;
import java.util.UUID;

public interface AssetsRepository extends JpaRepository<Asset, UUID> {
    List<Asset> findAllByAssetType(AssetTypeEnum assetTypeEnum);
    List<Asset> findAllByAssetVendor(AssetVendorEnum assetVendorEnum);
    List<Asset> findAllByAssetProduct(AssetProductEnum assetProductEnum);
    List<Asset> findAllByAssetSubType(AssetSubTypeEnum assetSubTypeEnum);
    List<Asset> findAllByQuirkEnablement(List<AssetProductQuirkEnum> quirkEnums);
}
