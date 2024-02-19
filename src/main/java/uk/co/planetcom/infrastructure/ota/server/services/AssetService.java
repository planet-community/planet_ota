package uk.co.planetcom.infrastructure.ota.server.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.planetcom.infrastructure.ota.server.db.AssetRepository;
import uk.co.planetcom.infrastructure.ota.server.db.entities.Asset;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendorEnum;
import uk.co.planetcom.infrastructure.ota.server.observers.api.EventSender;
import uk.co.planetcom.infrastructure.ota.server.observers.events.AssetDeviceNotifyEvent;
import uk.co.planetcom.infrastructure.ota.server.utils.UrlUtils;

import java.net.MalformedURLException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public final class AssetService {
    @Autowired
    private AssetRepository repository;

    @Autowired
    private EventSender eventSender;

    public Asset create(final Asset entity) throws MalformedURLException {
        entity.setAssetFileName(UrlUtils.getUrlFileName(entity.getAssetDownloadUri().toString()));
        return repository.saveAndFlush(entity);
    }

    public void delete(final Asset entity) {
        repository.delete(entity);
    }

    public void setNewReleaseTimestamp(final UUID id, final OffsetDateTime newReleaseTimestamp) {
        Asset asset = repository.findById(id)
            .orElseThrow(); // FIXME: handle safely.
        asset.setReleaseTimeStamp(newReleaseTimestamp);
        // Send out notification if Asset is *now* available.
        if (asset.isAvailable()) notifyDevices(asset);
        repository.saveAndFlush(asset);
    }

    private void notifyDevices(final Asset o) {
        log.info("Asset updated, and now available. Notifying Observers.");
        AssetDeviceNotifyEvent evt = new AssetDeviceNotifyEvent(this, o);
        eventSender.sendEvent(evt);
    }

    public void suppressAsset(final UUID id) {
        modifySuppressed(id, true);
    }

    public void deSuppressAsset(final UUID id) {
        modifySuppressed(id, false);
    }

    private void modifySuppressed(final UUID id, final boolean suppression) {
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

    public List<Asset> findAllByVendorType(final AssetVendorEnum assetVendorEnum) {
        return repository.findAllByAssetVendor(assetVendorEnum)
            .stream()
            .filter(Objects::nonNull)
            .filter(Asset::isAvailable)
            .toList();
    }

    public Optional<Asset> findByUuid(final UUID uuid) {
        return repository.findById(uuid)
            .stream()
            .filter(Asset::isAvailable)
            .findFirst();
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
