package uk.co.planetcom.infrastructure.ota.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetType;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendor;
import uk.co.planetcom.infrastructure.ota.server.enums.converters.AssetTypeConverter;
import uk.co.planetcom.infrastructure.ota.server.enums.converters.AssetVendorConverter;

import java.io.Serializable;
import java.net.URI;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Slf4j
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
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    @Column(nullable = false)
    @Setter(AccessLevel.NONE)
    private UUID assetId; /* UUID/GUID to avoid column collision */

    @NotNull
    @NotBlank
    private String assetFileName; /* String representation of the filename that the object should be downloaded as. */

    @NotNull
    @Convert(converter = AssetVendorConverter.class)
    private AssetVendor assetVendor; /* Vendor of Asset. */

    @NotNull
    @NotBlank
    private String assetVersion; /* Arbitrary String denoting the version of the Asset */

    @NotNull
    private URI assetDownloadUri; /* A URI to the asset. */

    @NotNull
    @NotBlank
    private String assetChangelog; /* Newline delimited String of changes in this asset. */

    @NotNull
    @NotBlank
    private String assetSha256Hash; /* SHA-256 hash of the asset, generate with utility method in this class */

    @NotNull
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /* Restrict access from public API. */
    private ZonedDateTime releaseTimeStamp; /* When the asset is 'due' to be released to users. */

    @NotNull
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /* Restrict access from public API. */
    private ZonedDateTime uploadTimeStamp; /* When the asset was uploaded to Agnostos.. */

    @Convert(converter = AssetTypeConverter.class)
    @Column(nullable = false)
    @NotNull
    private AssetType assetType; /* Can be queried from the `AssetController` class. */

    /* CoDi firmware specifics */

    @Column(nullable = true) /* accept `null` values */
    private String compatCodiFwVer;

    @Column(nullable = true) /* accept `null` values */
    private String compatCodiResVer;

    /* End CoDi firmware specifics */

    @NotNull
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /* Restrict access from public API. */
    private boolean assetSuppressed; /* Whenever the asset has been suppressed, for whatever reason. */

    @Transient
    @JsonIgnore
    public boolean isAvailable() {
        return (this.releaseTimeStamp.isAfter(ZonedDateTime.now(ZoneId.of("Europe/London")))
                && !this.assetSuppressed);
    }

    @Transient
    @JsonIgnore
    public boolean isNotAvailable() {
        return !isAvailable();
    }
}
