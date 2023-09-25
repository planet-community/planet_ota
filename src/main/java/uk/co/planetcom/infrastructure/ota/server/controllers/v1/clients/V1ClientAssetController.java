package uk.co.planetcom.infrastructure.ota.server.controllers.v1.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.planetcom.infrastructure.ota.server.domain.Asset;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendor;
import uk.co.planetcom.infrastructure.ota.server.enums.UpdateChannel;
import uk.co.planetcom.infrastructure.ota.server.services.AssetService;

import java.util.Collection;
import java.util.UUID;

import static uk.co.planetcom.infrastructure.ota.server.controllers.v1.V1Constants.V1_API_ACCEPT_HEADER_VALUE;

@RestController
@RequestMapping("/api/v1/assets")
public class V1ClientAssetController {
    @Autowired
    private AssetService assetService;

    @GetMapping(value = "/", produces = V1_API_ACCEPT_HEADER_VALUE)
    public Collection<Asset> findAssets() {
        return assetService.findAll();
    }

    @GetMapping(value = "/by/uuid/{uuid}", produces = V1_API_ACCEPT_HEADER_VALUE)
    public Asset findByUuid(@PathVariable UUID uuid) {
        return assetService.findByUuid(uuid)
            .orElseThrow();
    }

    @GetMapping(value = "/by/availability/available", produces = V1_API_ACCEPT_HEADER_VALUE)
    public Collection<Asset> findAllAssetsAvailable() {
        return assetService.findAllAvailable();
    }

    @GetMapping(value = "/by/availability/unavailable", produces = V1_API_ACCEPT_HEADER_VALUE)
    public Collection<Asset> findAllAssetsNotAvailable() {
        return assetService.findAllNotAvailable();
    }

    @GetMapping(value = "/by/type/{assetType}", produces = V1_API_ACCEPT_HEADER_VALUE)
    public Collection<Asset> findAssetByAssetType(@PathVariable AssetType assetType) {
        return assetService.findAllByAssetType(assetType);
    }

    @GetMapping(value = "/by/vendor/{vendorType}", produces = V1_API_ACCEPT_HEADER_VALUE)
    public Collection<Asset> findAssetByVendor(@PathVariable AssetVendor vendorType) {
        return assetService.findAllByVendorType(vendorType);
    }

    @GetMapping(value = "/by/channel/{channel}", produces = V1_API_ACCEPT_HEADER_VALUE)
    public Collection<Asset> findAssetByChannel(@PathVariable UpdateChannel channel) {
        return assetService.findAllByUpdateChannel(channel);
    }
}
