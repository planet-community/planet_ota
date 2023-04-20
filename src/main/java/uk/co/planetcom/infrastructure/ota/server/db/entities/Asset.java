package uk.co.planetcom.infrastructure.ota.server.db.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UuidGenerator;
import uk.co.planetcom.infrastructure.ota.server.db.entities.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.db.entities.enums.converters.AssetTypeConverter;

import java.io.Serializable;
import java.net.URI;
import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "assets")
public class Asset implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    @Column(nullable = false)
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private UUID assetId; /* UUID/GUID to avoid column collision */

    @NotNull
    @Column(nullable = false)
    private URI assetObjectUri; /* URI to be checked for specific Asset */

    @NotNull
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /* Restrict access from public API. */
    private ZonedDateTime releaseTimeStamp; /* When the asset is 'due' to be released to users. */

    @NotNull
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /* Restrict access from public API. */
    private ZonedDateTime uploadTimeStamp; /* When the asset was uploaded to the OTA system by Planet. */

    @Convert(converter = AssetTypeConverter.class)
    @Column(nullable = false)
    @NotNull
    private AssetType assetType; /* Can be queried from the `AssetController` class. */

    @NotNull
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /* Restrict access from public API. */
    private boolean assetSuppressed; /* Whenever the asset has been suppressed, for whatever reason. */

    public boolean isAvailable() {
        return this.releaseTimeStamp.isAfter(ZonedDateTime.now())
                && !this.assetSuppressed;
    }
}
