package uk.co.planetcom.infrastructure.ota.server.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetType;

@Converter
public class AssetTypeConverter implements AttributeConverter<AssetType, String> {
    @Override
    public String convertToDatabaseColumn(AssetType assetType) {
        if (assetType == null) throw new IllegalArgumentException("DB error. Argument is null.");

        return assetType.toString();
    }

    @Override
    public AssetType convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) throw new IllegalArgumentException("DB error. Argument is null.");

        return AssetType.valueOf(s);
    }
}
