package uk.co.planetcom.infrastructure.ota.server.controllers.webhooks;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Hidden
@RequestMapping("/webhooks/gitlab")
public class GitLabWebhookController {
}
