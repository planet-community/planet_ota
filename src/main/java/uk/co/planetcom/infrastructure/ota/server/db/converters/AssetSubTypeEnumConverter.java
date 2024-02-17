package uk.co.planetcom.infrastructure.ota.server.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetSubTypeEnum;

@Converter
public final class AssetSubTypeEnumConverter implements AttributeConverter<AssetSubTypeEnum, String> {
    @Override
    public String convertToDatabaseColumn(final AssetSubTypeEnum AssetSubTypeEnum) {
        if (AssetSubTypeEnum == null) throw new IllegalArgumentException("Cannot convert to DB column; `AssetSubType` argument is null.");

        return AssetSubTypeEnum.toString();
    }

    @Override
    public AssetSubTypeEnum convertToEntityAttribute(final String s) {
        if (s == null || s.isEmpty()) throw new IllegalArgumentException("Cannot convert to Entity; String argument (`AssetSubType`) is null.");

        return AssetSubTypeEnum.valueOf(s);
    }
}
