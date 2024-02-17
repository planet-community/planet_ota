package uk.co.planetcom.infrastructure.ota.server.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetTypeEnum;

@Converter
public final class AssetTypeEnumConverter implements AttributeConverter<AssetTypeEnum, String> {
    @Override
    public String convertToDatabaseColumn(final AssetTypeEnum assetTypeEnum) {
        if (assetTypeEnum == null) throw new IllegalArgumentException("Cannot convert to DB column; `AssetType` argument is null.");

        return assetTypeEnum.toString();
    }

    @Override
    public AssetTypeEnum convertToEntityAttribute(final String s) {
        if (s == null || s.isEmpty()) throw new IllegalArgumentException("Cannot convert to Entity; String argument (`AssetType`) is null.");

        return AssetTypeEnum.valueOf(s);
    }
}
