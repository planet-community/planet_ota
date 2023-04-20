package uk.co.planetcom.infrastructure.ota.server.db.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import uk.co.planetcom.infrastructure.ota.server.db.entities.enums.CoDiVendor;
import uk.co.planetcom.infrastructure.ota.server.db.entities.enums.converters.CoDiVendorConverter;

import java.io.Serializable;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "codi_bootloaders")
public class CoDiBootloaderAsset extends BaseAssetSuperClass implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NotNull
    @Column(nullable = false)
    private UUID codiBootloaderUuid; /* UUID/GUID to avoid column collision */

    @Convert(converter = CoDiVendorConverter.class)
    @NotNull
    @Column(nullable = false)
    private CoDiVendor codiVendor;
}
