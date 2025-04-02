package ru.fastdelivery.domain.common.volume;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public record Volume (BigInteger length, BigInteger width, BigInteger height) {

    private static Logger logger = LoggerFactory.getLogger(Volume.class.getName());

    public BigDecimal getCubicMeter() {
        String stL = this.length.toString();
        String stW = this.width.toString();
        String stH = this.height.toString();
        logger.trace("Вычислить объём в кубических метра для упаковки с размерами {} мм, {} мм, {} мм", stL, stW, stH);
        BigDecimal volume = new BigDecimal(length
                 .multiply(width)
                 .multiply(height))
                 .divide(new BigDecimal("1000000000"), 4, RoundingMode.CEILING);
        String stV = volume.toString();
        logger.trace("Объём упаковки - {} кубических метров", stV);
        return volume;
    }
    private BigInteger roundoff(BigInteger anyLength) {
        String st = anyLength.toString();
        logger.trace("Округляем сторону с размером {} мм", st);
        BigInteger fifty = new BigInteger("50");
        if ((anyLength.mod(fifty).equals(BigInteger.ZERO))) {
            return anyLength;
        } else {
            return anyLength.divide(fifty).add(BigInteger.ONE).multiply(fifty);
        }
    }
    public Volume {
        String l = length.toString();
        String w = width.toString();
        String h = height.toString();
        logger.trace("Создаём размерные параметры ураковки (объём)");
        logger.trace("Длина {} мм", l);
        logger.trace("Ширина {} мм", w);
        logger.trace("Высота {} мм", h);

        if (!isMoreThanZero(length) || !isMoreThanZero(width) || !isMoreThanZero(height)) {
            logger.warn("one or more dimensions cannot be below Zero!");
            throw new IllegalArgumentException("one or more dimensions cannot be below Zero!");
        }
        if (dimensionIsMore(length) || dimensionIsMore(width) || dimensionIsMore(height)) {
            logger.warn("one or more dimensions exceed 1500 mm!");
            throw new IllegalArgumentException("one or more dimensions exceed 1500 mm!");
        }
        length = roundoff(length);
        width = roundoff(width);
        height = roundoff(height);
        String stL = length.toString();
        String stW = width.toString();
        String stH = height.toString();
        logger.trace("Размеры упаковки для расчётов: длина - {} мм, ширина - {} мм, высота - {} мм", stL, stW, stH);
    }

    private static boolean isMoreThanZero(BigInteger dimension) {
        return dimension.compareTo(BigInteger.ZERO) > 0;
    }
    private boolean dimensionIsMore(BigInteger dimension){
        return dimension.intValue() > 1500;
    }
}

