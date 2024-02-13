package uk.co.planetcom.infrastructure.ota.server.controllers.webhooks.github.cosmo_codios;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.co.planetcom.infrastructure.ota.server.controllers.webhooks.github.BaseGitHubWebhookAbstractClass;
import uk.co.planetcom.infrastructure.ota.server.observers.events.GitHubEvent;

import java.util.Map;

@Slf4j
@RestController
@Hidden
@RequestMapping("/api/v1/webhooks/github/cosmo-codios")
@Tag(name = "Cosmo-CoDiOS webhook controller", description = "Handles Cosmo-CoDiOS GitHub org webhooks")
public final class CosmoCoDiOSWebhookController extends BaseGitHubWebhookAbstractClass {

    @Value("${github.webhooks.secrets.codid}")
    private String WEBHOOK_SECRET;

    @Override
    protected void dispatch(final String GH_USER_REPO) {
        GitHubEvent evt = new GitHubEvent(this, GH_USER_REPO);
        eventSender.sendGitHubEvent(evt);
    }

    @Operation(summary = "Process incoming payloads from Cosmo-CoDiOS GitHub webhooks",
        description = "Accept JSON payloads from GitHub (Cosmo-CoDiOS/codid) for new releases, including nightlies.")
    @ApiResponses(value = {@ApiResponse(responseCode = "202",
        description = "Payload dispatched to event handler."),
        @ApiResponse(responseCode = "401",
            description = "Unauthorized signature. Possible invalid webhook.")})
    @Override
    @PostMapping(value = "/codid", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, ?>> receiveWebhook(@RequestHeader("X-Hub-Signature") String sig, @RequestBody String payload) {
        dispatch("Cosmo-CoDiOS/codid");
        return super.doReceiveWebhook(sig, payload);
    }
}
