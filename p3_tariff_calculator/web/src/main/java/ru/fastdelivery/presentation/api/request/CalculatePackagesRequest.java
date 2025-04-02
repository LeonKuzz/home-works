package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Schema(description = "Данные для расчета стоимости доставки")
public record CalculatePackagesRequest(
        @Schema(description = "Список упаковок отправления",
                example = "[{\"weight\": 4056.45," +
                            "\"length\": 300," +
                            "\"width\": 300," +
                            "\"height\": 300}]")
        @NotNull
        @NotEmpty
        List<CargoPackage> packages,

        @Schema(description = "Трехбуквенный код валюты", example = "RUB")
        @NotNull
        String currencyCode,
        @Schema(description = "Координаты места назначения",
                example = "{\"latitude\" : 73.398660,\n" +
                        "   \"longitude\" : 55.027532\n" +
                        "  }")
        @NotNull

        DestinationInfo destinationInfo,
        @Schema(description = "Координаты места отправления",
                        example = "{\"latitude\" : 55.446008,\n" +
                        "   \"longitude\" : 65.339151\n" +
                        "  }")
        @NotNull
        DepartureInfo departureInfo

) {
}
