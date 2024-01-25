package uk.co.planetcom.infrastructure.ota.server.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import uk.co.planetcom.infrastructure.ota.server.db.converters.AssetOSEnumConverter;
import uk.co.planetcom.infrastructure.ota.server.db.converters.AssetProductEnumConverter;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetOSEnum;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetProductEnum;
import uk.co.planetcom.infrastructure.ota.server.enums.AssetProductQuirkEnum;

import java.util.Map;

@Embeddable
@Builder
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public final class AssetProduct {
    @Convert(converter = AssetProductEnumConverter.class)
    private final AssetProductEnum product;

    @ElementCollection
    @MapKeyColumn(name="product_quirk_enum")
    @Column(name="product_quirk_enablement")
    private final Map<AssetProductQuirkEnum, Boolean> productQuirkMap;

    @Convert(converter = AssetOSEnumConverter.class)
    private final AssetOSEnum productOS;
}
