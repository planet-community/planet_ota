package uk.co.planetcom.infrastructure.ota.server.controllers.v1.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uk.co.planetcom.infrastructure.ota.server.domain.Asset;
import uk.co.planetcom.infrastructure.ota.server.domain.AssetCompat;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendor;
import uk.co.planetcom.infrastructure.ota.server.enums.UpdateChannel;
import uk.co.planetcom.infrastructure.ota.server.services.AssetService;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/assets")
public class V1AssetController extends V1ControllerBase {

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

    @PostMapping(value = "/create-fw-asset", produces = V1_API_ACCEPT_HEADER_VALUE)
    public Asset createFwAsset() throws MalformedURLException {
        AssetCompat newAssetCompat = AssetCompat.builder()
                .coDiFwCompat(null)
                .androidFwCompat(AssetCompat.AndroidFwCompat.builder()
                        .baseBandCompatVersions(List.of("v1.0.0"))
                        .build())
                .build();
        Asset newAsset = Asset.builder()
                .assetCompat(newAssetCompat)
                .assetFileName("astro-android-fw-v07-patch.zip")
                .assetDownloadUri(URI.create("https://planet-aws-storage.amazonaws.com/astro-android-fw-v07-patch.zip"))
                .assetSha256Hash("4t2jglkrhjfsgjfggwrg24467")
                .assetType(AssetType.ASTRO_ANDROID_FW_PATCH)
                .assetVendor(AssetVendor.PLANET)
                .assetVersion("v07")
                .updateChannel(UpdateChannel.STABLE)
                .assetChangelog("Nothing much.")
                .uploadTimeStamp(ZonedDateTime.now())
                .releaseTimeStamp(ZonedDateTime.now().plusMinutes(6))
                .build();

        assetService.create(newAsset);

        return newAsset;
    }
}
