package uk.co.planetcom.infrastructure.ota.server.controllers.webhooks.gitlab;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GitLabWebhookController {
    abstract void dispatch();
}
