package uk.co.planetcom.infrastructure.ota.server.controller.webhooks;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/webhooks/github")
public class GitHubWebhookController {
    private final int SIG_LEN = 45;

    @Value("${github.webhooks.secrets.codid}")
    private String GITHUB_CODID_WEBHOOK_SECRET;

    @PostMapping(value = "/codid", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map> codidWebhook(@RequestHeader("X-Hub-Signature") String sig,
                                            @RequestBody String payload) {
        log.info("GitHub webhook: Received new webhook request.");
        try {
            log.debug("Computing GitHub SHA-1 hash..");
            byte[] computedHash = String.format("sha1=%s", new HmacUtils(HmacAlgorithms.HMAC_SHA_1,
                    GITHUB_CODID_WEBHOOK_SECRET.getBytes()).hmacHex(payload.getBytes()))
                    .getBytes();

            log.debug("Testing GitHub webhook validity and integrity.");
            if (sig.length() != SIG_LEN || MessageDigest.isEqual(sig.getBytes(), computedHash)) {
                log.warn("Warning: Webhook invalid.");
                log.warn("Ignoring webhook call, and returning with 401.");
                log.debug("For debugging purposes, webhook payload and signature are here:");
                log.debug("Payload: ", payload);
                log.debug("Signature: ", sig);

                return new ResponseEntity<>(Collections.singletonMap("errorMessage", "INVALID_SIG"),
                        HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("We encountered an exception. It's probably an error comparing the hash, but we don't want to tell the client. Return 500.");
            return new ResponseEntity<>(Collections.singletonMap("errorMessage", "INVALID_SIG"),
                    HttpStatus.UNAUTHORIZED);
        }
        log.info("GitHub webhook: is valid.");

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
