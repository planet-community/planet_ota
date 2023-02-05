package uk.co.planetcom.infrastructure.ota.server.controller.webhooks;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

@Slf4j
@RestController
@RequestMapping("/webhooks/github")
public class GithubWebhookController {
    private final int SIG_LEN = 45;

    @Value("${github.webhooks.secrets.codid}")
    private String GITHUB_CODID_WEBHOOK_SECRET;

    @PostMapping(value = "/codid", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> codidWebhook(@RequestHeader("X-Hub-Signature") String sig,
            @RequestBody String payload) {
        log.info("GitHub webhook: Received new webhook request.");
        try {
            log.info("Computing GitHub SHA-1 hash..");
            String computedHash = String.format("sha1=%s", new HmacUtils(HmacAlgorithms.HMAC_SHA_1,
                    GITHUB_CODID_WEBHOOK_SECRET.getBytes()).hmacHex(payload.getBytes()));

            if (sig.length() != SIG_LEN || MessageDigest.isEqual(sig.getBytes(), computedHash.getBytes())) {
                return new ResponseEntity<>("Invalid signature.\n", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.error("We encountered an exception. It's probably an error comparing the hash, but we don't want to tell the client. Return 500.");
            // Bit of explanation - we don't want to make it obvious there was an exception, because malicious attackers could use this knowledge.
            // Instead, we return BAD_REQUEST (400) status code.
            return new ResponseEntity<>("Invalid signature.\n", HttpStatus.BAD_REQUEST);
        }
        log.info("GitHub webhook: Signature processed all fine. ");

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
