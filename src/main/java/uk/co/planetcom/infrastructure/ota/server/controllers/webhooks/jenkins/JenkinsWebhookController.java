package uk.co.planetcom.infrastructure.ota.server.controllers.webhooks.jenkins;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class JenkinsWebhookController {
    abstract void dispatch();
}
