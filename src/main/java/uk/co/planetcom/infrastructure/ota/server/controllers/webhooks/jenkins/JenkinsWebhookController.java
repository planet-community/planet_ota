package uk.co.planetcom.infrastructure.ota.server.controllers.webhooks.jenkins;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
public abstract class JenkinsWebhookController {
    abstract void dispatch();
}
