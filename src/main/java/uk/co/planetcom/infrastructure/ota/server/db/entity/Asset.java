package uk.co.planetcom.infrastructure.ota.server.db.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import uk.co.planetcom.infrastructure.ota.manifests.schemas.AvailableAsset;
import uk.co.planetcom.infrastructure.ota.server.db.entity.converter.AssetTypeConverter;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data @AllArgsConstructor @NoArgsConstructor @ToString
@Entity @Table(name = "assets")
public class Asset {
    @Id
    @GeneratedValue
    private UUID assetId;
    private ZonedDateTime releaseTimeStamp;
    private ZonedDateTime uploadTimeStamp;
    @Convert(converter = AssetTypeConverter.class)
    private AvailableAsset.AssetType assetType;
}
