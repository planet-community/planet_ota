package uk.co.planetcom.infrastructure.ota.server.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetProductEnum;

@Converter
public class AssetProductEnumConverter implements AttributeConverter<AssetProductEnum, String> {
    @Override
    public String convertToDatabaseColumn(AssetProductEnum AssetProductEnum) {
        if (AssetProductEnum == null) throw new IllegalArgumentException("Cannot convert to DB column; `AssetProduct` argument is null.");

        return AssetProductEnum.toString();
    }

    @Override
    public AssetProductEnum convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) throw new IllegalArgumentException("Cannot convert to Entity; String argument (`AssetProduct`) is null.");

        return AssetProductEnum.valueOf(s);
    }
}
