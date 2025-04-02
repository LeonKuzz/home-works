package ru.fastdelivery.domain.delivery.shipment;

import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.coordinate.CoordinateFactory;
import ru.fastdelivery.domain.common.coordinate.Departure;
import ru.fastdelivery.domain.common.coordinate.Destination;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ShipmentTest {

    @Test
    void whenSummarizingWeightOfAllPackages_thenReturnSum() {
        var weight1 = new Weight(BigInteger.TEN);
        var weight2 = new Weight(BigInteger.ONE);
        var volume1 =  new Volume(BigInteger.ONE, BigInteger.ONE, BigInteger.ONE);
        var volume2 =  new Volume(BigInteger.TWO, BigInteger.TWO, BigInteger.TWO);

        var packages = List.of(new Pack(weight1, volume1), new Pack(weight2, volume2));
        var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"),
                new Destination(
                        new CoordinateFactory(latitude -> true, longitude -> true).createLatitude(60.22),
                        new CoordinateFactory(latitude -> true, longitude -> true).createLongitude(55.5)),
                new Departure(
                        new CoordinateFactory(latitude -> true, longitude -> true).createLatitude(60.22),
                        new CoordinateFactory(latitude -> true, longitude -> true).createLongitude(55.5)));

        var massOfShipment = shipment.weightAllPackages();

        assertThat(massOfShipment.weightGrams()).isEqualByComparingTo(BigInteger.valueOf(11));
    }
    @Test
    void whenSummarizingVolumeOfAllPackages_thenReturnSum() {
        var weight1 = new Weight(BigInteger.TEN);
        var weight2 = new Weight(BigInteger.ONE);
        var volume1 =  new Volume(BigInteger.valueOf(1000), BigInteger.valueOf(1000), BigInteger.valueOf(1000));
        var volume2 =  new Volume(BigInteger.valueOf(500), BigInteger.valueOf(800), BigInteger.valueOf(1000));

        var packages = List.of(new Pack(weight1, volume1), new Pack(weight2, volume2));
        var shipment = new Shipment(packages, new CurrencyFactory(code -> true).create("RUB"),
                new Destination(
                        new CoordinateFactory(latitude -> true, longitude -> true).createLatitude(60.22),
                        new CoordinateFactory(latitude -> true, longitude -> true).createLongitude(55.5)),
                new Departure(
                        new CoordinateFactory(latitude -> true, longitude -> true).createLatitude(60.22),
                        new CoordinateFactory(latitude -> true, longitude -> true).createLongitude(55.5)));

        var volumeOfShipment = shipment.volumeAllPackages();

        assertThat(volumeOfShipment).isEqualByComparingTo(new BigDecimal("1.4000"));
    }
}