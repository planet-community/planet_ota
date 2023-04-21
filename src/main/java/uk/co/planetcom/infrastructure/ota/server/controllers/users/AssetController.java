package uk.co.planetcom.infrastructure.ota.server.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.planetcom.infrastructure.ota.server.domain.Asset;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendor;
import uk.co.planetcom.infrastructure.ota.server.services.AssetService;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/assets")
public class AssetController {
    @Autowired
    private AssetService assetService;

    @GetMapping(value = "/by/uuid/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Asset findByUuid(@PathVariable UUID uuid) {
        return assetService.findByUuid(uuid)
                .orElseThrow();
    }

    @GetMapping(value = "/by/type/{assetType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Asset> findAssetByAssetType(@PathVariable AssetType assetType) {
        return assetService.findAllByAssetType(assetType);
    }

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Asset> findAssets() {
        return assetService.findAll();
    }

    @GetMapping(value = "/by/vendor/{vendorType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Asset> findAssetByVendor(@PathVariable AssetVendor vendorType) {
        return assetService.findAllByVendorType(vendorType);
    }
}
