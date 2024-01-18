package uk.co.planetcom.infrastructure.ota.server.db.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetProduct;

@Converter
public class AssetProductConverter implements AttributeConverter<AssetProduct, String> {
    @Override
    public String convertToDatabaseColumn(AssetProduct AssetProduct) {
        if (AssetProduct == null) throw new IllegalArgumentException("Cannot convert to DB column; `AssetProduct` argument is null.");

        return AssetProduct.toString();
    }

    @Override
    public AssetProduct convertToEntityAttribute(String s) {
        if (s == null || s.isEmpty()) throw new IllegalArgumentException("Cannot convert to Entity; String argument (`AssetProduct`) is null.");

        return AssetProduct.valueOf(s);
    }
}
