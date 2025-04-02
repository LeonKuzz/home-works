package ru.fastdelivery.presentation.api.request;

import io.swagger.v3.oas.annotations.media.Schema;

public record DestinationInfo (
        @Schema(description = "широта", example = "55.446008")
        Double latitude,
        @Schema(description = "долгота", example = "65.339151")
        Double longitude) {
}
