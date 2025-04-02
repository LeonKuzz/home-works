package ru.fastdelivery.properties.provider;

import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.usecase.VolumePriceProvider;
import ru.fastdelivery.usecase.WeightPriceProvider;

import java.math.BigDecimal;

/**
 * Настройки базовых цен стоимости перевозки из конфига
 */
@ConfigurationProperties("cost.rub")
@Setter
public class PricesRublesProperties implements WeightPriceProvider, VolumePriceProvider {

    private BigDecimal perKg;
    private BigDecimal minimal;
    private BigDecimal perCubicMeter;
    private static Logger logger = LoggerFactory.getLogger(PricesRublesProperties.class.getName());

    @Autowired
    private CurrencyFactory currencyFactory;

    @Override
    public Price costPerKg() {
        String pr = perKg.toString();
        logger.trace("Цена за кг - {}", pr);
        return new Price(perKg, currencyFactory.create("RUB"));
    }

    @Override
    public Price costPerCubicMeter() {
        String pr = perCubicMeter.toString();
        logger.trace("Цена за кубический метр - {}", pr);
        return new Price(perCubicMeter, currencyFactory.create("RUB"));
    }

    @Override
    public Price minimalPrice() {
        String pr = minimal.toString();
        logger.trace("Минимальная цена - {}", pr);
        return new Price(minimal, currencyFactory.create("RUB"));
    }
}
