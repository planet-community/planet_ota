package uk.co.planetcom.infrastructure.ota.server.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendorEnum;

@Converter
public final class AssetVendorEnumConverter implements AttributeConverter<AssetVendorEnum, String> {
    @Override
    public String convertToDatabaseColumn(final AssetVendorEnum assetVendorEnum) {
        if (assetVendorEnum == null)
            throw new IllegalArgumentException("Cannot convert to DB column; `AssetVendor` argument is null.");

        return assetVendorEnum.toString();
    }

    @Override
    public AssetVendorEnum convertToEntityAttribute(final String s) {
        if (s == null || s.isEmpty())
            throw new IllegalArgumentException("Cannot convert to Entity; String argument (`AssetType`) is null.");

        return AssetVendorEnum.valueOf(s);
    }
}
