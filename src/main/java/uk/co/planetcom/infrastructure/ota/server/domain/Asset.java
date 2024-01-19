package uk.co.planetcom.infrastructure.ota.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uk.co.planetcom.infrastructure.ota.server.db.converters.*;
import uk.co.planetcom.infrastructure.ota.server.enums.*;

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
@Entity(name = "Asset")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "assets")
// TODO: Javadoc and OpenAPI doc this class.
public final class Asset implements Serializable {
    @Transient
    @JsonIgnore
    @Hidden
    private final ZoneId timeZone = ZoneId.of(Optional.ofNullable(System.getenv("TZ"))
        .orElse("Europe/London"));

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    @Column(nullable = false, name = "id")
    @Setter(AccessLevel.NONE)
    private UUID assetId; /* UUID/GUID to avoid column collision */

    @NotNull
    @NotBlank
    @Column(nullable = false)
    private String assetFileName; /* String representation of the filename that the object was uploaded as. */

    @NotNull
    @Column(nullable = false)
    @Convert(converter = AssetVendorEnumConverter.class)
    private AssetVendorEnum assetVendorEnum; /* Vendor of Asset. */

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

    @Convert(converter = AssetTypeEnumConverter.class)
    @Column(nullable = false)
    @NotNull
    private AssetTypeEnum assetTypeEnum; /* Can be queried from the `AssetService` class. */

    @Convert(converter = AssetSubTypeEnumConverter.class)
    @Column(nullable = false)
    @NotNull
    private AssetSubTypeEnum assetSubTypeEnum;

    @Convert(converter = AssetProductEnumConverter.class)
    @Column(nullable = false)
    @NotNull
    private AssetProductEnum assetProductEnum;

    @Convert(converter = AssetOSEnumConverter.class)
    @Column(nullable = false)
    @NotNull
    private AssetOSEnum assetOSEnum;

    @Column(nullable = false)
    @Embedded
    @NotNull
    private AssetCompat assetCompat;

    @Column(nullable = false) // TODO: Look into way to valid origin.
    @NotNull
    @NotEmpty
    private String assetCryptoSignature;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /* Restrict access from public API. */
    @Hidden
    // By default, not suppressed.
    private boolean assetSuppressed; /* Whenever the asset has been suppressed, for whatever reason. */

    @NotNull
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /* Restrict access from public API. */
    private ZonedDateTime uploadTimeStamp = ZonedDateTime.now(timeZone);

    @Transient
    @JsonIgnore
    @Hidden
    public boolean isAvailable() {
        return !(this.releaseTimeStamp.isAfter(ZonedDateTime.now(this.timeZone)) && !this.isAssetSuppressed());
    }

    @Transient
    @JsonIgnore
    @Hidden
    public boolean isNotAvailable() {
        return !isAvailable();
    }
}
