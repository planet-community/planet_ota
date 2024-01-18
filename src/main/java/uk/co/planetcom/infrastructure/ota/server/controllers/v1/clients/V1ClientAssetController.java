package uk.co.planetcom.infrastructure.ota.server.controllers.v1.clients;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.planetcom.infrastructure.ota.server.domain.AssetVO;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetProduct;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetSubType;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendor;
import uk.co.planetcom.infrastructure.ota.server.services.AssetService;

import java.time.Duration;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static uk.co.planetcom.infrastructure.ota.server.controllers.v1.V1Constants.V1_API_ACCEPT_HEADER_VALUE;

@RestController
@RequestMapping("/api/v1/assets")
@Slf4j
@Tag(name = "Client-focused Assets Controller (v1)", description = "REST endpoint for Clients to query Assets")
public final class V1ClientAssetController {
    @Autowired
    private AssetService assetService;

    private final Bucket bucket;

    public V1ClientAssetController() {
        Bandwidth limit = Bandwidth.classic(20, Refill.greedy(20, Duration.ofMinutes(1)));

        this.bucket = Bucket.builder()
            .addLimit(limit)
            .build();
    }

    private ResponseEntity<Collection<AssetVO>> processCollectedReq(Collection<AssetVO> returnedObject) {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(returnedObject);
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
            .build();

    }

    @GetMapping(value = "/", produces = V1_API_ACCEPT_HEADER_VALUE)
    @Operation(summary = "Get all *available* assets", description = "Get a JSON array of all *available* Assets")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "500", description = "Operation encountered internal error.")
    })
    public ResponseEntity<Collection<AssetVO>> findAssets() {
        return processCollectedReq(assetService.findAll()
            .stream()
            .map(AssetVO::new)
            .collect(Collectors.toList())); // FIXME: Dangerous - handle errors with try/catch.
    }

    @GetMapping(value = "/by/uuid/{uuid}", produces = V1_API_ACCEPT_HEADER_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "500", description = "Operation encountered internal error.")
    })
    public ResponseEntity<AssetVO> findByUuid(@PathVariable UUID uuid) {
        if (bucket.tryConsume(1)) {
            return ResponseEntity.ok(assetService.findByUuid(uuid)
                .stream()
                .map(AssetVO::new)
                .findFirst()
                .orElseThrow()); // FIXME: Dangerous - handle errors with try/catch.
        }

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
            .build();
    }

    @GetMapping(value = "/by/availability/available", produces = V1_API_ACCEPT_HEADER_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "500", description = "Operation encountered internal error.")
    })
    public ResponseEntity<Collection<AssetVO>> findAllAssetsAvailable() {
        return ResponseEntity.ok(assetService.findAllAvailable()
            .stream()
            .map(AssetVO::new)
            .collect(Collectors.toList())); // FIXME: Dangerous - handle errors with try/catch.
    }

    @GetMapping(value = "/by/availability/unavailable", produces = V1_API_ACCEPT_HEADER_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "500", description = "Operation encountered internal error.")
    })
    public ResponseEntity<Collection<AssetVO>> findAllAssetsNotAvailable() {
        return ResponseEntity.ok(assetService.findAllUnavailable()
            .stream()
            .map(AssetVO::new)
            .collect(Collectors.toList())); // FIXME: Dangerous - handle errors with try/catch.
    }

    @GetMapping(value = "/by/type/{assetType}", produces = V1_API_ACCEPT_HEADER_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "500", description = "Operation encountered internal error.")
    })
    public ResponseEntity<Collection<AssetVO>> findAssetByAssetType(@PathVariable AssetType assetType) {
        return ResponseEntity.ok(assetService.findAllByAssetType(assetType)
            .stream()
            .map(AssetVO::new)
            .collect(Collectors.toList())); // FIXME: Dangerous - handle errors with try/catch.
    }

    @GetMapping(value = "/by/vendor/{vendorType}", produces = V1_API_ACCEPT_HEADER_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "500", description = "Operation encountered internal error.")
    })
    public ResponseEntity<Collection<AssetVO>> findAssetByVendor(@PathVariable AssetVendor vendorType) {
        return ResponseEntity.ok(assetService.findAllByVendorType(vendorType)
            .stream()
            .map(AssetVO::new)
            .collect(Collectors.toList())); // FIXME: Dangerous - handle errors with try/catch.
    }

    @GetMapping(value = "/by/product/{productType}", produces = V1_API_ACCEPT_HEADER_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "500", description = "Operation encountered internal error.")
    })
    public ResponseEntity<Collection<AssetVO>> findAssetByProduct(@PathVariable AssetProduct productType) {
        return ResponseEntity.ok(assetService.findAllByProduct(productType)
            .stream()
            .map(AssetVO::new)
            .collect(Collectors.toList())); // FIXME: Dangerous - handle errors with try/catch.
    }

    @GetMapping(value = "/by/subtype/{subType}", produces = V1_API_ACCEPT_HEADER_VALUE)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "500", description = "Operation encountered internal error.")
    })
    public ResponseEntity<Collection<AssetVO>> findAssetByAssetSubType(@PathVariable AssetSubType subType) {
        return ResponseEntity.ok(assetService.findAllByAssetSubType(subType)
            .stream()
            .map(AssetVO::new)
            .collect(Collectors.toList())); // FIXME: Dangerous - handle errors with try/catch.
    }
}
