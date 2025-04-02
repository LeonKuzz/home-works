package ru.fastdelivery.domain.common.coordinate;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoordinateFactoryTest {
    LatitudePropertiesProvider latitudePropertiesProvider = mock(LatitudePropertiesProvider.class);
    LongitudePropertiesProvider longitudePropertiesProvider = mock(LongitudePropertiesProvider.class);

    CoordinateFactory factory = new CoordinateFactory(latitudePropertiesProvider
    , longitudePropertiesProvider);

    @Test
    void whenLatitudeIsAvailable_thenCreateLatitude() {
        when(latitudePropertiesProvider.latitudeIsAvailable(50.0)).thenReturn(true);
        assertThat(factory.createLatitude(50.0)).isNotNull();
    }

    @Test
    void whenLatitudeIsNotAvailable_thenThrowException () {
        when(latitudePropertiesProvider.latitudeIsAvailable(20.0)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> factory.createLatitude(20.0));
    }

    @Test
    void whenLongitudeIsAvailable_thenCreateLongitude() {
        when(longitudePropertiesProvider.longitudeIsAvailable(50.0)).thenReturn(true);
        assertThat(factory.createLongitude(50.0)).isNotNull();
    }

    @Test
    void whenLongitudeIsNotAvailable_thenThrowException () {
        when(longitudePropertiesProvider.longitudeIsAvailable(20.0)).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> factory.createLongitude(20.0));
    }
}
