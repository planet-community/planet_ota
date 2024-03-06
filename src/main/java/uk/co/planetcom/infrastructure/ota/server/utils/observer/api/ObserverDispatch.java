package uk.co.planetcom.infrastructure.ota.server.utils.observer.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.co.planetcom.infrastructure.ota.server.utils.observer.events.AssetDeviceNotifyEvent;
import uk.co.planetcom.infrastructure.ota.server.utils.observer.events.BaseEvent;
import uk.co.planetcom.infrastructure.ota.server.utils.observer.events.GitHubEvent;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public final class ObserverDispatch implements ApplicationListener<BaseEvent> {
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);
    private final RestTemplate restTemplate = new RestTemplate();
    private final URL WEBHOOK_URL = new URL("https://webhook.site/9ade78ea-ac8f-471d-83aa-5b5a32dd02b7");

    public ObserverDispatch() throws MalformedURLException {
    }

    @Override
    public void onApplicationEvent(final BaseEvent evt) {
        executorService.submit(() -> {
            log.debug("Dispatching event: {}", evt);
            if (evt instanceof GitHubEvent) {
                handleGitHubEvent((GitHubEvent) evt);
            }
            if (evt instanceof AssetDeviceNotifyEvent) {
                handleAssetDeviceNotifyEvent((AssetDeviceNotifyEvent) evt);
            }
        });
    }

    private synchronized void handleGitHubEvent(final GitHubEvent evt) {
        log.info("Handling GitHub event: {}", evt.toString());
        // Update Asset DB.
    }

    private synchronized void handleAssetDeviceNotifyEvent(final AssetDeviceNotifyEvent evt) {
        log.info("Handling AssetDeviceNotify event: {}", evt.toString());
        // Notify devices.
        // Test with webhook.
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        final HttpEntity<String> entity = new HttpEntity<>(evt.toString(), headers);
        final ResponseEntity<?> result = restTemplate.postForEntity(WEBHOOK_URL.toString(), entity, String.class);
        log.info("Webhook response: {}", result);
    }
}
