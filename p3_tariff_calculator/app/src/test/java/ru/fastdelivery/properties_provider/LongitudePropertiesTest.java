package ru.fastdelivery.properties_provider;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.properties.provider.LongitudeProperties;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LongitudePropertiesTest {
    @Test
    @DisplayName("Если долгота в допустимых пределах -> true")
    void whenLatitudeIsAcceptable_thanLatitudeIsAvailableReturnTrue() {
        LongitudeProperties properties = new LongitudeProperties();
        properties.setMax(70);
        properties.setMin(40);

        assertTrue(properties.longitudeIsAvailable(40.0));
        assertTrue(properties.longitudeIsAvailable(50.0));
        assertTrue(properties.longitudeIsAvailable(70.0));
        assertFalse(properties.longitudeIsAvailable(39.9));
        assertFalse(properties.longitudeIsAvailable(70.1));
    }
}
