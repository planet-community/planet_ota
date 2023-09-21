package uk.co.planetcom.infrastructure.ota.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import uk.co.planetcom.infrastructure.ota.server.db.converters.AssetTypeConverter;
import uk.co.planetcom.infrastructure.ota.server.db.converters.AssetVendorConverter;
import uk.co.planetcom.infrastructure.ota.server.db.converters.UpdateChannelConverter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendor;
import uk.co.planetcom.infrastructure.ota.server.enums.UpdateChannel;

import java.io.Serializable;
import java.net.URI;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "assets")
public class Asset implements Serializable {
    // Helper fields:
    @Transient
    @JsonIgnore
    private final String timeZone = Optional.ofNullable(System.getenv("TZ"))
        .orElse("Europe/London");

    @Transient
    @JsonIgnore
    private final ZonedDateTime currentTs = ZonedDateTime.now(
        ZoneId.of(this.timeZone));

    // End Helper fields.

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private UUID assetId; /* UUID/GUID to avoid column collision */

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String assetFileName; /* String representation of the filename that the object was uploaded as. */

    @NotNull
    @Column(nullable = false)
    @Convert(converter = AssetVendorConverter.class)
    private AssetVendor assetVendor; /* Vendor of Asset. */

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String assetVersion; /* Arbitrary String denoting the version of the Asset */

    @NotNull
    @Column(nullable = false)
    private URI assetDownloadUri; /* A URI to the asset. */

    @NotNull
    @ElementCollection
    @Column(nullable = false)
    private List<String> assetChangelog; /* Newline delimited String of changes in this asset. */

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String assetSha256Hash; /* SHA-256 hash of the asset, generate from bytes stored in RDBMS */

    @NotNull
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /* Restrict access from public API. */
    private ZonedDateTime releaseTimeStamp; /* When the asset is 'due' to be released to users. */

    @NotNull
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /* Restrict access from public API. */
    private ZonedDateTime uploadTimeStamp = this.currentTs; /* When the asset was uploaded to the OTA system. */

    @Convert(converter = AssetTypeConverter.class)
    @Column(nullable = false)
    @NotNull
    private AssetType assetType; /* Can be queried from the `AssetService` class. */

    @Convert(converter = UpdateChannelConverter.class)
    @Column(nullable = false)
    @NotNull
    private UpdateChannel updateChannel; /* Channel that the update is released on. */

    @Column(nullable = false)
    @Embedded
    @NotNull
    private AssetCompat assetCompat;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /* Restrict access from public API. */
    // By default, not suppressed.
    private boolean assetSuppressed = false; /* Whenever the asset has been suppressed, for whatever reason. */

    @Transient
    @JsonIgnore
    public boolean isAvailable() {
        return !(this.releaseTimeStamp.isAfter(this.currentTs) && !this.isAssetSuppressed());
    }

    @Transient
    @JsonIgnore
    public boolean isNotAvailable() {
        return !isAvailable();
    }
}
