package uk.co.planetcom.infrastructure.ota.server.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendor;

@Converter
public class AssetVendorConverter implements AttributeConverter<AssetVendor, String> {
    @Override
    public String convertToDatabaseColumn(AssetVendor assetVendor) {
        if (assetVendor == null) throw new IllegalArgumentException("Cannot convert to DB column; `AssetVendor` argument is null.");

        return assetVendor.toString();
    }

    @Override
    public AssetVendor convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) throw new IllegalArgumentException("Cannot convert to Entity; String argument (`AssetType`) is null.");

        return AssetVendor.valueOf(s);
    }
}
