package uk.co.planetcom.infrastructure.ota.server.observers.events;

import org.springframework.context.ApplicationEvent;

import java.time.ZonedDateTime;

public class BaseEvent extends ApplicationEvent {
    protected ZonedDateTime timestamp = ZonedDateTime.now();
    protected Boolean internal = false; // False if publicly announced.
    public BaseEvent(Object source) {
        super(source);
    }
}
