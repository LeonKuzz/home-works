package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigInteger;

public record CargoPackage(
        @Schema(description = "Вес упаковки, граммы", example = "5667.45")
        BigInteger weight,
        @Schema(description = "длина упаковки, мм", example = "300")
        BigInteger length,
        @Schema(description = "ширина упаковки, мм", example = "300")
        BigInteger width,
        @Schema(description = "высота упаковки, мм", example = "300")
        BigInteger height
) {
}
