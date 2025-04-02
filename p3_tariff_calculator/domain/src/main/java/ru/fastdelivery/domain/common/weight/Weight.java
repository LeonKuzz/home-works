package ru.fastdelivery.domain.common.weight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Общий класс веса
 *
 * @param weightGrams вес в граммах
 */
public record Weight(BigInteger weightGrams) implements Comparable<Weight> {

    static Logger logger = LoggerFactory.getLogger(Weight.class.getName());

    public Weight {
        String w = weightGrams.toString();
        logger.trace("Создаём новый вес упаковки");
        if (isLessThanZero(weightGrams)) {
            logger.warn("Weight cannot be below Zero!");
            throw new IllegalArgumentException("Weight cannot be below Zero!");
        }
        logger.trace("Вес {} г создан", w);
    }

    private static boolean isLessThanZero(BigInteger price) {
//        logger.trace("Проверяем, что вес больше 0");
        return BigInteger.ZERO.compareTo(price) > 0;
    }

    public static Weight zero() {
        return new Weight(BigInteger.ZERO);
    }

    public BigDecimal kilograms() {
        String w = weightGrams.toString();
        logger.trace("Переводим {} г в килограммы", w);
        BigDecimal kg = new BigDecimal(weightGrams)
                .divide(BigDecimal.valueOf(1000), 100, RoundingMode.HALF_UP);
        String stK = kg.setScale(3, RoundingMode.HALF_UP).toString();
        logger.trace("Получили {} кг", stK);
        return kg;
    }

    public Weight add(Weight additionalWeight) {
        String w1 = this.weightGrams.toString();
        String w2 = additionalWeight.toString();
        logger.trace("Складываем {} г и {} г", w1, w2);
        return new Weight(this.weightGrams.add(additionalWeight.weightGrams));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Weight weight = (Weight) o;
        return weightGrams.compareTo(weight.weightGrams) == 0;
    }


    @Override
    public int compareTo(Weight w) {
        return w.weightGrams().compareTo(weightGrams());
    }

    public boolean greaterThan(Weight w) {
        return weightGrams().compareTo(w.weightGrams()) > 0;
    }
}
