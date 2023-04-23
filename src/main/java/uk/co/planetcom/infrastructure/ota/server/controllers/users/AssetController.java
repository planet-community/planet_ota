package uk.co.planetcom.infrastructure.ota.server.controllers.users;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import uk.co.planetcom.infrastructure.ota.server.domain.Asset;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendor;
import uk.co.planetcom.infrastructure.ota.server.services.AssetService;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/assets")
public class AssetController {
    @Autowired
    private AssetService assetService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Asset> findAssets(@RequestHeader(value = "X-Agnostos-DeviceId", required = false) String deviceId) {
        return assetService.findAll();
    }

    @GetMapping(value = "/by/uuid/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Asset findByUuid(@PathVariable UUID uuid, @RequestHeader(value = "X-Agnostos-DeviceId", required = false) String deviceId) {
        return assetService.findByUuid(uuid)
                .orElseThrow();
    }

    @GetMapping(value = "/by/type/{assetType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Asset> findAssetByAssetType(@PathVariable AssetType assetType, @RequestHeader(value = "X-Agnostos-DeviceId", required = false) String deviceId) {
        return assetService.findAllByAssetType(assetType);
    }

    @GetMapping(value = "/by/vendor/{vendorType}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<Asset> findAssetByVendor(@PathVariable AssetVendor vendorType, @RequestHeader(value = "X-Agnostos-DeviceId", required = false) String deviceId) {
        return assetService.findAllByVendorType(vendorType);
    }
}
