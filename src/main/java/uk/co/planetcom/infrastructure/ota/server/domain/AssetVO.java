package uk.co.planetcom.infrastructure.ota.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Data;
import uk.co.planetcom.infrastructure.ota.server.db.entities.Asset;
import uk.co.planetcom.infrastructure.ota.server.db.entities.embeddables.AssetCompat;
import uk.co.planetcom.infrastructure.ota.server.db.entities.embeddables.AssetProduct;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetVendorEnum;

import java.io.Serializable;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public final class AssetVO implements Serializable {
    private UUID assetId; /* UUID/GUID to avoid column collision */

    private String assetFileName; /* String representation of the filename that the object was uploaded as. */

    private AssetVendorEnum assetVendor; /* Vendor of Asset. */

    private String assetVersion; /* Arbitrary String denoting the version of the Asset */

    private URI assetDownloadUri; /* A URI to the asset. */

    private List<String> assetChangelog; /* Newline delimited String of changes in this asset. */

    private String assetSha256Hash; /* SHA-256 hash of the asset, generate from bytes stored in RDBMS */

    @Hidden
    @JsonIgnore
    private OffsetDateTime releaseTimeStamp; /* When the asset is 'due' to be released to users. */

    private AssetProduct assetProduct;

    private AssetCompat assetCompat;

    private String assetCryptoSignature;

    @Hidden
    @JsonIgnore
    private boolean assetSuppressed; /* Whenever the asset has been suppressed, for whatever reason. */

    @JsonIgnore
    @Hidden
    private OffsetDateTime uploadTimeStamp;

    public AssetVO(final Asset o) {
        this.setAssetId(o.getAssetId());
        this.setAssetFileName(o.getAssetFileName());
        this.setAssetVendor(o.getAssetVendor());
        this.setAssetVersion(o.getAssetVersion());
        this.setAssetDownloadUri(o.getAssetDownloadUri());
        this.setAssetChangelog(o.getAssetChangelog());
        this.setAssetSha256Hash(o.getAssetSha256Hash());
        this.setReleaseTimeStamp(o.getReleaseTimeStamp());
        this.setAssetProduct(o.getAssetProduct());
        this.setAssetCompat(o.getAssetCompat());
        this.setAssetCryptoSignature(o.getAssetCryptoSignature());
        this.setAssetSuppressed(o.isAssetSuppressed());
        this.setUploadTimeStamp(o.getUploadTimeStamp());
    }

    @JsonIgnore
    @Hidden
    public boolean isAvailable() {
        return (this.releaseTimeStamp.isAfter(OffsetDateTime.now()) && !this.isAssetSuppressed());
    }

    @JsonIgnore
    @Hidden
    public boolean isNotAvailable() {
        return !isAvailable();
    }
}
