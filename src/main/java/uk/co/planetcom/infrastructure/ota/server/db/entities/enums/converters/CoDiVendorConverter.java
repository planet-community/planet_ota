package uk.co.planetcom.infrastructure.ota.server.db.entities.enums.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import uk.co.planetcom.infrastructure.ota.server.db.entities.enums.CoDiVendor;

@Converter
public class CoDiVendorConverter implements AttributeConverter<CoDiVendor, String> {
    @Override
    public String convertToDatabaseColumn(CoDiVendor codiVendor) {
        return codiVendor.toString();
    }

    @Override
    public CoDiVendor convertToEntityAttribute(String s) {
        return CoDiVendor.valueOf(s);
    }
}