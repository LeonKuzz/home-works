package ru.fastdelivery.usecase;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.coordinate.Departure;
import ru.fastdelivery.domain.common.coordinate.Destination;
import ru.fastdelivery.domain.common.coordinate.Latitude;
import ru.fastdelivery.domain.common.coordinate.Longitude;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculatingDistanceTest {
    final Shipment shipment = mock(Shipment.class);
    final CalculatingDistance distance = new CalculatingDistance();

    @Test
    void whenTheDifferenceInLongitudeAtTheEquatorIsOneDegree_thenDistanceEquals111km(){
        when(shipment.departure()).thenReturn(new Departure(new Latitude(0.0), new Longitude(50.0)));
        when(shipment.destination()).thenReturn(new Destination(new Latitude(0.0), new Longitude(51.0)));

        var actual = Math.round(distance.getDistanceFromLatLonInKm(shipment));
        var expected = 111;

        assertEquals(expected, actual);
    }

    @Test
    void whenTheDifferenceInLatitudeIsOneDegree_thenDistanceEquals111km() {
        when(shipment.departure()).thenReturn(new Departure(new Latitude(50.0), new Longitude(50.0)));
        when(shipment.destination()).thenReturn(new Destination(new Latitude(51.0), new Longitude(50.0)));

        var actual = Math.round(distance.getDistanceFromLatLonInKm(shipment));
        var expected = 111;

        assertEquals(expected, actual);
    }
}
