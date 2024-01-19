package uk.co.planetcom.infrastructure.ota.server.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetOSEnum;

@Converter
public class AssetOSEnumConverter implements AttributeConverter<AssetOSEnum, String> {
    @Override
    public String convertToDatabaseColumn(AssetOSEnum AssetOSEnum) {
        if (AssetOSEnum == null) throw new IllegalArgumentException("Cannot convert to DB column; `AssetOS` argument is null.");

        return AssetOSEnum.toString();
    }

    @Override
    public AssetOSEnum convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) throw new IllegalArgumentException("Cannot convert to Entity; String argument (`AssetOS`) is null.");

        return AssetOSEnum.valueOf(s);
    }
}
