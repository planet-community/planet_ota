package uk.co.planetcom.infrastructure.ota.server.observers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import uk.co.planetcom.infrastructure.ota.server.observers.events.AssetDeviceNotifyEvent;
import uk.co.planetcom.infrastructure.ota.server.observers.events.BaseEvent;
import uk.co.planetcom.infrastructure.ota.server.observers.events.GitHubEvent;

@Component
public class EventSender {
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public EventSender(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public <T extends BaseEvent> void sendEvent(final T evt) {
        eventPublisher.publishEvent(evt);
    }
}
