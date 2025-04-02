package ru.fastdelivery.domain.common.price;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fastdelivery.domain.common.currency.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @param amount   значение цены
 * @param currency валюта цены
 */
public record Price(
        BigDecimal amount,
        Currency currency) {

    private static Logger logger = LoggerFactory.getLogger(Price.class.getName());
    public Price {
        String am = amount.setScale(2, RoundingMode.HALF_UP).toString();
        String cur = currency.getCode();
        if (isLessThanZero(amount)) {
            logger.warn("Price Amount cannot be below Zero");
            throw new IllegalArgumentException("Price Amount cannot be below Zero!");
        }
        logger.trace("Цена {} {}", am, cur);
    }

    private static boolean isLessThanZero(BigDecimal price) {
        return BigDecimal.ZERO.compareTo(price) > 0;
    }

    public Price multiply(BigDecimal amount) {
        String am = amount.toString();
        logger.trace("Умножаем цену на {}", am);
        return new Price(this.amount.multiply(amount), this.currency);
    }

    public Price max(Price price) {
        String am1 = this.amount.setScale(2, RoundingMode.HALF_UP).toString();
        String am2 = price.amount.setScale(2, RoundingMode.HALF_UP).toString();
        logger.trace("сравниваем какая цена больше {} или {}", am1, am2);
        if (!currency.equals(price.currency)) {
            logger.warn("Cannot compare Prices in difference Currency!");
            throw new IllegalArgumentException("Cannot compare Prices in difference Currency!");
        }
        return new Price(this.amount.max(price.amount), this.currency);
    }

    public Price round() {
        String am = this.amount.setScale(5, RoundingMode.HALF_UP).toString();
        logger.trace("Округление {}", am);
        return new Price(this.amount.setScale(2, RoundingMode.UP), this.currency);
    }
}
