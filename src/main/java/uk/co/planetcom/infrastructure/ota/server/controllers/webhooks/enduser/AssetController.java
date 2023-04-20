package uk.co.planetcom.infrastructure.ota.server.controllers.webhooks.enduser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.co.planetcom.infrastructure.ota.server.db.entities.Asset;
import uk.co.planetcom.infrastructure.ota.server.db.entities.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.db.entities.enums.AssetVendor;
import uk.co.planetcom.infrastructure.ota.server.db.repositories.AssetsRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/assets")
public class AssetController {
    @Autowired
    private AssetsRepository repository;

    @GetMapping("/by/uuid/{uuid}")
    public Asset findByUuid(@PathVariable UUID uuid) {
        return repository.findById(uuid)
                .stream()
                .filter(asset -> !asset.isAvailable())
                .findFirst()
                .orElseThrow();
    }

    @GetMapping("/by/type/{assetType}")
    public Collection<Asset> findAssetByAssetType(@PathVariable AssetType assetType) {
        return repository.findAllByAssetType(assetType)
                .stream()
                .filter(asset -> !asset.isAvailable())
                .toList();
    }

    @GetMapping("/")
    public Collection<Asset> findAssets() {
        return repository.findAll()
                .stream()
                .filter(asset -> !asset.isAvailable())
                .toList();
    }

    @PutMapping("/create-asset")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Asset createAsset() throws URISyntaxException {
        Asset asset = Asset.builder()
                .assetFileName("codi_os_bootloader-v1.0.0.bin")
                .assetDownloadUri(URI.create("https://planetcom-ota-assets.s3.eu-west-2.amazonaws.com/codi_os_bootloader-v1.0.0.bin"))
                .assetSha256Hash("d95381ae553b31f414a0a66de752a7e59ef4b24c486b2f1967b7a34634c47ffe")
                .assetVersion("v1.0.0")
                .assetType(AssetType.CODI_BOOTLOADER)
                .assetVendor(AssetVendor.CODI_OS)
                .releaseTimeStamp(ZonedDateTime.now())
                .uploadTimeStamp(ZonedDateTime.now())
                .build();

        repository.saveAndFlush(asset);

        return asset;
    }
}
