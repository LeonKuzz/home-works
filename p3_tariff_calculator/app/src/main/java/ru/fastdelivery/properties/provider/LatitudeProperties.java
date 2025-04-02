package ru.fastdelivery.properties.provider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.coordinate.LatitudePropertiesProvider;

@Configuration
@ConfigurationProperties("latitude")
@Setter
@Getter
public class LatitudeProperties implements LatitudePropertiesProvider {

    private double min;
    private double max;

    @Override
    public boolean latitudeIsAvailable(Double latitude) {
        return latitude >= min && latitude <= max;
    }
}
