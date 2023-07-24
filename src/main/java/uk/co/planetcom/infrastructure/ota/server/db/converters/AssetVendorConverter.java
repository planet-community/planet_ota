package uk.co.planetcom.infrastructure.ota.server.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendor;

@Converter
public class AssetVendorConverter implements AttributeConverter<AssetVendor, String> {
    @Override
    public String convertToDatabaseColumn(AssetVendor assetVendor) {
        return assetVendor.toString();
    }

    @Override
    public AssetVendor convertToEntityAttribute(String s) {
        return AssetVendor.valueOf(s);
    }
}