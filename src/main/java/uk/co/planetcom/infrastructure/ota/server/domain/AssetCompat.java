package uk.co.planetcom.infrastructure.ota.server.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Embeddable;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Embeddable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetCompat {
    private AssetCompatCoDiFw compatCoDiFw;
    private AssetCompatAndroidFw compatAndroidFw;
}
