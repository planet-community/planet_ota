package uk.co.planetcom.infrastructure.ota.server.controllers.v1.webhooks.planet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

public abstract class BasePlanetAbstractWebhook {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final int SIG_LEN = 45;

    private final String WEBHOOK_SECRET = "";

    protected abstract void dispatch();

    public abstract ResponseEntity<Map<String, ?>> receiveWebhook(@RequestHeader("X-Signature") String sig, @RequestBody String payload);

    protected ResponseEntity<Map<String, ?>> doReceiveWebhook(String sig,
                                                              String payload) {
        // TODO: Validate signature.

        // Dispatch to handler.
        // TODO: Implement observer pattern for events. Integrate with MQ.
        dispatch();

        return ResponseEntity.ok(Map.of("null", "null"));
    }
}
