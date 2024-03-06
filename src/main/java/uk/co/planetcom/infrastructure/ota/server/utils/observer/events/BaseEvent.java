package uk.co.planetcom.infrastructure.ota.server.utils.observer.events;

import org.springframework.context.ApplicationEvent;

import java.time.OffsetDateTime;

public class BaseEvent extends ApplicationEvent {
    protected OffsetDateTime timestamp = OffsetDateTime.now();

    public BaseEvent(Object source) {
        super(source);
    }
}
