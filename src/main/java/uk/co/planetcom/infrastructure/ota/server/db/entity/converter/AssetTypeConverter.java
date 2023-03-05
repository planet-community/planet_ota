package uk.co.planetcom.infrastructure.ota.server.db.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.manifests.schemas.AvailableAsset;

@Converter
public class AssetTypeConverter implements AttributeConverter<AvailableAsset.AssetType, String> {
    @Override
    public String convertToDatabaseColumn(AvailableAsset.AssetType assetType) {
        return assetType.toString();
    }

    @Override
    public AvailableAsset.AssetType convertToEntityAttribute(String s) {
        return AvailableAsset.AssetType.valueOf(s);
    }
}
