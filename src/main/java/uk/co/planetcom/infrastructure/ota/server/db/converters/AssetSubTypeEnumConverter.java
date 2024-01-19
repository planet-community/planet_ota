package uk.co.planetcom.infrastructure.ota.server.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetSubTypeEnum;

@Converter
public class AssetSubTypeEnumConverter implements AttributeConverter<AssetSubTypeEnum, String> {
    @Override
    public String convertToDatabaseColumn(AssetSubTypeEnum AssetSubTypeEnum) {
        if (AssetSubTypeEnum == null) throw new IllegalArgumentException("Cannot convert to DB column; `AssetSubType` argument is null.");

        return AssetSubTypeEnum.toString();
    }

    @Override
    public AssetSubTypeEnum convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) throw new IllegalArgumentException("Cannot convert to Entity; String argument (`AssetSubType`) is null.");

        return AssetSubTypeEnum.valueOf(s);
    }
}
