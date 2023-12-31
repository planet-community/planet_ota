package uk.co.planetcom.infrastructure.ota.server.controllers.v1.vendors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static uk.co.planetcom.infrastructure.ota.server.controllers.v1.V1Constants.V1_API_ACCEPT_HEADER_VALUE;

@RestController
@RequestMapping("/api/v1/assets/vendor")
public class V1VendorAssetController {
    @Autowired
    private AssetService assetService;

    @PostMapping(value = "/create-sample-asset", produces = V1_API_ACCEPT_HEADER_VALUE)
    public ResponseEntity<Asset> createSampleAsset() throws MalformedURLException {
        Asset asset = Asset.builder()
            .assetChangelog(Arrays.stream("Made serial connection more reliable.\nInitial work to introduce address book.\n".split("\n")).toList())
            .assetVendor(AssetVendor.CODI_OS)
            .assetCompat(AssetCompat.builder()
                .compatCoDiFw(AssetCompat.CoDiFw.builder()
                    .ospiCompatVersions(null)
                    .resourcesCompatVersions(List.of("1.1.11.4"))
                    .build())
                .compatAndroidFw(null)
                .build())
            .assetDownloadUri(URI.create("https://planet-computers-ota-assets.eu-west-2.s3.amazonaws.com/vendor/cosmo-codios/codios/v1.1.11.7/firmware.bin"))
            .assetType(AssetType.CODI_FIRMWARE)
            .assetSha256Hash("627d807c6c365f9be617bb411823a1d6b7dc8d0123c9e9bbd5bfb5f56c315606")
            .releaseTimeStamp(ZonedDateTime.now().plusSeconds(2))
            .assetVersion("v1.1.11.7")
            .updateChannel(UpdateChannel.BETA)
            .build();

        return new ResponseEntity<>(assetService.create(asset), HttpStatus.CREATED);
    }

    /* should be auth'd */
    @DeleteMapping(value = "/by/uuid/{uuid}", produces = V1_API_ACCEPT_HEADER_VALUE)
    public ResponseEntity<Object> deleteAssetByUuid(@PathVariable UUID uuid) {
        assetService.delete(assetService.findByUuid(uuid).orElseThrow());
        return ResponseEntity.noContent().build();
    }
}

