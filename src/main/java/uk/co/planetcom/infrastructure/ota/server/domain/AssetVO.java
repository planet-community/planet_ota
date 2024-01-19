package uk.co.planetcom.infrastructure.ota.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.Transient;
import lombok.Data;
import uk.co.planetcom.infrastructure.ota.server.enums.*;

import java.io.Serializable;
import java.net.URI;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public final class AssetVO implements Serializable {
    @Transient
    @JsonIgnore
    @Hidden
    private final ZoneId timeZone = ZoneId.of(Optional.ofNullable(System.getenv("TZ"))
        .orElse("Europe/London"));

    private UUID assetId; /* UUID/GUID to avoid column collision */

    private String assetFileName; /* String representation of the filename that the object was uploaded as. */

    private AssetVendorEnum assetVendorEnum; /* Vendor of Asset. */

    private String assetVersion; /* Arbitrary String denoting the version of the Asset */

    private URI assetDownloadUri; /* A URI to the asset. */

    private List<String> assetChangelog; /* Newline delimited String of changes in this asset. */

    private String assetSha256Hash; /* SHA-256 hash of the asset, generate from bytes stored in RDBMS */

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) /* Restrict access from public API. */
    @Hidden
    private ZonedDateTime releaseTimeStamp; /* When the asset is 'due' to be released to users. */

    private AssetTypeEnum assetTypeEnum; /* Can be queried from the `AssetService` class. */

    private AssetSubTypeEnum assetSubTypeEnum;

    private AssetProductEnum assetProductEnum;

    private AssetOSEnum assetOSEnum;

    private AssetCompat assetCompat;

    private String assetCryptoSignature;

    @Hidden
    @JsonIgnore
    private boolean assetSuppressed; /* Whenever the asset has been suppressed, for whatever reason. */

    @JsonIgnore
    private ZonedDateTime uploadTimeStamp;

    public AssetVO(Asset o) {
        this.setAssetId(o.getAssetId());
        this.setAssetFileName(o.getAssetFileName());
        this.setAssetVendorEnum(o.getAssetVendorEnum());
        this.setAssetVersion(o.getAssetVersion());
        this.setAssetDownloadUri(o.getAssetDownloadUri());
        this.setAssetChangelog(o.getAssetChangelog());
        this.setAssetSha256Hash(o.getAssetSha256Hash());
        this.setReleaseTimeStamp(o.getReleaseTimeStamp());
        this.setAssetTypeEnum(o.getAssetTypeEnum());
        this.setAssetSubTypeEnum(o.getAssetSubTypeEnum());
        this.setAssetProductEnum(o.getAssetProductEnum());
        this.setAssetOSEnum(o.getAssetOSEnum());
        this.setAssetCompat(o.getAssetCompat());
        this.setAssetCryptoSignature(o.getAssetCryptoSignature());
        this.setAssetSuppressed(o.isAssetSuppressed());
        this.setUploadTimeStamp(o.getUploadTimeStamp());
    }

    @JsonIgnore
    @Hidden
    public boolean isAvailable() {
        return !(this.releaseTimeStamp.isAfter(ZonedDateTime.now(this.timeZone)) && !this.isAssetSuppressed());
    }

    @JsonIgnore
    @Hidden
    public boolean isNotAvailable() {
        return !isAvailable();
    }
}
