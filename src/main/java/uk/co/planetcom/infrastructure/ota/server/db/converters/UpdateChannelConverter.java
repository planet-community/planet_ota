package uk.co.planetcom.infrastructure.ota.server.db.converters;

import jakarta.persistence.AttributeConverter;
import uk.co.planetcom.infrastructure.ota.server.enums.UpdateChannel;

public class UpdateChannelConverter implements AttributeConverter<UpdateChannel, String> {
    @Override
    public String convertToDatabaseColumn(UpdateChannel updateChannel) {
        return updateChannel.toString();
    }

    @Override
    public UpdateChannel convertToEntityAttribute(String s) {
        return UpdateChannel.valueOf(s);
    }
}
