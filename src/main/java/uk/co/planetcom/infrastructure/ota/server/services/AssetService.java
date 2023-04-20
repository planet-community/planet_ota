package uk.co.planetcom.infrastructure.ota.server.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.co.planetcom.infrastructure.ota.server.db.repositories.AssetsRepository;
import uk.co.planetcom.infrastructure.ota.server.domain.Asset;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AssetService {
    @Autowired
    private AssetsRepository repository;

    public Asset create(Asset entity) {
        return repository.saveAndFlush(entity);
    }

    public void delete(Asset entity) {
        repository.delete(entity);
    }

    public void suppressAsset(UUID id) {
        modifySuppressed(id, true);
    }

    public void deSuppressAsset(UUID id) {
        modifySuppressed(id, false);
    }

    private void modifySuppressed(UUID id, boolean suppression) {
        Asset asset = repository.findById(id)
                        .orElseThrow();
        asset.setAssetSuppressed(suppression);
        repository.save(asset);
    }

    public List<Asset> list() {
        return repository.findAll()
                .stream()
                .filter(Asset::isAvailable)
                .toList();
    }

    public List<Asset> listAllByVendorType(AssetVendor assetVendor) {
        return repository.findAllByAssetVendor(assetVendor)
                .stream()
                .filter(Asset::isAvailable)
                .toList();
    }

    public Optional<Asset> findByUuid(UUID uuid) {
        return repository.findById(uuid)
                .stream()
                .filter(Asset::isAvailable)
                .findFirst();
    }

    public List<Asset> listAllByAssetType(AssetType assetType) {
        return repository.findAllByAssetType(assetType)
                .stream()
                .filter(Asset::isAvailable)
                .toList();
    }
}