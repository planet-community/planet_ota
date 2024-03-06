package uk.co.planetcom.infrastructure.ota.server.db.entities;

import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.co.planetcom.infrastructure.ota.server.utils.observer.api.EventSender;
import uk.co.planetcom.infrastructure.ota.server.utils.observer.events.AssetDeviceNotifyEvent;

@Slf4j
@Component
public class AssetEntityListener {
    @Autowired
    private EventSender eventSender;

    @PostPersist
    @PostUpdate
    @PostRemove
    public void notifyAssetEvent(final Asset asset) {
        log.info("Asset updated, and now available. Notifying Observers.");
        AssetDeviceNotifyEvent evt = new AssetDeviceNotifyEvent(this, asset);
        eventSender.sendEvent(evt);
    }

}
