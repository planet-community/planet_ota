package uk.co.planetcom.infrastructure.ota.server.utils.observer.events;

import lombok.Setter;

@Setter
public class GitHubEvent extends BaseEvent {
    private String GH_REPO_SLUG;

    public GitHubEvent(final Object source, final String GH_REPO_SLUG) {
        super(source);
        this.GH_REPO_SLUG = GH_REPO_SLUG;
    }
}
