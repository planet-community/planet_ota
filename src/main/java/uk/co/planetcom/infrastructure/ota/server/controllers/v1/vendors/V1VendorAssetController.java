package uk.co.planetcom.infrastructure.ota.server.controllers.v1.vendors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.planetcom.infrastructure.ota.server.domain.Asset;
import uk.co.planetcom.infrastructure.ota.server.domain.AssetCompat;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendor;
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
public final class V1VendorAssetController {
    @Autowired
    private AssetService assetService;

    /* should be auth'd */
    @DeleteMapping(value = "/by/uuid/{uuid}", produces = V1_API_ACCEPT_HEADER_VALUE)
    public final ResponseEntity<Object> deleteAssetByUuid(@PathVariable UUID uuid) {
        assetService.delete(assetService.findByUuid(uuid).orElseThrow());
        return ResponseEntity.noContent().build();
    }
}

