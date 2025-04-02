package ru.fastdelivery.domain.delivery.pack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;

import java.math.BigInteger;

/**
 * Упаковка груза
 *
 * @param weight вес товаров в упаковке
 */
public record Pack(Weight weight, Volume volume) {

    private static final Weight maxWeight = new Weight(BigInteger.valueOf(150_000));

    private static Logger logger = LoggerFactory.getLogger(Pack.class.getName());
    public Pack {
        logger.trace("Создаём упаковку");
        if (weight.greaterThan(maxWeight)) {
            throw new IllegalArgumentException("Package can't be more than " + maxWeight);
        }
        logger.trace("Вес упаковки в норме");
    }
    @Override
    public String toString() {
        return "{\nweight - " + weight.toString() + " g" + System.lineSeparator() +
                "volume - " + volume.length().toString() + " mm X " +
                              volume.width().toString() + " mm X " +
                              volume.height().toString() + " mm\n}";
    }
}
