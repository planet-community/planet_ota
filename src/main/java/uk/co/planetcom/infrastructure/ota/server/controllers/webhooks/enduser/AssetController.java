package uk.co.planetcom.infrastructure.ota.server.controllers.webhooks.enduser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uk.co.planetcom.infrastructure.ota.server.db.entities.Asset;
import uk.co.planetcom.infrastructure.ota.server.db.entities.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.db.repositories.AssetsRepository;

import java.net.URI;
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
                .orElseThrow();
    }

    @GetMapping("/by/type/{assetType}")
    public Collection<Asset> findAssetByAssetType(@PathVariable AssetType assetType) {
        return repository.findAllByAssetType(assetType);
    }

    @GetMapping("/")
    public Collection<Asset> findAssets() {
        return repository.findAll();
    }

    @PutMapping("/create-asset")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Asset createAsset() {
        Asset asset = new Asset();
        asset.setAssetObjectUri(URI.create("https://planet-ota.planetcom.co.uk/api/v1/assets/codi-bootloader/" + UUID.randomUUID()));
        asset.setAssetSuppressed(false);
        asset.setUploadTimeStamp(ZonedDateTime.now());
        asset.setReleaseTimeStamp(ZonedDateTime.now().plusHours(1));
        asset.setAssetType(AssetType.CODI_BOOTLOADER);

        repository.saveAndFlush(asset);

        return asset;
    }

}
