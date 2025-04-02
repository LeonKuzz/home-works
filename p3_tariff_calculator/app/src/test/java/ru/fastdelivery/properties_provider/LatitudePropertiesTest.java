package ru.fastdelivery.properties_provider;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.properties.provider.LatitudeProperties;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LatitudePropertiesTest {
    @Test
    @DisplayName("Если широта в допустимых пределах -> true")
    void whenLatitudeIsAcceptable_thanLatitudeIsAvailableReturnTrue() {
        LatitudeProperties properties = new LatitudeProperties();
        properties.setMax(70);
        properties.setMin(40);

        assertTrue(properties.latitudeIsAvailable(40.0));
        assertTrue(properties.latitudeIsAvailable(50.0));
        assertTrue(properties.latitudeIsAvailable(70.0));
        assertFalse(properties.latitudeIsAvailable(39.9));
        assertFalse(properties.latitudeIsAvailable(70.1));
    }
}
