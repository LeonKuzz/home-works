package ru.fastdelivery.usecase;

import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.Test;
import ru.fastdelivery.domain.common.currency.Currency;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CalculateByWeightTest {
    final WeightPriceProvider weightPriceProvider = mock(WeightPriceProvider.class);
    final Shipment shipment = mock(Shipment.class);
    final Currency currency = new CurrencyFactory(code -> true).create("RUB");

    @Test
    void whenCalculatePriceByVolume_thenSuccess () {

        var pricePerKg = new Price(BigDecimal.valueOf(100), currency);
        var minimalPrice = new Price(BigDecimal.TEN, currency);

        when(shipment.weightAllPackages()).thenReturn(new Weight(BigInteger.valueOf(1000)));
        when(weightPriceProvider.costPerKg()).thenReturn(pricePerKg);
        when(weightPriceProvider.minimalPrice()).thenReturn(minimalPrice);

        var calculator = new CalculateByWeight(weightPriceProvider);

        var actualPrice  = calculator.priceByWeight(shipment);

        var expectedPrice = new Price(BigDecimal.valueOf(100), currency);

        assertThat(actualPrice).usingRecursiveComparison()
                .withComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrice);
    }
}
