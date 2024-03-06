package uk.co.planetcom.infrastructure.ota.server.db.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@Builder
@Entity(name = "AssetEvent")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "AssetEvents")
public final class AssetEvent extends AbstractEntity implements Serializable {
    private Asset assocAsset; // Associated `Asset` object – should be from JPA.
    private OffsetDateTime eventTimestamp; // Timestamp of the event.
    private String eventMessage; // Message of the event. (FIXME: Should be an enum?)
}
