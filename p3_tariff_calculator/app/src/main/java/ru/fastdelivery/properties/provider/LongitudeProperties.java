package ru.fastdelivery.properties.provider;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.fastdelivery.domain.common.coordinate.LongitudePropertiesProvider;

@Configuration
@ConfigurationProperties("longitude")
@Setter
@Getter
public class LongitudeProperties implements LongitudePropertiesProvider {
    private int min;
    private int max;

    @Override
    public boolean longitudeIsAvailable(Double longitude) {
        return longitude >= min && longitude <=max;
    }
}
