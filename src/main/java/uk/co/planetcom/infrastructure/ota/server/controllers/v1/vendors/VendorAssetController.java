package uk.co.planetcom.infrastructure.ota.server.controllers.v1.vendors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.planetcom.infrastructure.ota.server.services.AssetService;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/assets/vendor")
public final class VendorAssetController {
    @Autowired
    private AssetService assetService;

    /* should be auth'd */
    @DeleteMapping(value = "/by/uuid/{uuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteAssetByUuid(@PathVariable final UUID uuid,
                                                    @RequestHeader final String authToken)
    {
        try {
            assetService.delete(assetService.findByUuid(uuid).orElseThrow());
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
