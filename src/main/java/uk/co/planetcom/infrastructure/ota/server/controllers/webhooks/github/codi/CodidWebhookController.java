package uk.co.planetcom.infrastructure.ota.server.controllers.webhooks.github.codi;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.planetcom.infrastructure.ota.server.controllers.webhooks.github.GitHubWebhookController;

@Slf4j
@RestController
@Hidden
@RequestMapping("/api/v1/webhooks/github")
public class CodidWebhookController extends GitHubWebhookController {

    @Value("${github.webhooks.secrets.codid}")
    private String WEBHOOK_SECRET;

    @Override
    protected void dispatch() {
    }
}
