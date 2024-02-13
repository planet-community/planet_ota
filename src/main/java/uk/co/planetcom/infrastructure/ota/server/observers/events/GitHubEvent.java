package uk.co.planetcom.infrastructure.ota.server.observers.events;

import lombok.Setter;

@Setter
public final class GitHubEvent extends BaseEvent {
    private String GH_USER_REPO;

    public GitHubEvent(final Object source, final String GH_USER_REPO) {
        super(source);
        this.GH_USER_REPO = GH_USER_REPO;
    }
}
