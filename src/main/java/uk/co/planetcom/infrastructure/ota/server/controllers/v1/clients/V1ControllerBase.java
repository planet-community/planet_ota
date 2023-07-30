package uk.co.planetcom.infrastructure.ota.server.controllers.v1.clients;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class V1ControllerBase {
  protected static final String V1_API_ACCEPT_HEADER_VALUE = "application/vnd.planet.agnostos-api.v1+json";
}
