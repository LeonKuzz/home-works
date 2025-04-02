package ru.fastdelivery.domain.common.volume;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VolumeTest {

    @ParameterizedTest(name = "Размеры сторон в мм = {arguments} -> 1 метр в кубе")
    @CsvSource(value = {
            "1000, 1000, 1000",
            "1000, 990, 1000",
            "990, 990, 990"
    }, ignoreLeadingAndTrailingWhitespace = true)
    void whenGetCubicMeter_thenReceiveCubicMeter(int a, int b, int c) {
        var volume = new Volume(BigInteger.valueOf(a), BigInteger.valueOf(b), BigInteger.valueOf(c));
        assertEquals(new BigDecimal("1.0000"), volume.getCubicMeter());
    }
}
