package uk.co.planetcom.infrastructure.ota.server.observers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import uk.co.planetcom.infrastructure.ota.server.observers.events.AssetDeviceNotifyEvent;
import uk.co.planetcom.infrastructure.ota.server.observers.events.GitHubEvent;

@Component
public class EventSender {
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public EventSender(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void sendGitHubEvent(final GitHubEvent evt) {
        eventPublisher.publishEvent(evt);
    }

    public void sendAssetDeviceNotifyEvent(final AssetDeviceNotifyEvent evt) {
        eventPublisher.publishEvent(evt);
    }
}
