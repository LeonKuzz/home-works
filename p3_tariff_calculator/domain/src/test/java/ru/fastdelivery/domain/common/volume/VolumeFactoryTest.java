package ru.fastdelivery.domain.common.volume;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigInteger;

public class VolumeFactoryTest {

    @ParameterizedTest (name = "Размеры сторон в мм = {arguments} -> исключение")
    @CsvSource (value = {
            "0, 1, 1",
            "1, 0, 1",
            "1, 1, 0",
            "1, 0, 0"
    }, ignoreLeadingAndTrailingWhitespace = true)
    void whenOneOrMoreDimensionsAreLessThanOrEqualToZero_themThrowException (int a, int b, int c) {
        assertThatThrownBy(() -> new Volume(BigInteger.valueOf(a), BigInteger.valueOf(b), BigInteger.valueOf(c)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenEachDimensionsGreaterThanZero_themCreatedObject() {
        var volume = new Volume(BigInteger.ONE, BigInteger.ONE, BigInteger.ONE);
        assertNotNull(volume);
    }

    @ParameterizedTest (name = "Размеры сторон в мм = {arguments} -> исключение")
    @CsvSource (value = {
            "1501, 1, 1",
            "1, 1501, 1",
            "1, 1, 1501",
            "1, 1501, 1501"
    }, ignoreLeadingAndTrailingWhitespace = true)
    void whenOneOrMoreDimensionsAreMoreThanToLimit_themThrowException (int a, int b, int c) {
        assertThatThrownBy(() -> new Volume(BigInteger.valueOf(a), BigInteger.valueOf(b), BigInteger.valueOf(c)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void whenCreatedVolume_thenDimensionAreRoundedOff () {
        var volume = new Volume(BigInteger.valueOf(1), BigInteger.valueOf(49), BigInteger.valueOf(50));

        assertEquals(volume.length(), volume.width());
        assertEquals(volume.width(), volume.height());
    }
}
