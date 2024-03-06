package uk.co.planetcom.infrastructure.ota.server.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uk.co.planetcom.infrastructure.ota.server.db.converters.AssetVendorEnumConverter;
import uk.co.planetcom.infrastructure.ota.server.db.entities.embeddables.AssetCompat;
import uk.co.planetcom.infrastructure.ota.server.db.entities.embeddables.AssetProduct;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendorEnum;
import uk.co.planetcom.infrastructure.ota.server.utils.UrlUtils;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity(name = "Asset")
@EntityListeners(AssetEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "Assets")
// TODO: Javadoc and OpenAPI doc this class.
public final class Asset extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    @Column(nullable = false, name = "id")
    @Setter(AccessLevel.NONE)
    private UUID assetId; /* UUID/GUID to avoid column collision */

    @NotNull
    @NotBlank
    @JsonIgnore
    @Hidden
    @Column(nullable = false)
    private String assetFileName; /* String representation of the filename that the object was uploaded as. */

    @NotNull
    @Column(nullable = false)
    @Convert(converter = AssetVendorEnumConverter.class)
    private AssetVendorEnum assetVendor; /* Vendor of Asset. */

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
    @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @JsonIgnore
    @Hidden
    private OffsetDateTime releaseTimeStamp; /* When the asset is 'due' to be released to users. */

    @Column(nullable = false)
    @Embedded
    @NotNull
    private AssetProduct assetProduct;

    @Column(nullable = false)
    @Embedded
    @NotNull
    private AssetCompat assetCompat;

    @Column(nullable = false) // TODO: Look into way to validate origin.
    @NotNull
    @NotEmpty
    private String assetCryptoSignature;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /* Restrict access from public API. */
    @Hidden
    @Builder.Default
    // By default, not suppressed.
    private boolean assetSuppressed = false; /* Whenever the asset has been suppressed, for whatever reason. */

    @NotNull
    @Column(nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @JsonIgnore
    @Hidden
    private OffsetDateTime uploadTimeStamp;

    @PrePersist
    @JsonIgnore
    @Transient
    @Hidden
    void preInsert() {
        if (this.uploadTimeStamp == null)
            this.uploadTimeStamp = OffsetDateTime.now();

        if (this.assetFileName == null) {
            try {
                this.assetFileName = UrlUtils.getUrlFileName(this.assetDownloadUri.toString());
            } catch (MalformedURLException e) {
                // TODO: Return a 500 error.
                throw new RuntimeException(e);
            }
        }
    }

    @Transient
    @JsonIgnore
    @Hidden
    public boolean isAvailable() {
        return (this.releaseTimeStamp.isAfter(OffsetDateTime.now()) && !this.isAssetSuppressed());
    }

    @Transient
    @JsonIgnore
    @Hidden
    public boolean isNotAvailable() {
        return !isAvailable();
    }
}
