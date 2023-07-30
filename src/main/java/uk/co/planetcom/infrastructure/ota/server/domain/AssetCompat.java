package uk.co.planetcom.infrastructure.ota.server.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.relational.core.mapping.Embedded;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Embeddable
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetCompat {
    private AssetCompatCoDiFw compatCoDiFw;
    private AssetCompatAndroidFw compatAndroidFw;
}
