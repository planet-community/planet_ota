package uk.co.planetcom.infrastructure.ota.server.observers.events;

import lombok.Getter;
import uk.co.planetcom.infrastructure.ota.server.domain.Asset;

@Getter
public class AssetDeviceNotifyEvent extends BaseEvent {
    private final Asset asset;

    public AssetDeviceNotifyEvent(final Object source, final Asset asset) {
        super(source);
        this.asset = asset;
    }
}
