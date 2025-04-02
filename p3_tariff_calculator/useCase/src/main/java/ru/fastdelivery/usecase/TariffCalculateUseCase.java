package ru.fastdelivery.usecase;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.fastdelivery.domain.common.price.Price;
import ru.fastdelivery.domain.delivery.shipment.Shipment;

import javax.inject.Named;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Named
@RequiredArgsConstructor
public class TariffCalculateUseCase {
    private final WeightPriceProvider weightPriceProvider;
    private final VolumePriceProvider volumePriceProvider;
    static Logger logger = LoggerFactory.getLogger(TariffCalculateUseCase.class.getName());
//    private Price priceByWeight;
//    private Price priceByVolume;
//    private double distance;

    public Price calc(Shipment shipment) {
//        var weightAllPackagesKg = shipment.weightAllPackages().kilograms();
//        var minimalPrice = weightPriceProvider.minimalPrice();
//        var volumeAllPackagesCubicMeter = shipment.volumeAllPackages();
//        CalculateByWeight calculateByWeight = new CalculateByWeight(shipment, weightPriceProvider);
//        CalculateByVolume calculateByVolume = new CalculateByVolume(shipment, volumePriceProvider);
//        CalculatingDistance calculatingDistance = new CalculatingDistance(shipment);
//
//
        logger.info("Получены данные для расчёта:");
        logger.info(shipment.toString());
        var priceByWeight = priceByWeight(shipment);
        String byWeight = priceByWeight.toString();
        var priceByVolume = priceByVolume(shipment);
        String byVolume = priceByVolume.toString();
        var distanceKm = distance(shipment);
        String dist = String.valueOf(distanceKm);
        var basicPrice = priceByWeight.max(priceByVolume).max(minimalPrice());
        String bpA = basicPrice.amount().setScale(2, RoundingMode.HALF_UP).toString();
        String bpC = basicPrice.currency().getCode();
        logger.info("Цена доставки всего груза по весу = {}", byWeight);
        logger.info("Цена доставки всего груза по объёму = {}", byVolume);
        logger.info("Расстояние доставки = {}", dist);
        logger.info("Базоввая цена, без учёта расстояния = {} {}", bpA, bpC);

        if (distanceKm < 450) {
            Price finalPrice = basicPrice.round();
            String fp = finalPrice.toString();
            logger.info("Окончательная цена = {}", fp);
            return finalPrice;
        } else {
            Price finalPrice = basicPrice.multiply(BigDecimal.valueOf(distanceKm / 450)).round();
            String fp = finalPrice.toString();
            logger.info("Окончательная цена = {}", fp);
            return finalPrice;
        }
    }

    public Price minimalPrice() {
        return weightPriceProvider.minimalPrice();
    }

    public Price priceByWeight(Shipment shipment) {
        Price price =  new CalculateByWeight(weightPriceProvider).priceByWeight(shipment);
        return price;
    }

    public Price priceByVolume(Shipment shipment) {
        Price price = new CalculateByVolume(volumePriceProvider).priceByVolume(shipment);
        return price;
    }

    public double distance(Shipment shipment) {
        double dist = new CalculatingDistance().getDistanceFromLatLonInKm(shipment);
        return dist;
    }
}
