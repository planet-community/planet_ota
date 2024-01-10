package uk.co.planetcom.infrastructure.ota.server.controllers.v1.clients;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.planetcom.infrastructure.ota.server.domain.Asset;
import uk.co.planetcom.infrastructure.ota.server.domain.AssetVO;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendor;
import uk.co.planetcom.infrastructure.ota.server.services.AssetService;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

import static uk.co.planetcom.infrastructure.ota.server.controllers.v1.V1Constants.V1_API_ACCEPT_HEADER_VALUE;

@RestController
@RequestMapping("/api/v1/assets")
@Tag(name = "Client-focused Assets Controller (v1)", description = "REST endpoint for Clients to query Assets")
public final class V1ClientAssetController {
    @Autowired
    private AssetService assetService;

    @GetMapping(value = "/", produces = V1_API_ACCEPT_HEADER_VALUE)
    @Operation(summary = "Get all *available* assets", description = "Get a JSON array of all *available* Assets")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "500", description = "Errored operation")
    })
    public ResponseEntity<Collection<AssetVO>> findAssets() {
        return ResponseEntity.ok(assetService.findAll()
            .stream()
            .map(AssetVO::new)
            .collect(Collectors.toList())); // FIXME: Dangerous - handle errors with try/catch.
    }

    @GetMapping(value = "/by/uuid/{uuid}", produces = V1_API_ACCEPT_HEADER_VALUE)
    public ResponseEntity<AssetVO> findByUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(assetService.findAll()
            .stream()
            .map(AssetVO::new)
            .findFirst()
            .orElseThrow()); // FIXME: Dangerous - handle errors with try/catch.
    }

    @GetMapping(value = "/by/availability/available", produces = V1_API_ACCEPT_HEADER_VALUE)
    public ResponseEntity<Collection<AssetVO>> findAllAssetsAvailable() {
        return ResponseEntity.ok(assetService.findAllAvailable()
            .stream()
            .map(AssetVO::new)
            .collect(Collectors.toList())); // FIXME: Dangerous - handle errors with try/catch.
    }

    @GetMapping(value = "/by/availability/unavailable", produces = V1_API_ACCEPT_HEADER_VALUE)
    public ResponseEntity<Collection<AssetVO>> findAllAssetsNotAvailable() {
        return ResponseEntity.ok(assetService.findAllUnavailable()
            .stream()
            .map(AssetVO::new)
            .collect(Collectors.toList())); // FIXME: Dangerous - handle errors with try/catch.
    }

    @GetMapping(value = "/by/type/{assetType}", produces = V1_API_ACCEPT_HEADER_VALUE)
    public ResponseEntity<Collection<AssetVO>> findAssetByAssetType(@PathVariable AssetType assetType) {
        return ResponseEntity.ok(assetService.findAllByAssetType(assetType)
            .stream()
            .map(AssetVO::new)
            .collect(Collectors.toList())); // FIXME: Dangerous - handle errors with try/catch.
    }

    @GetMapping(value = "/by/vendor/{vendorType}", produces = V1_API_ACCEPT_HEADER_VALUE)
    public ResponseEntity<Collection<AssetVO>> findAssetByVendor(@PathVariable AssetVendor vendorType) {
        return ResponseEntity.ok(assetService.findAllByVendorType(vendorType)
            .stream()
            .map(AssetVO::new)
            .collect(Collectors.toList())); // FIXME: Dangerous - handle errors with try/catch.
    }
}
