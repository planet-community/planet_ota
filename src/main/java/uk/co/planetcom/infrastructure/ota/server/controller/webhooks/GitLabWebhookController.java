package uk.co.planetcom.infrastructure.ota.server.controller.webhooks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/webhooks/gitlab")
public class GitLabWebhookController {
    @Value("${gitlab.webhooks.secrets.codid}")
    private String GITLAB_CODID_WEBHOOK_SECRET;
}
