package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.coordinate.CoordinateFactory;
import ru.fastdelivery.domain.common.coordinate.Departure;
import ru.fastdelivery.domain.common.coordinate.Destination;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class TariffCalculateUseCaseTest {

    final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
    final VolumePriceProvider volumePriceProvider = mock(VolumePriceProvider.class);
    final Currency currency = new CurrencyFactory(code -> true).create("RUB");
//    final Shipment shipment = mock(Shipment.class);

    final TariffCalculateUseCase tariffCalculateUseCase = spy(new TariffCalculateUseCase(weightPriceProvider, volumePriceProvider));

//    @Test
//    @DisplayName("Расчет стоимости доставки -> успешно")
//    void whenCalculatePrice_thenSuccess() {
//        var minimalPrice = new Price(BigDecimal.TEN, currency);
//        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
//
//        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
//        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
//
//        var shipment = new Shipment(List.of(
//                new Pack(
//                        new Weight(BigInteger.valueOf(1200)),
//                        new Volume(BigInteger.valueOf(100), BigInteger.valueOf(100), BigInteger.valueOf(100)))),
//                new CurrencyFactory(code -> true).create("RUB"),
//                new Destination(
//                        new CoordinateFactory(latitude -> true, longitude -> true).createLatitude(60.22),
//                        new CoordinateFactory(latitude -> true, longitude -> true).createLongitude(55.5)),
//                new Departure(
//                        new CoordinateFactory(latitude -> true, longitude -> true).createLatitude(60.22),
//                        new CoordinateFactory(latitude -> true, longitude -> true).createLongitude(55.5))
//                );
//
//        var expectedPrice = new Price(BigDecimal.valueOf(120), currency);
//
//        var actualPrice = tariffCalculateUseCase.calc(shipment);
//
//        assertThat(actualPrice).usingRecursiveComparison()
//                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
//                .isEqualTo(expectedPrice);
//    }
//    @BeforeEach
//    void startSpy() {
//        var minimalPrice = new Price(BigDecimal.TEN, currency);
//        when(weightPriceProvider.costPerKg()).thenReturn(new Price(BigDecimal.TEN, currency));
//        when(volumePriceProvider.costPerCubicMeter()).thenReturn(new Price(BigDecimal.TEN, currency));
//        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
//        when(volumePriceProvider.minimalPrice()).thenReturn(minimalPrice);
//
//        var shipment = new Shipment(List.of(
//                new Pack(
//                        new Weight(BigInteger.valueOf(1200)),
//                        new Volume(BigInteger.valueOf(100), BigInteger.valueOf(100), BigInteger.valueOf(100)))),
//                new CurrencyFactory(code -> true).create("RUB"),
//                new Destination(
//                        new CoordinateFactory(latitude -> true, longitude -> true).createLatitude(60.22),
//                        new CoordinateFactory(latitude -> true, longitude -> true).createLongitude(55.5)),
//                new Departure(
//                        new CoordinateFactory(latitude -> true, longitude -> true).createLatitude(60.22),
//                        new CoordinateFactory(latitude -> true, longitude -> true).createLongitude(55.5))
//                );
//        tariffCalculateUseCase.calc(shipment);
//    }
    @Test
    void whenDistanceIsLessThan450Km_thenReturnBasicPrice() {

        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var priceByWeight = new Price(BigDecimal.valueOf(100), currency);
        var priceByVolume = new Price(BigDecimal.valueOf(200), currency);

        when(weightPriceProvider.costPerKg()).thenReturn(new Price(BigDecimal.TEN, currency));
        when(volumePriceProvider.costPerCubicMeter()).thenReturn(new Price(BigDecimal.TEN, currency));
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(volumePriceProvider.minimalPrice()).thenReturn(minimalPrice);

        var shipment = new Shipment(List.of(
                new Pack(
                        new Weight(BigInteger.valueOf(1200)),
                        new Volume(BigInteger.valueOf(100), BigInteger.valueOf(100), BigInteger.valueOf(100)))),
                new CurrencyFactory(code -> true).create("RUB"),
                new Destination(
                        new CoordinateFactory(latitude -> true, longitude -> true).createLatitude(60.22),
                        new CoordinateFactory(latitude -> true, longitude -> true).createLongitude(55.5)),
                new Departure(
                        new CoordinateFactory(latitude -> true, longitude -> true).createLatitude(60.22),
                        new CoordinateFactory(latitude -> true, longitude -> true).createLongitude(55.5))
        );
        tariffCalculateUseCase.calc(shipment);
//        CalculatingDistance calculatingDistance = spy(new CalculatingDistance());
//        CalculateByVolume calculateByVolume = spy(new CalculateByVolume(volumePriceProvider));
//        CalculateByWeight calculateByWeight = spy(new CalculateByWeight(weightPriceProvider));


//        when(calculatingDistance.getDistanceFromLatLonInKm(shipment)).thenReturn(200.0);
////        when(tariffCalculateUseCase.distance(shipment)).thenReturn(200.0);
//        when(calculateByWeight.priceByWeight(shipment)).thenReturn(priceByWeight);
//        when(calculateByVolume.priceByVolume(shipment)).thenReturn(priceByVolume);
////        when(tariffCalculateUseCase.priceByVolume(shipment)).thenReturn(priceByVolume);
////        when(tariffCalculateUseCase.priceByWeight(shipment)).thenReturn(priceByWeight);
//        when(tariffCalculateUseCase.minimalPrice()).thenReturn(minimalPrice);
//        Shipment shipment = mock(Shipment.class);

        when(tariffCalculateUseCase.distance(shipment)).thenReturn(200.0);
        when(tariffCalculateUseCase.priceByVolume(shipment)).thenReturn(priceByVolume);
        when(tariffCalculateUseCase.priceByWeight(shipment)).thenReturn(priceByWeight);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(volumePriceProvider.minimalPrice()).thenReturn(minimalPrice);



        var actualPrice = tariffCalculateUseCase.calc(shipment);
        var expectedPrice = new Price(new BigDecimal(200), currency);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);

    }

    @Test
    void asda() {
        var minimalPrice = new Price(BigDecimal.TEN, currency);
        var priceByWeight = new Price(BigDecimal.valueOf(100), currency);
        var priceByVolume = new Price(BigDecimal.valueOf(200), currency);

        when(weightPriceProvider.costPerKg()).thenReturn(new Price(BigDecimal.TEN, currency));
        when(volumePriceProvider.costPerCubicMeter()).thenReturn(new Price(BigDecimal.TEN, currency));
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(volumePriceProvider.minimalPrice()).thenReturn(minimalPrice);

        var shipment = new Shipment(List.of(
                new Pack(
                        new Weight(BigInteger.valueOf(1200)),
                        new Volume(BigInteger.valueOf(100), BigInteger.valueOf(100), BigInteger.valueOf(100)))),
                new CurrencyFactory(code -> true).create("RUB"),
                new Destination(
                        new CoordinateFactory(latitude -> true, longitude -> true).createLatitude(60.22),
                        new CoordinateFactory(latitude -> true, longitude -> true).createLongitude(55.5)),
                new Departure(
                        new CoordinateFactory(latitude -> true, longitude -> true).createLatitude(60.22),
                        new CoordinateFactory(latitude -> true, longitude -> true).createLongitude(55.5))
        );
        tariffCalculateUseCase.calc(shipment);
//        CalculatingDistance calculatingDistance = spy(new CalculatingDistance());
//        CalculateByVolume calculateByVolume = spy(new CalculateByVolume(volumePriceProvider));
//        CalculateByWeight calculateByWeight = spy(new CalculateByWeight(weightPriceProvider));


//        when(calculatingDistance.getDistanceFromLatLonInKm(shipment)).thenReturn(200.0);
////        when(tariffCalculateUseCase.distance(shipment)).thenReturn(200.0);
//        when(calculateByWeight.priceByWeight(shipment)).thenReturn(priceByWeight);
//        when(calculateByVolume.priceByVolume(shipment)).thenReturn(priceByVolume);
////        when(tariffCalculateUseCase.priceByVolume(shipment)).thenReturn(priceByVolume);
////        when(tariffCalculateUseCase.priceByWeight(shipment)).thenReturn(priceByWeight);
//        when(tariffCalculateUseCase.minimalPrice()).thenReturn(minimalPrice);
//        Shipment shipment = mock(Shipment.class);

        when(tariffCalculateUseCase.distance(shipment)).thenReturn(900.0);
        when(tariffCalculateUseCase.priceByVolume(shipment)).thenReturn(priceByVolume);
        when(tariffCalculateUseCase.priceByWeight(shipment)).thenReturn(priceByWeight);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);
        when(volumePriceProvider.minimalPrice()).thenReturn(minimalPrice);



        var actualPrice = tariffCalculateUseCase.calc(shipment);
        var expectedPrice = new Price(new BigDecimal(400), currency);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);

    }

    @Test
    @DisplayName("Получение минимальной стоимости -> успешно")
    void whenMinimalPrice_thenSuccess() {
        BigDecimal minimalValue = BigDecimal.TEN;
        var minimalPrice = new Price(minimalValue, currency);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);

        var actual = tariffCalculateUseCase.minimalPrice();

        assertThat(actual).isEqualTo(minimalPrice);
    }
}