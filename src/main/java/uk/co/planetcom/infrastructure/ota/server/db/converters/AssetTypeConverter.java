package uk.co.planetcom.infrastructure.ota.server.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetType;

@Converter
public class AssetTypeConverter implements AttributeConverter<AssetType, String> {
    @Override
    public String convertToDatabaseColumn(AssetType assetType) {
        return assetType.toString();
    }

    @Override
    public AssetType convertToEntityAttribute(String s) {
        return AssetType.valueOf(s);
    }
}
