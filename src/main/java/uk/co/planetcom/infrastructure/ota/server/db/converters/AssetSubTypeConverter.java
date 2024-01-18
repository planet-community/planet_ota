package uk.co.planetcom.infrastructure.ota.server.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetSubType;

@Converter
public class AssetSubTypeConverter implements AttributeConverter<AssetSubType, String> {
    @Override
    public String convertToDatabaseColumn(AssetSubType AssetSubType) {
        if (AssetSubType == null) throw new IllegalArgumentException("Cannot convert to DB column; `AssetSubType` argument is null.");

        return AssetSubType.toString();
    }

    @Override
    public AssetSubType convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) throw new IllegalArgumentException("Cannot convert to Entity; String argument (`AssetSubType`) is null.");

        return AssetSubType.valueOf(s);
    }
}
