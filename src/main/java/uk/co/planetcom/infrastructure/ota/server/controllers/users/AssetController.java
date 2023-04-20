package uk.co.planetcom.infrastructure.ota.server.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.co.planetcom.infrastructure.ota.server.domain.Asset;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendor;
import uk.co.planetcom.infrastructure.ota.server.db.repositories.AssetsRepository;
import uk.co.planetcom.infrastructure.ota.server.services.AssetService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/assets")
public class AssetController {
    @Autowired
    private AssetService assetService;

    @GetMapping("/by/uuid/{uuid}")
    public Asset findByUuid(@PathVariable UUID uuid) {
        return assetService.findByUuid(uuid)
                .orElseThrow();
    }

    @GetMapping("/by/type/{assetType}")
    public Collection<Asset> findAssetByAssetType(@PathVariable AssetType assetType) {
        return assetService.findAllByAssetType(assetType);
    }

    @GetMapping("/")
    public Collection<Asset> findAssets() {
        return assetService.findAll();
    }

    @GetMapping("/by/vendor/{vendorType}")
    public Collection<Asset> findAssetByVendor(@PathVariable AssetVendor vendorType) {
        return assetService.findAllByVendorType(vendorType);
    }

}
