package uk.co.planetcom.infrastructure.ota.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import uk.co.planetcom.infrastructure.ota.server.db.AssetsRepository;
import uk.co.planetcom.infrastructure.ota.server.domain.Asset;
import uk.co.planetcom.infrastructure.ota.server.enums.*;
import uk.co.planetcom.infrastructure.ota.server.utils.UrlUtils;

import java.net.MalformedURLException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public final class AssetService {
    @Autowired
    private AssetsRepository repository;

    public Asset create(Asset entity) throws MalformedURLException {
        entity.setAssetFileName(UrlUtils.getUrlFileName(entity.getAssetDownloadUri().toString()));
        return repository.saveAndFlush(entity);
    }

    public void delete(Asset entity) {
        repository.delete(entity);
    }

    public void setNewReleaseTimestamp(UUID id, ZonedDateTime newReleaseTimestamp) {
        Asset asset = repository.findById(id)
            .orElseThrow(); // FIXME: handle safely.
        asset.setReleaseTimeStamp(newReleaseTimestamp);
        // Send out notification if Asset is *now* available.
        if (asset.isAvailable()) notifyDevices(asset);
        repository.saveAndFlush(asset);
    }

    private void notifyDevices(Asset o) {
        log.info("Asset updated, and now available. Begin fan-out notify.");
    }

    public void suppressAsset(UUID id) {
        modifySuppressed(id, true);
    }

    public void deSuppressAsset(UUID id) {
        modifySuppressed(id, false);
    }

    private void modifySuppressed(UUID id, boolean suppression) {
        Asset asset = repository.findById(id)
            .orElseThrow(); // FIXME: handle safely.
        // Send out notification if Asset is *now* available.
        asset.setAssetSuppressed(suppression);

        if (asset.isAvailable()) notifyDevices(asset);
        repository.saveAndFlush(asset);
    }

    public List<Asset> findAll() {
        return repository.findAll()
            .stream()
            .filter(Objects::nonNull)
            .filter(Asset::isAvailable)
            .toList();
    }

    public List<Asset> findAllByVendorType(AssetVendor assetVendor) {
        return repository.findAllByAssetVendor(assetVendor)
            .stream()
            .filter(Objects::nonNull)
            .filter(Asset::isAvailable)
            .toList();
    }

    public Optional<Asset> findByUuid(UUID uuid) {
        return repository.findById(uuid)
            .stream()
            .filter(Objects::nonNull)
            .filter(Asset::isAvailable)
            .findFirst();
    }

    public List<Asset> findAllByAssetType(AssetType assetType) {
        return repository.findAllByAssetType(assetType)
            .stream()
            .filter(Objects::nonNull)
            .filter(Asset::isAvailable)
            .toList();
    }

    public List<Asset> findAllByAssetSubType(AssetSubType assetSubType) {
        return repository.findAllByAssetSubType(assetSubType)
            .stream()
            .filter(Objects::nonNull)
            .filter(Asset::isAvailable)
            .toList();
    }
    public List<Asset> findAllByProduct(AssetProduct assetProduct) {
        return repository.findAllByAssetProduct(assetProduct)
            .stream()
            .filter(Objects::nonNull)
            .filter(Asset::isAvailable)
            .toList();
    }

    public List<Asset> findAllAvailable() {
        return repository.findAll()
            .stream()
            .filter(Objects::nonNull)
            .filter(Asset::isAvailable)
            .toList();
    }

    public List<Asset> findAllUnavailable() {
        return repository.findAll()
            .stream()
            .filter(Objects::nonNull)
            .filter(Asset::isNotAvailable)
            .toList();
    }
}
