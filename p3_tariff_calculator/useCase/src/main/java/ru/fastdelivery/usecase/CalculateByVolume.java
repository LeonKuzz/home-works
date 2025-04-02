package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

@RequiredArgsConstructor
public class CalculateByVolume {

    private final VolumePriceProvider volumePriceProvider;
    private static Logger logger = LoggerFactory.getLogger(CalculateByVolume.class.getName());

    public Price priceByVolume(Shipment shipment) {
        logger.trace("Расчёт цены по объёму всего груза (всех упаковок)");
        return  volumePriceProvider
                .costPerCubicMeter()
                .multiply(shipment.volumeAllPackages())
                .max(volumePriceProvider.minimalPrice());
    }
}
