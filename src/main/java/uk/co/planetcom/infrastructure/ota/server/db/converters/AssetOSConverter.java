package uk.co.planetcom.infrastructure.ota.server.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetOS;

@Converter
public class AssetOSConverter implements AttributeConverter<AssetOS, String> {
    @Override
    public String convertToDatabaseColumn(AssetOS AssetOS) {
        if (AssetOS == null) throw new IllegalArgumentException("Cannot convert to DB column; `AssetOS` argument is null.");

        return AssetOS.toString();
    }

    @Override
    public AssetOS convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) throw new IllegalArgumentException("Cannot convert to Entity; String argument (`AssetOS`) is null.");

        return AssetOS.valueOf(s);
    }
}
