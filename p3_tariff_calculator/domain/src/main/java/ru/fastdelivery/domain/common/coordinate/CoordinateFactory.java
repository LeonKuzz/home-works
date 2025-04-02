package ru.fastdelivery.domain.common.coordinate;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
public class CoordinateFactory {
    private final LatitudePropertiesProvider latitudePropertiesProvider;
    private final LongitudePropertiesProvider longitudePropertiesProvider;
    private static Logger logger = LoggerFactory.getLogger(CoordinateFactory.class.getName());

    public Latitude createLatitude(Double latitude) {
        String lat = latitude.toString();
        logger.trace("получена широта {}", lat);
        if (!latitudePropertiesProvider.latitudeIsAvailable(latitude)) {
            logger.warn("the latitude {} is incorrect, acceptable values are from 45 to 65", lat);
            throw new IllegalArgumentException("the latitude is incorrect, acceptable values are from 45 to 65");
        }
        logger.trace("latitude {} created", lat);
        return new Latitude(latitude);
    }
    public Longitude createLongitude(Double longitude) {
        String lon = longitude.toString();
        logger.trace("получена долгота {}", lon);
        if (!longitudePropertiesProvider.longitudeIsAvailable(longitude)) {
            logger.warn("the longitude {} is incorrect, acceptable values are from 30 to 96", lon);
            throw new IllegalArgumentException("the longitude is incorrect, acceptable values are from 30 to 96");
        }
        logger.trace("longitude {} created", lon);
        return new Longitude(longitude);
    }
}
