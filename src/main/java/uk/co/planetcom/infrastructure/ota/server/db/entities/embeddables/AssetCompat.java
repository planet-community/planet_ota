package uk.co.planetcom.infrastructure.ota.server.db.entities.embeddables;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Embeddable
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class AssetCompat {
    @Embedded
    private CoDiFw compatCoDiFw;
    @Embedded
    private AndroidFw compatAndroidFw;

    @Data
    @Builder
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public final static class CoDiFw {
        private List<String> resourcesCompatVersions;
        private List<String> ospiCompatVersions;
    }

    @Data
    @Builder
    @Embeddable
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public final static class AndroidFw {
        private List<String> baseBandCompatVersions;
    }
}
