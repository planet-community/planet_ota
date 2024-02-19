package uk.co.planetcom.infrastructure.ota.server.controllers.v1.webhooks.github;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import uk.co.planetcom.infrastructure.ota.server.observers.api.EventSender;

import java.security.MessageDigest;
import java.util.Collections;
import java.util.Map;

public abstract class BaseGitHubWebhookAbstractClass {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    protected EventSender eventSender;

    private final int SIG_LEN = 45;

    private final String WEBHOOK_SECRET = "";

    protected abstract void dispatch(String GH_USER_REPO);

    public abstract ResponseEntity<Map<String, ?>> receiveWebhook(@RequestHeader("X-Hub-Signature") String sig, @RequestBody String payload);

    protected ResponseEntity<Map<String, ?>> doReceiveWebhook(String sig,
                                                              String payload) {
        log.info("GitHub webhook: Received new webhook request.");
        try {
            log.debug("Computing GitHub SHA-1 hash..");
            final byte[] computedHash = String.format("sha1=%s", new HmacUtils(HmacAlgorithms.HMAC_SHA_1,
                    WEBHOOK_SECRET.getBytes()).hmacHex(payload.getBytes()))
                .getBytes();

            log.debug("Testing GitHub webhook validity and integrity.");
            if (sig.length() != SIG_LEN || MessageDigest.isEqual(sig.getBytes(), computedHash)) {
                log.warn("Warning: Webhook invalid.");
                log.warn("Ignoring webhook call, and returning with 401.");
                log.debug("For debugging purposes, webhook payload and signature are printed below:");
                log.debug("Payload: {}", payload);
                log.debug("Signature: {}", sig);

                return new ResponseEntity<>(Collections.singletonMap("errorMessage", "INVALID_SIG"),
                    HttpStatus.UNAUTHORIZED);
            }
        } catch (final Exception e) {
            log.error("We encountered an exception. It's probably an error comparing the hash, but we don't want to tell the client. Return 403.");
            return new ResponseEntity<>(Collections.singletonMap("errorMessage", "INVALID_SIG"),
                HttpStatus.UNAUTHORIZED);
        }
        log.info("GitHub webhook: is valid.");

        return new ResponseEntity<>(Collections.singletonMap("success", true), HttpStatus.ACCEPTED);
    }
}
