package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

@RequiredArgsConstructor
public class CalculateByWeight {

    private final WeightPriceProvider weightPriceProvider;
    static Logger logger = LoggerFactory.getLogger(CalculateByWeight.class.getName());
    public Price priceByWeight(Shipment shipment) {
        logger.trace("Расчёт цена по весу всего груза (всех упаковок");
        return weightPriceProvider
                .costPerKg()
                .multiply(shipment.weightAllPackages().kilograms())
                .max(weightPriceProvider.minimalPrice());
    }
}
