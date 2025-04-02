package ru.fastdelivery.presentation.calc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fastdelivery.domain.common.coordinate.*;
import ru.fastdelivery.domain.common.currency.CurrencyFactory;
import ru.fastdelivery.domain.common.volume.Volume;
import ru.fastdelivery.domain.common.weight.Weight;
import ru.fastdelivery.domain.delivery.pack.Pack;
import ru.fastdelivery.domain.delivery.shipment.Shipment;
import ru.fastdelivery.presentation.api.request.CalculatePackagesRequest;
import ru.fastdelivery.presentation.api.request.CargoPackage;
import ru.fastdelivery.presentation.api.request.DepartureInfo;
import ru.fastdelivery.presentation.api.request.DestinationInfo;
import ru.fastdelivery.presentation.api.response.CalculatePackagesResponse;
import ru.fastdelivery.usecase.TariffCalculateUseCase;

@RestController
@RequestMapping("/api/v1/calculate/")
@RequiredArgsConstructor
@Tag(name = "Расчеты стоимости доставки")
public class CalculateController {
    private final TariffCalculateUseCase tariffCalculateUseCase;
    private final CurrencyFactory currencyFactory;
    private final CoordinateFactory coordinateFactory;

    static Logger logger = LoggerFactory.getLogger(CalculateController.class.getName());

    @PostMapping
    @Operation(summary = "Расчет стоимости по упаковкам груза")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "400", description = "Invalid input provided")
    })
    public CalculatePackagesResponse calculate(
            @Valid @RequestBody CalculatePackagesRequest request) {
        logger.info("Получен запрос " + request.toString());
        var packList = request.packages().stream()
                .map(this::cargoPackageToPack)
                .toList();

        var destination = getDestination(request.destinationInfo());
        var departure = getDeparture(request.departureInfo());

        var shipment = new Shipment(packList, currencyFactory.create(request.currencyCode()), destination, departure);
        var calculatedPrice = tariffCalculateUseCase.calc(shipment);
        var minimalPrice = tariffCalculateUseCase.minimalPrice();
        logger.info("В ответ отправлены calculatedPrice - " + calculatedPrice.toString() + " и minimalPrice - " + minimalPrice.toString());
        return new CalculatePackagesResponse(calculatedPrice, minimalPrice);
    }
    private Pack cargoPackageToPack(CargoPackage cargoPackage) {
        Weight weight = new Weight(cargoPackage.weight());
        Volume volume = new Volume(cargoPackage.length(), cargoPackage.width(), cargoPackage.height());
        return  new Pack(weight, volume);
    }
    private Destination getDestination(DestinationInfo info) {
        Latitude latitude = coordinateFactory.createLatitude(info.latitude());
        Longitude longitude = coordinateFactory.createLongitude(info.longitude());
        return new Destination(latitude, longitude);
    }

    private Departure getDeparture(DepartureInfo info){
        Latitude latitude = coordinateFactory.createLatitude(info.latitude());
        Longitude longitude = coordinateFactory.createLongitude(info.longitude());
        return new Departure(latitude, longitude);
    }
}

