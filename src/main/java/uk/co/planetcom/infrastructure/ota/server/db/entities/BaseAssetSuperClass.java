package uk.co.planetcom.infrastructure.ota.server.db.entities;

import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;
import java.net.URI;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BaseAssetSuperClass implements Serializable {
    @NotNull
    @NotBlank
    private String assetFileName; /* String representation of the filename that the object should be downloaded as. */

    @NotNull
    @NotBlank
    private String assetVersion; /* String, because Planet software versioning scheme varies greatly */

    @NotNull
    private URI assetDownloadUri; /* Generally a URI to either S3, or FileCoin. */

    @NotNull
    @NotBlank
    private String assetSha256Hash; /* SHA-256 hash of the asset, generate with utility method in this class */
}