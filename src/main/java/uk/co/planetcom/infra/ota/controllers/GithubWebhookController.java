package uk.co.planetcom.infra.ota.controllers;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;

@RestController
@RequestMapping("/webhooks/github")
public class GithubWebhookController {
    private final int SIG_LEN = 45;

    @Value("${github.webhooks.secrets.codid}")
    private String GITHUB_CODID_WEBHOOK_SECRET;

    @RequestMapping(value = "/codid", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> codidWebhook(@RequestHeader("X-Hub-Signature") String sig,
                                               @RequestBody String payload) {
        if (null == sig || sig.isEmpty()) {
            return new ResponseEntity<>("No signature provided.\n", HttpStatus.BAD_REQUEST);
        }
        try {
            String computedHash = String.format("sha1=%s", new HmacUtils(HmacAlgorithms.HMAC_SHA_1,
                    GITHUB_CODID_WEBHOOK_SECRET.getBytes()).hmacHex(payload.getBytes()));

            if (sig.length() != SIG_LEN || MessageDigest.isEqual(sig.getBytes(), computedHash.getBytes())) {
                return new ResponseEntity<>("Invalid signature.\n", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
